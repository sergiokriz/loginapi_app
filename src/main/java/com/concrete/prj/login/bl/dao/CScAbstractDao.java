package com.concrete.prj.login.bl.dao;

import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * Description: Abstract Data Access Object
 * Concrete API Challenge.
 *
 * @author : Sergio Kriz
 **/

public abstract class CScAbstractDao extends HibernateDaoSupport {

    @Autowired
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    public CScAbstractDao() {
    }

    @PostConstruct
    public void init() {
        this.setSessionFactory(this.sessionFactory);
    }

    /**
     * Inserts the EO
     *
     * @param aNewDataEO EO
     * @param <T>        EO class
     * @return loaded EO
     */
    protected <T> T insertData(T aNewDataEO) {

        Serializable dataId = getHibernateTemplate().save(aNewDataEO);
        T loadedDataEO = (T) getHibernateTemplate().load(aNewDataEO.getClass(), dataId);

        return loadedDataEO;
    }

    /**
     * Deletes the EO
     *
     * @param aDataEO EO
     * @param <T>     EO class
     */
    protected <T> void deleteData(T aDataEO) {
        getHibernateTemplate().delete(aDataEO);
    }

    /**
     * Resolves the EO by a given Id using the passed Lock mode
     *
     * @param aEntityClass EO class
     * @param aDataId      identifier
     * @param aLockMode    lock mode
     * @param <T>          EO class
     * @return EO
     */
    protected <T> T resolveData(Class<T> aEntityClass, Long aDataId, LockMode aLockMode) {

        assert getHibernateTemplate() != null;
        T dataEO = getHibernateTemplate().get(aEntityClass, aDataId, aLockMode);

        return dataEO;
    }

    /**
     * Finds a unique occurrence of a given criteria
     *
     * @param aCriteria detached criteria
     * @param <T>       EO
     * @return EO
     */
    protected <T> T findUniqueResult(DetachedCriteria aCriteria) {
        /*unchecked*/
        T result = (T) aCriteria.getExecutableCriteria(currentSession()).uniqueResult();

        return result;
    }

    /**
     * Finds the list of occurrences of a given criteria
     *
     * @param aCriteria detached criteria
     * @param <T>       EO class
     * @return EO list
     */
    protected <T> List<T> findByCriteria(DetachedCriteria aCriteria) {

        List<T> recordList = (List<T>) getHibernateTemplate().findByCriteria(aCriteria,
                -1, -1);

        return recordList;
    }

}
