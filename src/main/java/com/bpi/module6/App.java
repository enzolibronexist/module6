package com.bpi.module6;

import java.math.BigDecimal;
import java.util.List;

import com.bpi.module6.model.Category;
import com.bpi.module6.model.Product;
import com.bpi.module6.model.ProductInventory;
import com.bpi.module6.util.EntityManagerUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class App {

	public static void main(String[] args) {

		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

//			runFetchingSample(em);
			
			runCascadingSample(em);

		} finally {

			EntityManagerUtil.closeEntityManager(em);
			EntityManagerUtil.shutdownFactory();
		}

	}

	public static void runFetchingSample(EntityManager em) {

		/*
		 * transaction #1 - save sample data
		 */
		em.getTransaction().begin();

		Category workCategory = new Category();
		workCategory.setName("Work");

		em.persist(workCategory);

		Product macbookPro = new Product();
		macbookPro.setBrand("Apple");
		macbookPro.setCategory(workCategory);
		macbookPro.setName("Macbook Pro M4");
		macbookPro.setPrice(new BigDecimal("100000.00"));

		em.persist(macbookPro);

		em.getTransaction().commit();

		Long macbookProId = macbookPro.getId();

		/*
		 * transaction #2 - get sample data
		 */
		em.getTransaction().begin();

		em.clear(); // clear persistence context

		System.out.println("is macbookPro inside context: " + em.contains(macbookPro));

		Product macbookProFromDatabase = em.find(Product.class, macbookProId);

//		Category categoryFromDatabase = macbookProFromDatabase.getCategory();

//		System.out.println(categoryFromDatabase.toString());

		em.getTransaction().commit();

	}

	public static void runCascadingSample(EntityManager em) {

		/*
		 * transaction # 1 - cascade persist
		 */
		em.getTransaction().begin();
		
		Product macbookPro = new Product();
		macbookPro.setBrand("Apple");
		macbookPro.setName("Macbook Pro M4");
		macbookPro.setPrice(new BigDecimal("100000.00"));
		
		ProductInventory macbookProInventory = new ProductInventory();
		macbookProInventory.setProduct(macbookPro);
		macbookProInventory.setQuantity(20);
		macbookProInventory.setWarehouseLocation("Ortigas");
		
		em.persist(macbookProInventory);
		
		em.getTransaction().commit();
		
		
		/*
		 * transaction # 2 - cascade merge
		 */
		em.getTransaction().begin();
		
		em.clear(); // clear persistence context
		
		System.out.println(String.format("Is macbookProInventory inside the persistence context: %b", em.contains(macbookProInventory)));
		System.out.println(String.format("Is macbookPro inside the persistence context: %b", em.contains(macbookPro)));

		// since we include cascade type merge, macbookPro, which is a related entity of mackbookProInventory should also be merged
		macbookProInventory = em.merge(macbookProInventory);
		macbookPro = macbookProInventory.getProduct();
		
		System.out.println("after merging");
		System.out.println(String.format("Is macbookProInventory inside the persistence context: %b", em.contains(macbookProInventory)));
		System.out.println(String.format("Is macbookPro inside the persistence context: %b", em.contains(macbookPro)));
		
		em.getTransaction().commit();
		
		
		
	}
}
