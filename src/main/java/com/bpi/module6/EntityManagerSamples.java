package com.bpi.module6;

import com.bpi.module6.model.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EntityManagerSamples {
	
	static void persistSample(EntityManager em) {
		em.getTransaction().begin();
		
		Student newStudent = new Student(); // transient
		
		newStudent.setName("Patrick Mendoza");
		newStudent.setAge(43);
		newStudent.setEmail("patrickmendoza@gmail.com"); 
		
		em.persist(newStudent); // managed
		
		em.getTransaction().commit(); //hibernate generates sql, newStudent will be saved to db
		
		System.out.println("is newStudent inside the persistence context: " + em.contains(newStudent));

	}
	
	static void findSample(EntityManager em) {
		
		em.getTransaction().begin();
		
		//Hibernate retrieves the Student entity via SELECT and places it into the persistence context.
		Student studentWithId1 = em.find(Student.class, 1L); //managed entity
		
		if (studentWithId1 != null) {
			System.out.println("is newStudent inside the persistence context: " + em.contains(studentWithId1));
		}
		
		em.getTransaction().commit();
	}
	
	static void mergeSample(EntityManager em) {
		
		em.getTransaction().begin();
		
		//Hibernate retrieves the Student entity via SELECT and places it into the persistence context.
		Student studentWithId1 = em.find(Student.class, 1L); //managed entity
		
		em.detach(studentWithId1); //detach studentWithId1
		
		System.out.println("is studentWithId1 inside the persistence context: " + em.contains(studentWithId1)); // false
		
		// for example, studentWithId1 has changes while in detached
		studentWithId1.setAge(22);
		
		// in order for this changes to reflect in database
		// we need to re attach to the persistence context using merge
		// merge() returns a managed instance with the same persistent state 
		// as the given entity instance, but a distinct Java object identity.
		studentWithId1 = em.merge(studentWithId1);

		em.getTransaction().commit();
	}
	
	static void removeSample(EntityManager em) {
		em.getTransaction().begin();
		
		Student student = em.find(Student.class, 8L); // managed
		
		em.remove(student);
		
		em.getTransaction().commit();
		
		System.out.println("is student inside the persistence context: " + em.contains(student)); // false
		
		System.out.println(student.toString()); //detached
		
	}
	
	static void refreshSample(EntityManager em) {
		
		em.getTransaction().begin();
		
		Student newStudent = new Student();
		
		newStudent.setName("John Mark Santos");
		newStudent.setAge(21);
		newStudent.setEmail("johnmarksantos@gmail.com");
		
		em.persist(newStudent); // managed 
		
		em.getTransaction().commit(); // commit insert to database
		
		System.out.println("BEFORE REFRESH: " + newStudent.toString()); //createdAt=null
		
		em.refresh(newStudent); //generates select statement
		
		System.out.println("AFTER REFRESH: " + newStudent.toString()); //createdAt=2026-01-26T15:24:36.123455Z
		
	}
	
	
	static void detachSample(EntityManager em) {
		
		em.getTransaction().begin();
		
		Student student = em.find(Student.class, 1L); //managed
		
		em.detach(student); // detached 
		
		student.setAge(100);
		
		em.getTransaction().commit(); // no update statement generated because student is detached
		
	}
	
	static void clearSample(EntityManager em) {
		
		em.getTransaction().begin();
		
		Student student = em.find(Student.class, 1L); //managed
		Student student1 = em.find(Student.class, 9L); // managed
		
		System.out.println("BEFORE clear()");
		System.out.println("is student inside the persistence context: " + em.contains(student)); //true
		System.out.println("is student1 inside the persistence context: " + em.contains(student)); //true
		
		em.clear(); // detaches all managed entity 
		
		System.out.println("AFTER clear()");
		System.out.println("is student inside the persistence context: " + em.contains(student)); //false
		System.out.println("is student1 inside the persistence context: " + em.contains(student)); //false

		em.getTransaction().commit(); 
		
	}
	
	
	static void rollbackSample(EntityManager em) {
		
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			// first transaction 
			Student student = em.find(Student.class, 1L);
			student.setAge(30);
			
			em.flush();
			
			//second transaction - mock error by saving data that already exist
			Student newStudent = new Student();
			newStudent.setName("Juan Dela Cruz");
			newStudent.setAge(20);
			newStudent.setEmail("juandelacruz@gmail.com");
			
			em.persist(newStudent);
			
			tx.commit();
		} catch (Exception e) {
			System.out.println("Exception occurred: " + e.getMessage());
			
			if (tx.isActive()) { // always check if transaction is active before calling rollback;
				tx.rollback();
				System.out.println("Transaction rolled back.");
			}
		}
		
	}

}
