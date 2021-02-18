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
                        propertyValue = BusinessServerConstants.JMS_DEST_AUTHENTICATION_QUEUE),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") },
        mappedName = BusinessServerConstants.JMS_DEST_AUTHENTICATION_QUEUE)
public class AuthenticationBean extends AbstractSingleConsumerJaxbMDB implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationBean.class);

    public AuthenticationBean() {
        super();
        logger.info(AuthenticationBean.class.getSimpleName() + " was created successfully");
    }
}
