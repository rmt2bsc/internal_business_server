package org.rmt2.handlers.media;

import org.rmt2.handlers.AbstractMessageHandler;
import org.rmt2.jaxb.MimeContentType;
import org.rmt2.jaxb.MultimediaRequest;
import org.rmt2.jaxb.MultimediaResponse;
import org.rmt2.jaxb.ReplyStatusType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.messaging.InvalidRequestException;

/**
 * @author appdev
 *
 */
public class MediaPayloadHandler extends AbstractMessageHandler<MultimediaRequest, MultimediaResponse, MimeContentType> {
    private static final Logger logger = LoggerFactory.getLogger(MediaPayloadHandler.class);

    /**
     * @param payload
     */
    public MediaPayloadHandler() {
        super();
        this.responseObj = this.jaxbObjFactory.createMultimediaResponse();
        logger.info(MediaPayloadHandler.class.getName() + " was instantiated successfully");
    }

    @Override
    protected void validateRequest(MultimediaRequest req) throws InvalidRequestException {
        if (req == null) {
            throw new InvalidRequestException("Multimedia message request element is invalid");
        }
    }

    @Override
    protected String buildResponse(MimeContentType payload, ReplyStatusType replyStatus) {
        if (replyStatus != null) {
            this.responseObj.setReplyStatus(replyStatus);    
        }
        
        if (payload != null) {
            this.responseObj.setContent(payload);
        }
        
        String xml = this.jaxb.marshalMessage(this.responseObj);
        return xml;
    }
}
