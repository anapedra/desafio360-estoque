package com.anasantana.estoque.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryProvider {

    private static final String DEFAULT_ENV = "test"; // padrão: desenvolvimento
    private static EntityManagerFactory emf;

    private EntityManagerFactoryProvider() {
        // construtor privado para não instanciar
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            String env = System.getProperty("env", DEFAULT_ENV);
            String persistenceUnitName = resolvePersistenceUnitName(env);
            emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        }
        return emf;
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    private static String resolvePersistenceUnitName(String env) {
        return switch (env.toLowerCase()) {
            case "dev" -> "ecommercePU-dev";
            case "test" -> "ecommercePU-test";
            case "prod" -> "ecommercePU-prod";
            default -> throw new IllegalArgumentException("Unknown environment: " + env);
        };
    }
}