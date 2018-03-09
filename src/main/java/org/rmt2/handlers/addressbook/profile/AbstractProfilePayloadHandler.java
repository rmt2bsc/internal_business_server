package org.rmt2.handlers.addressbook.profile;

import java.io.Serializable;

import org.dto.BusinessContactDto;
import org.dto.ContactDto;
import org.modules.contacts.ContactsApi;
import org.modules.contacts.ContactsApiException;
import org.modules.contacts.ContactsApiFactory;
import org.rmt2.handlers.AbstractMessageHandler;
import org.rmt2.handlers.InvalidRequestException;
import org.rmt2.jaxb.AddressBookRequest;
import org.rmt2.jaxb.AddressBookResponse;
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
public abstract class AbstractProfilePayloadHandler extends AbstractMessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(AbstractProfilePayloadHandler.class);

    protected ObjectFactory f;
    protected BusinessContactDto dto;
    protected AddressBookRequest request;
    protected AddressBookResponse response;

    /**
     * @param payload
     */
    public AbstractProfilePayloadHandler() {
        super();
        this.f = new ObjectFactory();
        logger.info(AbstractProfilePayloadHandler.class.getName() + " was instantiated successfully");
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
        super.processMessage(command, payload);

        MessageHandlerResults results = null;

        // Unmarshall XML String
        String data = this.getPayloadAsString();
        AddressBookRequest req = (AddressBookRequest) this.jaxb.unMarshalMessage(data);

        try {
            // this.validateProfile(req);
            this.request = req;
            this.response = f.createAddressBookResponse();

        } catch (Exception e) {
            ReplyStatusType rs = null;
            rs = this.createReplyStatus(1, WebServiceConstants.RETURN_STATUS_ERROR, e.getMessage());
            AddressBookResponse resp = f.createAddressBookResponse();
            resp.setReplyStatus(rs);
            resp.setProfile(req.getProfile());
            results = new MessageHandlerResults();
            String xml = this.jaxb.marshalMessage(resp);
            results.setPayload(xml);
        }
        return results;
    }

    /**
     * 
     */
    protected void validdateRequest(AddressBookRequest req) {
        if (req == null) {
            throw new InvalidRequestException("AddressBook message request element is invalid");
        }
    }

    /**
     * 
     */
    // protected void validateProfile(AddressBookRequest req) {
    // this.validdateRequest(req);
    // if (req.getProfile() == null || req.getProfile().getValue() == null) {
    // if (req.getCriteria() == null || req.getCriteria().getValue() == null) {
    // throw new InvalidRequestProfileException(
    // "AddressBook message request profile and criteria elements are invalid or
    // null");
    // }
    // }
    // }
    //
    // /**
    // *
    // */
    // protected void validatePersonContacts(AddressBookRequest req) {
    // if (req.getProfile().getValue().getPersonContacts() != null) {
    // throw new InvalidRequestPersonContactsException(
    // "AddressBook message request person contact(s) element is invalid");
    // }
    // }

    /**
     * Returns the list of person contacts from the profile element.
     * <p>
     * Prior to accessing the list person contacts, this method validates the
     * parent nodes of the person contacts request element.
     * 
     * @param req
     *            an instance of {@link AddressBookRequest}
     * @return List {@link PersonType}
     * @throws InvalidRequestException
     * @throws InvalidRequestProfileException
     * @throws InvalidRequestPersonContactsException
     */
    // protected List<PersonType> getPersonContacts(AddressBookRequest req) {
    // this.validatePersonContacts(req);
    // return req.getProfile().getValue().getPersonContacts();
    // }

    /**
     * Updates a given contact by invoking the ContactsApi.
     * <p>
     * This method is capable of processing personal, business, or generic
     * contact types.
     * 
     * @param contactDto
     *            The contact to update. This contact can be of type personal,
     *            business, or common.
     * @throws ContactUpdateDaoException
     */
    protected void updateContact(ContactDto contactDto) throws ContactsApiException {
        ContactsApiFactory cf = new ContactsApiFactory();
        ContactsApi api = cf.createApi();
        api.updateContact(contactDto);
        return;
    }
}
