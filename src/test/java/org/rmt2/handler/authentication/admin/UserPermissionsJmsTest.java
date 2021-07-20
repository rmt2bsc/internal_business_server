package org.rmt2.handler.authentication.admin;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.dto.CategoryDto;
import org.dto.UserDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.SecurityModuleException;
import org.modules.roles.RoleSecurityApiFactory;
import org.modules.roles.UserAppRoleApi;
import org.modules.users.UserApi;
import org.modules.users.UserApiException;
import org.modules.users.UserApiFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;
import org.rmt2.handler.authentication.SecurityMockJmsDtoData;

import com.api.messaging.jms.JmsClientManager;
import com.api.util.RMT2File;



/**
 * Test the idenity and invocation of the user application role related JMS
 * messages for the Authentication API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, UserApiFactory.class, RoleSecurityApiFactory.class })
public class UserPermissionsJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.authentication";
    private UserApi mockApi;
    private UserAppRoleApi mockUserAppRoleApi;


    /**
     * 
     */
    public UserPermissionsJmsTest() {
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

        // Fetches user data
        mockApi = Mockito.mock(UserApi.class);
        PowerMockito.mockStatic(UserApiFactory.class);
        when(UserApiFactory.createApiInstance()).thenReturn(mockApi);
        doNothing().when(this.mockApi).close();

        // Fetches user app role data
        mockUserAppRoleApi = Mockito.mock(UserAppRoleApi.class);
        PowerMockito.mockStatic(RoleSecurityApiFactory.class);
        when(RoleSecurityApiFactory.createUserAppRoleApi()).thenReturn(mockUserAppRoleApi);
        doNothing().when(this.mockUserAppRoleApi).close();
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
        String request = RMT2File.getFileContentsAsString("xml/authentication/admin/UserPermissionsQueryRequest.xml");
        this.setupMocks(DESTINATION, request);

        try {
            when(this.mockApi.getUser(isA(UserDto.class))).thenReturn(SecurityMockJmsDtoData.createVwUserMockData());
        } catch (UserApiException e) {
            Assert.fail("Unable to setup mock stub for fetching user record");
        }

        try {
            when(this.mockUserAppRoleApi.getAssignedRoles(isA(CategoryDto.class)))
                    .thenReturn(SecurityMockJmsDtoData.createVwUserAppRolesMockData());
        } catch (SecurityModuleException e) {
            Assert.fail("Unable to setup mock stub for fetching user application role record");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).getUser(isA(UserDto.class));
            Mockito.verify(this.mockUserAppRoleApi, times(5)).getAssignedRoles(isA(CategoryDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

}
