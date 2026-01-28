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
			JpaAnnotationSampleTransaction.persistStudents(em);
			JpaAnnotationSampleTransaction.persistOneToMany(em);
			
			em.clear();
			CriteriaSample.selectStudentsWhereAgeGreaterThan21(em);
			
//			List<Student> students = JpqlSample.selectAllStudentsAndCoursesInnerJoinMultiple(em);


		} finally {
			EntityManagerUtil.getInstance().closeEntityManager(em);
			EntityManagerUtil.getInstance().shutdownFactory();
		}
	}
	

	

}
