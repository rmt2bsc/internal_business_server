package org.rmt2.handlers.addressbook.contacts;

import com.RMT2RuntimeException;

/**
 * @author appdev
 *
 */
public class InvalidRequestBusinessContactsCriteriaException extends RMT2RuntimeException {

    private static final long serialVersionUID = 5215472098798504260L;

    /**
     * 
     */
    public InvalidRequestBusinessContactsCriteriaException() {
        super();
    }

    /**
     * @param msg
     */
    public InvalidRequestBusinessContactsCriteriaException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param e
     */
    public InvalidRequestBusinessContactsCriteriaException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * @param e
     */
    public InvalidRequestBusinessContactsCriteriaException(Throwable e) {
        super(e);
    }

}
