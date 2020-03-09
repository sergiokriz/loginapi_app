package com.concrete.prj.login.exception.response;

import org.springframework.http.HttpStatus;

/**
 * Description: Conflict exception
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CSxConflictException extends CSxHttpException {

    /**
     * Constructs the CCxConflictException
     *
     * @param aMessage message to be returned with the http status code
     */
    public CSxConflictException(String aMessage) {
        super(HttpStatus.CONFLICT, aMessage);
    }

}
