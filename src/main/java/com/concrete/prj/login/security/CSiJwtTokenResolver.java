package com.concrete.prj.login.security;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: Jwt token resolver
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public interface CSiJwtTokenResolver {

    /**
     * Generates the jwt token from a given user id
     *
     * @param aUserId the identifier of the user
     * @return jwt
     */
    String generateToken(Long aUserId);

    /**
     * Gets the user id from a jwt
     *
     * @param aToken jwt
     * @return the user identifier
     */
    Long getUserIdFromJWT(String aToken);

    /**
     * Validates a given token
     *
     * @param aAuthToken jwt
     * @return status of the token validation
     */
    boolean validateToken(String aAuthToken);

    /**
     * Gets the jwt from the http request
     *
     * @param aHttpRequest http request
     * @return jwt
     */
    String getJwtFromRequest(HttpServletRequest aHttpRequest);
}
