package org.rmt2.handler.projecttracker.timesheet;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.timesheet.TimesheetApi;
import org.modules.timesheet.TimesheetApiException;
import org.modules.timesheet.TimesheetApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.timesheet.TimesheetDeleteApiHandler;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the timesheet delete related JMS messages
 * for the Project Tracker API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ TimesheetDeleteApiHandler.class, JmsClientManager.class, TimesheetApiFactory.class })
public class TimesheetDeleteJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.projecttracker";
    public static final int TIMESHEET_ID = 900;
    private TimesheetApi mockApi;
    private TimesheetApiFactory mockApiFactory;

    /**
     * 
     */
    public TimesheetDeleteJmsTest() {
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
        mockApi = Mockito.mock(TimesheetApi.class);
        PowerMockito.mockStatic(TimesheetApiFactory.class);
        mockApiFactory = Mockito.mock(TimesheetApiFactory.class);
        PowerMockito.whenNew(TimesheetApiFactory.class).withNoArguments().thenReturn(mockApiFactory);
        when(mockApiFactory.createApi(isA(String.class))).thenReturn(mockApi);
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
    public void invokeHandelrSuccess_Fetch() {
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/timesheet/TimesheetDeleteRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.deleteTimesheet(isA(Integer.class))).thenReturn(1);
        } catch (TimesheetApiException e) {
            e.printStackTrace();
            Assert.fail("Timesheet fetch test case failed");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).deleteTimesheet(isA(Integer.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
}
