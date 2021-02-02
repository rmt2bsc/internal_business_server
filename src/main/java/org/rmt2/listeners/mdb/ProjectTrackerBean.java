package org.rmt2.listeners.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MessageListener;

import org.rmt2.config.constants.BusinessServerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Message-Driven Bean implementation class for the Accounting system
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = BusinessServerConstants.JMS_DEST_PROJECTTRACKER_QUEUE),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") },
        mappedName = BusinessServerConstants.JMS_DEST_PROJECTTRACKER_QUEUE)
public class ProjectTrackerBean extends AbstractSingleConsumerJaxbMDB implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(ProjectTrackerBean.class);

    public ProjectTrackerBean() {
        super();
        logger.info(ProjectTrackerBean.class.getSimpleName() + " was created successfully");
    }
}
