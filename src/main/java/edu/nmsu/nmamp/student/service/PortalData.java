package edu.nmsu.nmamp.student.service;

import java.sql.Blob;
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
		int count = 0;

		for (String userid : userlist) {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from application_list where decision='Admit' and user_id='" + userid + "'");
//			sql.append("select * from application_list where user_id='" + userid + "'");
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

					boolean isExisted = existedInApplicationList(user_id, application_id);
					System.out.println(userid + ":" + application_id + "   ======>   " + isExisted + "--" + school_year
							+ " ## " + mentor_submit_date + "##" + program);

					SyncApplicationDetails(user_id, application_id, program);
					count++;

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
		System.out.println("application total:" + count);

	}

	private void SyncApplicationDetails(int user_id, int application_id, String program) {
		String tablename = ProgramCode.TABLE_APPLICATION_DETAIL.get(program);
		switch (program) {
		case "URS":
			SyncApplicationDetailsURS(user_id, application_id, program, tablename);
			break;
		case "CCCONF":
			SyncApplicationDetailsCCCONF(user_id, application_id, program, tablename);
			break;
		case "IREP":
			SyncApplicationDetailsIREP(user_id, application_id, program, tablename);
			break;
		case "MESA":
			SyncApplicationDetailsMESA(user_id, application_id, program, tablename);
			break;
		case "SCCORE":
			SyncApplicationDetailsSCCORE(user_id, application_id, program, tablename);
			break;
		case "TRANS":
			SyncApplicationDetailsTRANS(user_id, application_id, program, tablename);
			break;
		default:
			System.out.println(ProgramCode.TABLE_APPLICATION_DETAIL.get(program));
		}
	}

	private void SyncApplicationDetailsTRANS(int user_id, int application_id, String program, String tablename) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + tablename + " where application_id='" + application_id + "' and user_id='"
				+ user_id + "'");
		System.out.println(sql.toString());
		try {
			Statement stmt = this.con.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				String academic_school = rs.getString("academic_school");
				String academic_year = rs.getString("academic_year");
				Date academic_grad_date = rs.getDate("academic_grad_date");
				String academic_banner_id = rs.getString("academic_banner_id");
				String academic_major = rs.getString("academic_major");
				String academic_minor = rs.getString("academic_minor");
				float academic_gpa = rs.getFloat("academic_gpa");
				String academic_transfer_school = rs.getString("academic_transfer_school");
				Date transfer_date = rs.getDate("transfer_date");
				String academic_intended_major = rs.getString("academic_intended_major");
				String academic_referrer = rs.getString("academic_referrer");
				int amp_scholarship = rs.getInt("amp_scholarship");
				String amp_scholarship_school = rs.getString("amp_scholarship_school");
				String amp_scholarship_type = rs.getString("amp_scholarship_type");
				String amp_scholarship_semester = rs.getString("amp_scholarship_semester");
				String amp_scholarship_year = rs.getString("amp_scholarship_year");
				String amp_scholarship_amount = rs.getString("amp_scholarship_amount");
				String other_scholarship = rs.getString("other_scholarship");
				String list_other_scholarship = rs.getString("list_other_scholarship");
				String essay_profesional_goal = rs.getString("essay_profesional_goal");
				String essay_field_of_interest = rs.getString("essay_field_of_interest");
				String essay_critical_event = rs.getString("essay_critical_event");
				String essay_mentor = rs.getString("essay_mentor");
				String essay_amp_gain = rs.getString("essay_amp_gain");
				String recommender_first_name = rs.getString("recommender_first_name");
				String recommender_last_name = rs.getString("recommender_last_name");
				String recommender_prefix = rs.getString("recommender_prefix");
				String recommender_email = rs.getString("recommender_email");
				String recommender_institution = rs.getString("recommender_institution");
				String recommender_phone = rs.getString("recommender_phone");
				String recommender_address_line1 = rs.getString("recommender_address_line1");
				String recommender_address_line2 = rs.getString("recommender_address_line2");
				String recommender_address_city = rs.getString("recommender_address_city");
				String recommender_address_state = rs.getString("recommender_address_state");
				String recommender_address_zip = rs.getString("recommender_address_zip");
				String recommender_address_country = rs.getString("recommender_address_country");
				String recommendation_key = rs.getString("recommendation_key");
				long recommendation_timeout = rs.getLong("recommendation_timeout");
				String recommendation_file_name = rs.getString("recommendation_file_name");
				String recommendation_file_type = rs.getString("recommendation_file_type");
				long recommendation_file_size = rs.getLong("recommendation_file_size");
				Blob recommendation_file_content = rs.getBlob("recommendation_file_content");
				String transcript_name = rs.getString("transcript_name");
				String transcript_type = rs.getString("transcript_type");
				long transcript_size = rs.getLong("transcript_size");
				Blob transcript_content = rs.getBlob("transcript_content");

				boolean isExisted = existedInApplicationDetails(user_id, application_id, tablename);
				System.out.println(isExisted);
				StringBuilder updateSql = new StringBuilder();
				StringBuilder insertSql = new StringBuilder();
				updateSql.append("update " + tablename
						+ " set academic_school=?,academic_year=?,academic_grad_date=?,academic_banner_id=?,academic_major=?,academic_minor=?,academic_gpa=?,academic_transfer_school=?,"
						+ "transfer_date=?,academic_intended_major=?,academic_referrer=?,amp_scholarship=?,amp_scholarship_school=?,amp_scholarship_type=?,amp_scholarship_semester=?,"
						+ "amp_scholarship_year=?,amp_scholarship_amount=?,other_scholarship=?,list_other_scholarship=?,essay_profesional_goal=?,essay_field_of_interest=?,"
						+ "essay_critical_event=?,essay_mentor=?,essay_amp_gain=?,recommender_first_name=?,recommender_last_name=?,recommender_prefix=?,recommender_email=?,"
						+ "recommender_institution=?,recommender_phone=?,recommender_address_line1=?,recommender_address_line2=?,recommender_address_city=?,recommender_address_state=?,"
						+ "recommender_address_zip=?,recommender_address_country=?,recommendation_key=?,recommendation_timeout=?,recommendation_file_name=?,recommendation_file_type=?,"
						+ "recommendation_file_size=?,recommendation_file_content=?,transcript_name=?,transcript_type=?,transcript_size=?,transcript_content=?"
						+ " where application_id='" + application_id + "' and user_id='" + user_id + "'");
				insertSql.append("insert into " + tablename
						+ " (user_id,application_id,academic_school,academic_year,academic_grad_date,academic_banner_id,academic_major,academic_minor,academic_gpa,academic_transfer_school,"
						+ "transfer_date,academic_intended_major,academic_referrer,amp_scholarship,amp_scholarship_school,amp_scholarship_type,amp_scholarship_semester,amp_scholarship_year,"
						+ "amp_scholarship_amount,other_scholarship,list_other_scholarship,essay_profesional_goal,essay_field_of_interest,essay_critical_event,essay_mentor,essay_amp_gain,"
						+ "recommender_first_name,recommender_last_name,recommender_prefix,recommender_email,recommender_institution,recommender_phone,recommender_address_line1,"
						+ "recommender_address_line2,recommender_address_city,recommender_address_state,recommender_address_zip,recommender_address_country,recommendation_key,"
						+ "recommendation_timeout,recommendation_file_name,recommendation_file_type,recommendation_file_size,recommendation_file_content,transcript_name,transcript_type,"
						+ "transcript_size,transcript_content) values "
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

				if (isExisted) {
					jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, academic_school);
							ps.setString(2, academic_year);
							ps.setDate(3, academic_grad_date);
							ps.setString(4, academic_banner_id);
							ps.setString(5, academic_major);
							ps.setString(6, academic_minor);
							ps.setFloat(7, academic_gpa);
							ps.setString(8, academic_transfer_school);
							ps.setDate(9, transfer_date);
							ps.setString(10, academic_intended_major);
							ps.setString(11, academic_referrer);
							ps.setInt(12, amp_scholarship);
							ps.setString(13, amp_scholarship_school);
							ps.setString(14, amp_scholarship_type);
							ps.setString(15, amp_scholarship_semester);
							ps.setString(16, amp_scholarship_year);
							ps.setString(17, amp_scholarship_amount);
							ps.setString(18, other_scholarship);
							ps.setString(19, list_other_scholarship);
							ps.setString(20, essay_profesional_goal);
							ps.setString(21, essay_field_of_interest);
							ps.setString(22, essay_critical_event);
							ps.setString(23, essay_mentor);
							ps.setString(24, essay_amp_gain);
							ps.setString(25, recommender_first_name);
							ps.setString(26, recommender_last_name);
							ps.setString(27, recommender_prefix);
							ps.setString(28, recommender_email);
							ps.setString(29, recommender_institution);
							ps.setString(30, recommender_phone);
							ps.setString(31, recommender_address_line1);
							ps.setString(32, recommender_address_line2);
							ps.setString(33, recommender_address_city);
							ps.setString(34, recommender_address_state);
							ps.setString(35, recommender_address_zip);
							ps.setString(36, recommender_address_country);
							ps.setString(37, recommendation_key);
							ps.setLong(38, recommendation_timeout);
							ps.setString(39, recommendation_file_name);
							ps.setString(40, recommendation_file_type);
							ps.setLong(41, recommendation_file_size);
							ps.setBlob(42, recommendation_file_content);
							ps.setString(43, transcript_name);
							ps.setString(44, transcript_type);
							ps.setLong(45, transcript_size);
							ps.setBlob(46, transcript_content);

						}
					});

				} else {
					jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setInt(1, user_id);
							ps.setInt(2, application_id);
							ps.setString(3, academic_school);
							ps.setString(4, academic_year);
							ps.setDate(5, academic_grad_date);
							ps.setString(6, academic_banner_id);
							ps.setString(7, academic_major);
							ps.setString(8, academic_minor);
							ps.setFloat(9, academic_gpa);
							ps.setString(10, academic_transfer_school);
							ps.setDate(11, transfer_date);
							ps.setString(12, academic_intended_major);
							ps.setString(13, academic_referrer);
							ps.setInt(14, amp_scholarship);
							ps.setString(15, amp_scholarship_school);
							ps.setString(16, amp_scholarship_type);
							ps.setString(17, amp_scholarship_semester);
							ps.setString(18, amp_scholarship_year);
							ps.setString(19, amp_scholarship_amount);
							ps.setString(20, other_scholarship);
							ps.setString(21, list_other_scholarship);
							ps.setString(22, essay_profesional_goal);
							ps.setString(23, essay_field_of_interest);
							ps.setString(24, essay_critical_event);
							ps.setString(25, essay_mentor);
							ps.setString(26, essay_amp_gain);
							ps.setString(27, recommender_first_name);
							ps.setString(28, recommender_last_name);
							ps.setString(29, recommender_prefix);
							ps.setString(30, recommender_email);
							ps.setString(31, recommender_institution);
							ps.setString(32, recommender_phone);
							ps.setString(33, recommender_address_line1);
							ps.setString(34, recommender_address_line2);
							ps.setString(35, recommender_address_city);
							ps.setString(36, recommender_address_state);
							ps.setString(37, recommender_address_zip);
							ps.setString(38, recommender_address_country);
							ps.setString(39, recommendation_key);
							ps.setLong(40, recommendation_timeout);
							ps.setString(41, recommendation_file_name);
							ps.setString(42, recommendation_file_type);
							ps.setLong(43, recommendation_file_size);
							ps.setBlob(44, recommendation_file_content);
							ps.setString(45, transcript_name);
							ps.setString(46, transcript_type);
							ps.setLong(47, transcript_size);
							ps.setBlob(48, transcript_content);
						}
					});
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void SyncApplicationDetailsSCCORE(int user_id, int application_id, String program, String tablename) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + tablename + " where application_id='" + application_id + "' and user_id='"
				+ user_id + "'");
		System.out.println(sql.toString());
		try {
			Statement stmt = this.con.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				String academic_school = rs.getString("academic_school");
				String academic_year = rs.getString("academic_year");
				Date academic_grad_date = rs.getDate("academic_grad_date");
				String academic_banner_id = rs.getString("academic_banner_id");
				String academic_major = rs.getString("academic_major");
				String academic_minor = rs.getString("academic_minor");
				float academic_gpa = rs.getFloat("academic_gpa");
				int program_ever_in = rs.getInt("program_ever_in");
				int program_ever_in_year = rs.getInt("program_ever_in_year");
				int amp_scholarship = rs.getInt("amp_scholarship");
				String amp_scholarship_school = rs.getString("amp_scholarship_school");
				String amp_scholarship_type = rs.getString("amp_scholarship_type");
				String amp_scholarship_semester = rs.getString("amp_scholarship_semester");
				String amp_scholarship_year = rs.getString("amp_scholarship_year");
				String amp_scholarship_amount = rs.getString("amp_scholarship_amount");
				String other_scholarship = rs.getString("other_scholarship");
				String list_other_scholarship = rs.getString("list_other_scholarship");
				String sccore_school_attend_pref = rs.getString("sccore_school_attend_pref");
				String sccore_school_attend_altn = rs.getString("sccore_school_attend_altn");
				String essay_field_of_study = rs.getString("essay_field_of_study");
				String essay_educational_goal = rs.getString("essay_educational_goal");
				String essay_preferred_research = rs.getString("essay_preferred_research");
				String essay_strengths_bring = rs.getString("essay_strengths_bring");
				String essay_amp_gain = rs.getString("essay_amp_gain");
				String transcript_name = rs.getString("transcript_name");
				String transcript_type = rs.getString("transcript_type");
				long transcript_size = rs.getLong("transcript_size");
				Blob transcript_content = rs.getBlob("transcript_content");
				String medical_name = rs.getString("medical_name");
				String medical_type = rs.getString("medical_type");
				long medical_size = rs.getLong("medical_size");
				Blob medical_content = rs.getBlob("medical_content");

				boolean isExisted = existedInApplicationDetails(user_id, application_id, tablename);
				System.out.println(isExisted);
				StringBuilder updateSql = new StringBuilder();
				StringBuilder insertSql = new StringBuilder();
				updateSql.append("update " + tablename
						+ " set academic_school=?,academic_year=?,academic_grad_date=?,academic_banner_id=?,academic_major=?,academic_minor=?,academic_gpa=?,program_ever_in=?,"
						+ "program_ever_in_year=?,amp_scholarship=?,amp_scholarship_school=?,amp_scholarship_type=?,amp_scholarship_semester=?,amp_scholarship_year=?,"
						+ "amp_scholarship_amount=?,other_scholarship=?,list_other_scholarship=?,sccore_school_attend_pref=?,sccore_school_attend_altn=?,essay_field_of_study=?,"
						+ "essay_educational_goal=?,essay_preferred_research=?,essay_strengths_bring=?,essay_amp_gain=?,transcript_name=?,transcript_type=?,transcript_size=?,"
						+ "transcript_content=?,medical_name=?,medical_type=?,medical_size=?,medical_content=?"
						+ " where application_id='" + application_id + "' and user_id='" + user_id + "'");
				insertSql.append("insert into " + tablename
						+ " (user_id,application_id,academic_school,academic_year,academic_grad_date,academic_banner_id,academic_major,academic_minor,academic_gpa,"
						+ "program_ever_in,program_ever_in_year,amp_scholarship,amp_scholarship_school,amp_scholarship_type,amp_scholarship_semester,amp_scholarship_year,"
						+ "amp_scholarship_amount,other_scholarship,list_other_scholarship,sccore_school_attend_pref,sccore_school_attend_altn,essay_field_of_study,"
						+ "essay_educational_goal,essay_preferred_research,essay_strengths_bring,essay_amp_gain,transcript_name,transcript_type,transcript_size,"
						+ "transcript_content,medical_name,medical_type,medical_size,medical_content) values "
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

				if (isExisted) {
					jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, academic_school);
							ps.setString(2, academic_year);
							ps.setDate(3, academic_grad_date);
							ps.setString(4, academic_banner_id);
							ps.setString(5, academic_major);
							ps.setString(6, academic_minor);
							ps.setFloat(7, academic_gpa);
							ps.setInt(8, program_ever_in);
							ps.setInt(9, program_ever_in_year);
							ps.setInt(10, amp_scholarship);
							ps.setString(11, amp_scholarship_school);
							ps.setString(12, amp_scholarship_type);
							ps.setString(13, amp_scholarship_semester);
							ps.setString(14, amp_scholarship_year);
							ps.setString(15, amp_scholarship_amount);
							ps.setString(16, other_scholarship);
							ps.setString(17, list_other_scholarship);
							ps.setString(18, sccore_school_attend_pref);
							ps.setString(19, sccore_school_attend_altn);
							ps.setString(20, essay_field_of_study);
							ps.setString(21, essay_educational_goal);
							ps.setString(22, essay_preferred_research);
							ps.setString(23, essay_strengths_bring);
							ps.setString(24, essay_amp_gain);
							ps.setString(25, transcript_name);
							ps.setString(26, transcript_type);
							ps.setLong(27, transcript_size);
							ps.setBlob(28, transcript_content);
							ps.setString(29, medical_name);
							ps.setString(30, medical_type);
							ps.setLong(31, medical_size);
							ps.setBlob(32, medical_content);

						}
					});

				} else {
					jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setInt(1, user_id);
							ps.setInt(2, application_id);
							ps.setString(3, academic_school);
							ps.setString(4, academic_year);
							ps.setDate(5, academic_grad_date);
							ps.setString(6, academic_banner_id);
							ps.setString(7, academic_major);
							ps.setString(8, academic_minor);
							ps.setFloat(9, academic_gpa);
							ps.setInt(10, program_ever_in);
							ps.setInt(11, program_ever_in_year);
							ps.setInt(12, amp_scholarship);
							ps.setString(13, amp_scholarship_school);
							ps.setString(14, amp_scholarship_type);
							ps.setString(15, amp_scholarship_semester);
							ps.setString(16, amp_scholarship_year);
							ps.setString(17, amp_scholarship_amount);
							ps.setString(18, other_scholarship);
							ps.setString(19, list_other_scholarship);
							ps.setString(20, sccore_school_attend_pref);
							ps.setString(21, sccore_school_attend_altn);
							ps.setString(22, essay_field_of_study);
							ps.setString(23, essay_educational_goal);
							ps.setString(24, essay_preferred_research);
							ps.setString(25, essay_strengths_bring);
							ps.setString(26, essay_amp_gain);
							ps.setString(27, transcript_name);
							ps.setString(28, transcript_type);
							ps.setLong(29, transcript_size);
							ps.setBlob(30, transcript_content);
							ps.setString(31, medical_name);
							ps.setString(32, medical_type);
							ps.setLong(33, medical_size);
							ps.setBlob(34, medical_content);
						}
					});
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void SyncApplicationDetailsMESA(int user_id, int application_id, String program, String tablename) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + tablename + " where application_id='" + application_id + "' and user_id='"
				+ user_id + "'");
		System.out.println(sql.toString());
		try {
			Statement stmt = this.con.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				String academic_school = rs.getString("academic_school");
				String academic_year = rs.getString("academic_year");
				Date academic_grad_date = rs.getDate("academic_grad_date");
				float academic_gpa = rs.getFloat("academic_gpa");
				String academic_transfer_school = rs.getString("academic_transfer_school");
				Date transfer_date = rs.getDate("transfer_date");
				String academic_intended_major = rs.getString("academic_intended_major");
				String academic_referrer = rs.getString("academic_referrer");
				int amp_scholarship = rs.getInt("amp_scholarship");
				String amp_scholarship_school = rs.getString("amp_scholarship_school");
				String amp_scholarship_type = rs.getString("amp_scholarship_type");
				String amp_scholarship_semester = rs.getString("amp_scholarship_semester");
				String amp_scholarship_year = rs.getString("amp_scholarship_year");
				String amp_scholarship_amount = rs.getString("amp_scholarship_amount");
				String other_scholarship = rs.getString("other_scholarship");
				String list_other_scholarship = rs.getString("list_other_scholarship");
				String essay_profesional_goal = rs.getString("essay_profesional_goal");
				String essay_academic_pathway = rs.getString("essay_academic_pathway");
				String essay_critical_event = rs.getString("essay_critical_event");
				String transcript_name = rs.getString("transcript_name");
				String transcript_type = rs.getString("transcript_type");
				long transcript_size = rs.getLong("transcript_size");
				Blob transcript_content = rs.getBlob("transcript_content");
				String reference_name = rs.getString("reference_name");
				String reference_type = rs.getString("reference_type");
				long reference_size = rs.getLong("reference_size");
				Blob reference_content = rs.getBlob("reference_content");

				boolean isExisted = existedInApplicationDetails(user_id, application_id, tablename);
				System.out.println(isExisted);
				StringBuilder updateSql = new StringBuilder();
				StringBuilder insertSql = new StringBuilder();
				updateSql.append("update " + tablename
						+ " set academic_school=?,academic_year=?,academic_grad_date=?,academic_gpa=?,academic_transfer_school=?,"
						+ "transfer_date=?,academic_intended_major=?,academic_referrer=?,amp_scholarship=?,amp_scholarship_school=?,amp_scholarship_type=?,"
						+ "amp_scholarship_semester=?,amp_scholarship_year=?,amp_scholarship_amount=?,other_scholarship=?,list_other_scholarship=?,essay_profesional_goal=?,"
						+ "essay_academic_pathway=?,essay_critical_event=?,transcript_name=?,transcript_type=?,transcript_size=?,transcript_content=?,reference_name=?,"
						+ "reference_type=?,reference_size=?,reference_content=?" + " where application_id='"
						+ application_id + "' and user_id='" + user_id + "'");
				insertSql.append("insert into " + tablename
						+ " (user_id,application_id,academic_school,academic_year,academic_grad_date,academic_gpa,academic_transfer_school,transfer_date,academic_intended_major,"
						+ "academic_referrer,amp_scholarship,amp_scholarship_school,amp_scholarship_type,amp_scholarship_semester,amp_scholarship_year,"
						+ "amp_scholarship_amount,other_scholarship,list_other_scholarship,essay_profesional_goal,essay_academic_pathway,essay_critical_event,"
						+ "transcript_name,transcript_type,transcript_size,transcript_content,reference_name,reference_type,reference_size,reference_content) values "
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

				if (isExisted) {
					jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, academic_school);
							ps.setString(2, academic_year);
							ps.setDate(3, academic_grad_date);
							ps.setFloat(4, academic_gpa);
							ps.setString(5, academic_transfer_school);
							ps.setDate(6, transfer_date);
							ps.setString(7, academic_intended_major);
							ps.setString(8, academic_referrer);
							ps.setInt(9, amp_scholarship);
							ps.setString(10, amp_scholarship_school);
							ps.setString(11, amp_scholarship_type);
							ps.setString(12, amp_scholarship_semester);
							ps.setString(13, amp_scholarship_year);
							ps.setString(14, amp_scholarship_amount);
							ps.setString(15, other_scholarship);
							ps.setString(16, list_other_scholarship);
							ps.setString(17, essay_profesional_goal);
							ps.setString(18, essay_academic_pathway);
							ps.setString(19, essay_critical_event);
							ps.setString(20, transcript_name);
							ps.setString(21, transcript_type);
							ps.setLong(22, transcript_size);
							ps.setBlob(23, transcript_content);
							ps.setString(24, reference_name);
							ps.setString(25, reference_type);
							ps.setLong(26, reference_size);
							ps.setBlob(27, reference_content);
						}
					});

				} else {
					jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setInt(1, user_id);
							ps.setInt(2, application_id);
							ps.setString(3, academic_school);
							ps.setString(4, academic_year);
							ps.setDate(5, academic_grad_date);
							ps.setFloat(6, academic_gpa);
							ps.setString(7, academic_transfer_school);
							ps.setDate(8, transfer_date);
							ps.setString(9, academic_intended_major);
							ps.setString(10, academic_referrer);
							ps.setInt(11, amp_scholarship);
							ps.setString(12, amp_scholarship_school);
							ps.setString(13, amp_scholarship_type);
							ps.setString(14, amp_scholarship_semester);
							ps.setString(15, amp_scholarship_year);
							ps.setString(16, amp_scholarship_amount);
							ps.setString(17, other_scholarship);
							ps.setString(18, list_other_scholarship);
							ps.setString(19, essay_profesional_goal);
							ps.setString(20, essay_academic_pathway);
							ps.setString(21, essay_critical_event);
							ps.setString(22, transcript_name);
							ps.setString(23, transcript_type);
							ps.setLong(24, transcript_size);
							ps.setBlob(25, transcript_content);
							ps.setString(26, reference_name);
							ps.setString(27, reference_type);
							ps.setLong(28, reference_size);
							ps.setBlob(29, reference_content);
						}
					});
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void SyncApplicationDetailsIREP(int user_id, int application_id, String program, String tablename) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + tablename + " where application_id='" + application_id + "' and user_id='"
				+ user_id + "'");
		System.out.println(sql.toString());
		try {
			Statement stmt = this.con.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				String academic_school = rs.getString("academic_school");
				String academic_year = rs.getString("academic_year");
				Date academic_grad_date = rs.getDate("academic_grad_date");
				String academic_banner_id = rs.getString("academic_banner_id");
				String academic_major = rs.getString("academic_major");
				String academic_minor = rs.getString("academic_minor");
				float academic_gpa = rs.getFloat("academic_gpa");
				String mentor_first_name = rs.getString("mentor_first_name");
				String mentor_last_name = rs.getString("mentor_last_name");
				String mentor_institution = rs.getString("mentor_institution");
				String mentor_phone = rs.getString("mentor_phone");
				String mentor_email = rs.getString("mentor_email");
				String intl_mentor_first_name = rs.getString("intl_mentor_first_name");
				String intl_mentor_last_name = rs.getString("intl_mentor_last_name");
				String intl_mentor_institution = rs.getString("intl_mentor_institution");
				String intl_mentor_phone = rs.getString("intl_mentor_phone");
				String intl_mentor_email = rs.getString("intl_mentor_email");
				String intl_mentor_country = rs.getString("intl_mentor_country");
				String transcript_name = rs.getString("transcript_name");
				String transcript_type = rs.getString("transcript_type");
				long transcript_size = rs.getLong("transcript_size");
				Blob transcript_content = rs.getBlob("transcript_content");
				Date research_date = rs.getDate("research_date");
				Date leave_date = rs.getDate("leave_date");
				Date return_date = rs.getDate("return_date");
				int ever_fund_amp = rs.getInt("ever_fund_amp");
				String list_program = rs.getString("list_program");
				String project_abstract = rs.getString("project_abstract");
				String project_key = rs.getString("project_key");
				String project_mentor_signature = rs.getString("project_mentor_signature");
				String project_mentor_signature_date = rs.getString("project_mentor_signature_date");
				double budget_total_domestictravel = rs.getDouble("budget_total_domestictravel");
				double budget_total_roundtrip = rs.getDouble("budget_total_roundtrip");
				double budget_total_visa = rs.getDouble("budget_total_visa");
				double budget_total_passport = rs.getDouble("budget_total_passport");
				double budget_total_immunization = rs.getDouble("budget_total_immunization");
				double budget_total_housing = rs.getDouble("budget_total_housing");
				double budget_total_communication = rs.getDouble("budget_total_communication");
				double budget_total_meal = rs.getDouble("budget_total_meal");
				double budget_total_miscellaneous = rs.getDouble("budget_total_miscellaneous");
				double budget_current_domestictravel = rs.getDouble("budget_current_domestictravel");
				double budget_current_roundtrip = rs.getDouble("budget_current_roundtrip");
				double budget_current_visa = rs.getDouble("budget_current_visa");
				double budget_current_passport = rs.getDouble("budget_current_passport");
				double budget_current_immunization = rs.getDouble("budget_current_immunization");
				double budget_current_housing = rs.getDouble("budget_current_housing");
				double budget_current_communication = rs.getDouble("budget_current_communication");
				double budget_current_meal = rs.getDouble("budget_current_meal");
				double budget_current_miscellaneous = rs.getDouble("budget_current_miscellaneous");
				String budget_miscellaneous_describe = rs.getString("budget_miscellaneous_describe");
				String budget_funding_source = rs.getString("budget_funding_source");

				boolean isExisted = existedInApplicationDetails(user_id, application_id, tablename);
				System.out.println(isExisted);
				StringBuilder updateSql = new StringBuilder();
				StringBuilder insertSql = new StringBuilder();
				updateSql.append("update " + tablename
						+ " set academic_school=?,academic_year=?,academic_grad_date=?,academic_banner_id=?,academic_major=?,"
						+ "academic_minor=?,academic_gpa=?,mentor_first_name=?,mentor_last_name=?,mentor_institution=?,mentor_phone=?,mentor_email=?,intl_mentor_first_name=?,"
						+ "intl_mentor_last_name=?,intl_mentor_institution=?,intl_mentor_phone=?,intl_mentor_email=?,intl_mentor_country=?,transcript_name=?,transcript_type=?,"
						+ "transcript_size=?,transcript_content=?,research_date=?,leave_date=?,return_date=?,ever_fund_amp=?,list_program=?,project_abstract=?,project_key=?,"
						+ "project_mentor_signature=?,project_mentor_signature_date=?,budget_total_domestictravel=?,budget_total_roundtrip=?,budget_total_visa=?,"
						+ "budget_total_passport=?,budget_total_immunization=?,budget_total_housing=?,budget_total_communication=?,budget_total_meal=?,"
						+ "budget_total_miscellaneous=?,budget_current_domestictravel=?,budget_current_roundtrip=?,budget_current_visa=?,budget_current_passport=?,"
						+ "budget_current_immunization=?,budget_current_housing=?,budget_current_communication=?,budget_current_meal=?,budget_current_miscellaneous=?,"
						+ "budget_miscellaneous_describe=?,budget_funding_source=?" + " where application_id='"
						+ application_id + "' and user_id='" + user_id + "'");
				insertSql.append("insert into " + tablename
						+ " (user_id,application_id,academic_school,academic_year,academic_grad_date,academic_banner_id,academic_major,academic_minor,academic_gpa,"
						+ "mentor_first_name,mentor_last_name,mentor_institution,mentor_phone,mentor_email,intl_mentor_first_name,intl_mentor_last_name,intl_mentor_institution,"
						+ "intl_mentor_phone,intl_mentor_email,intl_mentor_country,transcript_name,transcript_type,transcript_size,transcript_content,research_date,leave_date,"
						+ "return_date,ever_fund_amp,list_program,project_abstract,project_key,project_mentor_signature,project_mentor_signature_date,budget_total_domestictravel,"
						+ "budget_total_roundtrip,budget_total_visa,budget_total_passport,budget_total_immunization,budget_total_housing,budget_total_communication,"
						+ "budget_total_meal,budget_total_miscellaneous,budget_current_domestictravel,budget_current_roundtrip,budget_current_visa,budget_current_passport,"
						+ "budget_current_immunization,budget_current_housing,budget_current_communication,budget_current_meal,budget_current_miscellaneous,"
						+ "budget_miscellaneous_describe,budget_funding_source) values "
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

				if (isExisted) {
					jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, academic_school);
							ps.setString(2, academic_year);
							ps.setDate(3, academic_grad_date);
							ps.setString(4, academic_banner_id);
							ps.setString(5, academic_major);
							ps.setString(6, academic_minor);
							ps.setFloat(7, academic_gpa);
							ps.setString(8, mentor_first_name);
							ps.setString(9, mentor_last_name);
							ps.setString(10, mentor_institution);
							ps.setString(11, mentor_phone);
							ps.setString(12, mentor_email);
							ps.setString(13, intl_mentor_first_name);
							ps.setString(14, intl_mentor_last_name);
							ps.setString(15, intl_mentor_institution);
							ps.setString(16, intl_mentor_phone);
							ps.setString(17, intl_mentor_email);
							ps.setString(18, intl_mentor_country);
							ps.setString(19, transcript_name);
							ps.setString(20, transcript_type);
							ps.setLong(21, transcript_size);
							ps.setBlob(22, transcript_content);
							ps.setDate(23, research_date);
							ps.setDate(24, leave_date);
							ps.setDate(25, return_date);
							ps.setInt(26, ever_fund_amp);
							ps.setString(27, list_program);
							ps.setString(28, project_abstract);
							ps.setString(29, project_key);
							ps.setString(30, project_mentor_signature);
							ps.setString(31, project_mentor_signature_date);
							ps.setDouble(32, budget_total_domestictravel);
							ps.setDouble(33, budget_total_roundtrip);
							ps.setDouble(34, budget_total_visa);
							ps.setDouble(35, budget_total_passport);
							ps.setDouble(36, budget_total_immunization);
							ps.setDouble(37, budget_total_housing);
							ps.setDouble(38, budget_total_communication);
							ps.setDouble(39, budget_total_meal);
							ps.setDouble(40, budget_total_miscellaneous);
							ps.setDouble(41, budget_current_domestictravel);
							ps.setDouble(42, budget_current_roundtrip);
							ps.setDouble(43, budget_current_visa);
							ps.setDouble(44, budget_current_passport);
							ps.setDouble(45, budget_current_immunization);
							ps.setDouble(46, budget_current_housing);
							ps.setDouble(47, budget_current_communication);
							ps.setDouble(48, budget_current_meal);
							ps.setDouble(49, budget_current_miscellaneous);
							ps.setString(50, budget_miscellaneous_describe);
							ps.setString(51, budget_funding_source);

						}
					});

				} else {
					jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setInt(1, user_id);
							ps.setInt(2, application_id);
							ps.setString(3, academic_school);
							ps.setString(4, academic_year);
							ps.setDate(5, academic_grad_date);
							ps.setString(6, academic_banner_id);
							ps.setString(7, academic_major);
							ps.setString(8, academic_minor);
							ps.setFloat(9, academic_gpa);
							ps.setString(10, mentor_first_name);
							ps.setString(11, mentor_last_name);
							ps.setString(12, mentor_institution);
							ps.setString(13, mentor_phone);
							ps.setString(14, mentor_email);
							ps.setString(15, intl_mentor_first_name);
							ps.setString(16, intl_mentor_last_name);
							ps.setString(17, intl_mentor_institution);
							ps.setString(18, intl_mentor_phone);
							ps.setString(19, intl_mentor_email);
							ps.setString(20, intl_mentor_country);
							ps.setString(21, transcript_name);
							ps.setString(22, transcript_type);
							ps.setLong(23, transcript_size);
							ps.setBlob(24, transcript_content);
							ps.setDate(25, research_date);
							ps.setDate(26, leave_date);
							ps.setDate(27, return_date);
							ps.setInt(28, ever_fund_amp);
							ps.setString(29, list_program);
							ps.setString(30, project_abstract);
							ps.setString(31, project_key);
							ps.setString(32, project_mentor_signature);
							ps.setString(33, project_mentor_signature_date);
							ps.setDouble(34, budget_total_domestictravel);
							ps.setDouble(35, budget_total_roundtrip);
							ps.setDouble(36, budget_total_visa);
							ps.setDouble(37, budget_total_passport);
							ps.setDouble(38, budget_total_immunization);
							ps.setDouble(39, budget_total_housing);
							ps.setDouble(40, budget_total_communication);
							ps.setDouble(41, budget_total_meal);
							ps.setDouble(42, budget_total_miscellaneous);
							ps.setDouble(43, budget_current_domestictravel);
							ps.setDouble(44, budget_current_roundtrip);
							ps.setDouble(45, budget_current_visa);
							ps.setDouble(46, budget_current_passport);
							ps.setDouble(47, budget_current_immunization);
							ps.setDouble(48, budget_current_housing);
							ps.setDouble(49, budget_current_communication);
							ps.setDouble(50, budget_current_meal);
							ps.setDouble(51, budget_current_miscellaneous);
							ps.setString(52, budget_miscellaneous_describe);
							ps.setString(53, budget_funding_source);
						}
					});
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void SyncApplicationDetailsCCCONF(int user_id, int application_id, String program, String tablename) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + tablename + " where application_id='" + application_id + "' and user_id='"
				+ user_id + "'");
		System.out.println(sql.toString());
		try {
			Statement stmt = this.con.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				String academic_school = rs.getString("academic_school");
				String academic_banner_id = rs.getString("academic_banner_id");
				String academic_major = rs.getString("academic_major");
				String academic_status = rs.getString("academic_status");
				String academic_credit = rs.getString("academic_credit");
				float academic_gpa = rs.getFloat("academic_gpa");
				String academic_transfer_school = rs.getString("academic_transfer_school");
				Date transfer_date = rs.getDate("transfer_date");
				String academic_intended_major = rs.getString("academic_intended_major");
				String academic_referrer = rs.getString("academic_referrer");
				int amp_scholarship = rs.getInt("amp_scholarship");
				String amp_scholarship_school = rs.getString("amp_scholarship_school");
				String amp_scholarship_type = rs.getString("amp_scholarship_type");
				String amp_scholarship_semester = rs.getString("amp_scholarship_semester");
				String amp_scholarship_year = rs.getString("amp_scholarship_year");
				String amp_scholarship_amount = rs.getString("amp_scholarship_amount");
				String other_scholarship = rs.getString("other_scholarship");
				String list_other_scholarship = rs.getString("list_other_scholarship");
				int is_current_employ = rs.getInt("is_current_employ");
				String list_employ_campus = rs.getString("list_employ_campus");
				String list_employ_dept = rs.getString("list_employ_dept");
				String list_employ_supervisor = rs.getString("list_employ_supervisor");
				Date list_employ_start = rs.getDate("list_employ_start");
				Date list_employ_end = rs.getDate("list_employ_end");
				int ever_in_research = rs.getInt("ever_in_research");
				String describe_research = rs.getString("describe_research");
				int ever_attend_conference = rs.getInt("ever_attend_conference");
				int program_ever_in = rs.getInt("program_ever_in");
				String program_ever_in_year = rs.getString("program_ever_in_year");
				String essay_critical_event = rs.getString("essay_critical_event");
				String essay_educational_goal = rs.getString("essay_educational_goal");
				String essay_profesional_goal = rs.getString("essay_profesional_goal");
				String essay_amp_gain = rs.getString("essay_amp_gain");
				String transcript_name = rs.getString("transcript_name");
				String transcript_type = rs.getString("transcript_type");
				long transcript_size = rs.getLong("transcript_size");
				Blob transcript_content = rs.getBlob("transcript_content");

				boolean isExisted = existedInApplicationDetails(user_id, application_id, tablename);
				System.out.println(isExisted);
				StringBuilder updateSql = new StringBuilder();
				StringBuilder insertSql = new StringBuilder();
				updateSql.append("update " + tablename
						+ " set academic_school=?,academic_banner_id=?,academic_major=?,academic_status=?,academic_credit=?,"
						+ "academic_gpa=?,academic_transfer_school=?,transfer_date=?,academic_intended_major=?,academic_referrer=?,amp_scholarship=?,"
						+ "amp_scholarship_school=?,amp_scholarship_type=?,amp_scholarship_semester=?,amp_scholarship_year=?,amp_scholarship_amount=?,"
						+ "other_scholarship=?,list_other_scholarship=?,is_current_employ=?,list_employ_campus=?,list_employ_dept=?,list_employ_supervisor=?,"
						+ "list_employ_start=?,list_employ_end=?,ever_in_research=?,describe_research=?,ever_attend_conference=?,program_ever_in=?,program_ever_in_year=?,"
						+ "essay_critical_event=?,essay_educational_goal=?,essay_profesional_goal=?,essay_amp_gain=?,transcript_name=?,"
						+ "transcript_type=?,transcript_size=?,transcript_content=?" + " where application_id='"
						+ application_id + "' and user_id='" + user_id + "'");
				insertSql.append("insert into " + tablename
						+ " (user_id,application_id,academic_school,academic_banner_id,academic_major,academic_status,academic_credit,"
						+ "academic_gpa,academic_transfer_school,transfer_date,academic_intended_major,academic_referrer,amp_scholarship,"
						+ "amp_scholarship_school,amp_scholarship_type,amp_scholarship_semester,amp_scholarship_year,amp_scholarship_amount,"
						+ "other_scholarship,list_other_scholarship,is_current_employ,list_employ_campus,list_employ_dept,list_employ_supervisor,"
						+ "list_employ_start,list_employ_end,ever_in_research,describe_research,ever_attend_conference,program_ever_in,program_ever_in_year,"
						+ "essay_critical_event,essay_educational_goal,essay_profesional_goal,essay_amp_gain,transcript_name,"
						+ "transcript_type,transcript_size,transcript_content) values "
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

				if (isExisted) {
					jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, academic_school);
							ps.setString(2, academic_banner_id);
							ps.setString(3, academic_major);
							ps.setString(4, academic_status);
							ps.setString(5, academic_credit);
							ps.setFloat(6, academic_gpa);
							ps.setString(7, academic_transfer_school);
							ps.setDate(8, transfer_date);
							ps.setString(9, academic_intended_major);
							ps.setString(10, academic_referrer);
							ps.setInt(11, amp_scholarship);
							ps.setString(12, amp_scholarship_school);
							ps.setString(13, amp_scholarship_type);
							ps.setString(14, amp_scholarship_semester);
							ps.setString(15, amp_scholarship_year);
							ps.setString(16, amp_scholarship_amount);
							ps.setString(17, other_scholarship);
							ps.setString(18, list_other_scholarship);
							ps.setInt(19, is_current_employ);
							ps.setString(20, list_employ_campus);
							ps.setString(21, list_employ_dept);
							ps.setString(22, list_employ_supervisor);
							ps.setDate(23, list_employ_start);
							ps.setDate(24, list_employ_end);
							ps.setInt(25, ever_in_research);
							ps.setString(26, describe_research);
							ps.setInt(27, ever_attend_conference);
							ps.setInt(28, program_ever_in);
							ps.setString(29, program_ever_in_year);
							ps.setString(30, essay_critical_event);
							ps.setString(31, essay_educational_goal);
							ps.setString(32, essay_profesional_goal);
							ps.setString(33, essay_amp_gain);
							ps.setString(34, transcript_name);
							ps.setString(35, transcript_type);
							ps.setLong(36, transcript_size);
							ps.setBlob(37, transcript_content);
						}
					});

				} else {
					jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setInt(1, user_id);
							ps.setInt(2, application_id);
							ps.setString(3, academic_school);
							ps.setString(4, academic_banner_id);
							ps.setString(5, academic_major);
							ps.setString(6, academic_status);
							ps.setString(7, academic_credit);
							ps.setFloat(8, academic_gpa);
							ps.setString(9, academic_transfer_school);
							ps.setDate(10, transfer_date);
							ps.setString(11, academic_intended_major);
							ps.setString(12, academic_referrer);
							ps.setInt(13, amp_scholarship);
							ps.setString(14, amp_scholarship_school);
							ps.setString(15, amp_scholarship_type);
							ps.setString(16, amp_scholarship_semester);
							ps.setString(17, amp_scholarship_year);
							ps.setString(18, amp_scholarship_amount);
							ps.setString(19, other_scholarship);
							ps.setString(20, list_other_scholarship);
							ps.setInt(21, is_current_employ);
							ps.setString(22, list_employ_campus);
							ps.setString(23, list_employ_dept);
							ps.setString(24, list_employ_supervisor);
							ps.setDate(25, list_employ_start);
							ps.setDate(26, list_employ_end);
							ps.setInt(27, ever_in_research);
							ps.setString(28, describe_research);
							ps.setInt(29, ever_attend_conference);
							ps.setInt(30, program_ever_in);
							ps.setString(31, program_ever_in_year);
							ps.setString(32, essay_critical_event);
							ps.setString(33, essay_educational_goal);
							ps.setString(34, essay_profesional_goal);
							ps.setString(35, essay_amp_gain);
							ps.setString(36, transcript_name);
							ps.setString(37, transcript_type);
							ps.setLong(38, transcript_size);
							ps.setBlob(39, transcript_content);
						}
					});
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void SyncApplicationDetailsURS(int user_id, int application_id, String program, String tablename) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + tablename + " where application_id='" + application_id + "' and user_id='"
				+ user_id + "'");
		System.out.println(sql.toString());
		try {
			Statement stmt = this.con.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				String academic_school = rs.getString("academic_school");
				String academic_year = rs.getString("academic_year");
				Date academic_grad_date = rs.getDate("academic_grad_date");
				String academic_banner_id = rs.getString("academic_banner_id");
				String academic_major = rs.getString("academic_major");
				String academic_minor = rs.getString("academic_minor");
				float academic_gpa = rs.getFloat("academic_gpa");
				int program_ever_in = rs.getInt("program_ever_in");
				String program_ever_in_semesters = rs.getString("program_ever_in_semesters");
				int amp_scholarship = rs.getInt("amp_scholarship");
				String amp_scholarship_school = rs.getString("amp_scholarship_school");
				String amp_scholarship_type = rs.getString("amp_scholarship_type");
				String amp_scholarship_semester = rs.getString("amp_scholarship_semester");
				String amp_scholarship_year = rs.getString("amp_scholarship_year");
				String amp_scholarship_amount = rs.getString("amp_scholarship_amount");
				String other_scholarship = rs.getString("other_scholarship");
				String list_other_scholarship = rs.getString("list_other_scholarship");
				String mentor_first_name = rs.getString("mentor_first_name");
				String mentor_last_name = rs.getString("mentor_last_name");
				String mentor_prefix = rs.getString("mentor_prefix");
				String mentor_email = rs.getString("mentor_email");
				String transcript_name = rs.getString("transcript_name");
				String transcript_type = rs.getString("transcript_type");
				long transcript_size = rs.getLong("transcript_size");
				Blob transcript_content = rs.getBlob("transcript_content");
				String project_title = rs.getString("project_title");
				int project_is_external = rs.getInt("project_is_external");
				String project_external_agency = rs.getString("project_external_agency");
				String project_external_title = rs.getString("project_external_title");
				String project_external_duration = rs.getString("project_external_duration");
				String project_goal = rs.getString("project_goal");
				String project_method = rs.getString("project_method");
				String project_result = rs.getString("project_result");
				String project_task = rs.getString("project_task");
				String project_mentor_signature = rs.getString("project_mentor_signature");
				Timestamp project_mentor_signature_date = rs.getTimestamp("project_mentor_signature_date");
				String project_mentee_signature = rs.getString("project_mentee_signature");
				Timestamp project_mentee_signature_date = rs.getTimestamp("project_mentee_signature_date");
				String essay_educational_goal = rs.getString("essay_educational_goal");

				boolean isExisted = existedInApplicationDetails(user_id, application_id, tablename);
				System.out.println(isExisted);
				StringBuilder updateSql = new StringBuilder();
				StringBuilder insertSql = new StringBuilder();
				updateSql.append("update " + tablename + " set academic_school=?,academic_year=?,academic_grad_date=?,"
						+ "academic_banner_id=?,academic_major=?,academic_minor=?,academic_gpa=?,program_ever_in=?,program_ever_in_semesters=?,"
						+ "amp_scholarship=?,amp_scholarship_school=?,amp_scholarship_type=?,amp_scholarship_semester=?,amp_scholarship_year=?,"
						+ "amp_scholarship_amount=?,other_scholarship=?,list_other_scholarship=?,mentor_first_name=?,mentor_last_name=?,"
						+ "mentor_prefix=?,mentor_email=?,transcript_name=?,transcript_type=?,transcript_size=?,transcript_content=?,"
						+ "project_title=?,project_is_external=?,project_external_agency=?,project_external_title=?,project_external_duration=?,"
						+ "project_goal=?,project_method=?,project_result=?,project_task=?,project_mentor_signature=?,project_mentor_signature_date=?,"
						+ "project_mentee_signature=?,project_mentee_signature_date=?,essay_educational_goal=? "
						+ " where application_id='" + application_id + "' and user_id='" + user_id + "'");
				insertSql.append("insert into " + tablename
						+ " (user_id,application_id,academic_school,academic_year,academic_grad_date,academic_banner_id,"
						+ "academic_major,academic_minor,academic_gpa,program_ever_in,program_ever_in_semesters,amp_scholarship,amp_scholarship_school,"
						+ "amp_scholarship_type,amp_scholarship_semester,amp_scholarship_year,amp_scholarship_amount,other_scholarship,"
						+ "list_other_scholarship,mentor_first_name,mentor_last_name,mentor_prefix,mentor_email,transcript_name,transcript_type,"
						+ "transcript_size,transcript_content,project_title,project_is_external,project_external_agency,project_external_title,"
						+ "project_external_duration,project_goal,project_method,project_result,project_task,project_mentor_signature,project_mentor_signature_date,"
						+ "project_mentee_signature,project_mentee_signature_date,essay_educational_goal) values "
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

				if (isExisted) {
					jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setString(1, academic_school);
							ps.setString(2, academic_year);
							ps.setDate(3, academic_grad_date);
							ps.setString(4, academic_banner_id);
							ps.setString(5, academic_major);
							ps.setString(6, academic_minor);
							ps.setFloat(7, academic_gpa);
							ps.setInt(8, program_ever_in);
							ps.setString(9, program_ever_in_semesters);
							ps.setInt(10, amp_scholarship);
							ps.setString(11, amp_scholarship_school);
							ps.setString(12, amp_scholarship_type);
							ps.setString(13, amp_scholarship_semester);
							ps.setString(14, amp_scholarship_year);
							ps.setString(15, amp_scholarship_amount);
							ps.setString(16, other_scholarship);
							ps.setString(17, list_other_scholarship);
							ps.setString(18, mentor_first_name);
							ps.setString(19, mentor_last_name);
							ps.setString(20, mentor_prefix);
							ps.setString(21, mentor_email);
							ps.setString(22, transcript_name);
							ps.setString(23, transcript_type);
							ps.setLong(24, transcript_size);
							ps.setBlob(25, transcript_content);
							ps.setString(26, project_title);
							ps.setInt(27, project_is_external);
							ps.setString(28, project_external_agency);
							ps.setString(29, project_external_title);
							ps.setString(30, project_external_duration);
							ps.setString(31, project_goal);
							ps.setString(32, project_method);
							ps.setString(33, project_result);
							ps.setString(34, project_task);
							ps.setString(35, project_mentor_signature);
							ps.setTimestamp(36, project_mentor_signature_date);
							ps.setString(37, project_mentee_signature);
							ps.setTimestamp(38, project_mentee_signature_date);
							ps.setString(39, essay_educational_goal);
						}
					});

				} else {
					jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement ps) throws SQLException {
							ps.setInt(1, user_id);
							ps.setInt(2, application_id);
							ps.setString(3, academic_school);
							ps.setString(4, academic_year);
							ps.setDate(5, academic_grad_date);
							ps.setString(6, academic_banner_id);
							ps.setString(7, academic_major);
							ps.setString(8, academic_minor);
							ps.setFloat(9, academic_gpa);
							ps.setInt(10, program_ever_in);
							ps.setString(11, program_ever_in_semesters);
							ps.setInt(12, amp_scholarship);
							ps.setString(13, amp_scholarship_school);
							ps.setString(14, amp_scholarship_type);
							ps.setString(15, amp_scholarship_semester);
							ps.setString(16, amp_scholarship_year);
							ps.setString(17, amp_scholarship_amount);
							ps.setString(18, other_scholarship);
							ps.setString(19, list_other_scholarship);
							ps.setString(20, mentor_first_name);
							ps.setString(21, mentor_last_name);
							ps.setString(22, mentor_prefix);
							ps.setString(23, mentor_email);
							ps.setString(24, transcript_name);
							ps.setString(25, transcript_type);
							ps.setLong(26, transcript_size);
							ps.setBlob(27, transcript_content);
							ps.setString(28, project_title);
							ps.setInt(29, project_is_external);
							ps.setString(30, project_external_agency);
							ps.setString(31, project_external_title);
							ps.setString(32, project_external_duration);
							ps.setString(33, project_goal);
							ps.setString(34, project_method);
							ps.setString(35, project_result);
							ps.setString(36, project_task);
							ps.setString(37, project_mentor_signature);
							ps.setTimestamp(38, project_mentor_signature_date);
							ps.setString(39, project_mentee_signature);
							ps.setTimestamp(40, project_mentee_signature_date);
							ps.setString(41, essay_educational_goal);
						}
					});
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void SynPortalMentorData() {
		this.connectDB();
		if (this.con != null) {
			System.out.println("Connected Success!!!");
			StringBuilder sql = new StringBuilder();
			sql.append("select * from profile_mentor ;");

			try {
				Statement stmt = this.con.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());
				int count = 0;
				while (rs.next()) {
					int mentor_id = rs.getInt("mentor_id");
					String mentor_first_name = rs.getString("mentor_first_name");
					String mentor_last_name = rs.getString("mentor_last_name");
					String mentor_email = rs.getString("mentor_email");
					String mentor_middle_name = rs.getString("mentor_middle_name");
					String mentor_prefix = rs.getString("mentor_prefix");
					String mentor_title = rs.getString("mentor_title");
					String mentor_institution = rs.getString("mentor_institution");
					String mentor_department = rs.getString("mentor_department");
					String mentor_phone = rs.getString("mentor_phone");
					String mentor_building = rs.getString("mentor_building");
					String mentor_fax = rs.getString("mentor_fax");
					int is_hispanic = rs.getInt("is_hispanic");
					String race = rs.getString("race");
					String disability = rs.getString("disability");

					boolean isExisted = existedInMentor(mentor_id);
					System.out.println(mentor_id + ":" + mentor_first_name + " " + mentor_last_name + " " + isExisted);

					StringBuilder updateSql = new StringBuilder();
					StringBuilder insertSql = new StringBuilder();
					updateSql.append(
							"update profile_mentor set mentor_first_name=?,mentor_last_name=?,mentor_email=?,mentor_middle_name=?,mentor_prefix=?,mentor_title=?,"
									+ "mentor_institution=?,mentor_department=?,mentor_phone=?,mentor_building=?,mentor_fax=?,is_hispanic=?,race=?,disability=? "
									+ "where mentor_id='" + mentor_id + "'");
					insertSql.append(
							"insert into profile_mentor (mentor_id,mentor_first_name,mentor_last_name,mentor_email,mentor_middle_name,mentor_prefix,"
									+ "mentor_title,mentor_institution,mentor_department,mentor_phone,mentor_building,mentor_fax,is_hispanic,race,disability) values "
									+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

					if (isExisted) {
						jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								ps.setString(1, mentor_first_name);
								ps.setString(2, mentor_last_name);
								ps.setString(3, mentor_email);
								ps.setString(4, mentor_middle_name);
								ps.setString(5, mentor_prefix);
								ps.setString(6, mentor_title);
								ps.setString(7, mentor_institution);
								ps.setString(8, mentor_department);
								ps.setString(9, mentor_phone);
								ps.setString(10, mentor_building);
								ps.setString(11, mentor_fax);
								ps.setInt(12, is_hispanic);
								ps.setString(13, race);
								ps.setString(14, disability);

							}
						});
					} else {
						jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								ps.setInt(1, mentor_id);
								ps.setString(2, mentor_first_name);
								ps.setString(3, mentor_last_name);
								ps.setString(4, mentor_email);
								ps.setString(5, mentor_middle_name);
								ps.setString(6, mentor_prefix);
								ps.setString(7, mentor_title);
								ps.setString(8, mentor_institution);
								ps.setString(9, mentor_department);
								ps.setString(10, mentor_phone);
								ps.setString(11, mentor_building);
								ps.setString(12, mentor_fax);
								ps.setInt(13, is_hispanic);
								ps.setString(14, race);
								ps.setString(15, disability);

							}
						});

					}

					count++;
				}
				System.out.println("total mentor:" + count);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.connectClose();
		}

	}

	public void SynPortalSelfReportData() {
		this.connectDB();
		if (this.con != null) {
			System.out.println("Connected Success!!!");
			StringBuilder sql = new StringBuilder();
			sql.append("select * from selfreport_data ;");

			try {
				Statement stmt = this.con.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());
				int count = 0;
				while (rs.next()) {
					int window_id = rs.getInt("window_id");
					int user_id = rs.getInt("user_id");
					String semester = rs.getString("semester");
					String first_name = rs.getString("first_name");
					String last_name = rs.getString("last_name");
					String current_address_line1 = rs.getString("current_address_line1");
					String current_address_line2 = rs.getString("current_address_line2");
					String current_address_city = rs.getString("current_address_city");
					String current_address_state = rs.getString("current_address_state");
					String current_address_zip = rs.getString("current_address_zip");
					String current_address_country = rs.getString("current_address_country");
					String select_school = rs.getString("select_school");
					String major = rs.getString("major");
					float gpa = rs.getFloat("gpa");
					String intern_json = rs.getString("intern_json");
					String travel_json = rs.getString("travel_json");
					String conference_json = rs.getString("conference_json");
					String publication_json = rs.getString("publication_json");
					String awards_json = rs.getString("awards_json");
					String other_activities = rs.getString("other_activities");
					Timestamp submit_date = rs.getTimestamp("submit_date");

					boolean isExisted = existedInSelfReportData(window_id, user_id, semester);
					System.out.println(window_id + ":" + user_id + " " + semester + " " + isExisted);

					StringBuilder updateSql = new StringBuilder();
					StringBuilder insertSql = new StringBuilder();
					updateSql.append(
							"update selfreport_data set first_name=?,last_name=?,current_address_line1=?,current_address_line2=?,current_address_city=?,current_address_state=?,"
									+ "current_address_zip=?,current_address_country=?,select_school=?,major=?,gpa=?,intern_json=?,travel_json=?,conference_json=?,publication_json=?,"
									+ "awards_json=?,other_activities=?,submit_date=? " + "where window_id='"
									+ window_id + "' and user_id='" + user_id + "' and semester='" + semester + "'");
					insertSql.append(
							"insert into selfreport_data (window_id,user_id,semester,first_name,last_name,current_address_line1,"
									+ "current_address_line2,current_address_city,current_address_state,current_address_zip,current_address_country,"
									+ "select_school,major,gpa,intern_json,travel_json,conference_json,publication_json,awards_json,"
									+ "other_activities,submit_date) values " + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

					if (isExisted) {
						jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								ps.setString(1,first_name);
								ps.setString(2,last_name);
								ps.setString(3,current_address_line1);
								ps.setString(4,current_address_line2);
								ps.setString(5,current_address_city);
								ps.setString(6,current_address_state);
								ps.setString(7,current_address_zip);
								ps.setString(8,current_address_country);
								ps.setString(9,select_school);
								ps.setString(10,major);
								ps.setFloat(11,gpa);
								ps.setString(12,intern_json);
								ps.setString(13,travel_json);
								ps.setString(14,conference_json);
								ps.setString(15,publication_json);
								ps.setString(16,awards_json);
								ps.setString(17,other_activities);
								ps.setTimestamp(18,submit_date);
							}
						});
					} else {
						jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps) throws SQLException {
								ps.setInt(1,window_id);
								ps.setInt(2,user_id);
								ps.setString(3,semester);
								ps.setString(4,first_name);
								ps.setString(5,last_name);
								ps.setString(6,current_address_line1);
								ps.setString(7,current_address_line2);
								ps.setString(8,current_address_city);
								ps.setString(9,current_address_state);
								ps.setString(10,current_address_zip);
								ps.setString(11,current_address_country);
								ps.setString(12,select_school);
								ps.setString(13,major);
								ps.setFloat(14,gpa);
								ps.setString(15,intern_json);
								ps.setString(16,travel_json);
								ps.setString(17,conference_json);
								ps.setString(18,publication_json);
								ps.setString(19,awards_json);
								ps.setString(20,other_activities);
								ps.setTimestamp(21,submit_date);
							}
						});

					}

					count++;
				}
				System.out.println("total mentor:" + count);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.connectClose();
		}

	}

	private boolean existedInSelfReportData(int window_id, int user_id, String semester) {
		int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM selfreport_data where window_id='" + window_id
				+ "' and user_id='" + user_id + "' and semester='" + semester + "'", Integer.class);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean existedInApplicationDetails(int user_id, int application_id, String tablename) {
		int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tablename + " where user_id='" + user_id
				+ "' and application_id='" + application_id + "'", Integer.class);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean existedInApplicationList(int user_id, int application_id) {
		int result = jdbcTemplate
				.queryForObject("SELECT COUNT(*) FROM application_list where decision='Admit' and application_id='"
						+ application_id + "' and user_id='" + user_id + "'", Integer.class);
		// int result = jdbcTemplate
		// .queryForObject("SELECT COUNT(*) FROM application_list where
		// application_id='"
		// + application_id + "' and user_id='"+user_id+"'", Integer.class);
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

	private boolean existedInMentor(int mentor_id) {
		int result = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM profile_mentor where mentor_id='" + mentor_id + "'", Integer.class);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	private HashSet<String> getAcceptedUserList() {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct user_id from application_list where decision='Admit'");
		// sql.append("select distinct user_id from application_list ");
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
