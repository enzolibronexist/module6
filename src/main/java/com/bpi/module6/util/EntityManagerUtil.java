package com.bpi.module6.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerUtil {
	
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
	
	//create a method that will create a entity manager
	public static EntityManager createEntityManager() {
		return emf.createEntityManager();
	}
	
    /** Check if an EntityManager is open */
    public static boolean isOpen(EntityManager em) {
        return em != null && em.isOpen();
    }

    /** Safely close an EntityManager */
    public static void closeEntityManager(EntityManager em) {
        if (isOpen(em)) {
            em.close();
        }
    }

    /** Shutdown the EntityManagerFactory */
    public static void shutdownFactory() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
