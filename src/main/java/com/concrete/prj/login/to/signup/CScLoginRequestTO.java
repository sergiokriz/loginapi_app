package com.concrete.prj.login.to.signup;

/**
 * Description: Login request transfer object
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScLoginRequestTO {

    private String email;
    private String password;

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
