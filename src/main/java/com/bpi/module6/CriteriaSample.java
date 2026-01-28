package com.bpi.module6;

import java.util.List;

import com.bpi.module6.model.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CriteriaSample {
	
	static void selectAllStudent(EntityManager em) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		cq.select(root);

		TypedQuery<Student> query = em.createQuery(cq);
		List<Student> result = query.getResultList();
		
		result.forEach(student -> System.out.println(student.getName()));
	}
	
	static void selectStudentsWhereAgeGreaterThan21(EntityManager em) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		
		Predicate condition = cb.greaterThan(root.get("age"), 21);
		cq.select(root).where(condition);

		cq.select(root);

		TypedQuery<Student> query = em.createQuery(cq);
		List<Student> result = query.getResultList();
		
		result.forEach(student -> System.out.println(student.getName()));
	}

}
