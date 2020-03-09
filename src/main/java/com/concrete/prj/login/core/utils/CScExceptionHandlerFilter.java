package com.concrete.prj.login.core.utils;

import com.concrete.prj.login.exception.response.CSxHttpException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: Exception filtering handler
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScExceptionHandlerFilter extends OncePerRequestFilter {

    /**
     * Handles the exceptions of the first filter of the filter chain
     *
     * @param aHttpServletRequest  request
     * @param aHttpServletResponse response
     * @param filterChain          calling of the filter chain
     * @throws ServletException exceptions than can be thrown by the inner filters
     * @throws RuntimeException internal system exceptions
     */
    @Override
    protected void doFilterInternal(HttpServletRequest aHttpServletRequest, HttpServletResponse aHttpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {

            filterChain.doFilter(aHttpServletRequest, aHttpServletResponse);

        } catch (CSxHttpException ex) {

            CScResponseErrorBuilder responseErrorBuilder = new CScResponseErrorBuilder();
            responseErrorBuilder.buildResponseJson(ex);
            aHttpServletResponse.setStatus(responseErrorBuilder.getHttpStatusCode());
            aHttpServletResponse.getWriter().write(responseErrorBuilder.getBodyJsonObject());
            aHttpServletResponse.flushBuffer();

        } catch (RuntimeException ex) {

            CScResponseErrorBuilder responseErrorBuilder = new CScResponseErrorBuilder();
            responseErrorBuilder.buildResponseJson(ex);
        }
    }
}
