package org.rmt2.handlers;

import java.io.Serializable;
import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.ReplyStatusType;

import com.RMT2Base;
import com.api.config.ConfigConstants;
import com.api.config.SystemConfigurator;
import com.api.messaging.handler.MessageHandlerResults;
import com.api.messaging.jms.handler.MessageHandlerCommand;
import com.api.messaging.jms.handler.MessageHandlerCommandException;
import com.api.messaging.webservice.WebServiceConstants;
import com.api.xml.jaxb.JaxbUtil;

/**
 * 
 * @author roy.terrell
 *
 * @param <T1> The request type to process
 * @param <T2> The response type to process
 * @param <P> The Payload type to process
 */
public abstract class AbstractMessageHandler<T1, T2, P> extends RMT2Base implements MessageHandlerCommand<T1, T2, P> {

    private static final Logger logger = Logger.getLogger(AbstractMessageHandler.class);

    protected Serializable payload;

    protected JaxbUtil jaxb;

    protected ObjectFactory jaxbObjFactory;

    protected T1 requestObj;
    
    protected T2 responseObj;

    /**
     * 
     */
    public AbstractMessageHandler() {
        this.jaxb = SystemConfigurator.getJaxb(ConfigConstants.JAXB_CONTEXNAME_DEFAULT);
        this.jaxbObjFactory = new ObjectFactory();
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
    public MessageHandlerResults processMessage(String command, Serializable payload) throws MessageHandlerCommandException {
        this.payload = payload;
        if (this.payload instanceof Serializable) {
            logger.info("Payload is identified as a valid Serializable");
        }
        
        MessageHandlerResults results = null;

        // Unmarshall XML String
        String data = this.getPayloadAsString();
        this.requestObj = (T1) this.jaxb.unMarshalMessage(data);
        
        try {
            this.validdateRequest(this.requestObj);
        } catch (Exception e) {
            ReplyStatusType rs = this.createReplyStatus(1, WebServiceConstants.RETURN_STATUS_ERROR, e.getMessage());
            String xml = this.buildResponse(null,rs);
            results = new MessageHandlerResults();
            results.setPayload(xml);
        }
        
        return results;
    }
    
    /**
     * 
     * @param req
     */
    protected abstract void validdateRequest(T1 req) throws InvalidRequestException;
    
    /**
     * 
     * @param payload
     * @param replyStatus
     * @return
     */
    protected abstract String buildResponse(P payload, ReplyStatusType replyStatus);

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
    protected ReplyStatusType createReplyStatus(int returnCode, String statusCode, String message) {
        ReplyStatusType rs = this.jaxbObjFactory.createReplyStatusType();
        rs.setReturnCode(BigInteger.valueOf(returnCode));
        rs.setReturnStatus(statusCode);
        rs.setMessage(message);
        return rs;
    }
}
