package com.bpi.module6;

import java.util.List;

import com.bpi.module6.dto.StudentDto;
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

		cq.select(root).orderBy(cb.desc(root.get("name")));

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

		Join<Student, Course> course = root.join("courses", JoinType.RIGHT);

		cq.select(root);

		TypedQuery<Student> query = em.createQuery(cq);
		List<Student> result = query.getResultList();

		System.out.println(result.size());

	}

	static void selectStudentCourseRightJoin(EntityManager em) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class);
		Root<Course> root = cq.from(Course.class);

		Join<Course, Student> course = root.join("student", JoinType.LEFT);

		cq.select(root);

		TypedQuery<Course> query = em.createQuery(cq);
		List<Course> result = query.getResultList();

		System.out.println(result.size());

	}

	static void aggregateFunction(EntityManager em) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Student> root = cq.from(Student.class);

		cq.select(cb.count(root));

		TypedQuery<Long> query = em.createQuery(cq);
		Long result = query.getSingleResult();

		System.out.println(result);

	}

	static void selectStudentNames(EntityManager em) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<String> cq = cb.createQuery(String.class);
		Root<Student> root = cq.from(Student.class);

		cq.select(root.get("name"));

		TypedQuery<String> query = em.createQuery(cq);
		List<String> result = query.getResultList();

		result.forEach(studentName -> System.out.println(studentName));
	}

	static void selectStudentIdNameEmail(EntityManager em) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
		Root<Student> root = cq.from(Student.class);

		cq.select(cb.array(root.get("id"), root.get("name"), root.get("email")));

		TypedQuery<Object[]> query = em.createQuery(cq);
		List<Object[]> results = query.getResultList();

		// Display the retrieved attributes
		for (Object[] student : results) {
			Long id = (Long) student[0];
			String name = (String) student[1];
			String email = (String) student[2];
			System.out.println("id: " + id + ", name: " + name + ", email: " + email);
		}

	}
	
	static void selectStudentDto(EntityManager em) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<StudentDto> cq = cb.createQuery(StudentDto.class);
		Root<Student> root = cq.from(Student.class);

		cq.select(
			    cb.construct(
			        StudentDto.class,
			        root.get("id"),
			        root.get("name"),
			        root.get("email")
			    )
			);
		
		TypedQuery<StudentDto> query = em.createQuery(cq);
		List<StudentDto> results = query.getResultList();

		// Display the retrieved attributes
		for (StudentDto student : results) {
			System.out.println("id: " + student.getId() + ", name: " + student.getName() + ", email: " + student.getEmail());
		}

	}

}
