package com.concrete.prj.login.exception.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

/**
 * Description: Api error object
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScApiError {

    @JsonIgnore
    private HttpStatus status;

    @JsonProperty("mensagem")
    private String message;

    private CScApiError() {
    }

    /**
     * @param aHttpStatus http aHttpStatus error
     */
    public CScApiError(HttpStatus aHttpStatus) {
        this.status = aHttpStatus;
    }

    /**
     * @return the message error to be returned with a given http status error code
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param aMessage the error aMessage
     */
    public void setMessage(String aMessage) {
        this.message = aMessage;
    }

    /**
     * @return http status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @param status http status
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
