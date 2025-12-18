package com.bpi.module6;

import java.math.BigDecimal;
import java.util.List;

import com.bpi.module6.model.Category;
import com.bpi.module6.model.Product;
import com.bpi.module6.model.ProductInventory;
import com.bpi.module6.model.Tag;
import com.bpi.module6.util.EntityManagerUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


public class App {
	
    public static void main(String[] args) {
    	
    	EntityManager em = EntityManagerUtil.createEntityManager();

    	
    	try {
    		
    		runBasicPersist(em);
    		
    		//transaction #3 - Selecting Specific Fields
    		em.getTransaction().begin(); //begin transaction
    		
    		TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c WHERE c.name = :name",
    				Category.class);
    		
    		query.setParameter("name", "Peripherals");

    		Category category = query.getSingleResult();
    		
    		System.out.println(category.toString());

    		System.out.println(category.getProducts());
    		
    		category.getProducts().forEach(product -> System.out.println(product.getName()));

    		em.getTransaction().commit(); // commit transaction
    		
    		
    	} finally {
    		
    		EntityManagerUtil.closeEntityManager(em);
    		EntityManagerUtil.shutdownFactory();
    	}
    	
    }
    
    public static void runJpqlJoin(EntityManager em) {
		//transaction #3 - Selecting Specific Fields
		em.getTransaction().begin(); //begin transaction
		
		
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p JOIN p.category c WHERE c.name = :categoryName",
		        Product.class);
		query.setParameter("categoryName", "Peripherals");
		

		List<Product> products = query.getResultList();
		
		products.forEach(name -> System.out.println(name));

		em.getTransaction().commit(); // commit transaction
    }
    
    public static void runJpqlPositionalParameters(EntityManager em) {
		//transaction #3 - Selecting Specific Fields
		em.getTransaction().begin(); //begin transaction
		
		TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p WHERE p.price > ?1 AND p.brand = ?2",
				Product.class);
		
		query.setParameter(1, 2000);
		query.setParameter(2, "Logitech");

		List<Product> products = query.getResultList();
		
		products.forEach(name -> System.out.println(name));

		em.getTransaction().commit(); // commit transaction
    }
    
    public static void runJpqlWhereAndNamedParameters(EntityManager em) {
		//transaction #3 - Selecting Specific Fields
		em.getTransaction().begin(); //begin transaction
		
		TypedQuery<Product> query = em.createQuery(
		        "SELECT p FROM Product p WHERE p.name = :name",
		        Product.class);
		
		query.setParameter("name", "keyboard");

		List<Product> products = query.getResultList();
		
		products.forEach(name -> System.out.println(name));

		em.getTransaction().commit(); // commit transaction
    }
    
    public static void runJpqlSelectingSpecificFields(EntityManager em) {
		//transaction #3 - Selecting Specific Fields
		em.getTransaction().begin(); //begin transaction
		
		TypedQuery<String> getAllProductName = em.createQuery(
		        "SELECT p.name FROM Product p",
		        String.class);

		List<String> names = getAllProductName.getResultList();
		
		names.forEach(name -> System.out.println(name));

		em.getTransaction().commit(); // commit transaction
    }
    
    public static void runBasicPersist(EntityManager em) {
		//transaction# 1 - persist
		em.getTransaction().begin(); // begin transaction
		
		Tag gaming = new Tag();
		gaming.setName("gaming");
		em.persist(gaming);
		
		Category peripheral = new Category();
		peripheral.setName("Peripherals");
		
		Product product = new Product();
		em.persist(product);
		
		Product keyboard = new Product();
		keyboard.setName("G915 Mechanical Keyboard");
		keyboard.setPrice(new BigDecimal("3000.00"));
		keyboard.setBrand("Logitech");
		keyboard.setCategory(peripheral);
		peripheral.getProducts().add(keyboard);
		keyboard.addTag(gaming);
		em.persist(keyboard);
		
		ProductInventory productInventory = new ProductInventory();
		productInventory.setQuantity(5);
		productInventory.setWarehouseLocation("Pasig Warehouse");
		productInventory.setProduct(keyboard);
		em.persist(productInventory);
		
		Product keyboard2 = new Product();
		keyboard2.setName("G502");
		keyboard2.setPrice(new BigDecimal("5000.00"));
		keyboard2.setBrand("Logitech");
		keyboard2.setCategory(peripheral);
		peripheral.getProducts().add(keyboard2);
		em.persist(keyboard2);

		Product mouse = new Product();
		mouse.setName("X3 CrazyLight");
		mouse.setPrice(new BigDecimal("6000.00"));
		mouse.setBrand("Pulsar");
		mouse.setCategory(peripheral);
		peripheral.getProducts().add(mouse);
		em.persist(mouse);
		
		Product monitor = new Product();
		monitor.setName("XL2540X");
		monitor.setPrice(new BigDecimal("8000.00"));
		monitor.setBrand("Zowie");
		monitor.setCategory(peripheral);
		peripheral.getProducts().add(monitor);
		em.persist(monitor);
		
		em.persist(peripheral);
		
		em.getTransaction().commit(); // commit transaction
    }
    
    public static void runBasicJpql(EntityManager em) {
		//transaction #3 - Basic JPQL
		em.getTransaction().begin(); //begin transaction
		
		TypedQuery<Product> getAllProducts = em.createQuery(
		        "SELECT p FROM Product p",
		        Product.class);
		
		List<Product> products = getAllProducts.getResultList();
		
		products.forEach(product -> System.out.println(product.toString()));
		
		
		em.getTransaction().commit(); //commit transaction
    }
}
