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
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.handlers.AbstractMessageHandler;
import org.rmt2.handlers.InvalidRequestException;
import org.rmt2.jaxb.AddressBookRequest;
import org.rmt2.jaxb.AddressBookResponse;
import org.rmt2.jaxb.BusinessContactCriteria;
import org.rmt2.jaxb.BusinessType;
import org.rmt2.jaxb.ContactDetailGroup;
import org.rmt2.jaxb.ReplyStatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.InvalidDataException;
import com.api.messaging.handler.MessageHandlerResults;
import com.api.messaging.jms.handler.MessageHandlerCommandException;
import com.api.messaging.webservice.WebServiceConstants;
import com.util.assistants.Verifier;
import com.util.assistants.VerifyException;

/**
 * 
 * @author roy.terrell
 *
 */
public class BusinessContactApiHandler extends 
                  AbstractMessageHandler<AddressBookRequest, AddressBookResponse, ContactDetailGroup> {
    
    private static final Logger logger = LoggerFactory.getLogger(BusinessContactApiHandler.class);
    protected ContactsApiFactory cf;
    protected ContactsApi api;

    /**
     * @param payload
     */
    public BusinessContactApiHandler() {
        super();
        this.responseObj = jaxbObjFactory.createAddressBookResponse();
        this.cf = new ContactsApiFactory();
        this.api = cf.createApi();
        logger.info(BusinessContactApiHandler.class.getName() + " was instantiated successfully");
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
            case ApiTransactionCodes.CONTACTS_BUSINESS_UPDATE:
                 r = this.updateBusinessContact(this.requestObj);
                break;
            case ApiTransactionCodes.CONTACTS_BUSINESS_DELETE:
                // r = this.deleteBusinessContact(this.request);
                break;
            case ApiTransactionCodes.CONTACTS_BUSINESS_GET:
                r = this.fetchBusinessContact(this.requestObj);
                break;
        }
        return r;
    }

    protected MessageHandlerResults fetchBusinessContact(AddressBookRequest obj) {
        MessageHandlerResults results = new MessageHandlerResults();
        ReplyStatusType rs = jaxbObjFactory.createReplyStatusType();
        BusinessContactDto criteriaDto = null;
        ContactDetailGroup cdg = jaxbObjFactory.createContactDetailGroup();

        try {
            criteriaDto = this.extractBusinessContactCriteria(obj);
            ContactsApiFactory cf = new ContactsApiFactory();
            ContactsApi api = cf.createApi();
            List<ContactDto> dtoList = api.getContact(criteriaDto);
            if (dtoList == null) {
                rs.setMessage("Businsess contact data not found!");
                rs.setReturnCode(BigInteger.valueOf(0));
                cdg = null;
            }
            else {
                ContactsJaxbFactory cjf = new ContactsJaxbFactory();
                List<BusinessType> jaxbList = cjf.createBusinessTypeInstance(dtoList);
                cdg.getBusinessContacts().addAll(jaxbList);
                rs.setMessage("Businsess contact record(s) found");
                rs.setReturnCode(BigInteger.valueOf(dtoList.size()));
            }
            this.responseObj.setHeader(obj.getHeader());
            // Set reply status
            rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_SUCCESS);
        } catch (Exception e) {
            rs.setReturnCode(BigInteger.valueOf(-1));
            rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_ERROR);
            rs.setMessage("Failure to retrieve business contact(s)");
            rs.setExtMessage(e.getMessage());
            cdg = null;
        }
        String xml = this.buildResponse(cdg, rs);
        results.setPayload(xml);
        return results;
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
        ContactDetailGroup cdg = null;
        BusinessContactDto contactDto = null;
        contactDto = this.extractBusinessContact(req);
        boolean newContact = (contactDto.getContactId() == 0);
        ContactsApiFactory cf = new ContactsApiFactory();
        ContactsApi api = cf.createApi();
        int rc = 0;
        try {
            // call api
            rc = api.updateContact(contactDto);
            
            // prepare response with updated contact data
            ContactsJaxbFactory cjf = new ContactsJaxbFactory();
            BusinessType busType = cjf.createBusinessTypeInstance(contactDto);
            cdg = jaxbObjFactory.createContactDetailGroup();
            cdg.getBusinessContacts().add(busType);
            
            // Return code is either the total number of rows updated or the business id of the contact created
            rs.setReturnCode(BigInteger.valueOf(rc));
            rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_SUCCESS);
            if (newContact) {
                rs.setMessage("Business contact was created successfully");
                rs.setExtMessage("The new business contact id is " + rc);
            }
            else {
                rs.setMessage("Business contact was modified successfully");
                rs.setExtMessage("Total number of rows modified: " + rc);
            }
        } catch (ContactsApiException e) {
            rs.setReturnCode(BigInteger.valueOf(-1));
            rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_ERROR);
            rs.setMessage("Failure to update " + (newContact ? "new " : "existing ")  + " business contact");
            rs.setExtMessage(e.getMessage());
            cdg = req.getProfile();
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
   private BusinessContactDto extractBusinessContactCriteria(AddressBookRequest req) {
       try {
           this.validateBusinessContactCriteria(req);    
       }
       catch (Exception e) {
           return null;
       }
       BusinessContactCriteria bcc = req.getCriteria().getBusinessCriteria();
       BusinessContactDto dto = JaxbAddressBookFactory.createBusinessContactDtoInstance(bcc);
       return dto;
   }

   private BusinessContactDto extractBusinessContact(AddressBookRequest req) {
       this.validateBusinessContacts(req);
       BusinessType contact = req.getProfile().getBusinessContacts().get(0);
       BusinessContactDto dto = JaxbAddressBookFactory.createBusinessContactDtoInstance(contact);
       return dto;
   }
   
    protected void validateBusinessContactCriteria(AddressBookRequest req) {
        try {
            Verifier.verifyNotNull(req.getCriteria());
        }
        catch (VerifyException e) {
            throw new InvalidRequestBusinessProfileCriteriaException(
                    "AddressBook message request business contact criteria group element is invalid or null");
        }

        try {
            Verifier.verifyNotNull(req.getCriteria().getBusinessCriteria());
        }
        catch (VerifyException e) {
            throw new InvalidRequestBusinessProfileCriteriaException(
                    "AddressBook message request business contact criteria element is null");
        }
    }
    
    /**
     * Validates the request's business contacts.
     */
    protected void validateBusinessContacts(AddressBookRequest req) {
        List<BusinessType> contacts = req.getProfile().getBusinessContacts();
        try {
            Verifier.verifyNotNull(contacts);
        }
        catch (VerifyException e) {
            throw new InvalidRequestBusinessProfileException(
                    "AddressBook message request business contact(s) element is invalid or null");
        }
        
        try {
            Verifier.verifyFalse(contacts.size() == 0);
        }
        catch (VerifyException e) {
            throw new NoContactProfilesAvailableException(
                    "AddressBook message request's Business Contacts element is valid, but does not contain a business contact record");
        }

        try {
            Verifier.verifyFalse(contacts.size() > 1);
        }
        catch (VerifyException e) {
            throw new TooManyContactProfilesException("Too many contacts were available for update operation");
        }
    }
    
    @Override
    protected void validdateRequest(AddressBookRequest req) throws InvalidDataException {
        try {
            Verifier.verifyNotNull(req);
        }
        catch (VerifyException e) {
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
}
