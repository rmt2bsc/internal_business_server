package org.rmt2.handler.addressbook;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dto.RegionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.AddressBookConstants;
import org.modules.postal.PostalApi;
import org.modules.postal.PostalApiException;
import org.modules.postal.PostalApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.AddressBookMockData;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.handlers.addressbook.profile.ContactProfileApiHandler;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ContactProfileApiHandler.class, JmsClientManager.class, PostalApiFactory.class })
public class RegionJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "Test-AddressBook-Queue";
    private PostalApi mockApi;


    /**
     * 
     */
    public RegionJmsTest() {
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
        String request = RMT2File.getFileContentsAsString("xml/postal/RegionSearchRequest.xml");
        List<RegionDto> apiResults = AddressBookMockData.createMockRegionDto();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getRegion(isA(RegionDto.class))).thenReturn(apiResults);
        } catch (PostalApiException e) {
            e.printStackTrace();
            Assert.fail("All region fetch test case failed");
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
        String request = RMT2File.getFileContentsAsString("xml/postal/RegionSearchInvalidTransCodeRequest.xml");
        List<RegionDto> apiResults = AddressBookMockData.createMockRegionDto();
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
