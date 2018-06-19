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
import org.rmt2.handlers.addressbook.profile.BusinessContactApiHandler;
import org.rmt2.jaxb.AddressBookResponse;

import com.api.messaging.handler.MessageHandlerResults;
import com.api.messaging.jms.handler.MessageHandlerCommandException;
import com.util.RMT2File;

/**
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ BusinessContactApiHandler.class, PostalApiFactory.class })
public class BuisnessProfileMessageHandlerTest extends BaseMessageHandlerTest {

//    private static final String DESTINATION = "Test-AddressBook-Queue";
    private ContactsApiFactory mockContactsApiFactory;
    private ContactsApi mockApi;


    /**
     * 
     */
    public BuisnessProfileMessageHandlerTest() {
        // TODO Auto-generated constructor stub
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
        String request = RMT2File.getFileContentsAsString("BusinessContactSimpleSearchRequest.xml");
        String expectedResponseXml = RMT2File.getFileContentsAsString("BusinessContactSimpleSearchResponse.xml");
        List<ContactDto> mockSingleContactDtoResponse = ContactMockData.createMockSingleContactDtoResponseData();

        try {
            when(this.mockApi.getContact(isA(ContactDto.class))).thenReturn(mockSingleContactDtoResponse);
        } catch (ContactsApiException e) {
            Assert.fail("Unable to setup mock stub for fetching a business contact");
        }
        
        MessageHandlerResults results = null;
        BusinessContactApiHandler handler = new BusinessContactApiHandler();
        try {
            results = handler.processMessage(ApiTransactionCodes.CONTACTS_BUSINESS_GET, request);
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
        Assert.assertEquals(expectedResponse.getProfile().getBusinessContacts().get(0).getContactEmail(),
                actualRepsonse.getProfile().getBusinessContacts().get(0).getContactEmail());
        
    }
    
    
    @Test
    public void testSuccess_FetchBusinessContactList() {
        String request = RMT2File.getFileContentsAsString("BusinessContactComplexSearchRequest.xml");
        String expectedResponseXml = RMT2File.getFileContentsAsString("BusinessContactSimpleSearchResponse.xml");
        List<ContactDto> mockContactDtoListResponse = ContactMockData.createMockContactDtoResponseListData();

        try {
            when(this.mockApi.getContact(isA(ContactDto.class))).thenReturn(mockContactDtoListResponse);
        } catch (ContactsApiException e) {
            Assert.fail("Unable to setup mock stub for fetching a list of business contacts");
        }
        
        MessageHandlerResults results = null;
        BusinessContactApiHandler handler = new BusinessContactApiHandler();
        try {
            results = handler.processMessage(ApiTransactionCodes.CONTACTS_BUSINESS_GET, request);
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
        Assert.assertEquals(expectedResponse.getProfile().getBusinessContacts().get(0).getContactEmail(),
                actualRepsonse.getProfile().getBusinessContacts().get(0).getContactEmail());
        
    }
}
