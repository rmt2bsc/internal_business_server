package org.rmt2.listeners.mdb;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.rmt2.util.JaxbPayloadFactory;

import com.api.messaging.handler.MessageHandlerCommonReplyStatus;
import com.api.messaging.handler.MessageHandlerException;
import com.api.messaging.handler.MessageHandlerResults;
import com.api.messaging.jms.AbstractMessageDrivenBean;
import com.api.messaging.jms.JmsConstants;
import com.api.messaging.webservice.WebServiceConstants;
import com.api.xml.RMT2XmlUtility;

/**
 * Abstract JMS message driven bean implemantation that provides common
 * functionality for processing JAXB specific payloads and provides the measn
 * for the concrete consumer to implement a custom API message handler solution.
 * 
 * @author roy.terrell
 *
 */
public class AbstractMultipleConsumerJaxbMDB extends AbstractMessageDrivenBean {
    
    private static Logger logger = Logger.getLogger(AbstractMultipleConsumerJaxbMDB.class);
    
    protected String requestPayload;

    /**
     * Processes the request message and returns the results to the consumer.
     * <p>
     * This implementation uses the command pattern which basically identifies
     * the message handler designated to process the message, passes the message
     * to the handler, and accepts the results of the message handler. The
     * message handler is required to be of type {@link MessageHandlerCommand}
     * and is identified by use of a command key.
     * <p>
     * The command key is a calculated value and its properties can be found in
     * the message. It is comprised of the following three properties:
     * <i>application</i>, <i>module</i>, and <i>transaction</i>. These three
     * properties are combined together as a String in the format of
     * <i>application.module.transaction</i>. In order to create the
     * MessageHandlerCommand instance, a transaction file, which contains the
     * class name of handler that is associated witht he command key, must be
     * loaded as a ResourceBundle object. The ResouceBundle is identified by the
     * <i>application</i> property of the command key.
     * 
     * @param msg
     * @return an instance of {@link MessageHandlerResults}
     */
    @Override
    protected MessageHandlerResults processMessage(Message message) {
        // Identify the Destination this message came from
        this.requestPayload = null;
        MessageHandlerResults response = null;
        Destination actualDestinationObj = null;
        String actualDestinationName = null;
        try {
            actualDestinationObj = message.getJMSDestination();
            actualDestinationName = this.jms.getInternalDestinationName(actualDestinationObj);
            logger.info("Recieved message from destination, " + actualDestinationName);
        } catch (JMSException e) {
            throw new MessageHandlerException("Error obttaining message destination object", e);
        }

        // For now, accept only TextMessage types
        if (message instanceof TextMessage) {
            try {
                this.requestPayload = ((TextMessage) message).getText();
            } catch (JMSException e) {
                String errMsg = "Error occurred extracting content from JMS TextMessage object";
                
                MessageHandlerCommonReplyStatus errorDetails = new MessageHandlerCommonReplyStatus(); 
                errorDetails.setApplication("unknow application code");   
                errorDetails.setModule( "unknow module");
                errorDetails.setTransaction("unknown transaction");
                errorDetails.setMessage(BUS_SERVER_ERROR);
                errorDetails.setExtMessage(errMsg);
                errorDetails.setReturnCode(JmsConstants.RMT2_JMS_ERROR_MESSAGE_EXTRACTION_FAILURE);
                errorDetails.setReturnStatus(WebServiceConstants.RETURN_STATUS_SERVER_ERROR);
                String xml = this.buildErrorResponse(errorDetails);

                response = new MessageHandlerResults();
                response.setPayload(xml);
                logger.error(BUS_SERVER_ERROR + ":  " + errMsg, e);
                return response;
            }
        }
        try {
            String printXml = RMT2XmlUtility.prettyPrint(this.requestPayload);
            logger.info("MDB request message: ");
            logger.info(printXml);
        } catch (TransformerException e1) {
            logger.info(requestPayload);
            logger.warn("Unable to print request XML in pretty format", e1);
        }

        return response;
    }


    @Override
    protected String buildErrorResponse(MessageHandlerCommonReplyStatus errorDetails) {
        String xml = JaxbPayloadFactory.buildCommonErrorResponseMessageXml(
                errorDetails.getMessage(), errorDetails.getExtMessage(),
                errorDetails.getReturnCode(), errorDetails.getApplication(),
                errorDetails.getModule(), errorDetails.getTransaction());
        return xml;
    }

}
