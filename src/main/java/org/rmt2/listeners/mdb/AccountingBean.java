package org.rmt2.listeners.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MessageListener;

import org.rmt2.config.BusinessServerConstants;
import org.rmt2.handlers.AbstractMessageDrivenBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Message-Driven Bean implementation class for: AccountingBean
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = BusinessServerConstants.JMS_DEST_ACCOUNTING_QUEUE),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") },
        mappedName = BusinessServerConstants.JMS_DEST_ACCOUNTING_QUEUE)
public class AccountingBean extends AbstractMessageDrivenBean implements MessageListener {
    private static Logger logger = LoggerFactory.getLogger(AccountingBean.class);

    // /*
    // * (non-Javadoc)
    // *
    // * @see
    // * org.rmt2.listeners.mdb.AbstractJmsListenerBean#processMessage(java.lang
    // * .String)
    // */
    // @Override
    // protected MessageHandlerResults processMessage(String msg) {
    // AccountingPayloadHandler p = new AccountingPayloadHandler(msg);
    // MessageHandlerResults results = p.processPayload();
    // logger.info("MDB response message: " + results.getPayload());
    // return results;
    //
    // }

}
