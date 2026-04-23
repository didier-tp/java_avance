package tp.market.adapter;

import tp.market.core.exception.EntityNotFoundException;
import tp.market.core.model.WithId;
import tp.market.persistence.generic.GenericDAO;
import tp.market.spi.repository.GenericSaver;
import tp.market.util.MyConverter;


public class GenericSaverAdapter<T extends WithId,E,ID> implements GenericSaver<T,ID> {
    private Class<T> modelClass;
    private Class<E> entityClass;
    private GenericDAO<E,ID> genericDao;

    public GenericSaverAdapter(Class<T> modelClass ,
                               Class<E> entityClass ,
                               GenericDAO<E,ID> genericDao){
        this.entityClass=entityClass;
        this.modelClass=modelClass;
        this.genericDao=genericDao;
    }


    @Override
    public T saveNew(T obj) {
        E entity = MyConverter.map(obj,entityClass);
        E savedEntity = genericDao.saveNew(entity);
        return  MyConverter.map(savedEntity,modelClass);
    }

    @Override
    public T saveOrUpdate(T obj) {
        ID id = (ID) obj.extractId();
        E entity = MyConverter.map(obj,entityClass);
        E savedEntity = genericDao.saveOrUpdate(entity,id);
        return  MyConverter.map(savedEntity,modelClass);
    }

    @Override
    public void updateExisting(T obj) throws EntityNotFoundException {
        ID id = (ID) obj.extractId();
        if(!genericDao.existsById(id))
            throw new EntityNotFoundException("entity not found with id="+id);
        E entity = MyConverter.map(obj,entityClass);
        genericDao.update(entity);
    }

    @Override
    public void deleteFromId(ID id) throws EntityNotFoundException {
        if(!genericDao.existsById(id))
            throw new EntityNotFoundException("entity not found with id="+id);
        genericDao.deleteById(id);
    }
}
