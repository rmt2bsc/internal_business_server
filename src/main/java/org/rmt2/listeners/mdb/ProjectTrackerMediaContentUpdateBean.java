package org.rmt2.listeners.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.rmt2.api.handlers.timesheet.TimesheetAttachDocumentApiHandler;
import org.rmt2.config.constants.BusinessServerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.messaging.handler.MessageHandlerCommandException;
import com.api.messaging.handler.MessageHandlerResults;
import com.api.messaging.webservice.WebServiceConstants;
import com.api.xml.RMT2XmlUtility;

/**
 * Message-Driven Bean implementation class for media content updates targeting
 * the Project Tracker system
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = BusinessServerConstants.JMS_DEST_MEDIA_TOPIC),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
        // , @ActivationConfigProperty(propertyName = "subscriptionDurability",
        // propertyValue = "Durable"),
        // @ActivationConfigProperty(propertyName = "clientId",
        // propertyValue =
        // BusinessServerConstants.JMS_TOPIC_CLIENT_ID_PROJECTTRACKER_ATTACHMENT)
        })
// do not include mappedName property, because mappedName will cause the
// creation of destination to default to queue instead of topic
// , mappedName = BusinessServerConstants.JMS_DEST_MEDIA_TOPIC)
public class ProjectTrackerMediaContentUpdateBean extends AbstractMultipleConsumerJaxbMDB implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(ProjectTrackerMediaContentUpdateBean.class);

    public ProjectTrackerMediaContentUpdateBean() {
        super();
        logger.info(ProjectTrackerMediaContentUpdateBean.class.getSimpleName() + " was created successfully");
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.rmt2.listeners.mdb.AbstractMultipleConsumerJaxbMDB#processMessage
     * (javax.jms.Message)
     */
    @Override
    protected MessageHandlerResults processMessage(Message message) {
        MessageHandlerResults rc = super.processMessage(message);
        if (rc == null) {
            String trans = RMT2XmlUtility.getElementValue(WebServiceConstants.TRANSACTION, this.requestPayload);
            // Call API handler and return result. This is an example
            TimesheetAttachDocumentApiHandler api = new TimesheetAttachDocumentApiHandler();
            try {
                api.processMessage(trans, this.requestPayload);
            } catch (MessageHandlerCommandException e) {
                e.printStackTrace();
            }
        }

        // return result from ancestor call
        return rc;
    }
}
