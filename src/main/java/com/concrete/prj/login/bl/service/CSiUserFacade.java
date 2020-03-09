package com.concrete.prj.login.bl.service;

import com.concrete.prj.login.bl.model.CScUserEO;
import com.concrete.prj.login.to.signup.CScLoginRequestTO;
import com.concrete.prj.login.to.signup.CScSignUpRequestTO;
import com.concrete.prj.login.to.signup.CScSignUpResponseTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Description: User facade
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public interface CSiUserFacade extends UserDetailsService {

    /**
     * Returns the User EO from a given id
     *
     * @param aId identifier of the user
     * @return User EO
     */
    CScUserEO findUserById(Long aId);

    /**
     * Returns the User EO from a given name
     *
     * @param aName user name
     * @return User EO
     */
    CScUserEO findUserByName(String aName);

    /**
     * Returns the User EO from a given email
     *
     * @param aEmail user email
     * @return User EO
     */
    CScUserEO findUserByEmail(String aEmail);

    /**
     * Registers a new user
     *
     * @param aSignUpRequestTO user transfer object
     * @return response TO
     */
    CScSignUpResponseTO registerUser(CScSignUpRequestTO aSignUpRequestTO);

    /**
     * Registers the login of a given user
     *
     * @param aLoginRequestTO login request TO
     * @return response TO
     */
    CScSignUpResponseTO registerLogin(CScLoginRequestTO aLoginRequestTO);

    /**
     * Gets the information of a single user
     * @param aUserId identifier of the user
     * @return response TO
     */
    CScSignUpResponseTO getUserProfile(Long aUserId);

    /**
     * Resolver the Spring Object UserDetails from a given user Id.
     *
     * @param aUserId identifier of the user
     * @return Spring UserDetails
     */
    UserDetails loadUserById(Long aUserId);
}
