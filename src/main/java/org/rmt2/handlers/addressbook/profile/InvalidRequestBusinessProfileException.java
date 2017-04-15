package org.rmt2.handlers.addressbook.profile;

import com.RMT2RuntimeException;

/**
 * @author appdev
 *
 */
public class InvalidRequestBusinessProfileException extends RMT2RuntimeException {

    private static final long serialVersionUID = 5215472098798504260L;

    /**
     * 
     */
    public InvalidRequestBusinessProfileException() {
        super();
    }

    /**
     * @param msg
     */
    public InvalidRequestBusinessProfileException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param e
     */
    public InvalidRequestBusinessProfileException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * @param e
     */
    public InvalidRequestBusinessProfileException(Throwable e) {
        super(e);
    }

}
