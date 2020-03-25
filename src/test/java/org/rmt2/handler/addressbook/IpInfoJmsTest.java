package org.rmt2.handler.addressbook;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import org.dto.IpLocationDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.AddressBookConstants;
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
 * Test the idenity and invocation of the IP Infomation API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ContactProfileApiHandler.class, JmsClientManager.class, PostalApiFactory.class })
public class IpInfoJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.addressbook";
    private PostalApi mockApi;


    /**
     * 
     */
    public IpInfoJmsTest() {
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
        this.mockApi = Mockito.mock(PostalApi.class);
        PowerMockito.mockStatic(PostalApiFactory.class);
        when(PostalApiFactory.createApi(eq(AddressBookConstants.APP_NAME))).thenReturn(this.mockApi);
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
        String request = RMT2File.getFileContentsAsString("xml/addressbook/postal/IpInfoStandardSearchRequest.xml");
        this.setupMocks(DESTINATION, request);

        try {
            IpLocationDto apiResults = AddressBookMockData.createMockIpLocationDto(null, "111.222.333.444", 90333.333,
                    29393.392838, "United States", "TX", "Dallas", "75240", "214");
            when(this.mockApi.getIpInfo(isA(String.class))).thenReturn(apiResults);
            this.startTest();    
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
    
    @Test
    public void invokeHandelrError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File.getFileContentsAsString("xml/addressbook/postal/IpInfoIncorrectTransCodeRequest.xml");
        IpLocationDto apiResults = AddressBookMockData.createMockIpLocationDto(null, "111.222.333.444", 90333.333,
                29393.392838, "United States", "TX", "Dallas", "75240", "214");
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
