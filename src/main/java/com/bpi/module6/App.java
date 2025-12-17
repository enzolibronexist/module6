package com.bpi.module6;

import java.math.BigDecimal;

import com.bpi.module6.model.Product;
import com.bpi.module6.util.EntityManagerUtil;

import jakarta.persistence.EntityManager;


public class App {
	
    public static void main(String[] args) {
    	
    	EntityManager em = EntityManagerUtil.createEntityManager();
    	
    	try {
    		
    		em.getTransaction().begin();
    		
    		Product newProduct = new Product();
    		newProduct.setProductName("keyboard");
    		newProduct.setPrice(new BigDecimal("3000.20"));
    		
    		em.persist(newProduct);
    		
    		em.getTransaction().commit();
    		
    	} finally {
    		EntityManagerUtil.closeEntityManager(em);
    		EntityManagerUtil.shutdownFactory();
    	}
    	
    }
}
