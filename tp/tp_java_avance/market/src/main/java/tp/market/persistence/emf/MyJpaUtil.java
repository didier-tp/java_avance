package tp.market.persistence.emf;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.Properties;
import java.util.function.Consumer;

public class MyJpaUtil {

    private static EntityManagerFactory emf=null;

    public static EntityManagerFactory entityManagerFactory(){
        if(emf==null) emf=initEmf();
        return emf;
    }

    public static EntityManagerFactory initEmf(){
        EntityManagerFactory emf =null;
        Properties props = new Properties();
        //props.setProperty("jakarta.persistence.jdbc.user", "root");
        emf = Persistence.createEntityManagerFactory("market");
        /*EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("market",props);*/
        //NB: market= name du persistent-unit configuré dans META-INF/persistence.xml
        return emf;
    }

    public static EntityManager entityManager(){
        return entityManagerFactory().createEntityManager();
    }

    public static void doInTransaction(Consumer<EntityManager> work){
        EntityManager entityManager = entityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            work.accept(entityManager);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
        finally {
            entityManager.close();
        }
    }


}
