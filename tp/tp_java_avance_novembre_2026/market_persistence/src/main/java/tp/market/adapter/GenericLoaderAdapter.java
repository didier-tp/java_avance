package tp.market.adapter;


import tp.market.core.exception.EntityNotFoundException;
import tp.market.core.model.WithId;
import tp.market.persistence.generic.GenericDAO;
import tp.market.spi.repository.GenericLoader;
import tp.market.util.MyConverter;

import java.util.List;
import java.util.Optional;


public class GenericLoaderAdapter<T extends WithId,E,ID> implements GenericLoader<T,ID> {
    private Class<T> modelClass;
    private Class<E> entityClass;
    private GenericDAO<E,ID> genericDAO;

    public GenericLoaderAdapter(Class<T> modelClass ,
                                Class<E> entityClass ,
                                GenericDAO<E,ID> genericDAO){
        this.entityClass=entityClass;
        this.modelClass=modelClass;
        this.genericDAO=genericDAO;
    }

    @Override
    public T findById(ID id) {
        E entity = genericDAO.findById(id);
        return entity==null?null:MyConverter.map(entity, modelClass);
    }

    @Override
    public Optional<T> loadById(ID id) throws EntityNotFoundException {
        E entity = genericDAO.findById(id);
        if(entity!=null)
            return Optional.of(MyConverter.map(entity, modelClass));
        else
            return Optional.empty();
    }

    @Override
    public List<T> loadAll() {
        List<E> entityList = (List<E>)genericDAO.findAll();
        return MyConverter.map(entityList, modelClass);
    }

    @Override
    public boolean existsWithThisId(ID id)  {
        return genericDAO.existsById(id);
    }

}
