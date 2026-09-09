package com.crud.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("crud");

    public static EntityManagerFactory getEntityManagerFactory() {
        return factory;
    }
}
