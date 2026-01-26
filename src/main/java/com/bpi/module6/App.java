package com.bpi.module6;

import java.util.ArrayList;
import java.util.List;

import com.bpi.module6.model.Club;
import com.bpi.module6.model.Course;
import com.bpi.module6.model.Profile;
import com.bpi.module6.model.Student;
import com.bpi.module6.util.EntityManagerUtil;

import jakarta.persistence.EntityManager;

public class App {

	public static void main(String[] args) {
		EntityManager em = EntityManagerUtil.getInstance().createEntityManager();

		try {
			persistStudents(em);
			
			persistManyToMany(em);

		} finally {
			EntityManagerUtil.getInstance().closeEntityManager(em);
			EntityManagerUtil.getInstance().shutdownFactory();
		}
	}
	
	static void persistStudents(EntityManager em) {
		em.getTransaction().begin();

		Student newStudent1 = new Student();
		newStudent1.setName("Juan Dela Cruz");
		newStudent1.setAge(21);
		newStudent1.setEmail("juandelacruz@gmail.com");
		em.persist(newStudent1);
		
		Student newStudent2 = new Student();
		newStudent2.setName("Maria Santos");
		newStudent2.setAge(22);
		newStudent2.setEmail("mariasantos@gmail.com");
		em.persist(newStudent2);

		em.getTransaction().commit();
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
	
	static void persistManyToMany(EntityManager em) {
		
		em.getTransaction().begin();
		
		Student student = em.find(Student.class, 1L);
		
		Student student2 = em.find(Student.class, 2L);
		
		Club basketballClub = new Club();
		basketballClub.setName("Basketball Club");
		basketballClub.setDescription("For basketball enthusiast");
		List<Student> basketballClubstudentList = new ArrayList();
		basketballClubstudentList.add(student);
		basketballClub.setStudents(basketballClubstudentList);
		em.persist(basketballClub);
		
		Club gleeClub = new Club();
		gleeClub.setName("Glee Club");
		gleeClub.setDescription("For students that wants to sing");
		List<Student> gleeClubstudentList = new ArrayList();
		gleeClubstudentList.add(student);
		gleeClubstudentList.add(student2);
		gleeClub.setStudents(gleeClubstudentList);
		em.persist(gleeClub);
		
		Club artClub = new Club();
		artClub.setName("Art Club");
		artClub.setDescription("For students that wants to make art");
		List<Student> artClubstudentList = new ArrayList();
		artClubstudentList.add(student);
		artClubstudentList.add(student2);
		artClub.setStudents(artClubstudentList);
		em.persist(artClub);
		
		em.getTransaction().commit();
	}

}
