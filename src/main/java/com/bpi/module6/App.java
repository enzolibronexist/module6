package com.bpi.module6;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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

			runFetchingSample(em);

//			runCascadingSample(em);

		} finally {

			EntityManagerUtil.closeEntityManager(em);
			EntityManagerUtil.shutdownFactory();
		}

	}

	public static void runFetchingSample(EntityManager em) {

		/*
		 * transaction #1 - save sample data
		 */
		System.out.println("begin transaction #1");
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
		
		em.clear(); // clear persistence context

		/*
		 * transaction #2 - get sample data
		 */
		System.out.println("begin transaction #2");
		em.getTransaction().begin();


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
		
		
		em.clear(); // clear persistence context


		/*
		 * transaction # 2 - cascade merge
		 */
		em.getTransaction().begin();
		
		
		System.out.println(String.format("Is macbookProInventory inside the persistence context: %b", em.contains(macbookProInventory)));
		System.out.println(String.format("Is macbookPro inside the persistence context: %b", em.contains(macbookPro)));

		// since we include cascade type merge, macbookPro, which is a related entity of mackbookProInventory should also be merged
		macbookProInventory = em.merge(macbookProInventory);
		macbookPro = macbookProInventory.getProduct();
		
		System.out.println("after merging");
		System.out.println(String.format("Is macbookProInventory inside the persistence context: %b", em.contains(macbookProInventory)));
		System.out.println(String.format("Is macbookPro inside the persistence context: %b", em.contains(macbookPro)));
		
		em.getTransaction().commit();

		/*
		 * transaction # 3 - cascade remove
		 */
//		em.getTransaction().begin();
//		
//		em.clear();
//		
//		ProductInventory macbookProInventoryCopy = em.find(ProductInventory.class, macbookProInventory.getId());
//		
//		System.out.println(String.format("Is macbookProInventoryCopy inside the persistence context: %b", em.contains(macbookProInventoryCopy)));
//		
//		em.remove(macbookProInventoryCopy);
//		
//		em.getTransaction().commit();

		/*
		 * transaction # 4 - cascade refresh
		 */
//		em.getTransaction().begin();
//
//		em.clear();
//
//		ProductInventory macbookProInventoryCopy = em.find(ProductInventory.class, macbookProInventory.getId());
//
//		runUpdateProductUpdatedByOtherTransaction(macbookProInventoryCopy.getId());
//
//		System.out.println("Before Refresh: ");
//		System.out.println(macbookProInventoryCopy.getProduct().getName());
//
//		em.refresh(macbookProInventoryCopy);
//
//		System.out.println("After Refresh: ");
//		System.out.println(macbookProInventoryCopy.getProduct().getName());
//
//		em.getTransaction().commit();
		
		/*
		 * transaction # 4 - cascade detach
		 */
//		em.getTransaction().begin();
//
//		System.out.println(String.format("Is macbookProInventory inside the persistence context: %b", em.contains(macbookProInventory)));
//
//		em.detach(macbookProInventory);
//
//		System.out.println("After Detach: ");
//		System.out.println(String.format("Is macbookPro inside the persistence context: %b", em.contains(macbookPro)));
//
//		em.getTransaction().commit();

	}

	public static void runUpdateProductUpdatedByOtherTransaction(UUID productInventoryId) {

		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

			em.getTransaction().begin();

			ProductInventory macbookProInventoryCopy = em.find(ProductInventory.class, productInventoryId);

			Product product = macbookProInventoryCopy.getProduct();
			product.setName("Macbook Pro M5");

			em.getTransaction().commit();

		} finally {

			EntityManagerUtil.closeEntityManager(em);
		}

	}
}
