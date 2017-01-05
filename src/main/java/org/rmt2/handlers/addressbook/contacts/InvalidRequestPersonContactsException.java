package org.rmt2.handlers.addressbook.contacts;

import com.RMT2RuntimeException;

/**
 * @author appdev
 *
 */
public class InvalidRequestPersonContactsException extends RMT2RuntimeException {

    private static final long serialVersionUID = 5215472098798504260L;

    /**
     * 
     */
    public InvalidRequestPersonContactsException() {
        super();
    }

    /**
     * @param msg
     */
    public InvalidRequestPersonContactsException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param e
     */
    public InvalidRequestPersonContactsException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * @param e
     */
    public InvalidRequestPersonContactsException(Throwable e) {
        super(e);
    }

}
