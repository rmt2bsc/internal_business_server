package org.rmt2.handlers.addressbook.contacts;

import com.RMT2RuntimeException;

/**
 * @author appdev
 *
 */
public class NoContactsAvailableException extends RMT2RuntimeException {

    private static final long serialVersionUID = 5215472098798504260L;

    /**
     * 
     */
    public NoContactsAvailableException() {
        super();
    }

    /**
     * @param msg
     */
    public NoContactsAvailableException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param e
     */
    public NoContactsAvailableException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * @param e
     */
    public NoContactsAvailableException(Throwable e) {
        super(e);
    }

}
