package org.rmt2.handler.projecttracker.timesheet;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dto.BusinessContactDto;
import org.dto.ContactDto;
import org.dto.TimesheetDto;
import org.dto.TimesheetHoursSummaryDto;
import org.dto.adapter.orm.TimesheetObjectFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.contacts.ContactsApi;
import org.modules.contacts.ContactsApiFactory;
import org.modules.timesheet.TimesheetApi;
import org.modules.timesheet.TimesheetApiException;
import org.modules.timesheet.TimesheetApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.timesheet.TimesheetPrintSummaryApiHandler;
import org.rmt2.handler.projecttracker.ProjectTrackerJmsMockData;

import com.api.config.SystemConfigurator;
import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2Date;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the timesheet summary print related JMS
 * messages for the Project Tracker API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, TimesheetPrintSummaryApiHandler.class, TimesheetApiFactory.class,
        ContactsApiFactory.class, SystemConfigurator.class, RMT2Date.class })
public class TimesheetPrintSummaryJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.projecttracker";
    public static final int TIMESHEET_ID = 111;
    public static final int BUSINESS_ID = 1351;
    public static final String PROP_SERIAL_PATH = "\\temp\\";
    public static final String PROP_RPT_XSLT_PATH = "reports";
    public static final String API_ERROR = "Test API Error";

    private TimesheetApi mockApi;
    private TimesheetApiFactory mockApiFactory;
    private ContactsApi mockContactApi;
    private TimesheetDto mockTimesheetExt;
    private TimesheetHoursSummaryDto mockHourSummary;
    private List<ContactDto> mockBusinessContactDtoList;

    /**
     * 
     */
    public TimesheetPrintSummaryJmsTest() {
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

        mockContactApi = Mockito.mock(ContactsApi.class);
        PowerMockito.mockStatic(ContactsApiFactory.class);
        when(ContactsApiFactory.createApi()).thenReturn(mockContactApi);

        doNothing().when(this.mockApi).close();

        System.setProperty("CompContactId", String.valueOf(BUSINESS_ID));
        System.setProperty("SerialPath", PROP_SERIAL_PATH);
        System.setProperty("RptXsltPath", PROP_RPT_XSLT_PATH);

        this.createInputData();
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

    private void createInputData() {
        this.mockTimesheetExt = ProjectTrackerJmsMockData.createMockExtTimesheetList().get(0);
        this.mockHourSummary = TimesheetObjectFactory.createTimesheetSummaryDtoInstance(ProjectTrackerJmsMockData
                .createMockTimesheetSummaryList().get(0));
        this.mockBusinessContactDtoList = ProjectTrackerJmsMockData.createMockSingleBusinessContactDto();
    }

    @Test
    public void invokeHandelrSuccess_Print() {
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/timesheet/TimesheetPrintSummaryRequest.xml");
        this.setupMocks(DESTINATION, request);

        try {
            when(this.mockApi.load(isA(Integer.class))).thenReturn(null);
        } catch (TimesheetApiException e) {
            Assert.fail("Unable to setup mock stub for loading timesheet graph");
        }

        try {
            when(this.mockApi.getTimesheet()).thenReturn(this.mockTimesheetExt);
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for obtaining timesheet graph");
        }
        try {
            when(this.mockApi.getTimesheetSummary()).thenReturn(this.mockHourSummary);
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for obtaining timesheet hour summary graph");
        }

        try {
            when(this.mockContactApi.getContact(isA(BusinessContactDto.class))).thenReturn(this.mockBusinessContactDtoList);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to setup stub for obtaining employee contact information graph");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).load(isA(Integer.class));
            Mockito.verify(this.mockApi, times(2)).getTimesheet();
            Mockito.verify(this.mockApi).getTimesheetSummary();
            Mockito.verify(this.mockContactApi).getContact(isA(BusinessContactDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
}
