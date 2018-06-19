package org.rmt2.handler;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.List;

import org.dto.ContactDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.contacts.ContactsApi;
import org.modules.contacts.ContactsApiException;
import org.modules.contacts.ContactsApiFactory;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.ContactMockData;
import org.rmt2.handlers.addressbook.profile.BusinessProfilePayloadHandler;

import com.api.messaging.jms.JmsClientManager;
import com.util.RMT2File;



/**
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ BusinessProfilePayloadHandler.class, JmsClientManager.class })
public class MessageDrivenBeanToMessageHandlerTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-AddressBook-Queue";
    private ContactsApiFactory mockContactsApiFactory;
    private ContactsApi mockApi;


    /**
     * 
     */
    public MessageDrivenBeanToMessageHandlerTest() {
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
        String request  = RMT2File.getFileContentsAsString("BusinessContactSimpleSearchRequest.xml");
        List<ContactDto> mockSingleContactDtoResponse = ContactMockData.createMockSingleContactDtoResponseData();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getContact(isA(ContactDto.class))).thenReturn(mockSingleContactDtoResponse);
        } catch (ContactsApiException e) {

        }

        try {
            this.startTest();    
        }
        catch (Exception e) {
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
}
