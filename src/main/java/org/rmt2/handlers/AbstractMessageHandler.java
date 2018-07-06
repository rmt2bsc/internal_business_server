package org.rmt2.handlers;

import java.io.Serializable;

import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.ReplyStatusType;
import org.rmt2.util.JaxbPayloadFactory;

import com.RMT2Base;
import com.api.config.ConfigConstants;
import com.api.config.SystemConfigurator;
import com.api.messaging.InvalidRequestException;
import com.api.messaging.handler.MessageHandlerCommand;
import com.api.messaging.handler.MessageHandlerCommandException;
import com.api.messaging.handler.MessageHandlerResults;
import com.api.messaging.webservice.WebServiceConstants;
import com.api.xml.RMT2XmlUtility;
import com.api.xml.jaxb.JaxbUtil;

/**
 * 
 * @author roy.terrell
 *
 * @param <T1> The request type to process
 * @param <T2> The response type to process
 * @param <P> The Payload type to process for responses and/or request update data
 */
public abstract class AbstractMessageHandler<T1, T2, P> extends RMT2Base implements MessageHandlerCommand<T1, T2, P> {

    private static final Logger logger = Logger.getLogger(AbstractMessageHandler.class);

    public static final String ERROR_MSG_TRANS_NOT_FOUND = "Unable to identify transaction code: ";

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
        String reqXml = this.getPayloadAsString();
        this.requestObj = (T1) this.jaxb.unMarshalMessage(reqXml);
        if (logger.isDebugEnabled()) {
            try {
                String printXml = RMT2XmlUtility.prettyPrint(reqXml);
                logger.debug(printXml);
            } catch (TransformerException e1) {
                logger.debug(reqXml);
                e1.printStackTrace();
            }
        }
        
        try {
            this.validateRequest(this.requestObj);
        } catch (Exception e) {
            ReplyStatusType rs = JaxbPayloadFactory.createReplyStatus(1,
                    WebServiceConstants.RETURN_STATUS_ERROR, e.getMessage(), null);
            String respXml = this.buildResponse(null,rs);
            results = new MessageHandlerResults();
            results.setPayload(respXml);

            if (logger.isDebugEnabled()) {
                try {
                    String printXml = RMT2XmlUtility.prettyPrint(respXml);
                    logger.debug(printXml);
                } catch (TransformerException e1) {
                    logger.debug(reqXml);
                    e1.printStackTrace();
                }
            }
        }
        
        return results;
    }
    
    /**
     * 
     * @param req
     */
    protected abstract void validateRequest(T1 req) throws InvalidRequestException;
    
    /**
     * Uses a generic payload type to build the payload response and/or the
     * payload representing data that is to be updated in a request.
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
     * Creates an error reply as XML String
     * 
     * @param errorCode
     * @param msg
     * @return
     */
    protected MessageHandlerResults createErrorReply(int errorCode, String msg) {
        MessageHandlerResults results = new MessageHandlerResults();
        ReplyStatusType rs = JaxbPayloadFactory.createReplyStatus(errorCode, WebServiceConstants.RETURN_STATUS_ERROR, msg, null);
        String xml = this.buildResponse(null, rs);
        results.setPayload(xml);
        return results;
    }
}
