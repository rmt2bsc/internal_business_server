package org.rmt2.handlers.addressbook.profile;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.dto.BusinessContactDto;
import org.dto.ContactDto;
import org.dto.adapter.jaxb.JaxbAddressBookFactory;
import org.dto.converter.jaxb.ContactsJaxbFactory;
import org.modules.contacts.ContactsApi;
import org.modules.contacts.ContactsApiException;
import org.modules.contacts.ContactsApiFactory;
import org.rmt2.handlers.AbstractMessageHandler;
import org.rmt2.handlers.InvalidRequestException;
import org.rmt2.jaxb.AddressBookRequest;
import org.rmt2.jaxb.AddressBookResponse;
import org.rmt2.jaxb.BusinessContactCriteria;
import org.rmt2.jaxb.BusinessType;
import org.rmt2.jaxb.ContactDetailGroup;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.ReplyStatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.InvalidDataException;
import com.api.messaging.handler.MessageHandlerResults;
import com.api.messaging.jms.handler.MessageHandlerCommandException;
import com.api.messaging.webservice.WebServiceConstants;

/**
 * 
 * @author roy.terrell
 *
 */
public class BusinessProfilePayloadHandler extends
        AbstractMessageHandler<AddressBookRequest, AddressBookResponse, ContactDetailGroup> {
    private static final Logger logger = LoggerFactory.getLogger(BusinessProfilePayloadHandler.class);

    private static final String TRANS_ADD = "addressbook.profile.ADD_BUSINESS_CONTACT";
    private static final String TRANS_UPDATE = "addressbook.profile.UPDATE_BUSINESS_CONTACT";
    private static final String TRANS_DELETE = "addressbook.profile.DELETE_BUSINESS_CONTACT";
    private static final String TRANS_FETCH_ALL = "addressbook.profile.GET_ALL_BUSINESS_CONTACTS";
    private static final String TRANS_FETCH_ONE = "addressbook.profile.GET_BUSINESS_CONTACT";
    private static final String TRANS_FETCH_CRITERIA = "addressbook.profile.GET_BUSINESS_CONTACTS_USING_CRITERIA";
    
    protected ContactsApiFactory cf;
    protected ContactsApi api;

    /**
     * @param payload
     */
    public BusinessProfilePayloadHandler() {
        super();
        this.responseObj = jaxbObjFactory.createAddressBookResponse();
        this.cf = new ContactsApiFactory();
        this.api = cf.createApi();
        logger.info(BusinessProfilePayloadHandler.class.getName() + " was instantiated successfully");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.api.messaging.jms.handler.AbstractMessageHandler#processRequest(java
     * .lang.String, java.io.Serializable)
     */
    @Override
    public MessageHandlerResults processMessage(String command, Serializable payload) throws MessageHandlerCommandException {
        MessageHandlerResults r = super.processMessage(command, payload);

        if (r != null) {
            // This means an error occurred.
            return r;
        }

        
        switch (command) {
            case BusinessProfilePayloadHandler.TRANS_ADD:
                // r = this.addBusinessContact(this.request);
                break;
            case BusinessProfilePayloadHandler.TRANS_UPDATE:
                 r = this.updateBusinessContact(this.requestObj);
                break;
            case BusinessProfilePayloadHandler.TRANS_DELETE:
                // r = this.deleteBusinessContact(this.request);
                break;
            case BusinessProfilePayloadHandler.TRANS_FETCH_ONE:
                r = this.fetchBusinessContact(this.requestObj);
                break;
            case BusinessProfilePayloadHandler.TRANS_FETCH_ALL:
                // r = this.fetchBusinessContact(this.request);
                break;
            case BusinessProfilePayloadHandler.TRANS_FETCH_CRITERIA:
                 r = this.fetchBusinessContact(this.requestObj);
                break;
        }
        return r;
    }

    protected MessageHandlerResults fetchBusinessContact(AddressBookRequest obj) {
        MessageHandlerResults results = new MessageHandlerResults();
        ObjectFactory f = new ObjectFactory();
        ReplyStatusType rs = f.createReplyStatusType();
//        AddressBookResponse response = f.createAddressBookResponse();
        BusinessContactDto dto = null;
        BusinessContactCriteria contact = null;
        ContactDetailGroup cdg = f.createContactDetailGroup();

        try {
            contact = this.getBusinessContactCriteria(obj);
            dto = JaxbAddressBookFactory.createBusinessContactDtoInstance(contact);
            ContactsApiFactory cf = new ContactsApiFactory();
            ContactsApi api = cf.createApi();
            List<ContactDto> dtoList = api.getContact(dto);
            if (dtoList == null) {
                rs.setMessage("Businsess contact records not found!");
//                ContactDetailGroup cdg = f.createContactDetailGroup();
                // JAXBElement<ContactDetailGroup> detailGrp =
                // f.createAddressBookRequestProfile(cdg);
                // response.setProfile(detailGrp);
            }
            else {
                ContactsJaxbFactory cjf = new ContactsJaxbFactory();
                List<BusinessType> jaxbList = cjf.createBusinessTypeInstance(dtoList);
//                ContactDetailGroup cdg = f.createContactDetailGroup();
                cdg.getBusinessContacts().addAll(jaxbList);
                // JAXBElement<ContactDetailGroup> detailGrp =
                // f.createAddressBookRequestProfile(cdg);
                // response.setProfile(detailGrp);
//                response.setProfile(cdg);
                rs.setMessage(dtoList.size() + " Businsess contact records found");
            }
            this.responseObj.setHeader(obj.getHeader());
            // Set reply status
            rs.setReturnCode(BigInteger.valueOf(0));
            rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_SUCCESS);
//            response.setReplyStatus(rs);
        } catch (Exception e) {
            rs.setReturnCode(BigInteger.valueOf(1));
            rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_ERROR);
            rs.setMessage("Failure to retrieve business contact(s): " + e.getMessage());
//            response.setReplyStatus(rs);
        }
        String xml = this.buildResponse(cdg, rs);
        results.setPayload(xml);
        return results;
    }

    /**
     *
     * @param req
     * @return
     */
    protected BusinessContactCriteria getBusinessContactCriteria(AddressBookRequest req) {
        this.validateBusinessContactCriteria(req);
        return req.getCriteria().getBusinessCriteria();
    }

    /**
     * Updates a given contact by invoking the ContactsApi.
     * <p>
     * This method is capable of processing personal, business, or generic
     * contact types.
     * 
     * @param req
     *            The request used to build the ContactDto
     * @throws ContactUpdateDaoException
     */
    protected MessageHandlerResults updateBusinessContact(AddressBookRequest req) {
        MessageHandlerResults results = new MessageHandlerResults();
        ReplyStatusType rs = jaxbObjFactory.createReplyStatusType();
        ContactDto contactDto = null;
        ContactsApiFactory cf = new ContactsApiFactory();
        ContactsApi api = cf.createApi();
        try {
            api.updateContact(contactDto);
        } catch (ContactsApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        String xml = this.buildResponse(null, rs);
        results.setPayload(xml);
        return results;
    }
    
    protected void validateBusinessContactCriteria(AddressBookRequest req) {
        if (req.getCriteria() == null || req.getCriteria().getBusinessCriteria() == null) {
            throw new InvalidRequestBusinessProfileCriteriaException(
                    "AddressBook message request business contact criteria group element is invalid or null");
        }
        BusinessContactCriteria criteria = req.getCriteria().getBusinessCriteria();
        if (criteria == null) {
            throw new InvalidRequestBusinessProfileCriteriaException(
                    "AddressBook message request business contact criteria element is null");
        }
    }
    
    @Override
    protected void validdateRequest(AddressBookRequest req) throws InvalidDataException {
        if (req == null) {
            throw new InvalidRequestException("AddressBook message request element is invalid");
        }
    }

    @Override
    protected String buildResponse(ContactDetailGroup payload,  ReplyStatusType replyStatus) {
        if (replyStatus != null) {
            this.responseObj.setReplyStatus(replyStatus);    
        }
        
        if (payload != null) {
            this.responseObj.setProfile((ContactDetailGroup) payload);
        }
        
        String xml = this.jaxb.marshalMessage(this.responseObj);
        return xml;
    }
    
    
    
    /**
     *
     */
    protected void validateBusinessContacts(AddressBookRequest req) {
        List<BusinessType> contacts = req.getProfile().getBusinessContacts();
        if (contacts == null) {
            throw new InvalidRequestBusinessProfileException(
                    "AddressBook message request business contact(s) element is invalid or null");
        }
        if (contacts.size() == 0) {
            throw new NoContactProfilesAvailableException(
                    "AddressBook message request's Business Contacts element is valid, but does not contain a business contact record");
        }
        if (contacts.size() > 1) {
            throw new TooManyContactProfilesException(
                    "Too many contacts were available for update operation");
        }
    }
    

    
}
