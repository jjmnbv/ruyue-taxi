package com.szyciov.supervision;

/**
 * Created by admin on 2017/7/6.
 */
public class TaxiExecption extends RuntimeException{

    /** serialVersionUID */
    private static final long serialVersionUID = 6417641452178955756L;

    public TaxiExecption() {
        super();
    }

    public TaxiExecption(String message) {
        super(message);
    }

    public TaxiExecption(Throwable cause) {
        super(cause);
    }

    public TaxiExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
