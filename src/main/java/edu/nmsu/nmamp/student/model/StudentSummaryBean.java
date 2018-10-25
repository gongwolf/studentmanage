package edu.nmsu.nmamp.student.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class StudentSummaryBean {
	private int user_id;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String recommendation_file_name;

	@DateTimeFormat(pattern="MM/dd/yyyy")
	private Date birthDate;
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	
	public String getRecommendation_file_name() {
		return recommendation_file_name;
	}

	public void setRecommendation_file_name(String recommendation_file_name) {
		this.recommendation_file_name = recommendation_file_name;
	}




}
