package edu.nmsu.nmamp.student.model;

public class StudentYearlyReportBean {
	int student_id;
	String firstName;
	String middleName;
	String lastName;
	
	String acdemic_school;
	String school_level;
	String major;
	String minor;
	int changed_major;
	String course_taken;
	float gpa;
	float semester_gpa;
	int credits;
	int semester_credits;
	
	//Graduate Information
	String graduated;
	String graduated_degree;
	String graduated_field;
	String graduated_semester;
	
	//Transfered Information
	String transfered;
	String transfered_AA_degree;
	String transfered_from;
	String transfered_to;
	String transfered_credits;
	
	//Withdrew Information
	String withdrew;
	String withdrew_reason;
	
	//Financial Information
	String fin_amp;
	String fin_amp_type;
	String fin_amp_summer;
	
	//activitiesList
	String activities_list;
	String activities_comments;
	
	//Internship Json
	String intern_json;
	String intern_comments;
	
	//Conferences Json
	String conference_json;
	
	//publication Json
	String publication_json;
	
	//volunteer experiences Json 
	String volunteer_json;
	
	//travel_json
	String travel_json;
	
	
	//Notes and comments
	String notesAndComments;
	
	String mentor_id;
	String mentor_name;
	
	
	
	public StudentYearlyReportBean(int student_id, String firstName, String middleName, String lastName) {
		super();
		this.student_id = student_id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.acdemic_school = null;
		this.school_level = null;
		this.major = null;
		this.minor = null;
		this.changed_major = -1;
		this.course_taken = null;
		this.gpa = 0;
		this.semester_gpa = 0;
		this.credits = 0;
		this.semester_credits = 0;
		this.graduated = null;
		this.graduated_degree = null;
		this.graduated_field = null;
		this.graduated_semester = null;
		this.transfered = null;
		this.transfered_AA_degree = null;
		this.transfered_from = null;
		this.transfered_to = null;
		this.transfered_credits = null;
		this.withdrew = null;
		this.withdrew_reason = null;
		this.fin_amp = null;
		this.fin_amp_type = null;
		this.fin_amp_summer = null;
		this.activities_list = null;
		this.activities_comments = null;
		this.intern_json = null;
		this.intern_comments = null;
		this.conference_json = null;
		this.publication_json = null;
		this.volunteer_json = null;
		this.travel_json = null;
		this.notesAndComments = null;
		this.mentor_id=null;
	}
	
	
	
	
	
	public StudentYearlyReportBean() {
		super();
		this.student_id = -1;
		this.firstName = null;
		this.middleName = null;
		this.lastName = null;
		this.acdemic_school = null;
		this.school_level = null;
		this.major = null;
		this.minor = null;
		this.changed_major = -1;
		this.course_taken = null;
		this.gpa = 0;
		this.semester_gpa = 0;
		this.credits = 0;
		this.semester_credits = 0;
		this.graduated = null;
		this.graduated_degree = null;
		this.graduated_field = null;
		this.graduated_semester = null;
		this.transfered = null;
		this.transfered_AA_degree = null;
		this.transfered_from = null;
		this.transfered_to = null;
		this.transfered_credits = null;
		this.withdrew = null;
		this.withdrew_reason = null;
		this.fin_amp = null;
		this.fin_amp_type = null;
		this.fin_amp_summer = null;
		this.activities_list = null;
		this.activities_comments = null;
		this.intern_json = null;
		this.intern_comments = null;
		this.conference_json = null;
		this.publication_json = null;
		this.volunteer_json = null;
		this.travel_json = null;
		this.notesAndComments = null;
		this.mentor_id=null;
	}





	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
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
	public String getAcdemic_school() {
		return acdemic_school;
	}
	public void setAcdemic_school(String acdemic_school) {
		this.acdemic_school = acdemic_school;
	}
	public String getSchool_level() {
		return school_level;
	}
	public void setSchool_level(String school_level) {
		this.school_level = school_level;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getMinor() {
		return minor;
	}
	public void setMinor(String minor) {
		this.minor = minor;
	}
	public int getChanged_major() {
		return changed_major;
	}
	public void setChanged_major(int changed_major) {
		this.changed_major = changed_major;
	}
	public String getCourse_taken() {
		return course_taken;
	}
	public void setCourse_taken(String course_taken) {
		this.course_taken = course_taken;
	}
	public float getGpa() {
		return gpa;
	}
	public void setGpa(float gpa) {
		this.gpa = gpa;
	}
	public float getSemester_gpa() {
		return semester_gpa;
	}
	public void setSemester_gpa(float semester_gpa) {
		this.semester_gpa = semester_gpa;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public int getSemester_credits() {
		return semester_credits;
	}
	public void setSemester_credits(int semester_credits) {
		this.semester_credits = semester_credits;
	}
	public String getGraduated() {
		return graduated;
	}
	public void setGraduated(String graduated) {
		this.graduated = graduated;
	}
	public String getGraduated_degree() {
		return graduated_degree;
	}
	public void setGraduated_degree(String graduated_degree) {
		this.graduated_degree = graduated_degree;
	}
	public String getGraduated_field() {
		return graduated_field;
	}
	public void setGraduated_field(String graduated_field) {
		this.graduated_field = graduated_field;
	}
	public String getGraduated_semester() {
		return graduated_semester;
	}
	public void setGraduated_semester(String graduated_semester) {
		this.graduated_semester = graduated_semester;
	}
	public String getTransfered() {
		return transfered;
	}
	public void setTransfered(String transfered) {
		this.transfered = transfered;
	}
	public String getTransfered_AA_degree() {
		return transfered_AA_degree;
	}
	public void setTransfered_AA_degree(String transfered_AA_degree) {
		this.transfered_AA_degree = transfered_AA_degree;
	}
	public String getTransfered_from() {
		return transfered_from;
	}
	public void setTransfered_from(String transfered_from) {
		this.transfered_from = transfered_from;
	}
	public String getTransfered_to() {
		return transfered_to;
	}
	public void setTransfered_to(String transfered_to) {
		this.transfered_to = transfered_to;
	}
	public String getTransfered_credits() {
		return transfered_credits;
	}
	public void setTransfered_credits(String transfered_credits) {
		this.transfered_credits = transfered_credits;
	}
	public String getWithdrew() {
		return withdrew;
	}
	public void setWithdrew(String withdrew) {
		this.withdrew = withdrew;
	}
	public String getWithdrew_reason() {
		return withdrew_reason;
	}
	public void setWithdrew_reason(String withdrew_reason) {
		this.withdrew_reason = withdrew_reason;
	}
	public String getFin_amp() {
		return fin_amp;
	}
	public void setFin_amp(String fin_amp) {
		this.fin_amp = fin_amp;
	}
	public String getFin_amp_type() {
		return fin_amp_type;
	}
	public void setFin_amp_type(String fin_amp_type) {
		this.fin_amp_type = fin_amp_type;
	}
	public String getFin_amp_summer() {
		return fin_amp_summer;
	}
	public void setFin_amp_summer(String fin_amp_summer) {
		this.fin_amp_summer = fin_amp_summer;
	}
	public String getActivities_list() {
		return activities_list;
	}
	public void setActivities_list(String activities_list) {
		this.activities_list = activities_list;
	}
	public String getActivities_comments() {
		return activities_comments;
	}
	public void setActivities_comments(String activities_comments) {
		this.activities_comments = activities_comments;
	}
	public String getIntern_json() {
		return intern_json;
	}
	public void setIntern_json(String intern_json) {
		this.intern_json = intern_json;
	}
	public String getIntern_comments() {
		return intern_comments;
	}
	public void setIntern_comments(String intern_comments) {
		this.intern_comments = intern_comments;
	}
	public String getConference_json() {
		return conference_json;
	}
	public void setConference_json(String conference_json) {
		this.conference_json = conference_json;
	}
	public String getPublication_json() {
		return publication_json;
	}
	public void setPublication_json(String publication_json) {
		this.publication_json = publication_json;
	}
	public String getVolunteer_json() {
		return volunteer_json;
	}
	public void setVolunteer_json(String volunteer_json) {
		this.volunteer_json = volunteer_json;
	}
	public String getTravel_json() {
		return travel_json;
	}
	public void setTravel_json(String travel_json) {
		this.travel_json = travel_json;
	}
	
	public String getNotesAndComments() {
		return notesAndComments;
	}
	public void setNotesAndComments(String notesAndComments) {
		this.notesAndComments = notesAndComments;
	}
	
	public String getMentor_id() {
		return mentor_id;
	}
	public void setMentor_id(String mentor_id) {
		this.mentor_id = mentor_id;
	}
	
	public String getMentor_name() {
		return mentor_name;
	}
	public void setMentor_name(String mentor_name) {
		this.mentor_name = mentor_name;
	}
	
	@Override
	public String toString() {
		return "StudentYearlyReportBean [student_id=" + student_id + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", acdemic_school=" + acdemic_school + ", school_level="
				+ school_level + ", major=" + major + ", minor=" + minor + ", changed_major=" + changed_major
				+ ", course_taken=" + course_taken + ", gpa=" + gpa + ", semester_gpa=" + semester_gpa + ", credits="
				+ credits + ", semester_credits=" + semester_credits + ", graduated=" + graduated
				+ ", graduated_degree=" + graduated_degree + ", graduated_field=" + graduated_field
				+ ", graduated_semester=" + graduated_semester + ", transfered=" + transfered
				+ ", transfered_AA_degree=" + transfered_AA_degree + ", transfered_from=" + transfered_from
				+ ", transfered_to=" + transfered_to + ", transfered_credits=" + transfered_credits + ", withdrew="
				+ withdrew + ", withdrew_reason=" + withdrew_reason + ", getStudent_id()=" + getStudent_id()
				+ ", getFirstName()=" + getFirstName() + ", getMiddleName()=" + getMiddleName() + ", getLastName()="
				+ getLastName() + ", getAcdemic_school()=" + getAcdemic_school() + ", getSchool_level()="
				+ getSchool_level() + ", getMajor()=" + getMajor() + ", getMinor()=" + getMinor()
				+ ", getChanged_major()=" + getChanged_major() + ", getCourse_taken()=" + getCourse_taken()
				+ ", getGpa()=" + getGpa() + ", getSemester_gpa()=" + getSemester_gpa() + ", getCredits()="
				+ getCredits() + ", getSemester_credits()=" + getSemester_credits() + ", getGraduated()="
				+ getGraduated() + ", getGraduated_degree()=" + getGraduated_degree() + ", getGraduated_field()="
				+ getGraduated_field() + ", getGraduated_semester()=" + getGraduated_semester() + ", getTransfered()="
				+ getTransfered() + ", getTransfered_AA_degree()=" + getTransfered_AA_degree()
				+ ", getTransfered_from()=" + getTransfered_from() + ", getTransfered_to()=" + getTransfered_to()
				+ ", getTransfered_credits()=" + getTransfered_credits() + ", getWithdrew()=" + getWithdrew()
				+ ", getWithdrew_reason()=" + getWithdrew_reason() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
