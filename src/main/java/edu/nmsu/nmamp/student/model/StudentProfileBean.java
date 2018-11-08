package edu.nmsu.nmamp.student.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class StudentProfileBean {
	int student_id;
	String firstName;
	String middleName;
	String lastName;
	String gender;
	String phone_num;
	int first_gen_college_student;
	String language_at_home;
	int veteran;
	String expected_highest_degree;
	String ssn_last_four;
	int family_income;
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	Date birthDate;

	/********current address*/
	String current_address_line1;
	String current_address_line2;
	String current_address_city;
	String current_address_county;
	String current_address_state;
	String current_address_zip;
	
	/********parent address*/
	String parent_address_line1;
	String parent_address_line2;
	String parent_address_city;
	String parent_address_county;
	String parent_address_state;
	String parent_address_zip;


	/**********ethnicity*************/
	int ethnicity;
	List<String> race;
	
	/**********disability*************/
	String disability;
	int disability_type;
	
	
	/**********high school*************/
	String high_shcool_name;
	String high_school_state;
	String high_school_city;
	String high_shcool_GPA;
	String high_shcool_workhours;
	String high_school_testing;
	String highschool_activities;
	

	String high_school_act_total_score;
	String high_school_act_reading_score;
	String high_school_act_writing_score;
	String high_school_act_math_score;
	String high_school_act_sci_score;
	String high_school_sat_total_score;
	String high_school_sat_reading_score;
	String high_school_sat_writing_score;
	String high_school_sat_math_score;
	String high_school_sat_sci_score;
	
	
	String recommendation_file_name;
	String hs_transcript_file_name;
	
	String pell_grant_eligiblity;

	String comments;
	public String getParent_address_line1() {
		return parent_address_line1;
	}

	public void setParent_address_line1(String parent_address_line1) {
		this.parent_address_line1 = parent_address_line1;
	}

	public String getParent_address_line2() {
		return parent_address_line2;
	}

	public void setParent_address_line2(String parent_address_line2) {
		this.parent_address_line2 = parent_address_line2;
	}

	public String getParent_address_city() {
		return parent_address_city;
	}

	public void setParent_address_city(String parent_address_city) {
		this.parent_address_city = parent_address_city;
	}

	public String getParent_address_county() {
		return parent_address_county;
	}

	public void setParent_address_county(String parent_address_county) {
		this.parent_address_county = parent_address_county;
	}

	public String getParent_address_zip() {
		return parent_address_zip;
	}

	public void setParent_address_zip(String parent_address_zip) {
		this.parent_address_zip = parent_address_zip;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getLanguage_at_home() {
		return language_at_home;
	}

	public void setLanguage_at_home(String language_at_home) {
		this.language_at_home = language_at_home;
	}

	public int getFirst_gen_college_student() {
		return first_gen_college_student;
	}

	public void setFirst_gen_college_student(int first_gen_college_student) {
		this.first_gen_college_student = first_gen_college_student;
	}

	public String getCurrent_address_line1() {
		return current_address_line1;
	}

	public void setCurrent_address_line1(String current_address_line1) {
		this.current_address_line1 = current_address_line1;
	}

	public String getCurrent_address_line2() {
		return current_address_line2;
	}

	public void setCurrent_address_line2(String current_address_line2) {
		this.current_address_line2 = current_address_line2;
	}

	public String getCurrent_address_city() {
		return current_address_city;
	}

	public void setCurrent_address_city(String current_address_city) {
		this.current_address_city = current_address_city;
	}

	public String getCurrent_address_county() {
		return current_address_county;
	}

	public void setCurrent_address_county(String current_address_county) {
		this.current_address_county = current_address_county;
	}

	public String getCurrent_address_zip() {
		return current_address_zip;
	}

	public void setCurrent_address_zip(String current_address_zip) {
		this.current_address_zip = current_address_zip;
	}

	public String getCurrent_address_state() {
		return current_address_state;
	}

	public void setCurrent_address_state(String current_address_state) {
		this.current_address_state = current_address_state;
	}

	public String getParent_address_state() {
		return parent_address_state;
	}

	public void setParent_address_state(String parent_address_state) {
		this.parent_address_state = parent_address_state;
	}

	public String getRecommendation_file_name() {
		return recommendation_file_name;
	}

	public void setRecommendation_file_name(String recommendation_file_name) {
		this.recommendation_file_name = recommendation_file_name;
	}

	public String getHs_transcript_file_name() {
		return hs_transcript_file_name;
	}

	public void setHs_transcript_file_name(String hs_transcript_file_name) {
		this.hs_transcript_file_name = hs_transcript_file_name;
	}

	public int getVeteran() {
		return veteran;
	}

	public void setVeteran(int veteran) {
		this.veteran = veteran;
	}

	public String getExpected_highest_degree() {
		return expected_highest_degree;
	}

	public void setExpected_highest_degree(String expected_highest_degree) {
		this.expected_highest_degree = expected_highest_degree;
	}

	public String getSsn_last_four() {
		return ssn_last_four;
	}

	public void setSsn_last_four(String ssn_last_four) {
		this.ssn_last_four = ssn_last_four;
	}

	public int getFamily_income() {
		return family_income;
	}

	public void setFamily_income(int family_income) {
		this.family_income = family_income;
	}

	public int getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(int ethnicity) {
		this.ethnicity = ethnicity;
	}
	
	public List<String> getRace() {
		if(race == null) return null; 
		return new ArrayList<String>(race);
	}
	public void setRace(List<String> race) {
		if(race == null) return; 
		this.race = new ArrayList<>(race);
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public int getDisability_type() {
		return disability_type;
	}

	public void setDisability_type(int disability_type) {
		this.disability_type = disability_type;
	}

	public String getHigh_shcool_name() {
		return high_shcool_name;
	}

	public void setHigh_shcool_name(String high_shcool_name) {
		this.high_shcool_name = high_shcool_name;
	}
	
	
	public String getHigh_shcool_GPA() {
		return high_shcool_GPA;
	}

	public void setHigh_shcool_GPA(String high_shcool_GPA) {
		this.high_shcool_GPA = high_shcool_GPA;
	}

	public String getHigh_shcool_workhours() {
		return high_shcool_workhours;
	}

	public void setHigh_shcool_workhours(String high_shcool_workhours) {
		this.high_shcool_workhours = high_shcool_workhours;
	}

	public String getHigh_school_testing() {
//		if(high_school_testing == null) return null; 
//		return new ArrayList<String>(high_school_testing);
		return high_school_testing;
	}
	public void setHigh_school_testing(String high_school_testing) {
//		if(high_school_testing == null) return; 
//		this.high_school_testing = new ArrayList<>(high_school_testing);
		this.high_school_testing = high_school_testing;
	}

	public String getHighschool_activities() {
		return highschool_activities;
	}

	public void setHighschool_activities(String highschool_activities) {
		this.highschool_activities = highschool_activities;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPell_grant_eligiblity() {
		return pell_grant_eligiblity;
	}

	public void setPell_grant_eligiblity(String pell_grant_eligiblity) {
		this.pell_grant_eligiblity = pell_grant_eligiblity;
	}

	public String getHigh_school_state() {
		return high_school_state;
	}

	public void setHigh_school_state(String high_school_state) {
		this.high_school_state = high_school_state;
	}

	public String getHigh_school_city() {
		return high_school_city;
	}

	public void setHigh_school_city(String high_school_city) {
		this.high_school_city = high_school_city;
	}
	
	public String getHigh_school_act_total_score() {
		return high_school_act_total_score;
	}

	public void setHigh_school_act_total_score(String high_school_act_total_score) {
		this.high_school_act_total_score = high_school_act_total_score;
	}

	public String getHigh_school_act_reading_score() {
		return high_school_act_reading_score;
	}

	public void setHigh_school_act_reading_score(String high_school_act_reading_score) {
		this.high_school_act_reading_score = high_school_act_reading_score;
	}

	public String getHigh_school_act_writing_score() {
		return high_school_act_writing_score;
	}

	public void setHigh_school_act_writing_score(String high_school_act_writing_score) {
		this.high_school_act_writing_score = high_school_act_writing_score;
	}

	public String getHigh_school_act_math_score() {
		return high_school_act_math_score;
	}

	public void setHigh_school_act_math_score(String high_school_act_math_score) {
		this.high_school_act_math_score = high_school_act_math_score;
	}

	public String getHigh_school_act_sci_score() {
		return high_school_act_sci_score;
	}

	public void setHigh_school_act_sci_score(String high_school_act_sci_score) {
		this.high_school_act_sci_score = high_school_act_sci_score;
	}

	public String getHigh_school_sat_total_score() {
		return high_school_sat_total_score;
	}

	public void setHigh_school_sat_total_score(String high_school_sat_total_score) {
		this.high_school_sat_total_score = high_school_sat_total_score;
	}

	public String getHigh_school_sat_reading_score() {
		return high_school_sat_reading_score;
	}

	public void setHigh_school_sat_reading_score(String high_school_sat_reading_score) {
		this.high_school_sat_reading_score = high_school_sat_reading_score;
	}

	public String getHigh_school_sat_writing_score() {
		return high_school_sat_writing_score;
	}

	public void setHigh_school_sat_writing_score(String high_school_sat_writing_score) {
		this.high_school_sat_writing_score = high_school_sat_writing_score;
	}

	public String getHigh_school_sat_math_score() {
		return high_school_sat_math_score;
	}

	public void setHigh_school_sat_math_score(String high_school_sat_math_score) {
		this.high_school_sat_math_score = high_school_sat_math_score;
	}

	public String getHigh_school_sat_sci_score() {
		return high_school_sat_sci_score;
	}

	public void setHigh_school_sat_sci_score(String high_school_sat_sci_score) {
		this.high_school_sat_sci_score = high_school_sat_sci_score;
	}

	@Override
	public String toString() {
		return "StudentProfileBean [student_id=" + student_id + ", firstName=" + firstName + ", middleName="
				+ middleName + ", lastName=" + lastName + ", gender=" + gender + ", phone_num=" + phone_num
				+ ", first_gen_college_student=" + first_gen_college_student + ", language_at_home=" + language_at_home
				+ ", veteran=" + veteran + ", expected_highest_degree=" + expected_highest_degree + ", ssn_last_four="
				+ ssn_last_four + ", family_income=" + family_income + ", birthDate=" + birthDate
				+ ", current_address_line1=" + current_address_line1 + ", current_address_line2="
				+ current_address_line2 + ", current_address_city=" + current_address_city + ", current_address_county="
				+ current_address_county + ", current_address_state=" + current_address_state + ", current_address_zip="
				+ current_address_zip + ", parent_address_line1=" + parent_address_line1 + ", parent_address_line2="
				+ parent_address_line2 + ", parent_address_city=" + parent_address_city + ", parent_address_county="
				+ parent_address_county + ", parent_address_state=" + parent_address_state + ", parent_address_zip="
				+ parent_address_zip + ", ethnicity=" + ethnicity + ", race=" + race + ", disability=" + disability
				+ ", disability_type=" + disability_type + ", high_shcool_name=" + high_shcool_name
				+ ", high_school_state=" + high_school_state + ", high_school_city=" + high_school_city
				+ ", high_shcool_GPA=" + high_shcool_GPA + ", high_shcool_workhours=" + high_shcool_workhours
				+ ", high_school_testing=" + high_school_testing + ", highschool_activities=" + highschool_activities
				+ ", high_school_act_total_score=" + high_school_act_total_score + ", high_school_act_reading_score="
				+ high_school_act_reading_score + ", high_school_act_writing_score=" + high_school_act_writing_score
				+ ", high_school_act_math_score=" + high_school_act_math_score + ", high_school_act_sci_score="
				+ high_school_act_sci_score + ", high_school_sat_total_score=" + high_school_sat_total_score
				+ ", high_school_sat_reading_score=" + high_school_sat_reading_score
				+ ", high_school_sat_writing_score=" + high_school_sat_writing_score + ", high_school_sat_math_score="
				+ high_school_sat_math_score + ", high_school_sat_sci_score=" + high_school_sat_sci_score
				+ ", recommendation_file_name=" + recommendation_file_name + ", hs_transcript_file_name="
				+ hs_transcript_file_name + ", pell_grant_eligiblity=" + pell_grant_eligiblity + ", comments="
				+ comments + ", getParent_address_line1()=" + getParent_address_line1() + ", getParent_address_line2()="
				+ getParent_address_line2() + ", getParent_address_city()=" + getParent_address_city()
				+ ", getParent_address_county()=" + getParent_address_county() + ", getParent_address_zip()="
				+ getParent_address_zip() + ", getStudent_id()=" + getStudent_id() + ", getFirstName()="
				+ getFirstName() + ", getMiddleName()=" + getMiddleName() + ", getLastName()=" + getLastName()
				+ ", getGender()=" + getGender() + ", getBirthDate()=" + getBirthDate() + ", getPhone_num()="
				+ getPhone_num() + ", getLanguage_at_home()=" + getLanguage_at_home()
				+ ", getFirst_gen_college_student()=" + getFirst_gen_college_student() + ", getCurrent_address_line1()="
				+ getCurrent_address_line1() + ", getCurrent_address_line2()=" + getCurrent_address_line2()
				+ ", getCurrent_address_city()=" + getCurrent_address_city() + ", getCurrent_address_county()="
				+ getCurrent_address_county() + ", getCurrent_address_zip()=" + getCurrent_address_zip()
				+ ", getCurrent_address_state()=" + getCurrent_address_state() + ", getParent_address_state()="
				+ getParent_address_state() + ", getRecommendation_file_name()=" + getRecommendation_file_name()
				+ ", getHs_transcript_file_name()=" + getHs_transcript_file_name() + ", getVeteran()=" + getVeteran()
				+ ", getExpected_highest_degree()=" + getExpected_highest_degree() + ", getSsn_last_four()="
				+ getSsn_last_four() + ", getFamily_income()=" + getFamily_income() + ", getEthnicity()="
				+ getEthnicity() + ", getRace()=" + getRace() + ", getDisability()=" + getDisability()
				+ ", getDisability_type()=" + getDisability_type() + ", getHigh_shcool_name()=" + getHigh_shcool_name()
				+ ", getHigh_shcool_GPA()=" + getHigh_shcool_GPA() + ", getHigh_shcool_workhours()="
				+ getHigh_shcool_workhours() + ", getHigh_school_testing()=" + getHigh_school_testing()
				+ ", getHighschool_activities()=" + getHighschool_activities() + ", getComments()=" + getComments()
				+ ", getPell_grant_eligiblity()=" + getPell_grant_eligiblity() + ", getHigh_school_state()="
				+ getHigh_school_state() + ", getHigh_school_city()=" + getHigh_school_city()
				+ ", getHigh_school_act_total_score()=" + getHigh_school_act_total_score()
				+ ", getHigh_school_act_reading_score()=" + getHigh_school_act_reading_score()
				+ ", getHigh_school_act_writing_score()=" + getHigh_school_act_writing_score()
				+ ", getHigh_school_act_math_score()=" + getHigh_school_act_math_score()
				+ ", getHigh_school_act_sci_score()=" + getHigh_school_act_sci_score()
				+ ", getHigh_school_sat_total_score()=" + getHigh_school_sat_total_score()
				+ ", getHigh_school_sat_reading_score()=" + getHigh_school_sat_reading_score()
				+ ", getHigh_school_sat_writing_score()=" + getHigh_school_sat_writing_score()
				+ ", getHigh_school_sat_math_score()=" + getHigh_school_sat_math_score()
				+ ", getHigh_school_sat_sci_score()=" + getHigh_school_sat_sci_score() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
	

}
