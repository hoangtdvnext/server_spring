package vn.hoangtd.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

/**
 * Created by hoangtd on 1/23/2017.
 */
public interface CrudRepository<T> {
    int BATCH_SIZE = 100;

    CriteriaBuilder createCriteriaBuilder();

    List<T> save(List<T> list);

    T save(T t);

    List<T> update(List<T> list);

    T update(T t);

    void delete(List<T> list);

    void delete(T t);

    T findBy(SingularAttribute attribute, Object value);

    List<T> findAll();
}
