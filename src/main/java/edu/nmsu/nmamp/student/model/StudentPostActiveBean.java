package edu.nmsu.nmamp.student.model;

public class StudentPostActiveBean {
	int student_id;
	String name;
	String firstName;
	String middleName;
	String lastName;
	String company_comments;

	String employmentName;
	String occupation;
	String position;
	String employ_city;
	String employ_county;
	String employ_state;
	String grud_school_name;
	String grud_city;
	String grud_county;
	String grud_state;
	String subsquent_degree;
	
	String subDegreeJson;
	String working_experience_json;
	String post_academic_experience;

	public StudentPostActiveBean() {
		super();
		this.student_id = 0;
		this.name = null;
		this.employmentName = null;
		this.occupation = null;
		this.position = null;
		this.employ_city = null;
		this.employ_county = null;
		this.employ_state = null;
		this.grud_school_name = null;
		this.grud_city = null;
		this.grud_county = null;
		this.grud_state = null;
		this.subsquent_degree = null;
		this.firstName=null;
		this.middleName=null;
		this.lastName = null;
		this.subDegreeJson=null;
		this.working_experience_json = null;
		this.post_academic_experience = null;
	}

	public StudentPostActiveBean(int student_id, String name) {
		super();
		this.student_id = student_id;
		this.name = name;
		this.employmentName = null;
		this.occupation = null;
		this.position = null;
		this.employ_city = null;
		this.employ_county = null;
		this.employ_state = null;
		this.grud_school_name = null;
		this.grud_city = null;
		this.grud_county = null;
		this.grud_state = null;
		this.subsquent_degree = null;
		this.firstName=null;
		this.middleName=null;
		this.lastName = null;
		this.subDegreeJson=null;
		this.working_experience_json = null;
		this.post_academic_experience = null;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public String getEmploymentName() {
		return employmentName;
	}

	public void setEmploymentName(String employmentName) {
		this.employmentName = employmentName;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmploy_city() {
		return employ_city;
	}

	public void setEmploy_city(String employ_city) {
		this.employ_city = employ_city;
	}

	public String getEmploy_county() {
		return employ_county;
	}

	public void setEmploy_county(String employ_county) {
		this.employ_county = employ_county;
	}

	public String getEmploy_state() {
		return employ_state;
	}

	public void setEmploy_state(String employ_state) {
		this.employ_state = employ_state;
	}

	public String getGrud_school_name() {
		return grud_school_name;
	}

	public void setGrud_school_name(String grud_school_name) {
		this.grud_school_name = grud_school_name;
	}

	public String getGrud_city() {
		return grud_city;
	}

	public void setGrud_city(String grud_city) {
		this.grud_city = grud_city;
	}

	public String getGrud_county() {
		return grud_county;
	}

	public void setGrud_county(String grud_county) {
		this.grud_county = grud_county;
	}

	public String getGrud_state() {
		return grud_state;
	}

	public void setGrud_state(String grud_state) {
		this.grud_state = grud_state;
	}

	public String getSubsquent_degree() {
		return subsquent_degree;
	}

	public void setSubsquent_degree(String subsquent_degree) {
		this.subsquent_degree = subsquent_degree;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompany_comments() {
		return company_comments;
	}

	public void setCompany_comments(String company_comments) {
		this.company_comments = company_comments;
	}

	public String getSubDegreeJson() {
		return subDegreeJson;
	}

	public void setSubDegreeJson(String subDegreeJson) {
		this.subDegreeJson = subDegreeJson;
	}

	public String getWorking_experience_json() {
		return working_experience_json;
	}

	public void setWorking_experience_json(String working_experience_json) {
		this.working_experience_json = working_experience_json;
	}

	public String getPost_academic_experience() {
		return post_academic_experience;
	}

	public void setPost_academic_experience(String post_academic_experience) {
		this.post_academic_experience = post_academic_experience;
	}
}
