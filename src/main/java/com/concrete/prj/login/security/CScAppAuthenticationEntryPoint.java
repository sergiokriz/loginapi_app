package com.concrete.prj.login.security;

import com.concrete.prj.login.core.utils.CScResponseErrorBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: Commences an authentication scheme
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@Component
public class CScAppAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Commences an authentication scheme
     *
     * @param aHttpServletRequest  http request
     * @param aHttpServletResponse http response
     * @param aEx                  authentication exception
     */
    @Override
    public void commence(HttpServletRequest aHttpServletRequest,
                         HttpServletResponse aHttpServletResponse,
                         AuthenticationException aEx) throws IOException, ServletException {

        CScResponseErrorBuilder responseErrorBuilder = new CScResponseErrorBuilder();
        responseErrorBuilder.buildResponseJson(aEx);
        aHttpServletResponse.setStatus(responseErrorBuilder.getHttpStatusCode());
        aHttpServletResponse.getWriter().write(responseErrorBuilder.getBodyJsonObject());
        aHttpServletResponse.flushBuffer();
    }
}
