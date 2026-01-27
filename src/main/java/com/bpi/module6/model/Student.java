package com.bpi.module6.model;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 50, columnDefinition = "VARCHAR(50)")
	private String name;

	@Column(name = "age", columnDefinition = "INT")
	private int age;

	@Column(name = "email", unique = true, length = 100, columnDefinition = "VARCHAR(100)")
	private String email;
	
	@Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST)
	private List<Course> courses;
	
	@ManyToMany(mappedBy = "students")
	private List<Club> clubs;
	
	@OneToOne(mappedBy = "student", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Profile profile;
	
	public List<Club> getClubs() {
		return clubs;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public void setClubs(List<Club> clubs) {
		this.clubs = clubs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", email=" + email + ", createdAt=" + createdAt + "]";
	}

}
