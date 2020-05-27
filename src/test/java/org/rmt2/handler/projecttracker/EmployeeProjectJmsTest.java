package org.rmt2.handler.projecttracker;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dto.ProjectEmployeeDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.ProjectTrackerApiConst;
import org.modules.contacts.ContactsApiFactory;
import org.modules.employee.EmployeeApi;
import org.modules.employee.EmployeeApiException;
import org.modules.employee.EmployeeApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.BaseMockMessageDrivenBeanTest;
import org.rmt2.api.handlers.employee.EmployeeQueryApiHandler;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the Employee/Project related JMS messages
 * for the Project Tracker API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ EmployeeQueryApiHandler.class, JmsClientManager.class, EmployeeApiFactory.class, ContactsApiFactory.class })
public class EmployeeProjectJmsTest extends BaseMockMessageDrivenBeanTest {

    private static final String DESTINATION = "rmt2.queue.projecttracker";
    public static final int EMPLOYEE_ID = 2000;
    public static final int CONTACT_ID = 900;
    private EmployeeApi mockApi;


    /**
     * 
     */
    public EmployeeProjectJmsTest() {
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

        this.mockApi = Mockito.mock(EmployeeApi.class);
        PowerMockito.mockStatic(EmployeeApiFactory.class);
        when(EmployeeApiFactory.createApi(eq(ProjectTrackerApiConst.APP_NAME))).thenReturn(this.mockApi);
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
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/employee/EmployeeProjectQueryRequest.xml");
        List<ProjectEmployeeDto> apiResults = ProjectTrackerJmsMockData.createMockMultipleVwEmployeeProjects();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getProjectEmployee(isA(ProjectEmployeeDto.class))).thenReturn(apiResults);
        } catch (EmployeeApiException e) {
            e.printStackTrace();
            Assert.fail("Employee fetch test case failed");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).getProjectEmployee(isA(ProjectEmployeeDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
    
    @Test
    public void invokeHandelrSuccess_Update() {
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/employee/EmployeeProjectUpdateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.update(isA(ProjectEmployeeDto.class))).thenReturn(1);
        } catch (EmployeeApiException e) {
            e.printStackTrace();
            Assert.fail("Employee fetch test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).update(isA(ProjectEmployeeDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }

    }
}
