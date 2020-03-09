package com.concrete.prj.login.exception;

import com.concrete.prj.login.core.utils.CScResponseErrorBuilder;
import com.concrete.prj.login.exception.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Description: Res response entity exception handler controller adviser
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@ControllerAdvice
public class CScRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles the exceptions that can be thrown by the controllers and their inner services
     *
     * @param aEx     Exception to be converted to a HttpException
     * @param request the request that thrown the exception
     * @return response entity api error object
     */
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class, AccessDeniedException.class})
    protected ResponseEntity<CScApiError> handleInnerControllerExceptions(RuntimeException aEx, WebRequest request) {

        CScResponseErrorBuilder responseErrorBuilder = new CScResponseErrorBuilder();

        if (aEx instanceof IllegalArgumentException) {
            CSxRequestBadRequestException badRequestException = new CSxRequestBadRequestException(aEx.getMessage());
            return responseErrorBuilder.buildResponseEntity(badRequestException);
        } else if (aEx instanceof AccessDeniedException) {
            CSxRequestUnauthorizedException unauthorizedException = new CSxRequestUnauthorizedException(aEx.getMessage());
            return responseErrorBuilder.buildResponseEntity(unauthorizedException);
        } else if (aEx instanceof IllegalStateException) {
            CSxRequestBadRequestException badRequestException = new CSxRequestBadRequestException(aEx.getMessage());
            return responseErrorBuilder.buildResponseEntity(badRequestException);
        } else {
            CSxRequestInternalErrorException internalErrorException = new CSxRequestInternalErrorException(aEx.getMessage());
            return responseErrorBuilder.buildResponseEntity(internalErrorException);
        }
    }

    /**
     * Builds a response entity object from a "common" http exception
     *
     * @param aEx     exception used to build the response entity api error object
     * @param request the request that thrown the exception
     * @return response entity api error object
     */
    @ExceptionHandler(value = {CSxHttpException.class})
    protected ResponseEntity<CScApiError> handleHttpExceptions(CSxHttpException aEx, WebRequest request) {

        CScResponseErrorBuilder responseErrorBuilder = new CScResponseErrorBuilder();
        return responseErrorBuilder.buildResponseEntity(aEx);
    }

}
