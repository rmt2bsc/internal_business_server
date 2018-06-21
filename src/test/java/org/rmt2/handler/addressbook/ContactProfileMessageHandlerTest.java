package org.rmt2.handler.addressbook;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.List;

import org.dto.ContactDto;
import org.dto.ZipcodeDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.contacts.ContactsApi;
import org.modules.contacts.ContactsApiException;
import org.modules.contacts.ContactsApiFactory;
import org.modules.postal.PostalApi;
import org.modules.postal.PostalApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMessageHandlerTest;
import org.rmt2.ContactMockData;
import org.rmt2.constants.ApiTransactionCodes;
import org.rmt2.handlers.addressbook.profile.ContactProfileApiHandler;
import org.rmt2.jaxb.AddressBookResponse;

import com.api.messaging.handler.MessageHandlerResults;
import com.api.messaging.jms.handler.MessageHandlerCommandException;
import com.api.util.RMT2File;

/**
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ContactProfileApiHandler.class, PostalApiFactory.class })
public class ContactProfileMessageHandlerTest extends BaseMessageHandlerTest {

    private ContactsApiFactory mockContactsApiFactory;
    private ContactsApi mockApi;


    /**
     * 
     */
    public ContactProfileMessageHandlerTest() {
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see testcases.messaging.MessageToListenerToHandlerTest#setUp()
     */
    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.mockContactsApiFactory = Mockito.mock(ContactsApiFactory.class);
        this.mockApi = Mockito.mock(ContactsApi.class);
        try {
            whenNew(ContactsApiFactory.class).withNoArguments().thenReturn(this.mockContactsApiFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        when(this.mockContactsApiFactory.createApi()).thenReturn(this.mockApi);
        
        PowerMockito.mockStatic(PostalApiFactory.class);
        PostalApi mockPostalApi = Mockito.mock(PostalApi.class);
        ZipcodeDto mockZipcodeDto = ContactMockData.createZipcodeOrm(75232, 75232, "Tx", "Dallas", "214", "Dallas", 6);
        when(PostalApiFactory.createApi()).thenReturn(mockPostalApi);
        when(mockPostalApi.getZipCode(isA(Integer.class))).thenReturn(mockZipcodeDto);
        
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see testcases.messaging.MessageToListenerToHandlerTest#tearDown()
     */
    @After
    public void tearDown() throws Exception {
        return;
    }

    @Test
    public void testSuccess_FetchSingleBusinessContact() {
        String request = RMT2File.getFileContentsAsString("xml/contacts/BusinessContactSimpleSearchRequest.xml");
        String expectedResponseXml = RMT2File.getFileContentsAsString("xml/contacts/BusinessContactSimpleSearchResponse.xml");
        List<ContactDto> mockSingleContactDtoResponse = ContactMockData.createMockSingleContactDtoResponseData();

        try {
            when(this.mockApi.getContact(isA(ContactDto.class))).thenReturn(mockSingleContactDtoResponse);
        } catch (ContactsApiException e) {
            Assert.fail("Unable to setup mock stub for fetching a business contact");
        }
        
        MessageHandlerResults results = null;
        ContactProfileApiHandler handler = new ContactProfileApiHandler();
        try {
            results = handler.processMessage(ApiTransactionCodes.CONTACTS_GET, request);
        } catch (MessageHandlerCommandException e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getPayload());
        
        AddressBookResponse expectedResponse = 
                (AddressBookResponse) jaxb.unMarshalMessage(expectedResponseXml);
        AddressBookResponse actualRepsonse = 
                (AddressBookResponse) jaxb.unMarshalMessage(results.getPayload().toString());
        Assert.assertEquals(1, actualRepsonse.getProfile().getBusinessContacts().size());
        Assert.assertEquals(1, actualRepsonse.getReplyStatus().getReturnCode().intValue());
        Assert.assertEquals("SUCCESS", actualRepsonse.getReplyStatus().getReturnStatus());
        Assert.assertEquals("Contact record(s) found",
                actualRepsonse.getReplyStatus().getMessage());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getContactFirstname(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getContactFirstname());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getContactLastname(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getContactLastname());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getContactEmail(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getContactEmail());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getLongName(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getLongName());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getTaxId(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getTaxId());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getBusinessId(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getBusinessId());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getContactPhone(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getContactPhone());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getWebsite(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getWebsite());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getShortName(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getShortName());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getAddr1(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getAddr1());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getAddr2(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getAddr2());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getAddr3(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getAddr3());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getAddr4(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getAddr4());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getAddrId(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getAddrId());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getZip().getCity(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getZip().getCity());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getZip().getState(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getZip().getState());
        Assert.assertEquals(
                expectedResponse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getZip().getZipcode(),
                actualRepsonse.getProfile().getBusinessContacts().get(0)
                        .getAddress().getZip().getZipcode());
    }
    
    
    @Test
    public void testSuccess_FetchBusinessContactList() {
        String request = RMT2File.getFileContentsAsString("xml/contacts/BusinessContactComplexSearchRequest.xml");
        String expectedResponseXml = RMT2File.getFileContentsAsString("xml/contacts/BusinessContactSimpleSearchResponse.xml");
        List<ContactDto> mockContactDtoListResponse = ContactMockData.createMockContactDtoResponseListData();

        try {
            when(this.mockApi.getContact(isA(ContactDto.class))).thenReturn(mockContactDtoListResponse);
        } catch (ContactsApiException e) {
            Assert.fail("Unable to setup mock stub for fetching a list of contacts");
        }
        
        MessageHandlerResults results = null;
        ContactProfileApiHandler handler = new ContactProfileApiHandler();
        try {
            results = handler.processMessage(ApiTransactionCodes.CONTACTS_GET, request);
        } catch (MessageHandlerCommandException e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getPayload());
        
        AddressBookResponse expectedResponse = 
                (AddressBookResponse) jaxb.unMarshalMessage(expectedResponseXml);
        AddressBookResponse actualRepsonse = 
                (AddressBookResponse) jaxb.unMarshalMessage(results.getPayload().toString());
        Assert.assertEquals(3, actualRepsonse.getProfile().getBusinessContacts().size());
        Assert.assertEquals(3, actualRepsonse.getReplyStatus().getReturnCode().intValue());
        Assert.assertEquals("SUCCESS", actualRepsonse.getReplyStatus().getReturnStatus());
        Assert.assertEquals("Contact record(s) found",
                actualRepsonse.getReplyStatus().getMessage());
        
        for (int ndx = 0; ndx < expectedResponse.getProfile().getBusinessContacts().size(); ndx++) {
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getContactFirstname(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getContactFirstname());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getContactLastname(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getContactLastname());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getContactEmail(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getContactEmail());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getLongName(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getLongName());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getTaxId(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getTaxId());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getBusinessId(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getBusinessId());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getContactPhone(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getContactPhone());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getWebsite(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getWebsite());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getShortName(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getShortName());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getAddr1(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getAddr1());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getAddr2(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getAddr2());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getAddr3(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getAddr3());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getAddr4(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getAddr4());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getAddrId(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getAddrId());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getZip().getCity(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getZip().getCity());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getZip().getState(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getZip().getState());
            Assert.assertEquals(
                    expectedResponse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getZip().getZipcode(),
                    actualRepsonse.getProfile().getBusinessContacts().get(ndx)
                            .getAddress().getZip().getZipcode());
        }
    }
    
    @Test
    public void testSuccess_FetchBusinessContact_NoDataFound() {
        String request = RMT2File.getFileContentsAsString("xml/contacts/BusinessContactSimpleSearchRequest.xml");

        try {
            when(this.mockApi.getContact(isA(ContactDto.class))).thenReturn(null);
        } catch (ContactsApiException e) {
            Assert.fail("Unable to setup mock stub for fetching a business contact");
        }
        
        MessageHandlerResults results = null;
        ContactProfileApiHandler handler = new ContactProfileApiHandler();
        try {
            results = handler.processMessage(ApiTransactionCodes.CONTACTS_GET, request);
        } catch (MessageHandlerCommandException e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getPayload());
        
        AddressBookResponse actualRepsonse = 
                (AddressBookResponse) jaxb.unMarshalMessage(results.getPayload().toString());
        Assert.assertNull(actualRepsonse.getProfile());
        Assert.assertEquals(0, actualRepsonse.getReplyStatus().getReturnCode().intValue());
        Assert.assertEquals("SUCCESS", actualRepsonse.getReplyStatus().getReturnStatus());
        Assert.assertEquals("Contact data not found!", actualRepsonse.getReplyStatus().getMessage());
    }
    
    @Test
    public void testError_FetchBusinessContact_API_Error() {
        String request = RMT2File.getFileContentsAsString("xml/contacts/BusinessContactNoCriteriaSearchRequest.xml");

        try {
            when(this.mockApi.getContact(null))
                    .thenThrow(new ContactsApiException("Test validation error: selection criteria is required"));
        } catch (ContactsApiException e) {
            Assert.fail("Unable to setup mock stub for fetching a business contact");
        }
        
        MessageHandlerResults results = null;
        ContactProfileApiHandler handler = new ContactProfileApiHandler();
        try {
            results = handler.processMessage(ApiTransactionCodes.CONTACTS_GET, request);
        } catch (MessageHandlerCommandException e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getPayload());
        
        AddressBookResponse actualRepsonse = 
                (AddressBookResponse) jaxb.unMarshalMessage(results.getPayload().toString());
        Assert.assertNull(actualRepsonse.getProfile());
        Assert.assertEquals("ERROR", actualRepsonse.getReplyStatus().getReturnStatus());
        Assert.assertEquals(-1, actualRepsonse.getReplyStatus().getReturnCode().intValue());
        Assert.assertEquals("Failure to retrieve contact(s)", actualRepsonse.getReplyStatus().getMessage());
        Assert.assertEquals(
                "AddressBook ContactCriteriaGroup is required to have one and only one criteria object that is of type either personal, business, or common",
                actualRepsonse.getReplyStatus().getExtMessage());
    }
    
    @Test
    public void testSuccess_UpdateBusinessContact() {
        String request = RMT2File.getFileContentsAsString("xml/contacts/BusinessContactUpdateRequest.xml");
        try {
            when(this.mockApi.updateContact(isA(ContactDto.class))).thenReturn(1);
        } catch (ContactsApiException e) {
            Assert.fail("Unable to setup mock stub for updating a business contact");
        }
        
        MessageHandlerResults results = null;
        ContactProfileApiHandler handler = new ContactProfileApiHandler();
        try {
            results = handler.processMessage(ApiTransactionCodes.CONTACTS_UPDATE, request);
        } catch (MessageHandlerCommandException e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getPayload());
        
        AddressBookResponse actualRepsonse = 
                (AddressBookResponse) jaxb.unMarshalMessage(results.getPayload().toString());
        Assert.assertEquals(1, actualRepsonse.getProfile().getBusinessContacts().size());
        Assert.assertEquals(1, actualRepsonse.getReplyStatus().getReturnCode().intValue());
        Assert.assertEquals("SUCCESS", actualRepsonse.getReplyStatus().getReturnStatus());
        Assert.assertEquals("Contact was modified successfully",
                actualRepsonse.getReplyStatus().getMessage());
        Assert.assertEquals("Total number of rows modified: " + actualRepsonse.getReplyStatus().getReturnCode().intValue(),
                actualRepsonse.getReplyStatus().getExtMessage());
        Assert.assertTrue(actualRepsonse.getProfile().getBusinessContacts().get(0).getBusinessId().intValue() > 0);
        Assert.assertTrue(actualRepsonse.getProfile().getBusinessContacts().get(0).getAddress().getAddrId().intValue() > 0);
    }
    
    @Test
    public void testSuccess_InsertBusinessContact() {
        String request = RMT2File.getFileContentsAsString("xml/contacts/BusinessContactInsertRequest.xml");
        try {
            when(this.mockApi.updateContact(isA(ContactDto.class))).thenReturn(1234);
        } catch (ContactsApiException e) {
            Assert.fail("Unable to setup mock stub for updating a business contact");
        }
        
        MessageHandlerResults results = null;
        ContactProfileApiHandler handler = new ContactProfileApiHandler();
        try {
            results = handler.processMessage(ApiTransactionCodes.CONTACTS_UPDATE, request);
        } catch (MessageHandlerCommandException e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getPayload());
        
        AddressBookResponse actualRepsonse = 
                (AddressBookResponse) jaxb.unMarshalMessage(results.getPayload().toString());
        Assert.assertEquals(1, actualRepsonse.getProfile().getBusinessContacts().size());
        Assert.assertEquals(1234, actualRepsonse.getReplyStatus().getReturnCode().intValue());
        Assert.assertEquals("SUCCESS", actualRepsonse.getReplyStatus().getReturnStatus());
        Assert.assertEquals("Contact was created successfully",
                actualRepsonse.getReplyStatus().getMessage());
        Assert.assertEquals("The new contact id is " + actualRepsonse.getReplyStatus().getReturnCode().intValue(),
                actualRepsonse.getReplyStatus().getExtMessage());
        Assert.assertEquals(1234, actualRepsonse.getProfile().getBusinessContacts().get(0).getBusinessId().intValue());
//        Assert.assertTrue(actualRepsonse.getProfile().getBusinessContacts().get(0).getAddress().getAddrId().intValue() > 0);
    }
}
