package com.concrete.prj.login.exception.response;

import org.springframework.http.HttpStatus;

/**
 * Description: Request bad request exception
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CSxRequestBadRequestException extends CSxHttpException {

    /**
     * Constructs the CCxRequestBadRequestException
     *
     * @param aMessage message to be returned with the http status code
     */
    public CSxRequestBadRequestException(String aMessage) {
        super(HttpStatus.BAD_REQUEST, aMessage);
    }
}
