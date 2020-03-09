package com.concrete.prj.login.to.core.securiry;

/**
 * Description: JWT transfer object
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScJwtTO {

    private String accessToken;
    private String tokenType = "Bearer";

    /**
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param aAccessToken the access token
     */
    public void setAccessToken(String aAccessToken) {
        this.accessToken = aAccessToken;
    }

    /**
     * @return type of the token
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param aTokenType type of the token
     */
    public void setTokenType(String aTokenType) {
        this.tokenType = aTokenType;
    }
}
