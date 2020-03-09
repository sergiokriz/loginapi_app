package com.concrete.prj.login.to.signup;

import com.concrete.prj.login.to.core.securiry.CScJwtTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Description: Sign up response transfer object
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScSignUpResponseTO {

    Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    Date created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    Date modified;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    Date lastLogin;
    CScJwtTO token = new CScJwtTO();

    /**
     * @return the identifier of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * @param aId the identifier of the user
     */
    public void setId(Long aId) {
        id = aId;
    }

    /**
     * @return created at
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param aCreated created at
     */
    public void setCreated(Date aCreated) {
        created = aCreated;
    }

    /**
     * @return modified at
     */
    public Date getModified() {
        return modified;
    }

    /**
     * @param aModified modified at
     */
    public void setModified(Date aModified) {
        modified = aModified;
    }

    /**
     * @return date of the last login
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * @param aLastLogin date of the last login
     */
    public void setLastLogin(Date aLastLogin) {
        lastLogin = aLastLogin;
    }

    /**
     * @return jwt transfer object
     */
    public CScJwtTO getToken() {
        return token;
    }

    /**
     * @param aToken jwt transfer object
     */
    public void setToken(CScJwtTO aToken) {
        this.token = aToken;
    }

}
