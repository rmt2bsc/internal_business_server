package org.rmt2.handler.projecttracker.client;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.List;

import org.dto.BusinessContactDto;
import org.dto.ClientDto;
import org.dto.ContactDto;
import org.dto.CustomerDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.ProjectTrackerApiConst;
import org.modules.admin.ProjectAdminApi;
import org.modules.admin.ProjectAdminApiException;
import org.modules.admin.ProjectAdminApiFactory;
import org.modules.contacts.ContactsApi;
import org.modules.contacts.ContactsApiFactory;
import org.modules.subsidiary.CustomerApi;
import org.modules.subsidiary.SubsidiaryApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.api.handlers.admin.client.ClientQueryApiHandler;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;
import org.rmt2.handler.projecttracker.ProjectTrackerJmsMockData;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the client related JMS messages for the
 * Project Tracker API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ClientQueryApiHandler.class, JmsClientManager.class, ProjectAdminApiFactory.class, SubsidiaryApiFactory.class, ContactsApiFactory.class, })
public class ClientJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.projecttracker";
    private ProjectAdminApi mockApi;


    /**
     * 
     */
    public ClientJmsTest() {
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
        this.mockApi = Mockito.mock(ProjectAdminApi.class);
        PowerMockito.mockStatic(ProjectAdminApiFactory.class);
        when(ProjectAdminApiFactory.createApi(eq(ProjectTrackerApiConst.APP_NAME))).thenReturn(this.mockApi);
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
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/admin/ClientQueryRequest.xml");
        List<ClientDto> apiResults = ProjectTrackerJmsMockData.createMockMultipleClient();
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getClient(isA(ClientDto.class))).thenReturn(apiResults);
        } catch (ProjectAdminApiException e) {
            e.printStackTrace();
            Assert.fail("Client fetch test case failed");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).getClient(isA(ClientDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
        
    }
    
    @Test
    public void invokeHandelrSuccess_Import() {
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/admin/ClientImportRequest.xml");
        this.setupMocks(DESTINATION, request);
        
        CustomerApi mockCustApi;
        ContactsApi contactApi;
        
        PowerMockito.mockStatic(SubsidiaryApiFactory.class);
        PowerMockito.mockStatic(ContactsApiFactory.class);
        mockCustApi = Mockito.mock(CustomerApi.class);
        contactApi = Mockito.mock(ContactsApi.class);
        
        try {
            when(SubsidiaryApiFactory.createCustomerApi(isA(String.class))).thenReturn(mockCustApi);
        }
        catch (Exception e) {
            Assert.fail("Unable to setup mock stub for CustomerApi");
        }
        try {
            when(ContactsApiFactory.createApi()).thenReturn(contactApi);
        }
        catch (Exception e) {
            Assert.fail("Unable to setup mock stub for ContactsApi");
        }
        
        
        List<ClientDto> apiResults = ProjectTrackerJmsMockData.createMockMultipleClient();
        try {
            when(this.mockApi.getClient(isA(ClientDto.class))).thenReturn(null, apiResults);
        } catch (ProjectAdminApiException e) {
            e.printStackTrace();
            Assert.fail("Client fetch test case failed");
        }
        CustomerDto mockCustomerData = ProjectTrackerJmsMockData.createMockCustomer().get(0);
        try {
            when(mockCustApi.get(isA(Integer.class))).thenReturn(mockCustomerData);
        } catch (Exception e) {
            Assert.fail("Unable to setup mock stub for fetching customer records");
        }
        
        List<ContactDto> mockContactData = ProjectTrackerJmsMockData.createMockSingleBusinessContactDto();
        try {
            when(contactApi.getContact(isA(BusinessContactDto.class))).thenReturn(mockContactData);
        } catch (Exception e) {
            Assert.fail("Unable to setup mock stub for fetching business contact records");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi, times(4)).getClient(isA(ClientDto.class));
            Mockito.verify(mockCustApi).get(isA(Integer.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandelrSuccess_Delete() {
        String request = RMT2File.getFileContentsAsString("xml/projecttracker/admin/ClientDeleteRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.deleteClient(isA(ClientDto.class))).thenReturn(1);
        } catch (ProjectAdminApiException e) {
            e.printStackTrace();
            Assert.fail("Client fetch test case failed");
        }

        try {
            this.startTest();    
            Mockito.verify(this.mockApi).deleteClient(isA(ClientDto.class));
        }
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }
    
    @Test
    public void invokeHandelrError_Fetch_Incorrect_Trans_Code() {
        String request = RMT2File
                .getFileContentsAsString("xml/projecttracker/ProjectTrackerInvalidTransactionCodeRequest.xml");
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
