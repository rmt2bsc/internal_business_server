package org.rmt2.handler.authentication.auth;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.dto.UserDto;
import org.dto.adapter.orm.Rmt2OrmDtoFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modules.SecurityModuleException;
import org.modules.authentication.Authenticator;
import org.modules.authentication.AuthenticatorFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.rmt2.handler.BaseMockSingleConsumerMDBTest;

import com.api.messaging.jms.JmsClientManager;
import com.api.security.User;
import com.api.util.RMT2File;
import com.api.web.security.RMT2SecurityToken;



/**
 * Test the idenity and invocation of the Single Sign On (SSO) user login
 * related JMS messages for the Authentication API Message Handler.
 * 
 * @author appdev
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ JmsClientManager.class, AuthenticatorFactory.class })
public class SsoUserLoginJmsTest extends BaseMockSingleConsumerMDBTest {

    private static final String DESTINATION = "rmt2.queue.authentication";
    private Authenticator mockApi;
    private RMT2SecurityToken mockSecurityToken;


    /**
     * 
     */
    public SsoUserLoginJmsTest() {
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
        
        this.mockSecurityToken = this.setupSecurityToken();
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

    private RMT2SecurityToken setupSecurityToken() {
        UserDto user = Rmt2OrmDtoFactory.getNewUserInstance();
        user.setLoginUid(7777);
        user.setUsername("test_username");
        user.setTotalLogons(5);
        user.setActive(1);
        user.setLoggedIn(1);
        user.setFirstname("roy");
        user.setLastname("terrell");
        RMT2SecurityToken token = new RMT2SecurityToken();
        User tokenUser = Rmt2OrmDtoFactory.getUserInstance(user);
        tokenUser.addRole("Role1");
        tokenUser.addRole("Role2");
        tokenUser.addRole("Role3");
        tokenUser.addRole("Role4");
        token.update(tokenUser);
        return token;
    }

    @Test
    public void invokeHandelrSuccess_Login() {
        String request = RMT2File.getFileContentsAsString("xml/authentication/userauth/SsoUserLoginRequest.xml");
        this.setupMocks(DESTINATION, request);
        try {
            when(this.mockApi.authenticate(isA(String.class))).thenReturn(this.mockSecurityToken);
        } catch (SecurityModuleException e) {
            e.printStackTrace();
            Assert.fail("User SSO test case failed");
        }

        try {
            this.startTest();
            Mockito.verify(this.mockApi).authenticate(isA(String.class));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("An unexpected exception was thrown");
        }
    }

}
