package tp.market.persistence.generic;

import java.util.List;

/*
DAO = Data Access Object with CRUD methods (Create,Retreive,Update,Delete)
GenericDAO<E,ID> where E = entity type , ID = primaryKey type
=====
usage:
public interface XyzDAO extends GenericDAO<XyzEntity,String_or_Long> { ...}
 */

public interface GenericDAO<E,ID> {
    public List<E> findAll();
    public E findById(ID id);
    public E saveNew(E entity);
    public void update(E entity);
    public E saveOrUpdate(E entity,ID id);
    public void deleteById(ID id);
}
