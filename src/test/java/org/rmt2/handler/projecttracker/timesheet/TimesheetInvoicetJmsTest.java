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
import org.modules.timesheet.invoice.InvoiceTimesheetApi;
import org.modules.timesheet.invoice.InvoiceTimesheetApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.timesheet.TimesheetInvoiceSingleApiHandler;

import com.api.config.SystemConfigurator;
import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2Date;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the timesheet invoicing related JMS
 * messages for the Project Tracker API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, TimesheetInvoiceSingleApiHandler.class, InvoiceTimesheetApiFactory.class,
        SystemConfigurator.class, RMT2Date.class })
public class TimesheetInvoicetJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.projecttracker";
    public static final int TIMESHEET_ID = 900;
    private InvoiceTimesheetApi mockApi;

    /**
     * 
     */
    public TimesheetInvoicetJmsTest() {
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
        mockApi = Mockito.mock(InvoiceTimesheetApi.class);
        PowerMockito.mockStatic(InvoiceTimesheetApiFactory.class);
        when(InvoiceTimesheetApiFactory.createApi(isA(String.class))).thenReturn(mockApi);
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
    public void invokeHandelrSuccess_Invoice() {
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/timesheet/TimesheetInvoiceRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.invoice(isA(Integer.class))).thenReturn(1);
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for approving timesheet method");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).invoice(isA(Integer.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
}
