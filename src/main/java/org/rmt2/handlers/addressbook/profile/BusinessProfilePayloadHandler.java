package org.rmt2.handlers.addressbook.profile;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.dto.BusinessContactDto;
import org.dto.ContactDto;
import org.dto.adapter.jaxb.JaxbAddressBookFactory;
import org.dto.converter.jaxb.ContactsJaxbFactory;
import org.modules.contacts.ContactsApi;
import org.modules.contacts.ContactsApiFactory;
import org.rmt2.jaxb.AddressBookRequest;
import org.rmt2.jaxb.AddressBookResponse;
import org.rmt2.jaxb.BusinessContactCriteria;
import org.rmt2.jaxb.BusinessType;
import org.rmt2.jaxb.ContactDetailGroup;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.ReplyStatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.messaging.handler.MessageHandlerResults;
import com.api.messaging.jms.handler.MessageHandlerCommandException;
import com.api.messaging.webservice.WebServiceConstants;

/**
 * @author appdev
 *
 */
public class BusinessProfilePayloadHandler extends AbstractProfilePayloadHandler {
    private static final Logger logger = LoggerFactory.getLogger(BusinessProfilePayloadHandler.class);

    private static final String TRANS_ADD = "addressbook.profile.ADD_BUSINESS_CONTACT";
    private static final String TRANS_UPDATE = "addressbook.profile.UPDATE_BUSINESS_CONTACT";
    private static final String TRANS_DELETE = "addressbook.profile.DELETE_BUSINESS_CONTACT";
    private static final String TRANS_FETCH_ALL = "addressbook.profile.GET_ALL_BUSINESS_CONTACTS";
    private static final String TRANS_FETCH_ONE = "addressbook.profile.GET_BUSINESS_CONTACT";
    private static final String TRANS_FETCH_CRITERIA = "addressbook.profile.GET_BUSINESS_CONTACTS_USING_CRITERIA";

    /**
     * @param payload
     */
    public BusinessProfilePayloadHandler() {
        super();
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
    public MessageHandlerResults processMessage(String command, Serializable payload)
            throws MessageHandlerCommandException {
        MessageHandlerResults r = super.processMessage(command, payload);

        if (r != null) {
            return r;
        }

        switch (command) {
            case BusinessProfilePayloadHandler.TRANS_ADD:
                // r = this.addBusinessContact(this.request);
                break;
            case BusinessProfilePayloadHandler.TRANS_UPDATE:
                // r = this.updateBusinessContact(this.request);
                break;
            case BusinessProfilePayloadHandler.TRANS_DELETE:
                // r = this.deleteBusinessContact(this.request);
                break;
            case BusinessProfilePayloadHandler.TRANS_FETCH_ONE:
                r = this.fetchBusinessContact(this.request);
                break;
            case BusinessProfilePayloadHandler.TRANS_FETCH_ALL:
                // r = this.fetchBusinessContact(this.request);
                break;
            case BusinessProfilePayloadHandler.TRANS_FETCH_CRITERIA:
                // r = this.fetchBusinessContact(this.request);
                break;
        }
        return r;
    }

    protected MessageHandlerResults fetchBusinessContact(AddressBookRequest obj) {
        MessageHandlerResults results = new MessageHandlerResults();
        ObjectFactory f = new ObjectFactory();
        ReplyStatusType rs = f.createReplyStatusType();
        AddressBookResponse response = f.createAddressBookResponse();
        BusinessContactDto dto = null;
        BusinessContactCriteria contact = null;

        try {
            contact = this.getBusinessContactCriteria(obj);
            dto = JaxbAddressBookFactory.createBusinessContactDtoInstance(contact);
            ContactsApiFactory cf = new ContactsApiFactory();
            ContactsApi api = cf.createApi();
            List<ContactDto> dtoList = api.getContact(dto);
            if (dtoList == null) {
                rs.setMessage("Businsess contact records not found!");
                ContactDetailGroup cdg = f.createContactDetailGroup();
                // JAXBElement<ContactDetailGroup> detailGrp =
                // f.createAddressBookRequestProfile(cdg);
                // response.setProfile(detailGrp);
            }
            else {
                ContactsJaxbFactory cjf = new ContactsJaxbFactory();
                List<BusinessType> jaxbList = cjf.createBusinessTypeInstance(dtoList);
                ContactDetailGroup cdg = f.createContactDetailGroup();
                cdg.getBusinessContacts().addAll(jaxbList);
                // JAXBElement<ContactDetailGroup> detailGrp =
                // f.createAddressBookRequestProfile(cdg);
                // response.setProfile(detailGrp);
                rs.setMessage(dtoList.size() + " Businsess contact records found");
            }
            response.setHeader(obj.getHeader());
            // Set reply status
            rs.setReturnCode(BigInteger.valueOf(0));
            rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_SUCCESS);
            response.setReplyStatus(rs);
        } catch (Exception e) {
            rs.setReturnCode(BigInteger.valueOf(1));
            rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_ERROR);
            rs.setMessage("Failure to retrieve business contact(s): " + e.getMessage());
            response.setReplyStatus(rs);
        }

        String xml = this.jaxb.marshalMessage(response);
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
    //
    // /**
    // *
    // */
    // protected void validateBusinessContacts(AddressBookRequest req) {
    // List<BusinessType> contacts =
    // req.getProfile().getValue().getBusinessContacts();
    // if (contacts == null) {
    // throw new InvalidRequestBusinessContactsException(
    // "AddressBook message request business contact(s) element is invalid or
    // null");
    // }
    // if (contacts.size() == 0) {
    // throw new NoContactsAvailableException(
    // "AddressBook message request's Business Contacts element is valid, but
    // does not contain a business contact record");
    // }
    // if (contacts.size() > 1) {
    // throw new TooManyContactsException("Too many contacts were available for
    // update operation");
    // }
    // }
    //

    //
    // /**
    // * Returns the list of business contacts from the profile element.
    // * <p>
    // * Prior to accessing the list business contacts, this method validates
    // the
    // * parent nodes of the businsess contacts request element.
    // *
    // * @param req
    // * an instance of {@link AddressBookRequest}
    // * @return List {@link BusinessType}
    // * @throws InvalidRequestException
    // * @throws InvalidRequestProfileException
    // * @throws InvalidRequestBusinessContactsException
    // */
    // protected List<BusinessType> getBusinessContacts(AddressBookRequest req)
    // {
    // this.validateBusinessContacts(req);
    // return req.getProfile().getValue().getBusinessContacts();
    // }
    //
    // protected MessageHandlerResults fetchBusinessContact(AddressBookRequest
    // obj) {
    // MessageHandlerResults results = new MessageHandlerResults();
    // ObjectFactory f = new ObjectFactory();
    // ReplyStatusType rs = f.createReplyStatusType();
    // AddressBookResponse response = f.createAddressBookResponse();
    // BusinessContactDto dto = null;
    // BusinessContactCriteria contact = null;
    //
    // try {
    // contact = this.getBusinessContactCriteria(obj);
    // dto = JaxbAddressBookFactory.createBusinessContactDtoInstance(contact);
    // ContactsApiFactory cf = new ContactsApiFactory();
    // ContactsApi api = cf.createApi();
    // List<ContactDto> dtoList = api.getContact(dto);
    // if (dtoList == null) {
    // rs.setMessage("Businsess contact records not found!");
    // ContactDetailGroup cdg = f.createContactDetailGroup();
    // JAXBElement<ContactDetailGroup> detailGrp =
    // f.createAddressBookRequestProfile(cdg);
    // response.setProfile(detailGrp);
    // }
    // else {
    // ContactsJaxbFactory cjf = new ContactsJaxbFactory();
    // List<BusinessType> jaxbList = cjf.createBusinessTypeInstance(dtoList);
    // ContactDetailGroup cdg = f.createContactDetailGroup();
    // cdg.getBusinessContacts().addAll(jaxbList);
    // JAXBElement<ContactDetailGroup> detailGrp =
    // f.createAddressBookRequestProfile(cdg);
    // response.setProfile(detailGrp);
    // rs.setMessage(dtoList.size() + " Businsess contact records found");
    // }
    // response.setHeader(obj.getHeader());
    // // Set reply status
    // rs.setReturnCode(BigInteger.valueOf(0));
    // rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_SUCCESS);
    // response.setReplyStatus(rs);
    // } catch (Exception e) {
    // rs.setReturnCode(BigInteger.valueOf(1));
    // rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_ERROR);
    // rs.setMessage("Failure to retrieve business contact(s): " +
    // e.getMessage());
    // response.setReplyStatus(rs);
    // }
    //
    // String xml = this.jaxb.marshalMessage(response);
    // results.setPayload(xml);
    // return results;
    // }

    /**
     * 
     * @param obj
     * @return
     */
    // protected MessageHandlerResults addBusinessContact(AddressBookRequest
    // obj) {
    // MessageHandlerResults results = new MessageHandlerResults();
    // ObjectFactory f = new ObjectFactory();
    // ReplyStatusType rs = f.createReplyStatusType();
    // AddressBookResponse response = f.createAddressBookResponse();
    // BusinessContactDto dto = null;
    // BusinessType contact = null;
    // ContactsApi api = null;
    // try {
    // contact = this.getBusinessContacts(this.request).get(0);
    // dto = JaxbAddressBookFactory.createBusinessContactDtoInstance(contact);
    // ContactsApiFactory cf = new ContactsApiFactory();
    // api = cf.createApi();
    // int key = api.updateContact(dto);
    // contact.setBusinessId(BigInteger.valueOf(key));
    //
    // response.setHeader(obj.getHeader());
    // // Set reply status
    // rs.setReturnCode(BigInteger.valueOf(0));
    // rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_SUCCESS);
    // rs.setMessage("Businsess contact was successfully added!");
    // response.setReplyStatus(rs);
    // } catch (Exception e) {
    // rs.setReturnCode(BigInteger.valueOf(1));
    // rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_ERROR);
    // rs.setMessage("Failure to create business contact: " + e.getMessage());
    // response.setReplyStatus(rs);
    // } finally {
    //
    // }
    // response.setProfile(obj.getProfile());
    //
    // String xml = this.jaxb.marshalMessage(response);
    // results.setPayload(xml);
    // return results;
    // }
    //
    // protected MessageHandlerResults updateBusinessContact(AddressBookRequest
    // obj) {
    // MessageHandlerResults results = new MessageHandlerResults();
    // ObjectFactory f = new ObjectFactory();
    // ReplyStatusType rs = f.createReplyStatusType();
    // AddressBookResponse response = f.createAddressBookResponse();
    // BusinessContactDto dto = null;
    // BusinessType contact = null;
    //
    // try {
    // contact = this.getBusinessContacts(this.request).get(0);
    // dto = JaxbAddressBookFactory.createBusinessContactDtoInstance(contact);
    //
    // // Call generic operation to update business contact.
    // this.updateContact(dto);
    //
    // response.setHeader(obj.getHeader());
    // // Set reply status
    // rs.setReturnCode(BigInteger.valueOf(0));
    // rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_SUCCESS);
    // rs.setMessage("Businsess contact was successfully updated!");
    // response.setReplyStatus(rs);
    // } catch (Exception e) {
    // rs.setReturnCode(BigInteger.valueOf(1));
    // rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_ERROR);
    // rs.setMessage("Failure to update business contact: " + e.getMessage());
    // response.setReplyStatus(rs);
    // }
    // response.setProfile(obj.getProfile());
    //
    // String xml = this.jaxb.marshalMessage(response);
    // results.setPayload(xml);
    // return results;
    // }
    //
    // protected MessageHandlerResults deleteBusinessContact(AddressBookRequest
    // obj) {
    // MessageHandlerResults results = new MessageHandlerResults();
    // ObjectFactory f = new ObjectFactory();
    // ReplyStatusType rs = f.createReplyStatusType();
    // AddressBookResponse response = f.createAddressBookResponse();
    // BusinessContactDto dto = null;
    // BusinessType contact = null;
    //
    // try {
    // contact = this.getBusinessContacts(this.request).get(0);
    // dto = JaxbAddressBookFactory.createBusinessContactDtoInstance(contact);
    // ContactsApiFactory cf = new ContactsApiFactory();
    // ContactsApi api = cf.createApi();
    // int rc = api.deleteContact(dto);
    //
    // response.setHeader(obj.getHeader());
    // // Set reply status
    // rs.setReturnCode(BigInteger.valueOf(0));
    // rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_SUCCESS);
    // rs.setMessage("Businsess contact was successfully deleted!");
    // response.setReplyStatus(rs);
    // } catch (Exception e) {
    // rs.setReturnCode(BigInteger.valueOf(1));
    // rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_ERROR);
    // rs.setMessage("Failure to delete business contact: " + e.getMessage());
    // response.setReplyStatus(rs);
    // }
    // response.setProfile(obj.getProfile());
    //
    // String xml = this.jaxb.marshalMessage(response);
    // results.setPayload(xml);
    // return results;
    // }
}
