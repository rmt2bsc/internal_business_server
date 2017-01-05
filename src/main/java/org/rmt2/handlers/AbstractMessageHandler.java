package org.rmt2.handlers;

import java.io.Serializable;
import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.modules.contacts.ContactsApi;
import org.modules.contacts.ContactsApiFactory;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.ReplyStatusType;

import com.RMT2Base;
import com.api.config.ConfigConstants;
import com.api.config.SystemConfigurator;
import com.api.messaging.handler.MessageHandlerResults;
import com.api.messaging.jms.handler.MessageHandlerCommand;
import com.api.messaging.jms.handler.MessageHandlerCommandException;
import com.api.xml.jaxb.JaxbUtil;

/**
 * @author Roy Terrell
 * 
 */
public abstract class AbstractMessageHandler extends RMT2Base implements
        MessageHandlerCommand {

    private static final Logger logger = Logger
            .getLogger(AbstractMessageHandler.class);

    protected Serializable payload;

    protected JaxbUtil jaxb;

    protected ObjectFactory f;

    protected ContactsApiFactory cf;
    protected ContactsApi api;

    // protected ReplyStatusType rs;

    /**
     * 
     */
    public AbstractMessageHandler() {
        this.jaxb = SystemConfigurator
                .getJaxb(ConfigConstants.JAXB_CONTEXNAME_DEFAULT);
        this.f = new ObjectFactory();
        this.cf = new ContactsApiFactory();
        this.api = cf.createApi();
        return;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.api.messaging.jms.handler.MessageHandlerCommand#processRequest(java
     * .lang.String, java.io.Serializable)
     */
    @Override
    public MessageHandlerResults processMessage(String command,
            Serializable payload) throws MessageHandlerCommandException {
        this.payload = payload;
        if (this.payload instanceof Serializable) {
            logger.info("Payload is identified as a valid Serializable");
        }
        return null;
    }

    /**
     * Return payload as a String.
     * 
     * @return
     */
    public String getPayloadAsString() {
        return this.payload.toString();
    }

    /**
     * 
     * @param returnCode
     * @param statusCode
     * @param message
     * @return
     */
    protected ReplyStatusType createReplyStatus(int returnCode,
            String statusCode, String message) {
        ReplyStatusType rs = this.f.createReplyStatusType();
        rs.setReturnCode(BigInteger.valueOf(returnCode));
        rs.setReturnStatus(statusCode);
        rs.setMessage(message);
        return rs;
    }
}
