package tp.market.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

public class MyJpaUtil {

    private static EntityManagerFactory emf=null;
    private static boolean hbm2ddlAuto=false; //or "create"

    private static ThreadLocal<EntityTransaction> transactionThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

    public static EntityManagerFactory entityManagerFactory(){
        if(emf==null) emf=initEmf();
        return emf;
    }

    public static void setHbm2ddlAuto(boolean hbm2ddlAuto){
        MyJpaUtil.hbm2ddlAuto=hbm2ddlAuto;
    }

    public static boolean getHbm2ddlAuto(){
        return MyJpaUtil.hbm2ddlAuto;
    }

    public static EntityManagerFactory initEmf(){
        EntityManagerFactory emf =null;
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto?"create":"none");
        /*emf = Persistence.createEntityManagerFactory("market");*/
       emf = Persistence.createEntityManagerFactory("market",props);
        //NB: market= name du persistent-unit configuré dans META-INF/persistence.xml
        return emf;
    }

    public static EntityManager entityManager(){
        EntityManager existingEntityManager  = entityManagerThreadLocal.get();
        if(existingEntityManager!=null)
            return existingEntityManager;
        /*else*/
        return entityManagerFactory().createEntityManager();
    }


    public static void beginGlobalJpaTransaction(){
        EntityManager entityManager = entityManager();
        entityManagerThreadLocal.set(entityManager);
        EntityTransaction globalTransaction = entityManager.getTransaction();
        transactionThreadLocal.set(globalTransaction);
        globalTransaction.begin();
    }

    public static void commitGlobalJpaTransaction(){
        EntityTransaction globalTransaction = transactionThreadLocal.get();
        transactionThreadLocal.remove();
        globalTransaction.commit();
        EntityManager entityManager  = entityManagerThreadLocal.get();
        entityManagerThreadLocal.remove();
        entityManager.close();
    }

    public static void rollbackGlobalJpaTransaction(){
        EntityTransaction globalTransaction = transactionThreadLocal.get();
        transactionThreadLocal.remove();
        if (globalTransaction.isActive()) {
            globalTransaction.rollback();
        }
        EntityManager entityManager  = entityManagerThreadLocal.get();
        entityManagerThreadLocal.remove();
        entityManager.close();
    }

    public static void doInTransaction(Consumer<EntityManager> work){
        execOrDoInTransaction(null,work); //no return value
    }

    public static Object execInTransaction(Function<EntityManager,Object> function){
        return execOrDoInTransaction(function,null); //with return value (to be casted)
    }

    public static Object execOrDoInTransaction(Function<EntityManager,Object> function , Consumer<EntityManager> work){
        Object result=null;
        EntityTransaction localTransaction =null;
        EntityManager entityManager = entityManager();
        EntityTransaction existingGlobalTransaction = transactionThreadLocal.get();
        if(existingGlobalTransaction==null) {
            localTransaction = entityManager.getTransaction();
        }
        try {
            if(existingGlobalTransaction==null) {
                localTransaction.begin();
            }
            if(function!=null) {
                result = function.apply(entityManager); //with return value
            }
            if(work!=null){
                work.accept(entityManager);//no return value
            }
            if(localTransaction!=null) {
                localTransaction.commit();
            }
        }
        catch (Exception e) {
            if (localTransaction!=null && localTransaction.isActive()) {
                    localTransaction.rollback();
            }
            throw e;
        }
        finally {
            if(localTransaction!=null) {
                entityManager.close();
            }
        }
        return result;
    }

}
