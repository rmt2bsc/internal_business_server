package org.rmt2.listeners.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MessageListener;

import org.rmt2.config.constants.BusinessServerConstants;
import org.rmt2.handlers.AbstractMessageDrivenBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Message-Driven Bean implementation class for the AddressBook system
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = BusinessServerConstants.JMS_DEST_CONTACT_QUEUE),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") },
        mappedName = BusinessServerConstants.JMS_DEST_CONTACT_QUEUE)
public class AddressBookBean extends AbstractMessageDrivenBean implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(AddressBookBean.class);

    public AddressBookBean() {
        super();
        logger.info(AddressBookBean.class.getSimpleName() + " was created successfully");
    }
}
