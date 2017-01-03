package org.rmt2.handlers.addressbook.contacts;

import com.RMT2RuntimeException;

/**
 * @author appdev
 *
 */
public class InvalidRequestBusinessContactsException extends RMT2RuntimeException {

    private static final long serialVersionUID = 5215472098798504260L;

    /**
     * 
     */
    public InvalidRequestBusinessContactsException() {
        super();
    }

    /**
     * @param msg
     */
    public InvalidRequestBusinessContactsException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param e
     */
    public InvalidRequestBusinessContactsException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * @param e
     */
    public InvalidRequestBusinessContactsException(Throwable e) {
        super(e);
    }

}
