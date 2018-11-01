package edu.nmsu.nmamp.student.model;

public class MentorBean {
	int mentor_id;
	String prefix;
	String first_name;
	String last_name;
	String institution;
	String department;
	
	public MentorBean() {
		this.mentor_id = 0;
		this.prefix = null;
		this.first_name = null;
		this.last_name = null;
		this.institution = null;
		this.department = null;
	}
	
	
	public int getMentor_id() {
		return mentor_id;
	}
	public void setMentor_id(int mentor_id) {
		this.mentor_id = mentor_id;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}


	@Override
	public String toString() {
		return "MentorBean [mentor_id=" + mentor_id + ", prefix=" + prefix + ", first_name=" + first_name
				+ ", last_name=" + last_name + ", institution=" + institution + ", department=" + department + "]";
	}
	
	

}
