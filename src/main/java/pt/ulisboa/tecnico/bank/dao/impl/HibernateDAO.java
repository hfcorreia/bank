package pt.ulisboa.tecnico.bank.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import pt.ulisboa.tecnico.bank.dao.DAO;
import pt.ulisboa.tecnico.bank.domain.DomainObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: Aliaksandra Sankova
 * Date: 11/29/13
 * Time: 3:58 AM
 */
public class HibernateDAO<T extends DomainObject> implements DAO<T> {
    private Class<T> _class;
    private static Logger log = Logger.getLogger(HibernateDAO.class);
    @Autowired
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public HibernateDAO(Class<T> _class) {
        this._class = _class;
    }

    public Long create(T item) {
        Session session = sessionFactory.getCurrentSession();
        Long resultId = (Long) session.save(item);
        session.flush();
        return resultId;
    }

    public T read(Long id) {
        T item = null;
        try {
            item = (T) sessionFactory.getCurrentSession().load(_class, id);
        } catch (org.hibernate.ObjectNotFoundException e) {
            log.warn(e);
        }
        return item;
    }

    public void update(T item) {
        createOrUpdate(item);
    }

    public void delete(T item) {
        item.setDeleteFlag(true);
        sessionFactory.getCurrentSession().update(item);
    }

    public void restore(T item) {
        item.setDeleteFlag(false);
        sessionFactory.getCurrentSession().update(item);
    }

    public List<T> list() {
        Criteria criteria =  sessionFactory.getCurrentSession().createCriteria(_class.getCanonicalName());
        criteria.add(Restrictions.eq("deleteFlag", false));
        return (List<T>) criteria.list();
    }

    public List<T> list(int first, int count) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(_class.getCanonicalName());
        criteria.setFirstResult(first);
        criteria.setMaxResults(count);
        criteria.add(Restrictions.eq("deleteFlag", false));

        return (List<T>) criteria.list();
    }

    public Long size() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(_class.getCanonicalName());
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("deleteFlag", false));

        return (Long) criteria.uniqueResult();
    }

    public void createOrUpdate(T item) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(item);
        session.flush();
    }
}
