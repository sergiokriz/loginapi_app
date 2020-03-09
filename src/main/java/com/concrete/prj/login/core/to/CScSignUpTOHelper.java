package com.concrete.prj.login.core.to;

import com.concrete.prj.login.bl.model.CScUserEO;
import com.concrete.prj.login.to.signup.CScSignUpResponseTO;

/**
 * Description: Sign up TO helper
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public class CScSignUpTOHelper {

    /**
     * Fills the TO
     * @param aSignUpResponseTO TO
     * @param aUserEO user EO
     * @return Filled TO
     */
    public static CScSignUpResponseTO fillSignUpResponseTO(CScSignUpResponseTO aSignUpResponseTO,
                                                           CScUserEO aUserEO) {

        aSignUpResponseTO.setId(aUserEO.getUserId());
        aSignUpResponseTO.setCreated(aUserEO.getCreatedAt());
        aSignUpResponseTO.setModified(aUserEO.getModifiedAt());
        aSignUpResponseTO.setLastLogin(aUserEO.getLastLoginAt());
        aSignUpResponseTO.getToken().setAccessToken(aUserEO.getJwt());

        return aSignUpResponseTO;
    }
}
