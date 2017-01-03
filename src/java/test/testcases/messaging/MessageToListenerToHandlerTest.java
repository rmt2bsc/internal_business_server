package testcases.messaging;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.RMT2RuntimeException;
import com.api.config.ConfigConstants;
import com.api.config.SystemConfigurator;
import com.api.messaging.jms.JmsClientManager;
import com.api.messaging.jms.JmsConnectionManager;
import com.api.messaging.jms.JmsConstants;
import com.api.xml.jaxb.JaxbUtil;

/**
 * NOTE =========================== Before executing this test, rename
 * 
 * @jndi.properties to jndi.properties which is located in the resources folder.
 * 
 * @author appdev
 * 
 */
public class MessageToListenerToHandlerTest {
    private static final String DESTINATION_ACCOUNTING = "Accounting";
    private static final String DESTINATION_CONTACTS = "Contacts";

    protected JmsClientManager jms;

    protected JaxbUtil jaxb;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        SystemConfigurator config = new SystemConfigurator();
        try {
            config.start("/AppServer/config/RMT2AppServerConfig.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        JmsConnectionManager jmsConMgr = JmsConnectionManager
                .getJmsConnectionManger();

        // Get the appropriate JMS connection based on the messaging
        // system specified in transaction's configuration.
        Connection con = jmsConMgr.getConnection(JmsConstants.DEFAUL_MSG_SYS);
        if (con == null) {
            Assert.fail("Error obtaining a connection for JMS client");
        }

        // Associate JMS connection with the client
        this.jms = new JmsClientManager(con, jmsConMgr.getJndi());

        this.jaxb = SystemConfigurator
                .getJaxb(ConfigConstants.JAXB_CONTEXNAME_RMT2);
        return;
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        // JmsConnectionManager.getJmsConnectionManger().removeConnection();
    }

    /**
     * 
     * @param destinationName
     * @param msg
     * @return
     */
    protected Message testSynchronizedListener(String destinationName,
            String msg) {
        Message response = null;
        try {
            TextMessage txtMsg = this.jms.createTextMessage(destinationName);
            txtMsg.setText(msg);
            Destination replyToDest = this.jms.createReplyToDestination(
                    destinationName, txtMsg);
            // Destination replyToDest =
            // this.jms.createReplyToDestination(txtMsg);
            this.jms.send(destinationName, txtMsg);
            response = this.jms.listen(replyToDest, 10000);
            return response;
        } catch (Exception e) {
            throw new RMT2RuntimeException(e);
        } finally {
            this.jms.stop(destinationName);
        }
    }

    // @Test
    // public void testSyncAccountingMessageConsumption() {
    // Message response = null;
    // try {
    // response = this.testSynchronizedListener(
    // MessageToListenerToHandlerTest.DESTINATION_ACCOUNTING,
    // this.getAccountingMessage());
    // } catch (Exception e) {
    // Assert.fail(e.getMessage());
    // }
    //
    // Assert.assertNotNull(response);
    // Assert.assertTrue(response instanceof TextMessage);
    // try {
    // System.out.println("Reply: " + ((TextMessage) response).getText());
    // } catch (JMSException e) {
    // e.printStackTrace();
    // }
    // }
    //
    // @Test
    // public void testSyncContactsMessageConsumption() {
    // Message response = null;
    // try {
    // response = this.testSynchronizedListener(
    // MessageToListenerToHandlerTest.DESTINATION_CONTACTS,
    // this.getContactsMessage());
    // } catch (Exception e) {
    // Assert.fail(e.getMessage());
    // }
    //
    // Assert.assertNotNull(response);
    // Assert.assertTrue(response instanceof TextMessage);
    // try {
    // System.out.println("Reply: " + ((TextMessage) response).getText());
    // } catch (JMSException e) {
    // e.printStackTrace();
    // }
    // }
    //
    // private String getContactsMessage() {
    //
    // StringBuilder b = new StringBuilder();
    //
    // b.append("<RQ_business_contact_search>\n");
    // b.append("  <header>\n");
    // b.append("      <message_id>RQ_business_contact_search</message_id>\n");
    // b.append("      <application>contacts</application>\n");
    // b.append("      <module>business</module>\n");
    // b.append("      <transaction>getBusiness</transaction>\n");
    // b.append("      <delivery_mode>SYNC</delivery_mode>\n");
    // b.append("      <message_mode>REQUEST</message_mode>\n");
    // b.append("      <delivery_date>02/13/2016 14:36:54</delivery_date>\n");
    // b.append("      <user_id>admin</user_id>\n");
    // b.append("  </header>\n");
    // b.append("  <criteria_data>\n");
    // b.append("      <business_id>1430</business_id>\n");
    // b.append("      <entity_type xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\"/>\n");
    // b.append("      <service_type xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\"/>\n");
    // b.append("  </criteria_data>\n");
    // b.append("</RQ_business_contact_search>\n");
    // return b.toString();
    // }
    //
    // private String getAccountingMessage() {
    // StringBuilder b = new StringBuilder();
    //
    // b.append("<RQ_accounting_maintenance>\n");
    // b.append("  <header>\n");
    // b.append("      <message_id>RQ_accounting_maintenance</message_id>\n");
    // b.append("      <application>accounting</application>\n");
    // b.append("      <module>accounts</module>\n");
    // b.append("      <transaction>getBusiness</transaction>\n");
    // b.append("      <delivery_mode>SYNC</delivery_mode>\n");
    // b.append("      <message_mode>REQUEST</message_mode>\n");
    // b.append("      <delivery_date>02/13/2016 14:36:54</delivery_date>\n");
    // b.append("      <user_id>admin</user_id>\n");
    // b.append("  </header>\n");
    // b.append("  <criteria_data>\n");
    // b.append("      <business_id>1430</business_id>\n");
    // b.append("      <entity_type xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\"/>\n");
    // b.append("      <service_type xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\"/>\n");
    // b.append("  </criteria_data>\n");
    // b.append("</RQ_accounting_maintenance>\n");
    // return b.toString();
    // }

}
