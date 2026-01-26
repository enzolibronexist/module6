package com.bpi.module6;

import com.bpi.module6.model.Student;

import jakarta.persistence.EntityManager;

public class EntityManagerSamples {
	
	static void persistSample(EntityManager em) {
		em.getTransaction().begin();
		
		Student newStudent = new Student(); // transient
		
		newStudent.setName("Pedro Reyes");
		newStudent.setAge(23);
		newStudent.setEmail("pedroreyes@gmail.com"); 
		
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
		
		System.out.println("is newStudent inside the persistence context: " + em.contains(studentWithId1)); // false
		
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
		
		System.out.println("is newStudent inside the persistence context: " + em.contains(student)); // false
		
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
		
		System.out.println("BEFORE REFRESH: " + newStudent.toString());
		
		em.refresh(newStudent);
		
		System.out.println("AFTER REFRESH: " + newStudent.toString());
		
		
	}

}
