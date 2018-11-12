package edu.nmsu.nmamp.student.dao.impl;

import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import edu.nmsu.nmamp.student.dao.Schemacode;
import edu.nmsu.nmamp.student.model.StudentPostActiveBean;
import edu.nmsu.nmamp.student.model.StudentProfileBean;
import edu.nmsu.nmamp.student.model.StudentSummaryBean;

@Repository("StudentDAOImpl")
public class StudentDAOImpl implements Schemacode {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public StudentProfileBean getStudentProfileByStudentID(int student_id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * \n");
		sql.append("FROM ").append(TABLE_PROFILE_STUDENT).append(" a \n");
		sql.append("WHERE a.user_id = ?");

		try {
			StudentProfileBean applicationBean = jdbcTemplate.queryForObject(sql.toString(),
					new Object[] { student_id }, new RowMapper<StudentProfileBean>() {
						@Override
						public StudentProfileBean mapRow(ResultSet reSet, int rowNum) throws SQLException {
							StudentProfileBean bean = new StudentProfileBean();
							bean.setStudent_id(student_id);
							bean.setFirstName(reSet.getString("first_name"));
							bean.setLastName(reSet.getString("last_name"));
							bean.setMiddleName(reSet.getString("middle_name"));
							bean.setGender(reSet.getString("gender"));
							bean.setBirthDate(reSet.getDate("birth_date"));
							bean.setPhone_num(reSet.getString("phone_num"));

							bean.setFirst_gen_college_student(reSet.getString("first_gen_college_student") == null ? -1
									: reSet.getInt("first_gen_college_student"));
							bean.setLanguage_at_home(reSet.getString("language_at_home"));
							bean.setVeteran(reSet.getString("Veteran") == null ? -1 : reSet.getInt("Veteran"));
							bean.setExpected_highest_degree(reSet.getString("expected_highest_degree"));
							bean.setSsn_last_four(reSet.getString("ssn_last_four"));
							bean.setFamily_income(
									reSet.getString("Family_income") == null ? -1 : reSet.getInt("Family_income"));
							bean.setPell_grant_eligiblity(reSet.getString("Pell_Grant_Eligibility"));

							/*********** current address ******************/
							bean.setCurrent_address_line1(reSet.getString("current_address_line1"));
							bean.setCurrent_address_line2(reSet.getString("current_address_line2"));
							bean.setCurrent_address_city(reSet.getString("current_address_city"));
							bean.setCurrent_address_county(reSet.getString("current_address_county"));
							bean.setCurrent_address_state(reSet.getString("current_address_state"));
							bean.setCurrent_address_zip(reSet.getString("current_address_zip"));

							/*********** parent address ******************/
							bean.setParent_address_line1(reSet.getString("parent_address_line1"));
							bean.setParent_address_line2(reSet.getString("parent_address_line2"));
							bean.setParent_address_city(reSet.getString("parent_address_city"));
							bean.setParent_address_county(reSet.getString("parent_address_county"));
							bean.setParent_address_state(reSet.getString("parent_address_state"));
							bean.setParent_address_zip(reSet.getString("parent_address_zip"));

							/*********** Ethnicity ******************/
							bean.setEthnicity(reSet.getString("ethnicity") == null ? -1 : reSet.getInt("ethnicity"));
							String race = reSet.getString("race");
							if (race != null) {
								bean.setRace(Arrays.asList(race.trim().split("\\s*,\\s*")));
							}

							/*********** Disability ******************/
							bean.setDisability(reSet.getString("disability"));
							bean.setDisability_type(
									reSet.getString("disability_type") == null ? -1 : reSet.getInt("disability_type"));

							/*********** HighSchool ******************/
							bean.setHigh_shcool_name(reSet.getString("highschool_name"));
							bean.setHigh_school_city(reSet.getString("high_school_city"));
							bean.setHigh_school_state(reSet.getString("high_school_state"));
							bean.setHigh_shcool_GPA(reSet.getString("highschool_GPA"));
							bean.setHigh_shcool_workhours(reSet.getString("worked_hours_hs"));
							String hs_testing = reSet.getString("high_school_testing");
							bean.setHigh_school_testing(hs_testing);
//							if (hs_testing != null) {
//								bean.setHigh_school_testing((Arrays.asList(hs_testing.trim().split("\\s*,\\s*"))));
//							}
							bean.setHighschool_activities(reSet.getString("highschool_activities"));
							
							/**************High school scores***********************/
							bean.setHigh_school_act_total_score(reSet.getString("high_school_act_total_score"));
							bean.setHigh_school_act_reading_score(reSet.getString("high_school_act_reading_score"));
							bean.setHigh_school_act_writing_score(reSet.getString("high_school_act_writing_score"));
							bean.setHigh_school_act_math_score(reSet.getString("high_school_act_math_score"));
							bean.setHigh_school_act_sci_score(reSet.getString("high_school_act_sci_score"));
							bean.setHigh_school_sat_total_score(reSet.getString("high_school_sat_total_score"));
							bean.setHigh_school_sat_reading_score(reSet.getString("high_school_sat_reading_score"));
							bean.setHigh_school_sat_writing_score(reSet.getString("high_school_sat_writing_score"));
							bean.setHigh_school_sat_math_score(reSet.getString("high_school_sat_math_score"));
							bean.setHigh_school_sat_sci_score(reSet.getString("high_school_sat_sci_score"));

							bean.setRecommendation_file_name(reSet.getString("recommendation_file_name"));
							bean.setHs_transcript_file_name(reSet.getString("hs_transcript_file_name"));

							bean.setComments(reSet.getString("comments"));

							System.out.println(bean);
							return bean;
						}
					});
			return applicationBean;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	public int uploadRecommendationFile(MultipartFile file, int student_id) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(TABLE_PROFILE_STUDENT).append(" set recommendation_file_name=?,"
				+ "recommendation_file_type=?,recommendation_file_size=?,recommendation_file_content=? ");
		sql.append("WHERE user_id = " + student_id + "");
		System.out.println(sql);

		return jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, file.getOriginalFilename());
				ps.setString(2, file.getContentType());
				ps.setLong(3, file.getSize());
				try {
					ps.setBytes(4, file.getBytes());
				} catch (IOException e) {
					return;
				}
			}
		});

	}

	public byte[] downloadRecommendationFile(int student_id) {
		String sql = "SELECT recommendation_file_content FROM " + TABLE_PROFILE_STUDENT + " WHERE user_id = ?";
		return jdbcTemplate.queryForObject(sql, byte[].class, new Object[] { student_id });
	}

	public int uploadTranscriptFile(MultipartFile file, int student_id) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(TABLE_PROFILE_STUDENT).append(" set hs_transcript_file_name=?,"
				+ "hs_transcript_file_type=?,hs_transcript_file_size=?,hs_transcript_file_content=? ");
		sql.append("WHERE user_id = " + student_id + "");
		System.out.println(sql);

		return jdbcTemplate.update(sql.toString(), new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, file.getOriginalFilename());
				ps.setString(2, file.getContentType());
				ps.setLong(3, file.getSize());
				try {
					ps.setBytes(4, file.getBytes());
				} catch (IOException e) {
					return;
				}
			}
		});

	}

	public byte[] downloadTranscript(int student_id) {
		String sql = "SELECT hs_transcript_file_content FROM " + TABLE_PROFILE_STUDENT + " WHERE user_id = ?";
		return jdbcTemplate.queryForObject(sql, byte[].class, new Object[] { student_id });
	}

	public StudentSummaryBean getStudentSummaryByStudentID(int student_id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT first_name,last_name,middle_name,birth_date,recommendation_file_name \n");
		sql.append("FROM ").append(TABLE_PROFILE_STUDENT).append(" a \n");
		sql.append("WHERE a.user_id = ?");

		try {
			StudentSummaryBean bean = jdbcTemplate.queryForObject(sql.toString(), new Object[] { student_id },
					new RowMapper<StudentSummaryBean>() {
						@Override
						public StudentSummaryBean mapRow(ResultSet reSet, int rowNum) throws SQLException {
							StudentSummaryBean bean = new StudentSummaryBean();
							bean.setUser_id(student_id);
							bean.setFirst_name(reSet.getString("first_name"));
							bean.setLast_name(reSet.getString("last_name"));
							bean.setMiddle_name(reSet.getString("middle_name"));
							bean.setBirthDate(reSet.getDate("birth_date"));
							bean.setRecommendation_file_name(reSet.getString("recommendation_file_name"));
							return bean;
						}
					});
			return bean;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int updateStudentProfile(StudentProfileBean bean, String activitiesList, int student_id) {
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("update profile_student set "
				+ "highschool_activities=?,first_name=?,middle_name=?,last_name=?,birth_date=?,phone_num=?,veteran=?,first_gen_college_student=?,"
				+ "Language_at_home=?,expected_highest_degree=?,ssn_last_four=?,Family_income=?,"
				+ "current_address_line1=?,current_address_line2=?,current_address_city=?,current_address_county=?,current_address_state=?,current_address_zip=?,"
				+ "parent_address_line1=?,parent_address_line2=?,parent_address_city=?,parent_address_county=?,parent_address_state=?,parent_address_zip=?,"
				+ "ethnicity=?,race=?,disability=?,disability_type=?,highschool_name=?,highschool_GPA=?,worked_hours_hs=?,high_school_testing=?,"
				+ "comments=?" + " where user_id='" + student_id + "'");

		return jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, activitiesList);
				ps.setString(2, bean.getFirstName());
				ps.setString(3, bean.getMiddleName());
				ps.setString(4, bean.getLastName());
				ps.setDate(5, new java.sql.Date(bean.getBirthDate().getTime()));
				ps.setString(6, bean.getPhone_num());
				ps.setString(7, String.valueOf(bean.getVeteran()));
				ps.setString(8, String.valueOf(bean.getFirst_gen_college_student()));
				ps.setString(9, bean.getLanguage_at_home());
				ps.setString(10, bean.getExpected_highest_degree());
				ps.setString(11, bean.getSsn_last_four());
				ps.setString(12, String.valueOf(bean.getFamily_income()));
				ps.setString(13, bean.getCurrent_address_line1());
				ps.setString(14, bean.getCurrent_address_line2());
				ps.setString(15, bean.getCurrent_address_city());
				ps.setString(16, bean.getCurrent_address_county());
				ps.setString(17, bean.getCurrent_address_state());
				ps.setString(18, bean.getCurrent_address_zip());
				ps.setString(19, bean.getParent_address_line1());
				ps.setString(20, bean.getParent_address_line2());
				ps.setString(21, bean.getParent_address_city());
				ps.setString(22, bean.getParent_address_county());
				ps.setString(23, bean.getParent_address_state());
				ps.setString(24, bean.getParent_address_zip());
				ps.setString(25, String.valueOf(bean.getEthnicity()));
				String strRace = bean.getRace().toString();
				ps.setString(26, strRace.substring(1, strRace.length() - 1));
				ps.setString(27, bean.getDisability());
				ps.setString(28, String.valueOf(bean.getDisability_type()));
				ps.setString(29, bean.getHigh_shcool_name());
				ps.setString(30, bean.getHigh_shcool_GPA());
				// ps.setInt(31,
				// Integer.parseInt(bean.getHigh_shcool_workhours().equals("")?"0":bean.getHigh_shcool_workhours()));
				if (!bean.getHigh_shcool_workhours().equals("")) {
					ps.setInt(31, Integer.parseInt(bean.getHigh_shcool_workhours()));
				} else {
					ps.setNull(31, java.sql.Types.INTEGER);
				}
				String strHs_test = bean.getHigh_school_testing().toString();
				ps.setString(32, strHs_test.substring(1, strHs_test.length() - 1));
				ps.setString(33, bean.getComments());
			}
		});
	}

	public StudentPostActiveBean getPostActiveByStudentID(int student_id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.user_id,a.first_name,a.middle_name,a.last_name,b.* \n");
		sql.append("FROM ").append(TABLE_PROFILE_STUDENT).append(" a,").append(TABLE_STUDENT_POST_ACTIVE)
				.append(" b \n");
		sql.append("WHERE a.user_id = b.student_id and a.user_id=?");

		try {
			StudentPostActiveBean Bean = jdbcTemplate.queryForObject(sql.toString(), new Object[] { student_id },
					new RowMapper<StudentPostActiveBean>() {
						@Override
						public StudentPostActiveBean mapRow(ResultSet reSet, int rowNum) throws SQLException {
							StudentPostActiveBean bean = new StudentPostActiveBean();
							bean.setStudent_id(student_id);

							bean.setName(reSet.getString("first_name") + " " + reSet.getString("middle_name") + " "
									+ reSet.getString("last_name"));
							
							bean.setFirstName(reSet.getString("first_name"));
							bean.setMiddleName(reSet.getString("middle_name"));
							bean.setLastName(reSet.getString("last_name"));

							bean.setEmploymentName(reSet.getString("employmentName"));
							bean.setOccupation(reSet.getString("occupation"));
							bean.setPosition(reSet.getString("position"));
							bean.setEmploy_city(reSet.getString("employ_city"));
							bean.setEmploy_county(reSet.getString("employ_county"));
							bean.setEmploy_state(reSet.getString("employ_state"));

							bean.setGrud_school_name(reSet.getString("grud_school_name"));
							bean.setGrud_city(reSet.getString("grud_city"));
							bean.setGrud_county(reSet.getString("grud_county"));
							bean.setGrud_state(reSet.getString("grud_state"));
							bean.setSubsquent_degree(reSet.getString("subsquent_degree"));
							bean.setCompany_comments(reSet.getString("company_comments"));
							return bean;
						}
					});
			return Bean;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void updateStudentPostActiveByStudentID(int student_id, StudentPostActiveBean bean) {
		// TODO Auto-generated method stub
		boolean existed = existedInPostActiveTable(student_id);
		if (existed) {
//			System.out.println("in table :" + bean);
			StringBuilder updateSql = new StringBuilder();
			updateSql.append("update " + TABLE_STUDENT_POST_ACTIVE
					+ " set employmentName=?,occupation=?,position=?,"
					+ "employ_city=?,employ_county=?,employ_state=?,company_comments=?,grud_school_name=?,"
					+ "grud_city=?,grud_county=?,grud_state=?,subsquent_degree=? where student_id='" + student_id
					+ "'");
			jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, bean.getEmploymentName());
					ps.setString(2, bean.getOccupation());
					ps.setString(3, bean.getPosition());
					ps.setString(4, bean.getEmploy_city());
					ps.setString(5, bean.getEmploy_county());
					ps.setString(6, bean.getEmploy_state());
					ps.setString(7, bean.getCompany_comments());
					ps.setString(8, bean.getGrud_school_name());
					ps.setString(9, bean.getGrud_city());
					ps.setString(10, bean.getGrud_county());
					ps.setString(11, bean.getGrud_state());
					ps.setString(12, bean.getSubsquent_degree());
				}
			});
		} else {
//			System.out.println("not in table :" + bean);
			StringBuilder insertSql = new StringBuilder();
			insertSql.append("insert into " + TABLE_STUDENT_POST_ACTIVE
					+ " (student_id,employmentName,occupation,position,employ_city,"
					+ "employ_county,employ_state,company_comments,grud_school_name,grud_city,grud_county,"
					+ "grud_state,subsquent_degree) values " + "(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, bean.getStudent_id());
					ps.setString(2, bean.getEmploymentName());
					ps.setString(3, bean.getOccupation());
					ps.setString(4, bean.getPosition());
					ps.setString(5, bean.getEmploy_city());
					ps.setString(6, bean.getEmploy_county());
					ps.setString(7, bean.getEmploy_state());
					ps.setString(8, bean.getCompany_comments());
					ps.setString(9, bean.getGrud_school_name());
					ps.setString(10, bean.getGrud_city());
					ps.setString(11, bean.getGrud_county());
					ps.setString(12, bean.getGrud_state());
					ps.setString(13, bean.getSubsquent_degree());
				}
			});
		}

	}

	private boolean existedInPostActiveTable(int student_id) {
		int result = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM " + TABLE_STUDENT_POST_ACTIVE + " where student_id='" + student_id + "'",
				Integer.class);
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

	public int updateStudentProfileForm(StudentProfileBean bean, int student_id) {
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("update profile_student set "
				+ "first_name=?,middle_name=?,last_name=?,birth_date=?,veteran=?,first_gen_college_student=?,"
				+ "Language_at_home=?,ssn_last_four=?,Family_income=?,"
				+ "current_address_line1=?,current_address_line2=?,current_address_city=?,current_address_county=?,current_address_state=?,current_address_zip=?,"
				+ "ethnicity=?,race=?,disability=?,disability_type=?,Pell_Grant_Eligibility=? "
				+ " where user_id='" + student_id + "'");

		return jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bean.getFirstName());
				ps.setString(2, bean.getMiddleName());
				ps.setString(3, bean.getLastName());
				ps.setDate(4, new java.sql.Date(bean.getBirthDate().getTime()));
				ps.setString(5, String.valueOf(bean.getVeteran()));
				ps.setString(6, String.valueOf(bean.getFirst_gen_college_student()));
				ps.setString(7, bean.getLanguage_at_home());
				ps.setString(8, bean.getSsn_last_four());
				ps.setString(9, String.valueOf(bean.getFamily_income()));
				ps.setString(10, bean.getCurrent_address_line1());
				ps.setString(11, bean.getCurrent_address_line2());
				ps.setString(12, bean.getCurrent_address_city());
				ps.setString(13, bean.getCurrent_address_county());
				ps.setString(14, bean.getCurrent_address_state());
				ps.setString(15, bean.getCurrent_address_zip());
				ps.setString(16, String.valueOf(bean.getEthnicity()));
				String strRace = bean.getRace().toString();
				ps.setString(17, strRace.substring(1, strRace.length() - 1));
				ps.setString(18, bean.getDisability());
				ps.setString(19, String.valueOf(bean.getDisability_type()));
				ps.setString(20, bean.getPell_grant_eligiblity());
			}
		});
		
	}

	public int updateStudentHighSchoolForm(StudentProfileBean bean, int student_id) {
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("update profile_student set "
				+ "high_school_act_total_score=?,high_school_act_reading_score=?,high_school_act_writing_score=?,high_school_act_math_score=?,high_school_act_sci_score=?,"
				+ "high_school_sat_total_score=?,high_school_sat_reading_score=?,high_school_sat_writing_score=?,high_school_sat_math_score=?,high_school_sat_sci_score=?,"
				+ "high_school_testing=?,highschool_activities=?,comments=?"
				+ " where user_id='" + student_id + "'");

		return jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bean.getHigh_school_act_total_score());
				ps.setString(2, bean.getHigh_school_act_reading_score());
				ps.setString(3, bean.getHigh_school_act_writing_score());
				ps.setString(4, bean.getHigh_school_act_math_score());
				ps.setString(5, bean.getHigh_school_act_sci_score());
				ps.setString(6, bean.getHigh_school_sat_total_score());
				ps.setString(7, bean.getHigh_school_sat_reading_score());
				ps.setString(8, bean.getHigh_school_sat_writing_score());
				ps.setString(9, bean.getHigh_school_sat_math_score());
				ps.setString(10, bean.getHigh_school_sat_sci_score());
				ps.setString(11, bean.getHigh_school_testing());
				ps.setString(12, bean.getHighschool_activities());
				ps.setString(13, bean.getComments());
			}
		});
		
	}

}
