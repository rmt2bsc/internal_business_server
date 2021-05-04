package org.rmt2.handler.authentication.admin;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.dto.UserDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
 * Test the idenity and invocation of the user related JMS messages for the
 * Authentication API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, UserApiFactory.class })
public class UserJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.authentication";
    private UserApi mockApi;


    /**
     * 
     */
    public UserJmsTest() {
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
        mockApi = Mockito.mock(UserApi.class);
        PowerMockito.mockStatic(UserApiFactory.class);
        when(UserApiFactory.createApiInstance()).thenReturn(mockApi);
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
        String request = RMT2File.getFileContentsAsString("xml/authentication/admin/UserUpdateRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.updateUser(isA(UserDto.class))).thenReturn(1);
        } catch (UserApiException e) {
            e.printStackTrace();
            Assert.fail("User Group update test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).updateUser(isA(UserDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

    @Test
    public void invokeHandelrSuccess_Fetch() {
        String request = RMT2File.getFileContentsAsString("xml/authentication/admin/UserQueryRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.getUser(isA(UserDto.class))).thenReturn(SecurityMockJmsDtoData.createVwUserMockData());
        } catch (UserApiException e) {
            e.printStackTrace();
            Assert.fail("User Group update test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).getUser(isA(UserDto.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

    @Test
    public void invokeHandelrSuccess_Delete() {
        String request = RMT2File.getFileContentsAsString("xml/authentication/admin/UserDeleteRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.deleteUser(isA(Integer.class))).thenReturn(1);
        } catch (UserApiException e) {
            e.printStackTrace();
            Assert.fail("User Group update test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).deleteUser(isA(Integer.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

}
