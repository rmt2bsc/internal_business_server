package org.rmt2.handler.addressbook;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.util.List;

import org.dto.LookupGroupDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.lookup.LookupDataApi;
import org.modules.lookup.LookupDataApiException;
import org.modules.lookup.LookupDataApiFactory;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.AddressBookMockData;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.contacts.ContactProfileApiHandler;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the Lookup Group API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ContactProfileApiHandler.class, JmsClientManager.class })
public class LookupGroupJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-AddressBook-Queue";
    private LookupDataApiFactory mockApiFactory;
    private LookupDataApi mockApi;


    /**
     * 
     */
    public LookupGroupJmsTest() {
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
        this.mockApiFactory = Mockito.mock(LookupDataApiFactory.class);
        this.mockApi = Mockito.mock(LookupDataApi.class);
        try {
            whenNew(LookupDataApiFactory.class).withNoArguments().thenReturn(this.mockApiFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        when(this.mockApiFactory.createApi()).thenReturn(this.mockApi);
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
    public void invokeHandelrSuccess_Fetch() {
        String request = RMT2File.getFileContentsAsString("xml/lookup/LookupGroupSearchRequest.xml");
        List<LookupGroupDto> mockDtoDataResponse = AddressBookMockData.createMockLookupGroupDtoListResponse();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getGroup(isA(LookupGroupDto.class))).thenReturn(mockDtoDataResponse);
        } catch (LookupDataApiException e) {

        }

        try {
            this.startTest();    
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
    
    @Test
    public void invokeHandelrError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File.getFileContentsAsString("xml/lookup/LookupGroupIncorrectTransRequest.xml");
        List<LookupGroupDto> mockDtoDataResponse = AddressBookMockData.createMockLookupGroupDtoListResponse();
        this.setupMocks(DESTINATION, request);
        try {
            this.startTest();    
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
}
