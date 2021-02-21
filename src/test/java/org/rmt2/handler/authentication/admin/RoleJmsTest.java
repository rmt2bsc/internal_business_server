package org.rmt2.handler.authentication.admin;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.dto.CategoryDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.SecurityModuleException;
import org.modules.roles.RoleApi;
import org.modules.roles.RoleSecurityApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;
import org.rmt2.handler.authentication.SecurityMockJmsDtoData;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the role related JMS messages for the
 * Authentication API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, RoleSecurityApiFactory.class })
public class RoleJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.authentication";
    private RoleApi mockApi;


    /**
     * 
     */
    public RoleJmsTest() {
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
        mockApi = Mockito.mock(RoleApi.class);
        PowerMockito.mockStatic(RoleSecurityApiFactory.class);
        when(RoleSecurityApiFactory.createRoleApi()).thenReturn(mockApi);
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
        String request = RMT2File.getFileContentsAsString("xml/authentication/admin/RoleUpdateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.update(isA(CategoryDto.class))).thenReturn(1);
        } catch (SecurityModuleException e) {
            e.printStackTrace();
            Assert.fail("Application update test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).update(isA(CategoryDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

    @Test
    public void invokeHandelrSuccess_Fetch() {
        String request = RMT2File.getFileContentsAsString("xml/authentication/admin/RoleQueryRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.get(isA(CategoryDto.class))).thenReturn(SecurityMockJmsDtoData.createRolesMockData());
        } catch (SecurityModuleException e) {
            e.printStackTrace();
            Assert.fail("Role query test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).get(isA(CategoryDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

    @Test
    public void invokeHandelrSuccess_Delete() {
        String request = RMT2File.getFileContentsAsString("xml/authentication/admin/RoleDeleteRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.delete(isA(Integer.class))).thenReturn(1);
        } catch (SecurityModuleException e) {
            e.printStackTrace();
            Assert.fail("Application delete test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).delete(isA(Integer.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

}
