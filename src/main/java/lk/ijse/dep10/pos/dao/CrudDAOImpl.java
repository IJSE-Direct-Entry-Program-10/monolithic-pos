package lk.ijse.dep10.pos.dao;

import lk.ijse.dep10.pos.entity.SuperEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class CrudDAOImpl<T extends SuperEntity, ID extends Serializable> implements CrudDAO<T, ID>{

    @Autowired
    private SessionFactory sessionFactory;
    private final Class<T> entityClassObj;

    public CrudDAOImpl() {
        entityClassObj = (Class<T>)(((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public long count() throws Exception {
        return getSession().createQuery("SELECT count(e) FROM "+ entityClassObj.getName() + " e", Long.class).uniqueResult();
    }

    @Override
    public T save(T entity) throws Exception {
        getSession().save(entity);
        return entity;
    }

    @Override
    public void update(T entity) throws Exception {
        getSession().update(entity);
    }

    @Override
    public void deleteById(ID pk) throws Exception {
        getSession().delete(getSession().get(entityClassObj, pk));
    }

    @Override
    public Optional<T> findById(ID pk) throws Exception {
        return Optional.ofNullable(getSession().get(entityClassObj, pk));
    }

    @Override
    public List<T> findAll() throws Exception {
        return getSession().createQuery("FROM " + entityClassObj.getName(), entityClassObj).list();
    }

    @Override
    public boolean existsById(ID pk) throws Exception {
        return findById(pk).isPresent();
    }
}
