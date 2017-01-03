package org.rmt2.handlers;

import com.RMT2RuntimeException;

/**
 * @author appdev
 *
 */
public class InvalidRequestProfileException extends RMT2RuntimeException {

    private static final long serialVersionUID = 5215472098798504260L;

    /**
     * 
     */
    public InvalidRequestProfileException() {
        super();
    }

    /**
     * @param msg
     */
    public InvalidRequestProfileException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param e
     */
    public InvalidRequestProfileException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * @param e
     */
    public InvalidRequestProfileException(Throwable e) {
        super(e);
    }

}
