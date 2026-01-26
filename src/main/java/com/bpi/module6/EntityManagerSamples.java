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
		
		
		em.getTransaction().commit();
		
		System.out.println("is newStudent inside the persistence context: " + em.contains(newStudent));

	
	}

}
