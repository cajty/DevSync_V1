package com.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DbConnection {
    private static DbConnection instance;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private DbConnection() {

    }

    public static DbConnection getInstance() {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }

    private void createEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("DevSync");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public EntityManager getEntityManager() {
        if (entityManagerFactory == null || !entityManager.isOpen()) {
            createEntityManagerFactory();
        }
        return entityManager;
    }

    public void closeEntityManager() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}