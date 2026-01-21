package com.bpi.module6;

import java.math.BigDecimal;

import com.bpi.module6.model.Product;
import com.bpi.module6.util.EntityManagerUtil;

import jakarta.persistence.EntityManager;

public class App {

	public static void main(String[] args) {

//		runFindExisitingSample();
		
		runRefreshSample();

	}

	public static void runFindExisitingSample() {
		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

			/*
			 * transaction # 1
			 */
			em.getTransaction().begin();

			Product newProduct = new Product();
			newProduct.setProductName("Simplus Air Fryer");
			newProduct.setPrice(new BigDecimal("3000.00"));

			em.persist(newProduct);

			em.getTransaction().commit();

			Long newProductId = newProduct.getId(); // get product id of persisted product
			
			em.clear();

			/*
			 * transaction # 2
			 */
			em.getTransaction().begin();

			Product product = em.find(Product.class, newProductId);

			System.out.println("Is product inside the context: " + em.contains(product));

			em.getTransaction().commit();

			if(product != null) {
				System.out.println("PRODUCT: " + product.toString());
			} else {
				System.out.println("Product is null");
			}

		} finally {
			EntityManagerUtil.closeEntityManager(em);
			EntityManagerUtil.shutdownFactory();
		}

	}

	public static void runFindNotExisitingSample() {
		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

			em.getTransaction().begin();

			Product product = em.find(Product.class, 1L);

			em.getTransaction().commit();

			System.out.println("PRODUCT IS: " + product);

		} finally {
			EntityManagerUtil.closeEntityManager(em);
			EntityManagerUtil.shutdownFactory();
		}

	}

	public static void runMergeSample() {

		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

			/*
			 * start of transaction # 1
			 */
			em.getTransaction().begin();

			Product newProduct = new Product();
			newProduct.setProductName("keyboard");
			newProduct.setPrice(new BigDecimal("3000.20"));

			em.persist(newProduct);

			em.getTransaction().commit();

			em.clear();

			/*
			 * start of transaction # 2
			 */ 
			em.getTransaction().begin();

			newProduct = em.merge(newProduct); // use merge to re-attach newProduct to the context

			newProduct.setPrice(new BigDecimal("4000.00"));

			em.getTransaction().commit();

		} finally {
			EntityManagerUtil.closeEntityManager(em);
			EntityManagerUtil.shutdownFactory();
		}

	}
	
	public static void runRefreshSample() {
		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

			/*
			 * start of transaction # 1
			 */
			em.getTransaction().begin();

			Product newProduct = new Product();
			newProduct.setProductName("keyboard");
			newProduct.setPrice(new BigDecimal("3000.20"));

			em.persist(newProduct);

			em.getTransaction().commit();
			
			System.out.println("timestamp before refresh: " + newProduct.getCreatedAt());
			
			em.refresh(newProduct);
			
			System.out.println("timestamp after refresh: " + newProduct.getCreatedAt());

			em.clear();

		} finally {
			EntityManagerUtil.closeEntityManager(em);
			EntityManagerUtil.shutdownFactory();
		}
	}
}
