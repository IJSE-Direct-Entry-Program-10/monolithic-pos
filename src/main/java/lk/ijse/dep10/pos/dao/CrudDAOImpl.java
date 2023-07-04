package lk.ijse.dep10.pos.dao;

import lk.ijse.dep10.pos.entity.SuperEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class CrudDAOImpl<T extends SuperEntity, ID extends Serializable> implements CrudDAO<T, ID>{

    @Override
    public long count() throws Exception {
        return 0;
    }

    @Override
    public T save(T entity) throws Exception {
        return null;
    }

    @Override
    public void update(T entity) throws Exception {

    }

    @Override
    public void deleteById(ID pk) throws Exception {

    }

    @Override
    public Optional<T> findById(ID pk) throws Exception {
        return Optional.empty();
    }

    @Override
    public List<T> findAll() throws Exception {
        return null;
    }

    @Override
    public boolean existsById(ID pk) throws Exception {
        return false;
    }
}
