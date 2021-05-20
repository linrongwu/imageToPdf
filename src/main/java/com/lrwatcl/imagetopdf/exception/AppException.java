package com.lrwatcl.imagetopdf.exception;

/**
 * @author String
 */
public class AppException extends Exception{
    public AppException(String message) {
        super(message);
    }
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
    public AppException(Throwable cause) {
        super(cause);
    }
}