package tp.market.persistence.generic;

import tp.market.persistence.jpa.MyJpaUtil;

import java.util.List;

/*
JPA GenericDao for pure java project (without spring, without jakartaEE integration)
like JPARepository of springBoot , but very simple code here:
=====
usage:
public class XyzDaoJpa extends GenericDaoJpa<XyzEntity,String_or_Long> implements XyzDAO{
   public XyzDaoJpa(){
        super(XyzEntity.class);
    }
    ...
}
 */

public class GenericDaoJpa<E,ID> implements GenericDAO<E,ID> {

    private Class<E> entityClass;

    public GenericDaoJpa( Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public boolean existsById(ID id) {
        return (this.findById(id)!=null);
    }

    @Override
    public List<E> findAll() {
        List<E> entityList = null;
        /*
        EntityManager entityManager = MyJpaUtil.entityManager();
        entityList=entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e",entityClass).getResultList();
        entityManager.close();
         */
        entityList = (List<E>) MyJpaUtil.execInTransaction(entityManager ->
                entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e",entityClass).getResultList()
        );
        return entityList;
    }

    @Override
    public E findById(ID id) {
        E entity = null;
        /*
        EntityManager entityManager = MyJpaUtil.entityManager();
        entity=entityManager.find(entityClass,id);
        entityManager.close();
        */
        entity = (E) MyJpaUtil.execInTransaction(entityManager ->
                entityManager.find(entityClass,id)
        );
        return entity;
    }

    @Override
    public E saveNew(E entity) {
        MyJpaUtil.doInTransaction(entityManager -> {
            entityManager.persist(entity);
        });
        return entity;
    }

    @Override
    public void update(E entity) {
        MyJpaUtil.doInTransaction(entityManager -> {
            entityManager.merge(entity);
        });
    }

    @Override
    public E saveOrUpdate(E entity,ID id) {
        if(id==null) {
            return saveNew(entity);
        }
        else {
            E exististingEntity = findById(id);
            if(exististingEntity==null)
                return saveNew(entity);
            else {
                update(entity);
                return entity;
            }
        }
    }

    @Override
    public void deleteById(ID id) {
        MyJpaUtil.doInTransaction(entityManager -> {
            E entity=entityManager.find(entityClass,id);
            entityManager.remove(entity);
        });
    }
}

/*
NB: with additional framework like JakartaEE or Spring :
-------------
@Transactional
public void update(E entity) {
            entityManager.merge(entity);
    }
à la place de
 public void update(E entity) {
        MyJpaUtil.doInTransaction(entityManager -> {
            entityManager.merge(entity);
        });
    }


 */
