package com.bpi.module6;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class App {
    public static void main(String[] args) {
    	
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    	EntityManager em = emf.createEntityManager();
    	
    	try {
    		
    		em.getTransaction().begin();
    		
    		
    		em.getTransaction().commit();
    	} finally {
    		em.close();
    		emf.close();
    	}
    	
    }
}
