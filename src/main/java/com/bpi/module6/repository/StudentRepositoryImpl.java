package com.bpi.module6.repository;

import java.util.List;

import com.bpi.module6.model.Student;

import jakarta.persistence.EntityManager;

public class StudentRepositoryImpl implements Repository<Student, Long> {

	private final EntityManager em;

	public StudentRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public Student save(Student student) {
		if (student.getId() == null) {
			em.persist(student);
		} else {
			student = em.merge(student);
		}
		return student;
	}

	@Override
	public void delete(Student student) {
		em.remove(em.contains(student) ? student : em.merge(student));
	}

	@Override
	public void deleteById(Long id) {
		Student student = findById(id);
		if (student != null) {
			delete(student);
		}
	}

	@Override
	public Student findById(Long id) {
		return em.find(Student.class, id);
	}

	@Override
	public List<Student> findAll() {
		return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
	}

	public List<Student> findByLastName(String lastName) {
		return em.createQuery("SELECT s FROM Student s WHERE s.lastName = :lastName", Student.class)
				.setParameter("lastName", lastName).getResultList();
	}

}
