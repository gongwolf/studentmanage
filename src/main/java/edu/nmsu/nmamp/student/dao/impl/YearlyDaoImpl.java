package edu.nmsu.nmamp.student.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.nmsu.nmamp.student.dao.Schemacode;
import edu.nmsu.nmamp.student.model.StudentProfileBean;
import edu.nmsu.nmamp.student.model.StudentYearlyReportBean;
import edu.nmsu.nmamp.student.service.ProgramCode;

@Repository("YearlyDaoImpl")

public class YearlyDaoImpl implements Schemacode {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public StudentYearlyReportBean getYearBeanByUseIdAndYear(int student_id, String queryYear) {
		StringBuilder sql = new StringBuilder();
		sql.append("select c.*,CONCAT(d.mentor_first_name, ' ', mentor_last_name) as mentor_name from \n");

		sql.append("( SELECT a.*,");
		sql.append("b.first_name as b_first_name,b.middle_name as b_middle_name,b.last_name as b_last_name \n");
		sql.append("FROM ").append(TABLE_SELFREPORT_DATA).append(" a,  \n");
		sql.append(TABLE_PROFILE_STUDENT).append(" b \n");
		sql.append("WHERE a.user_id = ? and a.semester=? and a.user_id = b.user_id) c ");
		sql.append("left join \n" + TABLE_PROFILE_MENTOR + " d on c.mentor_id = d.mentor_id");
		System.out.println(student_id + " " + queryYear + " " + sql);
		try {
			StudentYearlyReportBean Bean = jdbcTemplate.queryForObject(sql.toString(),
					new Object[] { student_id, queryYear }, new RowMapper<StudentYearlyReportBean>() {
						@Override
						public StudentYearlyReportBean mapRow(ResultSet reSet, int rowNum) throws SQLException {
							StudentYearlyReportBean bean = new StudentYearlyReportBean();
							bean.setStudent_id(reSet.getInt("user_id"));
							bean.setFirstName(reSet.getString("b_first_name"));
							bean.setMiddleName(reSet.getString("b_middle_name"));
							bean.setLastName(reSet.getString("b_last_name"));

							bean.setAcdemic_school(reSet.getString("select_school"));
							bean.setSchool_level(ProgramCode.SCHOOL_LEVEL.get(bean.getAcdemic_school()));
							bean.setCollege_level(reSet.getString("college_level"));

							bean.setDiscipline(reSet.getString("discipline"));
							bean.setMajor(reSet.getString("major"));
							bean.setMinor(reSet.getString("minor"));
							bean.setChanged_major(
									reSet.getString("changed_major") == null ? -1 : reSet.getInt("changed_major"));
							bean.setCourse_taken(reSet.getString("course_taken"));
							bean.setGpa(reSet.getFloat("gpa"));
							bean.setSemester_gpa(reSet.getFloat("semester_gpa"));
							bean.setCredits(reSet.getInt("credits"));
							bean.setSemester_credits(reSet.getInt("semester_credits"));

							String graduated = reSet.getString("graduated");
							bean.setGraduated(graduated == null ? "" : graduated);
							bean.setGraduated_degree(reSet.getString("graduated_degree"));
							bean.setGraduated_field(reSet.getString("graduated_field"));
							bean.setGraduated_semester(reSet.getString("graduated_semester"));

							String transfered = reSet.getString("transfered");
							bean.setTransfered(transfered == null ? "" : transfered);
							bean.setTransfered_AA_degree(reSet.getString("transfered_aa_degree"));
							bean.setTransfered_from(reSet.getString("transfered_from"));
							bean.setTransfered_to(reSet.getString("transfered_to"));
							bean.setTransfered_credits(reSet.getString("transfered_credits"));

							String withdrew = reSet.getString("withdrew");
							bean.setWithdrew(withdrew == null ? "" : withdrew);
							bean.setWithdrew_reason(reSet.getString("withdrew_reason"));

							String fin_amp = reSet.getString("fin_amp");
							String fin_amp_summer = reSet.getString("fin_amp");
							bean.setFin_amp(fin_amp == null ? "" : fin_amp);
							bean.setFin_amp_summer(fin_amp_summer == null ? "" : fin_amp_summer);
							bean.setFin_amp_type(reSet.getString("fin_amp_type"));

							bean.setActivities_list(reSet.getString("Activities_list"));
							System.out.println(bean.getActivities_list());
							bean.setActivities_comments(reSet.getString("Activities_comments"));
							
							//other activities
							bean.setOtherActivities(reSet.getString("other_activities"));

							bean.setIntern_json(reSet.getString("intern_json"));
							bean.setIntern_comments(reSet.getString("intern_comments"));

							bean.setConference_json(reSet.getString("conference_json"));

							bean.setPublication_json(reSet.getString("publication_json"));

							bean.setVolunteer_json(reSet.getString("volunteer_json"));

							bean.setTravel_json(reSet.getString("travel_json"));

							bean.setNotesAndComments(reSet.getString("comments"));

							bean.setMentor_id(reSet.getString("mentor_id"));
							bean.setMentor_name(reSet.getString("mentor_name"));

							return bean;
						}
					});
			return Bean;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int UpdateYearBeanByUseIdAndYear(StudentYearlyReportBean bean, String activitiesList, int student_id,
			String queryYear) {

		boolean existed = existedInSelfReportTable(student_id, queryYear);
		if (existed) {
			StringBuilder updateSql = new StringBuilder();
			updateSql.append("update selfreport_data set "
					+ "Activities_list=?, Activities_comments=?, intern_json=?, conference_json=?,publication_json=?,intern_comments=?, volunteer_json=?,travel_json=?, "
					+ "comments=?, select_school=?, minor=?,changed_major=?,major=?,course_taken=?,gpa=?,semester_gpa=?,credits=?,semester_credits=?,"
					+ "graduated=?,graduated_degree=?,graduated_field=?,graduated_semester=?,"
					+ "Transfered=?,Transfered_from=?,Transfered_to=?,Transfered_AA_Degree=?,Transfered_credits=?,withdrew=?,withdrew_reason=?,"
					+ "Fin_AMP=?,Fin_AMP_type=?,Fin_AMP_summer=?,mentor_id=?" + " where user_id='" + student_id
					+ "' and semester='" + queryYear + "'");
			// System.out.println(activitiesList);
			// System.out.println(updateSql);
			System.out.println(bean);
			return jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, activitiesList);
					ps.setString(2, bean.getActivities_comments());
					ps.setString(3, bean.getIntern_json());
					ps.setString(4, bean.getConference_json());
					ps.setString(5, bean.getPublication_json());
					ps.setString(6, bean.getIntern_comments());
					ps.setString(7, bean.getVolunteer_json());
					ps.setString(8, bean.getTravel_json());
					ps.setString(9, bean.getNotesAndComments());
					ps.setString(10, bean.getAcdemic_school());
					ps.setString(11, bean.getMinor());
					if (bean.getChanged_major() == -1) {
						ps.setNull(12, java.sql.Types.INTEGER);
					} else {
						ps.setInt(12, bean.getChanged_major());
					}
					ps.setString(13, bean.getMajor());
					ps.setString(14, bean.getCourse_taken());
					ps.setFloat(15, bean.getGpa());
					ps.setFloat(16, bean.getSemester_gpa());
					ps.setInt(17, bean.getCredits());
					ps.setInt(18, bean.getSemester_credits());
					ps.setString(19, bean.getGraduated());
					ps.setString(20, bean.getGraduated_degree());
					ps.setString(21, bean.getGraduated_field());
					ps.setString(22, bean.getGraduated_semester());
					ps.setString(23, bean.getTransfered());
					ps.setString(24, bean.getTransfered_from());
					ps.setString(25, bean.getTransfered_to());
					ps.setString(26, bean.getTransfered_AA_degree());
					ps.setString(27, bean.getTransfered_credits());
					ps.setString(28, bean.getWithdrew());
					ps.setString(29, bean.getWithdrew_reason());
					ps.setString(30, bean.getFin_amp());
					ps.setString(31, bean.getFin_amp_type());
					ps.setString(32, bean.getFin_amp_summer());
					ps.setString(33, bean.getMentor_id());

				}
			});
		} else {
			System.out.println("not in table :" + bean);
			StringBuilder insertSql = new StringBuilder();
			insertSql.append("insert into " + TABLE_SELFREPORT_DATA
					+ " (window_id,user_id,semester,first_name,last_name,current_address_line1,current_address_line2,current_address_city,"
					+ "current_address_state,current_address_zip,current_address_country,select_school,major,gpa,"
					+ "intern_json,travel_json,conference_json,publication_json,awards_json,other_activities,submit_date,"
					+ "minor,changed_major,course_taken,semester_gpa,semester_credits,credits,graduated,graduated_degree,"
					+ "graduated_field,graduated_semester,Transfered,Transfered_from,Transfered_to,Transfered_AA_Degree,"
					+ "Transfered_credits,withdrew,withdrew_reason,Fin_AMP,Fin_AMP_type,Fin_AMP_summer,Activities_list,"
					+ "Activities_comments,intern_comments,volunteer_json,comments,mentor_id) values "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			return jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, -1);
					ps.setInt(2, bean.getStudent_id());
					ps.setString(3, queryYear);
					ps.setString(4, bean.getFirstName());
					ps.setString(5, bean.getLastName());
					ps.setString(6, null);
					ps.setString(7, null);
					ps.setString(8, null);
					ps.setString(9, null);
					ps.setString(10, null);
					ps.setString(11, null);
					ps.setString(12, bean.getAcdemic_school());
					ps.setString(13, bean.getMajor());
					ps.setFloat(14, bean.getGpa());
					ps.setString(15, bean.getIntern_json());
					ps.setString(16, bean.getTravel_json());
					ps.setString(17, bean.getConference_json());
					ps.setString(18, bean.getPublication_json());
					ps.setString(19, null);
					ps.setString(20, null);
					ps.setTimestamp(21, null);
					ps.setString(22, bean.getMinor());

					if (bean.getChanged_major() == -1) {
						ps.setNull(23, java.sql.Types.INTEGER);
					} else {
						ps.setInt(23, bean.getChanged_major());
					}
					ps.setString(24, bean.getCourse_taken());
					ps.setFloat(25, bean.getSemester_gpa());
					ps.setInt(26, bean.getSemester_credits());
					ps.setInt(27, bean.getCredits());
					ps.setString(28, bean.getGraduated());
					ps.setString(29, bean.getGraduated_degree());
					ps.setString(30, bean.getGraduated_field());
					ps.setString(31, bean.getGraduated_semester());
					ps.setString(32, bean.getTransfered());
					ps.setString(33, bean.getTransfered_from());
					ps.setString(34, bean.getTransfered_to());
					ps.setString(35, bean.getTransfered_AA_degree());
					ps.setString(36, bean.getTransfered_credits());
					ps.setString(37, bean.getWithdrew());
					ps.setString(38, bean.getWithdrew_reason());
					ps.setString(39, bean.getFin_amp());
					ps.setString(40, bean.getFin_amp_type());
					ps.setString(41, bean.getFin_amp_summer());
					ps.setString(42, activitiesList);
					ps.setString(43, bean.getActivities_comments());
					ps.setString(44, bean.getIntern_comments());
					ps.setString(45, bean.getVolunteer_json());
					ps.setString(46, bean.getNotesAndComments());
					ps.setString(47, bean.getMentor_id());

				}
			});
		}
	}

	private boolean existedInSelfReportTable(int student_id, String queryYear) {
		int result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + TABLE_SELFREPORT_DATA + " where user_id='"
				+ student_id + "' and semester='" + queryYear + "'", Integer.class);
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

	public int UpdateYearBeanAcdemicByUseIdAndYear(StudentYearlyReportBean bean, String activitiesList, int student_id,
			String queryYear) {
		boolean existed = existedInSelfReportTable(student_id, queryYear);
		if (existed) {
			StringBuilder updateSql = new StringBuilder();
			updateSql.append("update selfreport_data set "
					+ "select_school=?,college_level=?,discipline=?,major=?,minor=?,mentor_id=?,course_taken=?,gpa=?,semester_gpa=?,credits=?,semester_credits=?,"
					+ "graduated=?,graduated_degree=?,graduated_field=?,graduated_semester=?,"
					+ "Transfered=?,Transfered_from=?,Transfered_to=?,Transfered_AA_Degree=?,Transfered_credits=?,withdrew=?,withdrew_reason=?,"
					+ "Fin_AMP=?,Fin_AMP_type=?,Fin_AMP_summer=?," + "Activities_list=?, Activities_comments=? "
					+ " where user_id='" + student_id + "' and semester='" + queryYear + "'");
			// System.out.println(activitiesList);
			// System.out.println(updateSql);
			System.out.println(bean);
			return jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, bean.getAcdemic_school());
					ps.setString(2, bean.getCollege_level());
					ps.setString(3, bean.getDiscipline());
					ps.setString(4, bean.getMajor());
					ps.setString(5, bean.getMinor());
					ps.setString(6, bean.getMentor_id());
					ps.setString(7, bean.getCourse_taken());
					ps.setFloat(8, bean.getGpa());
					ps.setFloat(9, bean.getSemester_gpa());
					ps.setInt(10, bean.getSemester_credits());
					ps.setInt(11, bean.getCredits());
					ps.setString(12, bean.getGraduated());
					ps.setString(13, bean.getGraduated_degree());
					ps.setString(14, bean.getGraduated_field());
					ps.setString(15, bean.getGraduated_semester());
					ps.setString(16, bean.getTransfered());
					ps.setString(17, bean.getTransfered_from());
					ps.setString(18, bean.getTransfered_to());
					ps.setString(19, bean.getTransfered_AA_degree());
					ps.setString(20, bean.getTransfered_credits());
					ps.setString(21, bean.getWithdrew());
					ps.setString(22, bean.getWithdrew_reason());
					ps.setString(23, bean.getFin_amp());
					ps.setString(24, bean.getFin_amp_type());
					ps.setString(25, bean.getFin_amp_summer());
					ps.setString(26, activitiesList);
					ps.setString(27, bean.getActivities_comments());
				}
			});
		} else {
			System.out.println("not in table :" + bean);
			StringBuilder insertSql = new StringBuilder();
			insertSql.append("insert into " + TABLE_SELFREPORT_DATA + " ("
					+ "window_id,user_id,semester,first_name,last_name,"
					+ "select_school,college_level,discipline,major,minor,mentor_id,"
					+ "course_taken,gpa,semester_gpa,credits,semester_credits,"
					+ "graduated,graduated_degree,graduated_field,graduated_semester,"
					+ "Transfered,Transfered_from,Transfered_to,Transfered_AA_Degree,Transfered_credits,withdrew,withdrew_reason,"
					+ "Fin_AMP,Fin_AMP_type,Fin_AMP_summer," 
					+ "Activities_list, Activities_comments "
					+ ") values "
					+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			return jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, -1);
					ps.setInt(2, bean.getStudent_id());
					ps.setString(3, queryYear);
					ps.setString(4, bean.getFirstName());
					ps.setString(5, bean.getLastName());
					ps.setString(6, bean.getAcdemic_school());
					ps.setString(7, bean.getCollege_level());
					ps.setString(8, bean.getDiscipline());
					ps.setString(9, bean.getMajor());
					ps.setString(10, bean.getMinor());
					ps.setString(11, bean.getMentor_id());
					ps.setString(12, bean.getCourse_taken());
					ps.setFloat(13, bean.getGpa());
					ps.setFloat(14, bean.getSemester_gpa());
					ps.setInt(15, bean.getSemester_credits());
					ps.setInt(16, bean.getCredits());
					ps.setString(17, bean.getGraduated());
					ps.setString(18, bean.getGraduated_degree());
					ps.setString(19, bean.getGraduated_field());
					ps.setString(20, bean.getGraduated_semester());
					ps.setString(21, bean.getTransfered());
					ps.setString(22, bean.getTransfered_from());
					ps.setString(23, bean.getTransfered_to());
					ps.setString(24, bean.getTransfered_AA_degree());
					ps.setString(25, bean.getTransfered_credits());
					ps.setString(26, bean.getWithdrew());
					ps.setString(27, bean.getWithdrew_reason());
					ps.setString(28, bean.getFin_amp());
					ps.setString(29, bean.getFin_amp_type());
					ps.setString(30, bean.getFin_amp_summer());
					ps.setString(31, activitiesList);
					ps.setString(32, bean.getActivities_comments());

				}
			});
		}
	}

	public int UpdateYearBeanActivitiesByUseIdAndYear(StudentYearlyReportBean bean, String activitiesList,
			int student_id, String queryYear) {
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("update selfreport_data set "
				+ "other_activities=?,intern_json=?,intern_comments=?,conference_json=?,publication_json=?,volunteer_json=?,travel_json=?,comments=?"
				+ " where user_id='" + student_id + "' and semester='" + queryYear + "'");
		// System.out.println(activitiesList);
		 System.out.println(updateSql);
		System.out.println(bean);
		return jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, bean.getOtherActivities());
				ps.setString(2, bean.getIntern_json());
				ps.setString(3, bean.getIntern_comments());
				ps.setString(4, bean.getConference_json());
				ps.setString(5, bean.getPublication_json());
				ps.setString(6, bean.getVolunteer_json());
				ps.setString(7, bean.getTravel_json());
				ps.setString(8, bean.getNotesAndComments());
				
			}
		});
	}
}
