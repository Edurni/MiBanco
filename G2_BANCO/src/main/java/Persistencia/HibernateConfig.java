package Persistencia;

import jakarta.persistence.*;

/**
 * BENEFICIOS DEL USO DEL PATRÓN SINGLETON: en casos como este, donde cada
 * conexión a la BBDD con Hibernate hace referencia a una sesión, no tiene mucho
 * sentido abrir y cerrar el entityManager constantemente. Este patrón singleton
 * nos permite utilizar una sola instancia del entityManager, por lo que sólo
 * hace falta cerrarlo antes de cerrar el programa.
 */
public class HibernateConfig {

    private static EntityManager entityManager = null;

    private HibernateConfig() {
    }

    public static EntityManager getEntityManagerInstance() {
        if (entityManager == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ad_banco_hiber_PU");
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }

    /*
    ---- ESTA ES LA MANERA DE HACERLO SIN USAR SINGLETON ----
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public HibernateConfig() {
        this.setEntityManagerFactory(Persistence.createEntityManagerFactory("ad_banco_hiber_PU"));
    }

    public void setEntityManagerFactory(EntityManagerFactory factory) {
        this.entityManagerFactory = factory;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManager = new HibernateConfig().getEntityManagerFactory().createEntityManager();
    }

    public void closeConnection() {
        entityManager.close();
        entityManagerFactory.close();
    }
     */
}
