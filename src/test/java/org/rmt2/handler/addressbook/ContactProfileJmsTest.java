package org.rmt2.handler.addressbook;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.ArrayList;
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
import org.rmt2.AddressBookMockData;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.contacts.ContactProfileApiHandler;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the Contacts API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ContactProfileApiHandler.class, ContactsApiFactory.class, PostalApiFactory.class, JmsClientManager.class })
public class ContactProfileJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-AddressBook-Queue";
    private ContactsApiFactory mockContactsApiFactory;
    private ContactsApi mockApi;


    /**
     * 
     */
    public ContactProfileJmsTest() {
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
        
        List<ZipcodeDto> list = new ArrayList<>();
        ZipcodeDto p = AddressBookMockData.createMockZipcodeDto(75231,75231, "State1", "City1", "AreaCode1", "County1", 6);

        PowerMockito.mockStatic(PostalApiFactory.class);
        PostalApi mockPostalApi = Mockito.mock(PostalApi.class);
        when(PostalApiFactory.createApi()).thenReturn(mockPostalApi);
        when(mockPostalApi.getZipCode(isA(Integer.class))).thenReturn(p);
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
    public void fetchSingleBusinessContact() {
        String request = RMT2File.getFileContentsAsString("xml/contacts/BusinessContactSimpleSearchRequest.xml");
        List<ContactDto> mockSingleContactDtoResponse = AddressBookMockData.createMockSingleContactDtoResponseData();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getContact(isA(ContactDto.class))).thenReturn(mockSingleContactDtoResponse);
        } catch (ContactsApiException e) {

        }

        try {
            this.startTest();    
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
}
