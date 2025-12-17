package com.bpi.module6;

import com.bpi.module6.util.EntityManagerUtil;

import jakarta.persistence.EntityManager;


public class App {
	
    public static void main(String[] args) {
    	
    	EntityManager em = EntityManagerUtil.createEntityManager();
    	
    	try {

    		
    	} finally {
    		EntityManagerUtil.closeEntityManager(em);
    		EntityManagerUtil.shutdownFactory();
    	}
    	
    }
}
