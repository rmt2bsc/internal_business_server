package org.rmt2.handlers.media;

import org.rmt2.handlers.AbstractMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author appdev
 *
 */
public class MediaPayloadHandler extends AbstractMessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(MediaPayloadHandler.class);

    /**
     * @param payload
     */
    public MediaPayloadHandler() {
        super();
        logger.info(MediaPayloadHandler.class.getName() + " was instantiated successfully");
    }
}
