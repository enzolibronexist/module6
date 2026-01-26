package com.bpi.module6;

import com.bpi.module6.model.Profile;
import com.bpi.module6.model.Student;

import jakarta.persistence.EntityManager;

public class CascadingSampleTransaction {
	
	//before applying CascadeType.PERSIST we need to call em.persist for student1Profile
	static void beforeApplyingPersistCascade(EntityManager em) {
		
		em.getTransaction().begin();
		
		Student student1 = new Student();
		student1.setAge(25);
		student1.setName("Jane Doe");
		student1.setEmail("janedoe@gmail.com");
		em.persist(student1);
		
		Profile student1Profile = new Profile();
		student1Profile.setAddress("Pasig City");
		student1Profile.setNationality("Filipino");
		student1Profile.setStudent(student1);
		em.persist(student1Profile);
		
		
		em.getTransaction().commit();
	
	}
	
	
	//after applying CascadeType.PERSIST we don't have to call em.persist for student1Profile
	//because if we student1 student persist operation will cascade to the child
	static void afterApplyingPersistCascade(EntityManager em) {
		
		em.getTransaction().begin();
		
		Student student1 = new Student();
		student1.setAge(25);
		student1.setName("Jane Doe");
		student1.setEmail("janedoe@gmail.com");
		em.persist(student1);
		
		Profile student1Profile = new Profile();
		student1Profile.setAddress("Pasig City");
		student1Profile.setNationality("Filipino");
		//we will not call em.persist(student1Profile);
		
		student1.setProfile(student1Profile);
		
		em.getTransaction().commit();
	
	}

}
