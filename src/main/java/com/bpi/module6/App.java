package com.bpi.module6;

import java.math.BigDecimal;

import com.bpi.module6.model.Product;
import com.bpi.module6.util.EntityManagerUtil;

import jakarta.persistence.EntityManager;

public class App {

	public static void main(String[] args) {

		runFindExisitingSample();
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

			/*
			 * transaction # 2
			 */
			em.getTransaction().begin();

			Product product = em.find(Product.class, newProductId);

			System.out.println("Is product inside the context: " + em.contains(product));

			em.getTransaction().commit();

			System.out.println("PRODUCT IS: " + product.toString());

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

			// after commit newProduct will be in a detached state, meaning it is not
			// residing in the persistence context anymore
			em.getTransaction().commit();
			/* end of transaction # 1 */

			/* start of transaction # 2 */
			em.getTransaction().begin();

			em.merge(newProduct); // use merge to re-attach newProduct to the context

			newProduct.setPrice(new BigDecimal("4000.00"));

			em.getTransaction().commit();
			/* end of transaction # 2 */

		} finally {
			EntityManagerUtil.closeEntityManager(em);
			EntityManagerUtil.shutdownFactory();
		}

	}
}
