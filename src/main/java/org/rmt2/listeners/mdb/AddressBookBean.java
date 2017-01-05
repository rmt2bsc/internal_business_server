package org.rmt2.listeners.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MessageListener;

import org.rmt2.config.BusinessServerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.messaging.jms.handler.AbstractMessageDrivenBean;

/**
 * Message-Driven Bean implementation class for: AddressBookBean
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(
                        propertyName = "destination",
                        propertyValue = BusinessServerConstants.JMS_DEST_CONTACT_QUEUE),
                @ActivationConfigProperty(propertyName = "destinationType",
                        propertyValue = "javax.jms.Queue") },
        mappedName = BusinessServerConstants.JMS_DEST_CONTACT_QUEUE)
public class AddressBookBean extends AbstractMessageDrivenBean implements
        MessageListener {
    private static Logger logger = LoggerFactory
            .getLogger(AddressBookBean.class);

    // /*
    // * (non-Javadoc)
    // *
    // * @see
    // * org.rmt2.listeners.mdb.AbstractJmsListenerBean#processMessage(java.lang
    // * .String)
    // */
    // @Override
    // protected MessageHandlerResults processMessage(String msg) {
    // BusinessContactMsgHandler p = new BusinessContactMsgHandler(msg);
    // MessageHandlerResults results = p.processPayload();
    // logger.info("MDB response message: " + results.getPayload());
    // return results;
    // }

}
