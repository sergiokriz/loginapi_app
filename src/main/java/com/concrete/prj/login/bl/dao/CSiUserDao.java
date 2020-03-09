package com.concrete.prj.login.bl.dao;

import com.concrete.prj.login.bl.model.CScUserEO;
import com.concrete.prj.login.bl.model.CScUserRoleEO;
import com.concrete.prj.login.bl.model.CScUserPhoneEO;

import java.util.List;

/**
 * Description: User Dao
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public interface CSiUserDao {

    /**
     * Retrieves the list of users
     *
     * @return list of all users EO
     */
    List<CScUserEO> getAllUsers();

    /**
     * Returns a user of a given id
     *
     * @param aId identifier of the user to be retrieved
     * @return User EO of a given Id
     */
    CScUserEO findUserById(Long aId);

    /**
     * Returns a user of a given name
     *
     * @param aName Name of the user to be found
     * @return User EO of a given name
     */
    CScUserEO findUserByName(String aName);

    /**
     * Returns a user of a given email
     *
     * @param aEmail Email of the user to be found
     * @return User EO of a given email
     */
    CScUserEO findUserByEmail(String aEmail);

    /**
     * Deletes a user of a given id
     *
     * @param aUserId identifier of the user
     */
    void deleteUser(Long aUserId);

    /**
     * Persists a given user
     *
     * @param aUser user EO to be persisted
     */
    void saveUser(CScUserEO aUser);

    /**
     * Persists a given phone
     *
     * @param aPhone phone EO to be persisted
     */
    void savePhone(CScUserPhoneEO aPhone);

    /**
     * Persists a given user role
     *
     * @param aUserRole user role EO to be persisted
     */
    void saveUserRole(CScUserRoleEO aUserRole);
}
