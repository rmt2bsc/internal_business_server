package org.rmt2.util;

import java.util.Date;

import org.rmt2.jaxb.HeaderType;
import org.rmt2.jaxb.ObjectFactory;
import org.rmt2.jaxb.RSCommonReply;
import org.rmt2.jaxb.ReplyStatusType;

import com.api.config.ConfigConstants;
import com.api.config.SystemConfigurator;
import com.api.messaging.webservice.WebServiceConstants;
import com.api.xml.jaxb.JaxbUtil;

/**
 * Utility class for managing messages on the business server.
 * 
 * @author roy.terrell
 *
 */
public class MessageHandlerHelper {

    /**
     * Default constructor
     */
    public MessageHandlerHelper() {
       
    }
    
    /**
     * Create a reply status type object.
     * 
     * @param returnCode
     * @param statusCode
     * @param message
     * @param extMessage
     * @return
     */
    public static final ReplyStatusType createReplyStatus(int returnCode, String statusCode, String message, String extMessage) {
        ObjectFactory fact = new ObjectFactory();
        ReplyStatusType rs = ReplyStatusTypeBuilder.Builder.create()
                .withReturnCode(returnCode)
                .withStatus(statusCode.equals(WebServiceConstants.RETURN_STATUS_SUCCESS) ? true : false)
                .withMessage(message)
                .withDetailMessage(extMessage)
                .build();
        return rs;
    }
    
    /**
     * Build a common response reply which will represent an error message.
     * 
     * @param errMsg
     * @param errCode
     * @param app
     * @param module
     * @param trans
     * @return an instance of {@link RSCommonReply}
     */
    public static final RSCommonReply buildCommonErrorResponseMessage(String errMsg, String extErrMsg, 
            int errCode, String app, String module, String trans) {
        ObjectFactory fact = new ObjectFactory();
        HeaderType header = HeaderTypeBuilder.Builder.create()
                .withApplication(app).withModule(module).withTransaction(trans)
                .withDeliveryDate(new Date()).withRouting("Busiess Server")
                .build();
        
        ReplyStatusType rs = createReplyStatus(errCode, WebServiceConstants.RETURN_STATUS_ERROR, errMsg, extErrMsg);
        RSCommonReply reply = fact.createRSCommonReply();
        reply.setReplyStatus(rs);
        reply.setHeader(header);
        return reply;
    }
    
    /**
     * Build a common response reply, which will represent an error message, and
     * return as an XML String.
     * 
     * @param errMsg
     * @param errCode
     * @param app
     * @param module
     * @param trans
     * @return XML String equivalent of {@link RSCommonReply}
     */
    public static final String buildCommonErrorResponseMessageXml(String errMsg, String extErrMsg, 
            int errCode, String app, String module, String trans) {
        RSCommonReply reply = buildCommonErrorResponseMessage(errMsg, extErrMsg,
                errCode, app, module, trans);
        JaxbUtil jaxb = SystemConfigurator.getJaxb(ConfigConstants.JAXB_CONTEXNAME_DEFAULT);
        String xml = jaxb.marshalJsonMessage(reply);
        return xml;
    }
}
