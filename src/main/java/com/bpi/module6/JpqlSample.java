package com.bpi.module6;

import java.util.List;

import com.bpi.module6.model.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class JpqlSample {
	
	static void selectAllStudent(EntityManager em) {
		
		em.getTransaction().begin();
		
		String jpql = "Select s FROM Student s";
		TypedQuery<Student> query = em.createQuery(jpql, Student.class);
		List<Student> students = query.getResultList();
		
		//print student names
		students.forEach(student -> System.out.println(student.getName()));
		
		em.getTransaction().commit();
		
	}
	
	static void selectStudentByName(EntityManager em, String name) {
		
		em.getTransaction().begin();
		
		String jpql = "Select s FROM Student s WHERE s.name = :name";
		TypedQuery<Student> query = em.createQuery(jpql, Student.class);
		query.setParameter("name", name);
		List<Student> students = query.getResultList();
		
		//print student names
		students.forEach(student -> System.out.println(student.getName()));
		
		em.getTransaction().commit();
		
	}
	
	static void selectStudentByNamePositionalParamaters(EntityManager em, String name, int age) {
		
		em.getTransaction().begin();
		
		String jpql = "Select s FROM Student s WHERE s.name = ?1 AND age = ?2";
		TypedQuery<Student> query = em.createQuery(jpql, Student.class);
		query.setParameter(1, name);
		query.setParameter(2, age);
		List<Student> students = query.getResultList();
		
		//print student names
		students.forEach(student -> System.out.println(student.getName()));
		
		em.getTransaction().commit();
		
	}
	
	static void selectAllStudentEmails(EntityManager em) {
		
		em.getTransaction().begin();
		
		String jpql = "Select s.email FROM Student s";
		TypedQuery<String> query = em.createQuery(jpql, String.class);
		List<String> studentEmails = query.getResultList();
		
		//print student emails
		studentEmails.forEach(email -> System.out.println(email));
		
		em.getTransaction().commit();
		
	}

}
