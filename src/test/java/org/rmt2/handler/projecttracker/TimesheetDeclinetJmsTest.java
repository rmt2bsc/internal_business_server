package org.rmt2.handler.projecttracker;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.dto.EmployeeDto;
import org.dto.TimesheetDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.admin.ProjectAdminApiFactory;
import org.modules.employee.EmployeeApi;
import org.modules.employee.EmployeeApiFactory;
import org.modules.timesheet.TimesheetApi;
import org.modules.timesheet.TimesheetApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.timesheet.TimesheetPostSubmitApiHandler;

import com.api.config.SystemConfigurator;
import com.api.messaging.email.EmailMessageBean;
import com.api.messaging.email.smtp.SmtpApi;
import com.api.messaging.email.smtp.SmtpFactory;
import com.api.messaging.jms.JmsClientManager;
import com.api.persistence.DaoClient;
import com.api.util.RMT2Date;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the timesheet decline related JMS messages
 * for the Project Tracker API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, TimesheetPostSubmitApiHandler.class, TimesheetApiFactory.class,
        EmployeeApiFactory.class, ProjectAdminApiFactory.class, SmtpFactory.class, SystemConfigurator.class, RMT2Date.class })
public class TimesheetDeclinetJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.projecttracker";
    public static final int TIMESHEET_ID = 900;
    private TimesheetApi mockApi;
    private TimesheetApiFactory mockApiFactory;
    private EmployeeApi mockEmpApi;

    private EmployeeDto employee;
    private TimesheetDto timesheetExt;

    /**
     * 
     */
    public TimesheetDeclinetJmsTest() {
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
        this.employee = ProjectTrackerJmsMockData.createMockMultipleExtEmployee().get(0);
        this.employee.setEmployeeFirstname("John");
        this.employee.setEmployeeLastname("Smith");
        this.timesheetExt = ProjectTrackerJmsMockData.createMockExtTimesheetList().get(0);
    }


    @Test
    public void invokeHandelrSuccess_Approve() {
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/timesheet/TimesheetDeclineRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.decline(isA(Integer.class))).thenReturn(1);
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for declining timesheet method");
        }

        try {
            when(this.mockApi.getTimesheet()).thenReturn(this.timesheetExt);
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for obtaining timesheet graph");
        }

        try {
            when(this.mockEmpApi.getEmployeeExt(isA(EmployeeDto.class))).thenReturn(
                    ProjectTrackerJmsMockData.createMockMultipleExtEmployee());
        } catch (Exception e) {
            Assert.fail("Failed to setup stub for obtaining timesheet employee");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).decline(isA(Integer.class));
            Mockito.verify(this.mockEmpApi).getEmployeeExt(isA(EmployeeDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
}
