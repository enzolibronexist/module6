package com.bpi.module6.service;

import com.bpi.module6.model.Student;
import com.bpi.module6.repository.StudentRepositoryImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class StudentServiceImpl implements StudentService {
	
    private final EntityManager em;
    
    private final StudentRepositoryImpl studentRepository;
    
    public StudentServiceImpl(EntityManager em) {
        this.em = em;
        this.studentRepository = new StudentRepositoryImpl(em);
    }

	@Override
	public Student enrollStudent(Student student) {
		EntityTransaction tx = em.getTransaction();
		
        try {
        	
            tx.begin();
            
            
            Student savedStudent = studentRepository.save(student);
            tx.commit();
            
            return savedStudent;
        } catch (Exception e) {
        	
            if (tx.isActive()) {
                tx.rollback();
            }
            
            throw e;
        }   
	}

}
