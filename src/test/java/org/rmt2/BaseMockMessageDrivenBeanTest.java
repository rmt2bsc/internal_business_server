package org.rmt2;

import static org.mockito.Mockito.when;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.junit.Before;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.rmt2.handlers.AbstractMessageDrivenBean;

import com.api.config.SystemConfigurator;
import com.api.messaging.jms.JmsClientManager;
import com.util.RMT2File;

/**
 * Base class for testing the API handlers.
 * <p>
 * 
 * @author royterrell
 *
 */
public class BaseMockMessageDrivenBeanTest extends AbstractMessageDrivenBean {
    private Message mockJMSMessageRequest;
    private TextMessage mockJMSTextMessageReply;
    private Destination mockJMSDestination;
    private Destination mockJMSReplyDestination;
    private JmsClientManager mockJmsClientManger;

    private static String APP_CONFIG_FILENAME;

    @Before
    public void setUp() throws Exception {
        String curDir = RMT2File.getCurrentDirectory();
        APP_CONFIG_FILENAME = curDir + "/src/test/resources/config/TestAppServerConfig.xml";
        SystemConfigurator appConfig = new SystemConfigurator();
        appConfig.start(APP_CONFIG_FILENAME);
    }

    protected void setupMocks(String jmsDestName, String requestPayload) {
        this.mockJMSMessageRequest = Mockito.mock(TextMessage.class);
        this.mockJMSTextMessageReply = Mockito.mock(TextMessage.class);
        this.mockJMSDestination = Mockito.mock(Destination.class);
        this.mockJMSReplyDestination = Mockito.mock(Destination.class);
        this.mockJmsClientManger = Mockito.mock(JmsClientManager.class);

        PowerMockito.mockStatic(JmsClientManager.class);
        when(JmsClientManager.getInstance()).thenReturn(this.mockJmsClientManger);
        try {
            when(this.mockJMSMessageRequest.getJMSDestination()).thenReturn(this.mockJMSDestination);
            when(((TextMessage) this.mockJMSMessageRequest).getText()).thenReturn(requestPayload);
            when(this.mockJMSMessageRequest.getJMSReplyTo()).thenReturn(this.mockJMSReplyDestination);
        } catch (JMSException e) {

        }
        when(this.mockJmsClientManger.getInternalDestinationName(this.mockJMSDestination)).thenReturn(jmsDestName);

        try {
            when(this.mockJmsClientManger.createTextMessage()).thenReturn(this.mockJMSTextMessageReply);
        } catch (Exception e) {

        }
    }

    protected void startTest() {
        this.onMessage(mockJMSMessageRequest);
    }
}

