package org.rmt2.handlers.addressbook.contacts;

import com.RMT2RuntimeException;

/**
 * @author appdev
 *
 */
public class TooManyContactsException extends RMT2RuntimeException {

    private static final long serialVersionUID = 5215472098798504260L;

    /**
     * 
     */
    public TooManyContactsException() {
        super();
    }

    /**
     * @param msg
     */
    public TooManyContactsException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param e
     */
    public TooManyContactsException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * @param e
     */
    public TooManyContactsException(Throwable e) {
        super(e);
    }

}
