package org.rmt2.handlers.addressbook.profile;

import com.RMT2RuntimeException;

/**
 * @author appdev
 *
 */
public class InvalidRequestPersonProfileException extends RMT2RuntimeException {

    private static final long serialVersionUID = 5215472098798504260L;

    /**
     * 
     */
    public InvalidRequestPersonProfileException() {
        super();
    }

    /**
     * @param msg
     */
    public InvalidRequestPersonProfileException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param e
     */
    public InvalidRequestPersonProfileException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * @param e
     */
    public InvalidRequestPersonProfileException(Throwable e) {
        super(e);
    }

}
