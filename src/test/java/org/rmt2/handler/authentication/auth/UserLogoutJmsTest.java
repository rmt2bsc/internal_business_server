package org.rmt2.handler.authentication.auth;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.authentication.Authenticator;
import org.modules.authentication.AuthenticatorFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;

import com.api.messaging.jms.JmsClientManager;
import com.api.security.authentication.web.LogoutException;
import com.api.util.RMT2File;
import com.api.web.security.RMT2SecurityToken;



/**
 * Test the idenity and invocation of the user logout operation related JMS
 * messages for the Authentication API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, AuthenticatorFactory.class })
public class UserLogoutJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.authentication";
    public static final int APPS_LOGGED_IN_COUNT = 0;
    private Authenticator mockApi;
    private RMT2SecurityToken mockSecurityToken;


    /**
     * 
     */
    public UserLogoutJmsTest() {
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
        mockApi = Mockito.mock(Authenticator.class);
        PowerMockito.mockStatic(AuthenticatorFactory.class);
        when(AuthenticatorFactory.createApi()).thenReturn(mockApi);
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
    public void invokeHandelrSuccess_Logout() {
        String request = RMT2File.getFileContentsAsString("xml/authentication/userauth/UserLogoutRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.logout(isA(String.class))).thenReturn(APPS_LOGGED_IN_COUNT);
        } catch (LogoutException e) {
            e.printStackTrace();
            Assert.fail("User logout test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).logout(isA(String.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

}
