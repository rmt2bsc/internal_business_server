package org.rmt2.listeners.mdb;

import java.util.List;
import java.util.ResourceBundle;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.rmt2.util.JaxbPayloadFactory;

import com.RMT2RuntimeException;
import com.SystemException;
import com.api.messaging.handler.MessageHandlerCommand;
import com.api.messaging.handler.MessageHandlerCommandException;
import com.api.messaging.handler.MessageHandlerCommonReplyStatus;
import com.api.messaging.handler.MessageHandlerException;
import com.api.messaging.handler.MessageHandlerResults;
import com.api.messaging.jms.AbstractMessageDrivenBean;
import com.api.messaging.jms.JmsConstants;
import com.api.messaging.webservice.WebServiceConstants;
import com.api.util.RMT2File;
import com.api.util.RMT2String;
import com.api.util.RMT2Utility;
import com.api.xml.RMT2XmlUtility;

/**
 * Abstract JMS message driven bean implemantation that provides common functionality for 
 * processing JAXB specific payloads and dynamically identifies the appropriated API message handler for processing the payload.
 * 
 * @author roy.terrell
 *
 */
public class AbstractSingleConsumerJaxbMDB extends AbstractMessageDrivenBean {
    
    private static Logger logger = Logger.getLogger(AbstractSingleConsumerJaxbMDB.class);
    
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
        String requestPayload = null;
        String responsePayload = null;
        if (message instanceof TextMessage) {
            try {
                requestPayload = ((TextMessage) message).getText();
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

                MessageHandlerResults response = new MessageHandlerResults();
                response.setPayload(xml);
                logger.error(BUS_SERVER_ERROR + ":  " + errMsg, e);
                return response;
            }
        }
        try {
            String printXml = RMT2XmlUtility.prettyPrint(requestPayload);
            logger.info("MDB request message: ");
            logger.info(printXml);
        } catch (TransformerException e1) {
            logger.info(requestPayload);
            logger.warn("Unable to print request XML in pretty format", e1);
        }

        String app = RMT2XmlUtility.getElementValue(WebServiceConstants.APPLICATION, requestPayload);
        String module = RMT2XmlUtility.getElementValue(WebServiceConstants.MODULE, requestPayload);
        String trans = RMT2XmlUtility.getElementValue(WebServiceConstants.TRANSACTION, requestPayload);
        String routing = RMT2XmlUtility.getElementValue(WebServiceConstants.ROUTING, requestPayload);

        // Determine if the message was sent to the correct destination
        // TODO: Provide logic to obtain system level flag to determine whether
        // or not destination integrity should be performed
        String msgRoutingDestinamtionName = null;
        List<String> routingTokens = RMT2String.getTokens(routing, ":");
        if (routingTokens != null && routingTokens.size() == 2) {
            msgRoutingDestinamtionName = routingTokens.get(1).trim();
            if (!msgRoutingDestinamtionName.equalsIgnoreCase(actualDestinationName)) {
                String errMsg = "Message was sent the incorrect destination.  Actual destination [" + actualDestinationName
                        + "] Required destination [" + msgRoutingDestinamtionName + "]";

                MessageHandlerCommonReplyStatus errorDetails = new MessageHandlerCommonReplyStatus();
                errorDetails.setApplication(app);
                errorDetails.setModule(module);
                errorDetails.setTransaction(trans);
                errorDetails.setMessage(BUS_SERVER_ERROR);
                errorDetails.setExtMessage(errMsg);
                errorDetails.setReturnCode(JmsConstants.RMT2_JMS_ERROR_TRANSCODE_MSGHANDLER_CONFIG_NOTFOUND);
                errorDetails.setReturnStatus(WebServiceConstants.RETURN_STATUS_SERVER_ERROR);
                String xml = this.buildErrorResponse(errorDetails);

                MessageHandlerResults response = new MessageHandlerResults();
                response.setPayload(xml);
                logger.error(BUS_SERVER_ERROR + ":  " + errMsg);
                return response;
            }
        }
        else {
            // TODO: remove this condition branch once the above destination
            // integrity flag logic is implemented.
            logger.info("Actual and Intended destination integrity check was bypassed!");
        }

        // Create handler mapping key name
        String commandKey = this.createCommandKey(app, module, trans);

        // Load transacton code / message handler mappings based on
        // application name.
        ResourceBundle handlerMapping = null;
        try {
            handlerMapping = this.getApplicationMappings(app);
        } catch (Exception e) {
            String errMsg = "Unable to locate Transaction Code / Message Handler configuration.  ";

            MessageHandlerCommonReplyStatus errorDetails = new MessageHandlerCommonReplyStatus();
            errorDetails.setApplication(app);
            errorDetails.setModule(module);
            errorDetails.setTransaction(trans);
            errorDetails.setMessage(BUS_SERVER_ERROR);
            errorDetails.setExtMessage(errMsg + e.getMessage());
            errorDetails.setReturnCode(JmsConstants.RMT2_JMS_ERROR_TRANSCODE_MSGHANDLER_CONFIG_NOTFOUND);
            errorDetails.setReturnStatus(WebServiceConstants.RETURN_STATUS_SERVER_ERROR);
            String xml = this.buildErrorResponse(errorDetails);

            MessageHandlerResults response = new MessageHandlerResults();
            response.setPayload(xml);
            logger.error(BUS_SERVER_ERROR + ":  " + errMsg, e);
            return response;
        }

        // Instantiate Handler class
        MessageHandlerCommand apiHandler = null;
        try {
            apiHandler = this.getApiHandlerInstance(commandKey, handlerMapping);
        } catch (Exception e) {
            String errMsg = "Unable to determine the API that would be responsible for processing the message.  Check the header's application, module, and transaction values for possible corrections.";

            MessageHandlerCommonReplyStatus errorDetails = new MessageHandlerCommonReplyStatus(); 
            errorDetails.setApplication(app);   
            errorDetails.setModule(module);
            errorDetails.setTransaction(trans);
            errorDetails.setMessage(BUS_SERVER_ERROR);
            errorDetails.setExtMessage(errMsg);
            errorDetails.setReturnCode(JmsConstants.RMT2_JMS_ERROR_HANDLER_INVOCATION_PROBLEM);
            errorDetails.setReturnStatus(WebServiceConstants.RETURN_STATUS_SERVER_ERROR);
            String xml = this.buildErrorResponse(errorDetails);
         
            MessageHandlerResults response = new MessageHandlerResults();
            response.setPayload(xml);
            logger.error(BUS_SERVER_ERROR + ":  " + errMsg, e);
            logger.info(xml);
            return response;
        }

        // Invoke handler.
        try {
            MessageHandlerResults response = apiHandler.processMessage(trans, requestPayload);
            if (response != null && response.getPayload() != null) {
                responsePayload = response.getPayload().toString();
                try {
                    String printXml = RMT2XmlUtility.prettyPrint(responsePayload);
                    logger.info("MDB response message: ");
                    logger.info(printXml);
                } catch (TransformerException e1) {
                    logger.info(responsePayload);
                    logger.warn("Unable to print response XML in pretty format", e1);
                }
            }
            return response;
        } catch (MessageHandlerCommandException e) {
            throw new RMT2RuntimeException("Error executing message handler for command, " + commandKey.toString(), e);
        }
    }

    /**
     * Generates the command key which is used in dynamically identifying the
     * appropriate message handler at runtime.
     * <p>
     * The command key is a calculated value and its properties can be found in
     * the message. It is comprised of the following three properties:
     * <i>application</i>, <i>module</i>, and <i>transaction</i>. These three
     * properties are combined together as a String in the format of
     * <i>application.module.transaction</i> and returned to the caller.
     * 
     * @param app
     *            the name of the application
     * @param module
     *            the name of the application module
     * @param transaction
     *            the transaction
     * @return the command key in the format of
     *         <i>application.module.transaction</i>.
     */
    private String createCommandKey(String app, String module, String transaction) {
        StringBuilder commandKey = new StringBuilder();
        commandKey.append(app).append(".").append(module).append(".").append(transaction);
        return commandKey.toString();
    }

    /**
     * Load handler mappings based on header data.
     * <p>
     * Example Properties file: org.rmt2.config.HandlerMappings_Accounting
     * 
     * @param app
     * @return
     */
    private ResourceBundle getApplicationMappings(String app) {
        String mappingConfig = System.getProperty(JmsConstants.MSG_HANDLER_MAPPINGS_KEY) + "."
                + JmsConstants.HANDLER_MAPPING_CONFIG + "_" + app;
        ResourceBundle rb = RMT2File.loadAppConfigProperties(mappingConfig);
        return rb;
    }

    /**
     * Identifies, instantiates, and returns the MessageHandlerCommand instance
     * to the caller based of the command key.
     * 
     * @param commandKey
     *            the command key used to identify the message handler to
     *            instantiate
     * @param config
     *            the data source containing the message configuration used to
     *            lookup the transaction details.
     * @return an instance of {@link MessageHandlerCommand}
     */
    private MessageHandlerCommand getApiHandlerInstance(String commandKey, ResourceBundle config) {
        String handlerClassName = null;
        MessageHandlerCommand handler = null;
        try {
            // Get handler class from mapping file
            handlerClassName = config.getString(commandKey + ".handler");
            // Instantiate Handler class
            handler = (MessageHandlerCommand) RMT2Utility.getClassInstance(handlerClassName);
        } catch (Exception e) {
            throw new SystemException("Failed to create handler, " + commandKey + ".handler"
                    + ".  Check handler mapping configuration",
                    e);
        }

        return handler;
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
