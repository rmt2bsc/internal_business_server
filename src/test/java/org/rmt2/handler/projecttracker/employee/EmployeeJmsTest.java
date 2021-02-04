package org.rmt2.handler.projecttracker.employee;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dto.EmployeeDto;
import org.dto.PersonalContactDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.ProjectTrackerApiConst;
import org.modules.contacts.ContactsApi;
import org.modules.contacts.ContactsApiException;
import org.modules.contacts.ContactsApiFactory;
import org.modules.employee.EmployeeApi;
import org.modules.employee.EmployeeApiException;
import org.modules.employee.EmployeeApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.api.handlers.employee.EmployeeQueryApiHandler;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;
import org.rmt2.handler.projecttracker.ProjectTrackerJmsMockData;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the Employee related JMS messages for the
 * Project Tracker API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ EmployeeQueryApiHandler.class, JmsClientManager.class, EmployeeApiFactory.class, ContactsApiFactory.class })
public class EmployeeJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.projecttracker";
    public static final int EMPLOYEE_ID = 2000;
    public static final int CONTACT_ID = 900;
    private EmployeeApi mockApi;
    private ContactsApi mockContactApi;


    /**
     * 
     */
    public EmployeeJmsTest() {
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

        mockContactApi = Mockito.mock(ContactsApi.class);
        PowerMockito.mockStatic(ContactsApiFactory.class);
        when(ContactsApiFactory.createApi()).thenReturn(mockContactApi);
        doNothing().when(this.mockContactApi).close();

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
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/employee/EmployeeQueryRequest.xml");
        List<EmployeeDto> apiResults = ProjectTrackerJmsMockData.createMockMultipleExtEmployee();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getEmployeeExt(isA(EmployeeDto.class))).thenReturn(apiResults);
        } catch (EmployeeApiException e) {
            e.printStackTrace();
            Assert.fail("Employee fetch test case failed");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).getEmployeeExt(isA(EmployeeDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
    
    @Test
    public void invokeHandelrSuccess_Update() {
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/employee/EmployeeInsertRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockContactApi.updateContact(isA(PersonalContactDto.class))).thenReturn(CONTACT_ID);
        } catch (ContactsApiException e) {
            Assert.fail("Unable to setup mock stub for creating employee contact record");
        }
        try {
            when(this.mockApi.update(isA(EmployeeDto.class))).thenReturn(EMPLOYEE_ID);
        } catch (EmployeeApiException e) {
            Assert.fail("Unable to setup mock stub for creating employee record");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockContactApi).updateContact(isA(PersonalContactDto.class));
            Mockito.verify(this.mockApi).update(isA(EmployeeDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }

    }

    @Test
    public void invokeHandelrError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File
                .getFileContentsAsString("xml/projecttracker/employee/EmployeeInvalidTransactionCodeRequest.xml");
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
