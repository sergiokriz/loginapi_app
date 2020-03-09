package com.concrete.prj.login.security;

import com.concrete.prj.login.bl.service.CSiUserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: Authorization jwt filtering
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScJwtAuthorizationFiltering extends OncePerRequestFilter {

    @Autowired
    private CSiJwtTokenResolver jwtTokenResolver;

    @Autowired
    @Qualifier("myUserDetailsService")
    private CSiUserFacade customUserDetailsService;

    /**
     * Does internal filtering for jwt authorization
     *
     * @param aHttpRequest  http request
     * @param aHttpResponse http response
     * @param aFilterChain  filter chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest aHttpRequest, HttpServletResponse aHttpResponse, FilterChain aFilterChain) throws ServletException, IOException {

        String jwt = jwtTokenResolver.getJwtFromRequest(aHttpRequest);

        if (StringUtils.hasText(jwt) && jwtTokenResolver.validateToken(jwt)) {

            Long userId = jwtTokenResolver.getUserIdFromJWT(jwt);
            UserDetails userDetails = customUserDetailsService.loadUserById(userId);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(aHttpRequest));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        aFilterChain.doFilter(aHttpRequest, aHttpResponse);
    }
}
