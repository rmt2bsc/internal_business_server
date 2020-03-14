package org.rmt2.listeners.mdb;

import org.rmt2.util.JaxbPayloadFactory;

import com.api.messaging.handler.MessageHandlerCommonReplyStatus;
import com.api.messaging.jms.AbstractMessageDrivenBean;;

/**
 * Abstract JMS message driven bean implemantation that handles common logic for
 * processing JAXB specific data
 * 
 * @author roy.terrell
 *
 */
public class AbstractJaxbMessageDrivenBean extends AbstractMessageDrivenBean {

    @Override
    protected String buildErrorResponse(MessageHandlerCommonReplyStatus errorDetails) {
        String xml = JaxbPayloadFactory.buildCommonErrorResponseMessageXml(
                errorDetails.getMessage(), errorDetails.getExtMessage(),
                errorDetails.getReturnCode(), errorDetails.getApplication(),
                errorDetails.getModule(), errorDetails.getTransaction());
        return xml;
    }

}
