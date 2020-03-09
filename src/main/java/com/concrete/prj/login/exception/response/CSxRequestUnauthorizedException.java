package com.concrete.prj.login.exception.response;

import org.springframework.http.HttpStatus;

/**
 * Description: Request unauthorized exception
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CSxRequestUnauthorizedException extends CSxHttpException {

    /**
     * Constructs the CCxRequestUnauthorizedException
     *
     * @param aMessage message to be returned with the http status code
     */
    public CSxRequestUnauthorizedException(String aMessage) {
        super(HttpStatus.UNAUTHORIZED, aMessage);
    }
}
