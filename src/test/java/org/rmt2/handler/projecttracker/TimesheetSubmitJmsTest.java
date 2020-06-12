package org.rmt2.handler.projecttracker;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dao.mapping.orm.rmt2.ProjEvent;
import org.dao.mapping.orm.rmt2.VwTimesheetProjectTask;
import org.dto.ClientDto;
import org.dto.EmployeeDto;
import org.dto.EventDto;
import org.dto.ProjectTaskDto;
import org.dto.TimesheetDto;
import org.dto.TimesheetHistDto;
import org.dto.adapter.orm.EmployeeObjectFactory;
import org.dto.adapter.orm.ProjectObjectFactory;
import org.dto.adapter.orm.TimesheetObjectFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.admin.ProjectAdminApi;
import org.modules.admin.ProjectAdminApiFactory;
import org.modules.employee.EmployeeApi;
import org.modules.employee.EmployeeApiFactory;
import org.modules.timesheet.TimesheetApi;
import org.modules.timesheet.TimesheetApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.timesheet.TimesheetSubmitApiHandler;

import com.api.config.SystemConfigurator;
import com.api.messaging.email.EmailMessageBean;
import com.api.messaging.email.smtp.SmtpApi;
import com.api.messaging.email.smtp.SmtpFactory;
import com.api.messaging.jms.JmsClientManager;
import com.api.persistence.DaoClient;
import com.api.util.RMT2Date;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the timesheet submit related JMS messages
 * for the Project Tracker API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, TimesheetSubmitApiHandler.class, TimesheetApiFactory.class,
        EmployeeApiFactory.class, ProjectAdminApiFactory.class, SmtpFactory.class, SystemConfigurator.class, RMT2Date.class })
public class TimesheetSubmitJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.projecttracker";
    public static final int TIMESHEET_ID = 900;
    private TimesheetApi mockApi;
    private TimesheetApiFactory mockApiFactory;
    private EmployeeApi mockEmpApi;
    private ProjectAdminApi mockProjApi;

    private ClientDto client;
    private EmployeeDto employee;
    private EmployeeDto manager;
    private TimesheetDto timesheet;
    private TimesheetDto timesheetExt;
    private TimesheetHistDto currentStatus;
    private Map<ProjectTaskDto, List<EventDto>> hours;

    /**
     * 
     */
    public TimesheetSubmitJmsTest() {
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

        mockEmpApi = Mockito.mock(EmployeeApi.class);
        PowerMockito.mockStatic(EmployeeApiFactory.class);
        when(EmployeeApiFactory.createApi(isA(DaoClient.class))).thenReturn(mockEmpApi);

        mockProjApi = Mockito.mock(ProjectAdminApi.class);
        PowerMockito.mockStatic(ProjectAdminApiFactory.class);
        when(ProjectAdminApiFactory.createApi(isA(DaoClient.class))).thenReturn(mockProjApi);

        doNothing().when(this.mockApi).close();

        SmtpApi mockSmtpApi = Mockito.mock(SmtpApi.class);
        PowerMockito.mockStatic(SmtpFactory.class);
        try {
            when(SmtpFactory.getSmtpInstance()).thenReturn(mockSmtpApi);
        } catch (Exception e) {
            Assert.fail("Failed to stub SmtpFactory.getSmtpInstance method");
        }
        try {
            when(mockSmtpApi.sendMessage(isA(EmailMessageBean.class))).thenReturn(1);
            doNothing().when(mockSmtpApi).close();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("The mocking of TimesheetTransmissionApi's send method failed");
        }

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
        this.client = ProjectTrackerJmsMockData.createMockSingleClient().get(0);
        this.employee = ProjectTrackerJmsMockData.createMockMultipleExtEmployee().get(0);
        this.employee.setEmployeeFirstname("John");
        this.employee.setEmployeeLastname("Smith");
        this.manager = EmployeeObjectFactory.createEmployeeDtoInstance(ProjectTrackerJmsMockData.createMockSingleEmployee()
                .get(0));
        this.timesheetExt = ProjectTrackerJmsMockData.createMockExtTimesheetList().get(0);
        this.currentStatus = TimesheetObjectFactory.createTimesheetHistoryDtoInstance(ProjectTrackerJmsMockData
                .createMockTimesheetStatusHistory().get(2));
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
    public void invokeHandelrSuccess_Submit() {
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/timesheet/TimesheetSubmitRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.submit(isA(Integer.class))).thenReturn(1);
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for submitting timesheet method");
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
            when(this.mockEmpApi.getEmployeeExt(isA(EmployeeDto.class))).thenReturn(
                    ProjectTrackerJmsMockData.createMockMultipleExtEmployee());
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for obtaining timesheet employee");
        }
        try {
            when(this.mockEmpApi.getEmployee(isA(Integer.class))).thenReturn(this.manager);
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for obtaining timesheet employee manager");
        }
        try {
            when(this.mockProjApi.getClient(isA(ClientDto.class))).thenReturn(
                    ProjectTrackerJmsMockData.createMockMultipleClient());
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for obtaining timesheet client");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).submit(isA(Integer.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
}
