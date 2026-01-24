package com.bpi.module6;

import com.bpi.module6.model.Course;
import com.bpi.module6.model.Profile;
import com.bpi.module6.model.Student;
import com.bpi.module6.util.EntityManagerUtil;

import jakarta.persistence.EntityManager;

public class App {

	public static void main(String[] args) {
		EntityManager em = EntityManagerUtil.getInstance().createEntityManager();

		try {
			persistOneToMany(em);

		} finally {
			EntityManagerUtil.getInstance().closeEntityManager(em);
			EntityManagerUtil.getInstance().shutdownFactory();
		}
	}

	static void persistOneToOne(EntityManager em) {
		em.getTransaction().begin();

		Student newStudent1 = new Student();
		newStudent1.setName("Juan Dela Cruz");
		newStudent1.setAge(50);
		newStudent1.setEmail("juandelacruz@gmail.com");
		em.persist(newStudent1);

		Profile newStudent1Profile = new Profile();
		newStudent1Profile.setAddress("Pasig City");
		newStudent1Profile.setNationality("Filipino");
		newStudent1Profile.setStudent(newStudent1);
		em.persist(newStudent1Profile);

		em.getTransaction().commit();
	}
	
	static void persistOneToMany(EntityManager em) {
		em.getTransaction().begin();
		
		Student student1 = em.find(Student.class, 1L);
		
		Course newCourse = new Course();
		newCourse.setCourseName("Math");
		newCourse.setGrade("80");
		newCourse.setStudent(student1);
		
		em.persist(newCourse);
		
		em.getTransaction().commit();
	}
	
	static void runBidirectional(EntityManager em) {
		em.getTransaction().begin();
		
		Student student = em.find(Student.class, 1L);
		
		student.getCourses().forEach(course -> System.out.print(course.getCourseName()));
		
		
		em.getTransaction().commit();
	}

}
