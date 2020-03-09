package com.concrete.prj.login.bl.dao;

import com.concrete.prj.login.bl.model.CScUserEO;
import com.concrete.prj.login.bl.model.CScUserPhoneEO;
import com.concrete.prj.login.bl.model.CScUserRoleEO;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * Description: User DAO
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

@Repository
public class CScUserDao extends CScAbstractDao implements CSiUserDao {

    public List<CScUserEO> getAllUsers() {

        DetachedCriteria criteria = DetachedCriteria.forClass(CScUserEO.class);
        List<CScUserEO> userList = findByCriteria(criteria);
        return userList;
    }

    /*
    public List<User> getAllUsers() {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u ORDER BY u.id", User.class);
        return query.getResultList();
    }
     */

    @Override
    public CScUserEO findUserById(Long aId) {

        CScUserEO userEO = resolveData(CScUserEO.class, aId, LockMode.NONE);
        return userEO;
    }

    @Override
    public CScUserEO findUserByName(String aName) {

        DetachedCriteria criteria = DetachedCriteria.forClass(CScUserEO.class);
        criteria.add(Restrictions.eq("userName", aName));

        CScUserEO userEO = findUniqueResult(criteria);
        return userEO;
    }

    @Override
    public CScUserEO findUserByEmail(String aEmail) {

        DetachedCriteria criteria = DetachedCriteria.forClass(CScUserEO.class);
        criteria.add(Restrictions.eq("email", aEmail));

        CScUserEO userEO = findUniqueResult(criteria);
        return userEO;
    }

    @Override
    public void deleteUser(Long aUserId) {

        CScUserEO userEO = findUserById(aUserId);
        if(userEO != null) {
            deleteData(userEO);
        }
    }

    @Override
    public void saveUser(CScUserEO aUser) {
        insertData(aUser);
    }

    @Override
    public void savePhone(CScUserPhoneEO aPhone) {
        insertData(aPhone);
        aPhone.getUser().getPhoneSet().add(aPhone);
    }

    @Override
    public void saveUserRole(CScUserRoleEO aUserRole) {
        insertData(aUserRole);
        aUserRole.getUser().getUserRoleSet().add(aUserRole);
    }
}
