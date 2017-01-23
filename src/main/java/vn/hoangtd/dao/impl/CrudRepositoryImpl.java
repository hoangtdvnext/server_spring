package vn.hoangtd.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import vn.hoangtd.dao.CrudRepository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

/**
 * Created by hoangtd on 1/23/2017.
 */
public class CrudRepositoryImpl<T> implements CrudRepository<T> {
    private final SessionFactory sessionFactory;
    private Session session;
    private static CriteriaBuilder builder;
    private final Class<T> clazz;

    protected CrudRepositoryImpl(SessionFactory sessionFactory, Class<T> clazz) {
        this.sessionFactory = sessionFactory;
        this.clazz = clazz;
        createCriteriaBuilder();
    }

    public Class<T> getClazz() {
        return clazz;
    }

    protected Session getSession() {
        if (session == null) {
            openCurrentSession();
        }
        return session;
    }

    private void openCurrentSession() {
        session = sessionFactory.openSession();
    }

    @Override
    public CriteriaBuilder createCriteriaBuilder() {
        builder = sessionFactory.getCriteriaBuilder();
        return builder;
    }

    @Override
    public List<T> save(List<T> list) {
        openCurrentSession();
        for (int i = 0; i < list.size(); i++) {
            session.save(list.get(i));
            if ((i + 1) % BATCH_SIZE == 0) {
                session.flush();
                session.clear();
            }
        }
        return list;
    }

    @Override
    public T save(T t) {
        openCurrentSession();
        session.save(t);
        return t;
    }

    @Override
    public List<T> update(List<T> list) {
        openCurrentSession();
        Transaction tx = session.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            session.update(list.get(i));
            if ((i + 1) % BATCH_SIZE == 0) {
                session.flush();
                session.clear();
            }
        }
        tx.commit();
        return list;
    }

    @Override
    public T update(T t) {
        openCurrentSession();
        session.update(t);
        return t;
    }

    @Override
    public void delete(List<T> list) {
        openCurrentSession();
        Transaction tx = session.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            session.delete(list.get(i));
            if ((i + 1) % BATCH_SIZE == 0) {
                session.flush();
                session.clear();
            }
        }
        tx.commit();
    }

    @Override
    public void delete(T t) {
        openCurrentSession();
        session.delete(t);
    }

    @Override
    public T findBy(SingularAttribute attribute, Object value) {
        CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root).where(builder.equal(root.get(attribute.getName()), value));
        try {
            return getSession().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
        criteriaQuery.from(clazz);
        return getSession().createQuery(criteriaQuery).list();
    }
}
