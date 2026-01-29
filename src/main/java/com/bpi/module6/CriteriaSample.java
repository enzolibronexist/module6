package com.bpi.module6;

import java.util.List;

import com.bpi.module6.model.Club;
import com.bpi.module6.model.Course;
import com.bpi.module6.model.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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

		TypedQuery<Student> query = em.createQuery(cq);
		List<Student> result = query.getResultList();
		
		result.forEach(student -> System.out.println(student.getName()));
	}
	
	
	static void selectStudentMultipleWhereCondition(EntityManager em) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
		
		Predicate condition1 = cb.greaterThan(root.get("age"), 21);
		Predicate condition2 = cb.lessThan(root.get("age"), 60);

		cq.select(root).where(cb.and(condition1, condition2));

		TypedQuery<Student> query = em.createQuery(cq);
		List<Student> result = query.getResultList();
		
		result.forEach(student -> System.out.println(student.getName()));
		
	}
	
	static void selectStudentOrderBy(EntityManager em) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
	
		cq.select(root).orderBy(cb.asc(root.get("name")));

		TypedQuery<Student> query = em.createQuery(cq);
		List<Student> result = query.getResultList();
		
		result.forEach(student -> System.out.println(student.getName()));
		
	}
	
	static void selectStudentClubInnerJoin(EntityManager em) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
	
		Join<Student, Club> club = root.join("clubs", JoinType.INNER);
		
		cq.select(root).distinct(true);

		TypedQuery<Student> query = em.createQuery(cq);
		List<Student> result = query.getResultList();
		
		result.forEach(student -> {
			
			student.getClubs().forEach(studentClub -> {
				System.out.println(student.getName() + " : " + studentClub.getName());
			});
		});
		
	}
	
	static void selectStudentCourseLeftJoin(EntityManager em) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Student> cq = cb.createQuery(Student.class);
		Root<Student> root = cq.from(Student.class);
	
		Join<Student, Course> course = root.join("courses", JoinType.LEFT);
		
		cq.select(root);

		TypedQuery<Student> query = em.createQuery(cq);
		List<Student> result = query.getResultList();
		
		System.out.println(result.size());
		
	}

}
