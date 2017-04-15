package org.rmt2.listeners.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MessageListener;

import org.rmt2.config.constants.BusinessServerConstants;
import org.rmt2.handlers.AbstractMessageDrivenBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Message-Driven Bean implementation class for Media system
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = BusinessServerConstants.JMS_DEST_MEDIA_QUEUE),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") },
        mappedName = BusinessServerConstants.JMS_DEST_MEDIA_QUEUE)
public class MediaBean extends AbstractMessageDrivenBean implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(MediaBean.class);

    public MediaBean() {
        super();
        logger.info(MediaBean.class.getSimpleName() + " was created successfully");
    }
}
