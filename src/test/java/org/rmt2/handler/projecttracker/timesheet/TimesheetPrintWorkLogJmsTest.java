package org.rmt2.handler.projecttracker.timesheet;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dao.mapping.orm.rmt2.ProjEvent;
import org.dao.mapping.orm.rmt2.VwTimesheetProjectTask;
import org.dto.BusinessContactDto;
import org.dto.ContactDto;
import org.dto.EmployeeDto;
import org.dto.EventDto;
import org.dto.ProjectTaskDto;
import org.dto.TimesheetDto;
import org.dto.adapter.orm.ProjectObjectFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.contacts.ContactsApi;
import org.modules.contacts.ContactsApiFactory;
import org.modules.timesheet.TimesheetApi;
import org.modules.timesheet.TimesheetApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.timesheet.TimesheetPrintWorkLogApiHandler;
import org.rmt2.handler.projecttracker.ProjectTrackerJmsMockData;

import com.api.config.SystemConfigurator;
import com.api.messaging.jms.JmsClientManager;
import com.api.persistence.DaoClient;
import com.api.util.RMT2Date;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the timesheet work log print related JMS
 * messages for the Project Tracker API Message Handler.
 * 
 * @author roy terrell
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, TimesheetPrintWorkLogApiHandler.class, TimesheetApiFactory.class,
        ContactsApiFactory.class, SystemConfigurator.class, RMT2Date.class })
public class TimesheetPrintWorkLogJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.projecttracker";
    public static final int TIMESHEET_ID = 111;
    public static final int BUSINESS_ID = 1351;
    public static final String PROP_SERIAL_PATH = "\\temp\\";
    public static final String PROP_RPT_XSLT_PATH = "reports";
    private TimesheetApi mockApi;
    private TimesheetApiFactory mockApiFactory;
    private ContactsApi mockContactApi;

    private EmployeeDto employee;
    private TimesheetDto timesheetExt;
    private Map<ProjectTaskDto, List<EventDto>> hours;
    private List<ContactDto> mockBusinessContactDtoList;

    /**
     * 
     */
    public TimesheetPrintWorkLogJmsTest() {
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

        DaoClient mockDaoClient = Mockito.mock(DaoClient.class);
        when(mockApi.getSharedDao()).thenReturn(mockDaoClient);

        mockContactApi = Mockito.mock(ContactsApi.class);
        PowerMockito.mockStatic(ContactsApiFactory.class);
        when(ContactsApiFactory.createApi()).thenReturn(mockContactApi);

        doNothing().when(this.mockApi).close();
        this.createInputData();

        System.setProperty("CompContactId", String.valueOf(BUSINESS_ID));
        System.setProperty("SerialPath", PROP_SERIAL_PATH);
        System.setProperty("RptXsltPath", PROP_RPT_XSLT_PATH);
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
        this.employee = ProjectTrackerJmsMockData.createMockMultipleExtEmployee().get(0);
        this.employee.setEmployeeFirstname("John");
        this.employee.setEmployeeLastname("Smith");
        this.timesheetExt = ProjectTrackerJmsMockData.createMockExtTimesheetList().get(0);
        this.mockBusinessContactDtoList = ProjectTrackerJmsMockData.createMockSingleBusinessContactDto();
        this.hours = this.buildTimesheetHoursDtoMap();
    }

    private Map<ProjectTaskDto, List<EventDto>> buildTimesheetHoursDtoMap() {
        Map<ProjectTaskDto, List<EventDto>> hours = new HashMap<>();
        for (VwTimesheetProjectTask pt : ProjectTrackerJmsMockData.createMockMultipleVwTimesheetProjectTask()) {
            ProjectTaskDto ptDto = ProjectObjectFactory.createProjectTaskExtendedDtoInstance(pt);
            List<EventDto> eventsDto = this.buildTimesheetEventDtoList(pt.getProjectTaskId());
            hours.put(ptDto, eventsDto);
        }
        return hours;
    }

    private List<EventDto> buildTimesheetEventDtoList(int projectTaskId) {
        List<ProjEvent> projEvents = ProjectTrackerJmsMockData.createMockMultiple_Day_Task_Events(projectTaskId);
        List<EventDto> eventsDto = new ArrayList<>();
        for (ProjEvent evt : projEvents) {
            EventDto evtDto = ProjectObjectFactory.createEventDtoInstance(evt);
            eventsDto.add(evtDto);
        }
        return eventsDto;
    }

    @Test
    public void invokeHandelrSuccess_Print() {
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/timesheet/TimesheetPrintWorkLogRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.load(isA(Integer.class))).thenReturn(null);
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for loading timesheet method");
        }

        try {
            when(this.mockApi.getTimesheet()).thenReturn(this.timesheetExt);
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for obtaining timesheet graph");
        }
        try {
            when(this.mockApi.getTimesheetHours()).thenReturn(this.hours);
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for obtaining timesheet hours graph");
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
            Mockito.verify(this.mockApi).getTimesheetHours();
            Mockito.verify(this.mockContactApi).getContact(isA(BusinessContactDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
}
