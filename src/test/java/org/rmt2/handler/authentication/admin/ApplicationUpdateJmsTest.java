package org.rmt2.handler.authentication.admin;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.dto.ApplicationDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.application.AppApi;
import org.modules.application.AppApiException;
import org.modules.application.AppApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.api.handlers.admin.application.ApplicationUpdateApiHandler;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the application related JMS messages for
 * the Authentication API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ApplicationUpdateApiHandler.class, JmsClientManager.class, AppApiFactory.class })
public class ApplicationUpdateJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.authentication";
    private AppApi mockApi;


    /**
     * 
     */
    public ApplicationUpdateJmsTest() {
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
        mockApi = Mockito.mock(AppApi.class);
        PowerMockito.mockStatic(AppApiFactory.class);
        when(AppApiFactory.createApi()).thenReturn(mockApi);
        doNothing().when(this.mockApi).close();
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
    public void invokeHandelrSuccess_Update() {
        String request = RMT2File.getFileContentsAsString("xml/authentication/admin/ApplicationUpdateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.update(isA(ApplicationDto.class))).thenReturn(1);
        } catch (AppApiException e) {
            e.printStackTrace();
            Assert.fail("Application update test case failed");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).update(isA(ApplicationDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
    
}
