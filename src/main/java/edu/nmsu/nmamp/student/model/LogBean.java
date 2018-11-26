package edu.nmsu.nmamp.student.model;

import java.util.Date;

public class LogBean {
	String username;
	String firstname;
	String lastname;
	String tablename;
	String sub_tablename;
	Date submitted_time;
	
	@Override
	public String toString() {
		return "LogBean [username=" + username + ", firstname=" + firstname + ", lastname=" + lastname + ", tablename="
				+ tablename + ", sub_tablename=" + sub_tablename + ", submitted_time=" + submitted_time + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getSub_tablename() {
		return sub_tablename;
	}
	public void setSub_tablename(String sub_tablename) {
		this.sub_tablename = sub_tablename;
	}
	public Date getSubmitted_time() {
		return submitted_time;
	}
	public void setSubmitted_time(Date submitted_time) {
		this.submitted_time = submitted_time;
	} 

}
