package edu.nmsu.nmamp.student.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
@PropertySource("classpath:database_portal.properties")
public class PortalData {
	// private Environment env;
	@Value("${ds.database-driver}")
	private String driver;

	@Value("${ds.url}")
	private String url;

	@Value("${ds.username}")
	private String uname;

	@Value("${ds.password}")
	private String password;

	@Value("${ds.connectPorperties}")
	private String connectProperties;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Connection con;

	public void SynPortalStudenData() {
		// System.out.println("I am SynPortalStudenData function "+
		// env.getProperty("ds.username"));
		System.out.println("bbbbbbbbbbb" + this.driver + " \n" + this.url + "&amp;" + this.connectProperties + " \n"
				+ this.uname + " " + this.password);
		this.connectDB();
		if (this.con != null) {
			System.out.println("Connected Success!!!");
			HashSet<String> userlist = getAcceptedUserList();
			SyncStudentProfile(userlist);

			this.connectClose();
		}
	}

	private void SyncStudentProfile(HashSet<String> userlist) {
		for (String userid : userlist) {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from profile_student where user_id='" + userid + "'");
			System.out.println(sql.toString());
			try {
				Statement stmt = this.con.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());
				if (rs.next()) {
					int user_id = Integer.parseInt(rs.getString("user_id"));
					String first_name = rs.getString("first_name");
					String middle_name = rs.getString("middle_name");
					String last_name = rs.getString("last_name");
					Date birth_date = rs.getDate("birth_date");
					String current_address_line1 = rs.getString("current_address_line1");
					String current_address_line2 = rs.getString("current_address_line2");
					String current_address_city = rs.getString("current_address_city");
					String current_address_county = rs.getString("current_address_county");
					String current_address_state = rs.getString("current_address_state");
					String current_address_zip = rs.getString("current_address_zip");
					String phone_num = rs.getString("phone_num1");
					String ssn_last_four = rs.getString("ssn_last_four");
					String first_gen_college_student = rs.getString("parent_has_degree");
					String gender = rs.getString("gender");
					String ethnicity = rs.getString("is_hispanic");
					String race = rs.getString("race");
					String disability = rs.getString("disability");

					boolean isExisted = existedInStudentDBProfile(user_id);
					System.out.println(userid + "   ======>   " + isExisted);

					StringBuilder updateSql = new StringBuilder();
					StringBuilder insertSql = new StringBuilder();
					updateSql.append("update profile_student set first_name=?,middle_name=?,last_name=?,birth_date=?,"
							+ "current_address_line1=?,current_address_line2=?,current_address_city=?,current_address_county=?,current_address_state=?,current_address_zip=?,"
							+ "phone_num=?,ssn_last_four=?,first_gen_college_student=?,gender=?,ethnicity=?,race=?,disability=? where user_id='"
							+ user_id + "'");
					insertSql.append("insert into profile_student (user_id,first_name,middle_name,last_name,birth_date,"
							+ "current_address_line1,current_address_line2,current_address_city,current_address_county,current_address_state,current_address_zip,"
							+ "phone_num,ssn_last_four,first_gen_college_student,gender,ethnicity,race,disability) values "
							+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

					if (isExisted) {
						jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								// ps.setInt(1, user_id);
								ps.setString(1, first_name);
								ps.setString(2, middle_name);
								ps.setString(3, last_name);
								ps.setDate(4, birth_date);
								ps.setString(5, current_address_line1);
								ps.setString(6, current_address_line2);
								ps.setString(7, current_address_city);
								ps.setString(8, current_address_county);
								ps.setString(9, current_address_state);
								ps.setString(10, current_address_zip);
								ps.setString(11, phone_num);
								ps.setString(12, ssn_last_four);
								ps.setString(13, first_gen_college_student);
								ps.setString(14, gender);
								ps.setString(15, ethnicity);
								ps.setString(16, race);
								ps.setString(17, disability);
							}
						});
					} else {
						jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								ps.setInt(1, user_id);
								ps.setString(2, first_name);
								ps.setString(3, middle_name);
								ps.setString(4, last_name);
								ps.setDate(5, birth_date);
								ps.setString(6, current_address_line1);
								ps.setString(7, current_address_line2);
								ps.setString(8, current_address_city);
								ps.setString(9, current_address_county);
								ps.setString(10, current_address_state);
								ps.setString(11, current_address_zip);
								ps.setString(12, phone_num);
								ps.setString(13, ssn_last_four);
								ps.setString(14, first_gen_college_student);
								ps.setString(15, gender);
								ps.setString(16, ethnicity);
								ps.setString(17, race);
								ps.setString(18, disability);
							}
						});

					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void SynPortalApplicationData() {
		// System.out.println("I am SynPortalStudenData function "+
		// env.getProperty("ds.username"));
		this.connectDB();
		if (this.con != null) {
			System.out.println("Connected Success!!!");
			HashSet<String> userlist = getAcceptedUserList();
			SyncApplicationListTable(userlist);
			this.connectClose();
		}
	}

	private void SyncApplicationListTable(HashSet<String> userlist) {
		for (String userid : userlist) {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from application_list where decision='Admit' and user_id='" + userid + "'");
			System.out.println(sql.toString());
			try {
				Statement stmt = this.con.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());
				while (rs.next()) {
					int application_id = Integer.parseInt(rs.getString("application_id"));
					int user_id = Integer.parseInt(rs.getString("user_id"));
					int school_year = rs.getInt("school_year");
					String school_semester = rs.getString("school_semester");
					String program = rs.getString("program");
					Timestamp start_date = rs.getTimestamp("start_date");
					Timestamp applicant_submit_date = rs.getTimestamp("applicant_submit_date");
					String applicant_signature = rs.getString("applicant_signature");
					Timestamp applicant_signature_date = rs.getTimestamp("applicant_signature_date");
					Timestamp transcript_date = rs.getTimestamp("transcript_date");
					Timestamp mentor_info_date = rs.getTimestamp("mentor_info_date");
					int mentor_id = rs.getInt("applicant_signature_date");
					Timestamp mentor_submit_date = rs.getTimestamp("mentor_submit_date");
					Timestamp recommender_submit_date = rs.getTimestamp("recommender_submit_date");
					Timestamp medical_submit_date = rs.getTimestamp("medical_submit_date");
					Timestamp complete_date = rs.getTimestamp("complete_date");

					String decision = rs.getString("decision");
					Timestamp notified_date = rs.getTimestamp("notified_date");
					String accept_school = rs.getString("accept_school");
					int accept_status = rs.getInt("accept_status");

					boolean isExisted = existedInApplicationList(application_id);
					System.out.println(userid + ":" + application_id + "   ======>   " + isExisted + "--" + school_year
							+ " ## " + mentor_submit_date);

					StringBuilder updateSql = new StringBuilder();
					StringBuilder insertSql = new StringBuilder();
					updateSql.append("update application_list set user_id=?,school_year=?,school_semester=?,program=?,"
							+ "start_date=?,applicant_submit_date=?,applicant_signature=?,applicant_signature_date=?,transcript_date=?,mentor_info_date=?,"
							+ "mentor_id=?,mentor_submit_date=?,recommender_submit_date=?,medical_submit_date=?,complete_date=?,"
							+ "decision=?,notified_date=?,accept_school=?,accept_status=? where application_id='"
							+ application_id + "'");
					insertSql.append(
							"insert into application_list (application_id,user_id,school_year,school_semester,program,"
									+ "start_date,applicant_submit_date,applicant_signature,applicant_signature_date,transcript_date,mentor_info_date,"
									+ "mentor_id,mentor_submit_date,recommender_submit_date,medical_submit_date,complete_date,"
									+ "decision,notified_date,accept_school,accept_status) values "
									+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

					if (isExisted) {
						jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								ps.setInt(1, user_id);
								ps.setInt(2, school_year);
								ps.setString(3, school_semester);
								ps.setString(4, program);
								ps.setTimestamp(5, start_date);
								ps.setTimestamp(6, applicant_submit_date);
								ps.setString(7, applicant_signature);
								ps.setTimestamp(8, applicant_signature_date);
								ps.setTimestamp(9, transcript_date);
								ps.setTimestamp(10, mentor_info_date);
								ps.setInt(11, mentor_id);
								ps.setTimestamp(12, mentor_submit_date);
								ps.setTimestamp(13, recommender_submit_date);
								ps.setTimestamp(14, medical_submit_date);
								ps.setTimestamp(15, complete_date);
								ps.setString(16, decision);
								ps.setTimestamp(17, notified_date);
								ps.setString(18, accept_school);
								ps.setInt(19, accept_status);

							}
						});
					} else {
						jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								ps.setInt(1, application_id);
								ps.setInt(2, user_id);
								ps.setInt(3, school_year);
								ps.setString(4, school_semester);
								ps.setString(5, program);
								ps.setTimestamp(6, start_date);
								ps.setTimestamp(7, applicant_submit_date);
								ps.setString(8, applicant_signature);
								ps.setTimestamp(9, applicant_signature_date);
								ps.setTimestamp(10, transcript_date);
								ps.setTimestamp(11, mentor_info_date);
								ps.setInt(12, mentor_id);
								ps.setTimestamp(13, mentor_submit_date);
								ps.setTimestamp(14, recommender_submit_date);
								ps.setTimestamp(15, medical_submit_date);
								ps.setTimestamp(16, complete_date);
								ps.setString(17, decision);
								ps.setTimestamp(18, notified_date);
								ps.setString(19, accept_school);
								ps.setInt(20, accept_status);
							}
						});

					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private boolean existedInApplicationList(int application_id) {
		int result = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM application_list where decision='Admit' and application_id='" + application_id + "'",
				Integer.class);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean existedInStudentDBProfile(int user_id) {
		int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM profile_student where user_id='" + user_id + "'",
				Integer.class);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	private HashSet<String> getAcceptedUserList() {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct user_id from application_list where decision='Admit'");
		HashSet<String> userList = new HashSet<String>();
		try {
			Statement stmt = this.con.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			int count = 0;
			while (rs.next()) {
				userList.add(rs.getString("user_id"));
				count++;
			}
			System.out.println("total:" + count);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}

	private void connectClose() {
		try {
			this.con.close();
			System.out.println("close connection");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void connectDB() {
		try {
			Class.forName(this.driver);
			Properties ps = new Properties();
			ps.setProperty("user", this.uname);
			ps.setProperty("password", this.password);
			ps.setProperty("autoReconnect", "true");
			ps.setProperty("useSSL", "false");
			ps.setProperty("useUnicode", "true");
			ps.setProperty("characterEncoding", "UTF-8");
			ps.setProperty("serverTimezone", "UTC");

			this.con = DriverManager.getConnection(this.url, ps);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
