package org.rmt2.handlers.addressbook.profile;

import com.RMT2RuntimeException;

/**
 * @author appdev
 *
 */
public class InvalidRequestBusinessProfileCriteriaException extends RMT2RuntimeException {

    private static final long serialVersionUID = 5215472098798504260L;

    /**
     * 
     */
    public InvalidRequestBusinessProfileCriteriaException() {
        super();
    }

    /**
     * @param msg
     */
    public InvalidRequestBusinessProfileCriteriaException(String msg) {
        super(msg);
    }

    /**
     * @param msg
     * @param e
     */
    public InvalidRequestBusinessProfileCriteriaException(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * @param e
     */
    public InvalidRequestBusinessProfileCriteriaException(Throwable e) {
        super(e);
    }

}
