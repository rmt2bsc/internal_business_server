package org.rmt2.handlers.addressbook.profile;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dto.BusinessContactDto;
import org.dto.ContactDto;
import org.dto.PersonalContactDto;
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
import org.rmt2.jaxb.CommonContactCriteria;
import org.rmt2.jaxb.CommonContactType;
import org.rmt2.jaxb.ContactCriteriaGroup;
import org.rmt2.jaxb.ContactDetailGroup;
import org.rmt2.jaxb.PersonContactCriteria;
import org.rmt2.jaxb.PersonType;
import org.rmt2.jaxb.ReplyStatusType;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.InvalidDataException;
import com.api.messaging.handler.MessageHandlerResults;
import com.api.messaging.jms.handler.MessageHandlerCommandException;
import com.api.messaging.webservice.WebServiceConstants;
import com.api.util.assistants.Verifier;
import com.api.util.assistants.VerifyException;

/**
 * 
 * @author roy.terrell
 *
 */
public class ContactProfileApiHandler extends 
                  AbstractMessageHandler<AddressBookRequest, AddressBookResponse, ContactDetailGroup> {
    
    private static final Logger logger = Logger.getLogger(ContactProfileApiHandler.class);
    protected ContactsApiFactory cf;
    protected ContactsApi api;

    /**
     * @param payload
     */
    public ContactProfileApiHandler() {
        super();
        this.responseObj = jaxbObjFactory.createAddressBookResponse();
        this.cf = new ContactsApiFactory();
        this.api = cf.createApi();
        logger.info(ContactProfileApiHandler.class.getName() + " was instantiated successfully");
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
            case ApiTransactionCodes.CONTACTS_UPDATE:
                 r = this.updateContact(this.requestObj);
                break;
            case ApiTransactionCodes.CONTACTS_DELETE:
                // r = this.deleteBusinessContact(this.request);
                break;
            case ApiTransactionCodes.CONTACTS_GET:
                r = this.fetchContact(this.requestObj);
                break;
        }
        return r;
    }

    /**
     * Handler for invoking the appropriate API in order to fetch one or more contacts.
     * <p>
     * This method is capable of processing personal, business, or generic
     * contact types.
     * 
     * @param req
     *            The request used to build the ContactDto selection criteria
     * @return an instance of {@link MessageHandlerResults}           
     */
    protected MessageHandlerResults fetchContact(AddressBookRequest req) {
        MessageHandlerResults results = new MessageHandlerResults();
        ReplyStatusType rs = jaxbObjFactory.createReplyStatusType();
        ContactDetailGroup cdg = null;

        try {
            this.validateRequest(req);
            Object generiicCriteriaObj = this.validateSelectionCriteria(req.getCriteria());
            ContactDto criteriaDto = this.extractSelectionCriteria(generiicCriteriaObj);
            
            ContactsApiFactory cf = new ContactsApiFactory();
            ContactsApi api = cf.createApi();
            List<ContactDto> dtoList = api.getContact(criteriaDto);
            if (dtoList == null) {
                rs.setMessage("Contact data not found!");
                rs.setReturnCode(BigInteger.valueOf(0));
            }
            else {
                cdg = this.buildQueryResults(dtoList);
                rs.setMessage("Contact record(s) found");
                rs.setReturnCode(BigInteger.valueOf(dtoList.size()));
            }
            this.responseObj.setHeader(req.getHeader());
            // Set reply status
            rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_SUCCESS);
        } catch (Exception e) {
            rs.setReturnCode(BigInteger.valueOf(-1));
            rs.setReturnStatus(WebServiceConstants.RETURN_STATUS_ERROR);
            rs.setMessage("Failure to retrieve contact(s)");
            rs.setExtMessage(e.getMessage());
        }
        String xml = this.buildResponse(cdg, rs);
        results.setPayload(xml);
        return results;
    }
    
    /**
     * Handler for invoking the appropriate API in order to update the specified contact.
     * <p>
     * This method is capable of processing personal, business, or generic
     * contact types.
     * 
     * @param req
     *            The request used to build the ContactDto selection criteria
     * @return an instance of {@link MessageHandlerResults}           
     */
    protected MessageHandlerResults updateContact(AddressBookRequest req) {
        MessageHandlerResults results = new MessageHandlerResults();
        ReplyStatusType rs = jaxbObjFactory.createReplyStatusType();
        ContactDetailGroup cdg = null;
        BusinessContactDto contactDto = null;
        contactDto = this.extractContactObject(req);
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
    
    private ContactDetailGroup buildQueryResults(List<ContactDto> results) {
        ContactDetailGroup cdg = jaxbObjFactory.createContactDetailGroup();
        ContactsJaxbFactory cjf = new ContactsJaxbFactory();
        
        for (ContactDto contact : results) {
            if (contact instanceof BusinessContactDto) {
                BusinessType jaxbObj = cjf.createBusinessTypeInstance(contact);
                cdg.getBusinessContacts().add(jaxbObj);
            } else if (contact instanceof PersonalContactDto) {
                PersonType jaxbObj = cjf.createPersonalTypeInstance(contact);
                cdg.getPersonContacts().add(jaxbObj);
            } else {
                CommonContactType jaxbObj = cjf.createCommonContactTypeInstance(contact);
                cdg.getCommonContacts().add(jaxbObj);
            }
        }
        return cdg;
    }
   /**
    * 
    * @param criteriaObj
    * @return
    */
   private ContactDto extractSelectionCriteria(Object criteriaObj) {
       ContactDto dto = null;
       if (criteriaObj instanceof BusinessContactCriteria) {
           BusinessContactCriteria bcc = (BusinessContactCriteria) criteriaObj;
           dto = JaxbAddressBookFactory.createBusinessContactDtoInstance(bcc);
       }
       if (criteriaObj instanceof PersonContactCriteria) {
           PersonContactCriteria pcc = (PersonContactCriteria) criteriaObj;
           dto = JaxbAddressBookFactory.createPersonContactDtoInstance(pcc);
       }
       if (criteriaObj instanceof CommonContactCriteria) {
           CommonContactCriteria ccc = (CommonContactCriteria) criteriaObj;
           dto = JaxbAddressBookFactory.createContactDtoInstance(ccc);
       }
       
       return dto;
   }

   private BusinessContactDto extractContactObject(AddressBookRequest req) {
       this.validateBusinessContacts(req);
       BusinessType contact = req.getProfile().getBusinessContacts().get(0);
       BusinessContactDto dto = JaxbAddressBookFactory.createBusinessContactDtoInstance(contact);
       return dto;
   }
   
    private Object validateSelectionCriteria(ContactCriteriaGroup criteriaGroup) {
        try {
            Verifier.verifyNotNull(criteriaGroup);
        }
        catch (VerifyException e) {
            throw new InvalidRequestContactCriteriaException(
                    "AddressBook contact query request is rquired to have a criteria group element");
        }

        // Use a hashtable to assist in ContactCriteriaGroup validations since
        // hashtable only allows non-null keys and values.
        Map<Object, Object> criteriaHash = new Hashtable<>();
        this.addCriteriaObjectToHashTable(criteriaHash, criteriaGroup.getBusinessCriteria());
        this.addCriteriaObjectToHashTable(criteriaHash, criteriaGroup.getCommonCriteria());
        this.addCriteriaObjectToHashTable(criteriaHash, criteriaGroup.getPersonCriteria());
        
        // ContactCriteriaGroup is required to have one and only one criteria
        // object available.
        try {
            Verifier.verifyTrue(!criteriaHash.isEmpty() && criteriaHash.size() == 1);
        } catch (VerifyException e) {
            throw new InvalidRequestContactCriteriaException(
                    "AddressBook ContactCriteriaGroup is required to have one and only one criteria object that is of type either personal, business, or common");
        }
        
        // If we arrived here, that means there must be one and only one criteria object available
        return criteriaHash.keySet().iterator().next();
    }
    
    private void addCriteriaObjectToHashTable(Map hash, Object criteriaObj) {
        try {
            hash.put(criteriaObj, criteriaObj);    
        }
        catch (NullPointerException e) {
            //Do nothing
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
            throw new InvalidRequestContactProfileException(
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
    protected void validateRequest(AddressBookRequest req) throws InvalidDataException {
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
