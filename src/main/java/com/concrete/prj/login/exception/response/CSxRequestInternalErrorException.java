package com.concrete.prj.login.exception.response;

import org.springframework.http.HttpStatus;

/**
 * Description: Request internal error exception
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CSxRequestInternalErrorException extends CSxHttpException {

    /**
     * Constructs the CCxRequestInternalErrorException
     *
     * @param aMessage message to be returned with the http status code
     */
    public CSxRequestInternalErrorException(String aMessage) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, aMessage);
    }
}
