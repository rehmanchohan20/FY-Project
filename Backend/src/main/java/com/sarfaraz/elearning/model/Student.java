package com.sarfaraz.elearning.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "student")
public class Student extends CommonEntity {
	@Id
	@Column(name = "user_id")
	private Long userId;

	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany
	@JoinTable(name = "enroll_course", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
	private Set<Course> courses;

	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	private Set<CourseProgress> courseProgress;

	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	private Set<Payment> payments;

	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ticket> tickets;

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Set<CourseProgress> getCourseProgress() {
		return courseProgress;
	}

	public void setCourseProgress(Set<CourseProgress> courseProgress) {
		this.courseProgress = courseProgress;
	}

	public Set<Payment> getPayments() {
		return payments;
	}

	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}
}
