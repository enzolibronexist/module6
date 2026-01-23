package com.bpi.module6;

import com.bpi.module6.util.EntityManagerUtil;

import jakarta.persistence.EntityManager;


public class App {
	
	   public static void main( String[] args ){
	    	testConnection();
	    }
	    
	    static void testConnection() {

	    	EntityManager em = EntityManagerUtil.createEntityManager();
	    	
	    	try {
	    		
	    		if(em.isOpen()) {
	    			System.out.println("entity manager is open, ready to create transaction");
	    		}
	    		
	    	} finally {
	    		EntityManagerUtil.closeEntityManager(em);
	    		EntityManagerUtil.shutdownFactory();
	    	}
	    }

}
