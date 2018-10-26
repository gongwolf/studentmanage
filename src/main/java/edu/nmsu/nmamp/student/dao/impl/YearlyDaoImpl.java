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
		sql.append("SELECT a.*,");
		sql.append("b.first_name as b_first_name,b.middle_name as b_middle_name,b.last_name as b_last_name \n");
		sql.append("FROM ").append(TABLE_SELFREPORT_DATA).append(" a,  \n");
		sql.append(TABLE_PROFILE_STUDENT).append(" b \n");
		sql.append("WHERE a.user_id = ? and a.semester=? and a.user_id = b.user_id");
//		System.out.println(sql);
		try {
			StudentYearlyReportBean Bean = jdbcTemplate.queryForObject(sql.toString(),
					new Object[] { student_id,queryYear}, new RowMapper<StudentYearlyReportBean>() {
						@Override
						public StudentYearlyReportBean mapRow(ResultSet reSet, int rowNum) throws SQLException {
							StudentYearlyReportBean bean = new StudentYearlyReportBean();
							bean.setStudent_id(reSet.getInt("user_id"));
							bean.setFirstName(reSet.getString("b_first_name"));
							bean.setMiddleName(reSet.getString("b_middle_name"));
							bean.setLastName(reSet.getString("b_last_name"));
							
							bean.setAcdemic_school(reSet.getString("select_school"));
							bean.setSchool_level(ProgramCode.SCHOOL_LEVEL.get(bean.getAcdemic_school()));
							bean.setMajor(reSet.getString("major"));
							bean.setMinor(reSet.getString("minor"));
							bean.setChanged_major(reSet.getString("changed_major") == null ? -1 : reSet.getInt("changed_major"));
							bean.setCourse_taken(reSet.getString("course_taken"));
							bean.setGpa(reSet.getFloat("gpa"));
							bean.setSemester_gpa(reSet.getFloat("semester_gpa"));
							bean.setCredits(reSet.getInt("credits"));
							bean.setSemester_credits(reSet.getInt("semester_credits"));
							
							String graduated=reSet.getString("graduated");
							bean.setGraduated(graduated==null?"":graduated);
							bean.setGraduated_degree(reSet.getString("graduated_degree"));
							bean.setGraduated_field(reSet.getString("graduated_field"));
							bean.setGraduated_semester(reSet.getString("graduated_semester"));
							
							String transfered=reSet.getString("transfered");
							bean.setTransfered(transfered==null?"":transfered);
							bean.setTransfered_AA_degree(reSet.getString("transfered_aa_degree"));
							bean.setTransfered_from(reSet.getString("transfered_from"));
							bean.setTransfered_to(reSet.getString("transfered_to"));
							bean.setTransfered_credits(reSet.getString("transfered_credits"));
							
							String withdrew=reSet.getString("withdrew");
							bean.setWithdrew(withdrew==null?"":withdrew);
							bean.setWithdrew_reason(reSet.getString("withdrew_reason"));
							
							String fin_amp = reSet.getString("fin_amp");
							String fin_amp_summer = reSet.getString("fin_amp");
							bean.setFin_amp(fin_amp==null?"":fin_amp);
							bean.setFin_amp_summer(fin_amp_summer==null?"":fin_amp_summer);
							bean.setFin_amp_type(reSet.getString("fin_amp_type"));
							
							
							bean.setActivities_list(reSet.getString("Activities_list"));
							System.out.println(bean.getActivities_list());
							bean.setActivities_comments(reSet.getString("Activities_comments"));
							
							bean.setIntern_json(reSet.getString("intern_json"));
							bean.setIntern_comments(reSet.getString("intern_comments"));
							
							bean.setConference_json(reSet.getString("conference_json"));
							
							bean.setPublication_json(reSet.getString("publication_json"));

							bean.setVolunteer_json(reSet.getString("volunteer_json"));
							
							bean.setTravel_json(reSet.getString("travel_json"));
							
							bean.setNotesAndComments(reSet.getString("comments"));
							return bean;
						}
					});
			return Bean;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public int UpdateYearBeanByUseIdAndYear(StudentYearlyReportBean bean, String activitiesList, int student_id,String queryYear) {
		StringBuilder updateSql = new StringBuilder();
		updateSql.append("update selfreport_data set "
				+ "Activities_list=?, Activities_comments=?, intern_json=?, conference_json=?,publication_json=?,intern_comments=?, volunteer_json=?,travel_json=?, "
				+ "comments=?, select_school=?, minor=?,changed_major=?,major=?,course_taken=?,gpa=?,semester_gpa=?,credits=?,semester_credits=?,"
				+ "graduated=?,graduated_degree=?,graduated_field=?,graduated_semester=?,"
				+ "Transfered=?,Transfered_from=?,Transfered_to=?,Transfered_AA_Degree=?,Transfered_credits=?,withdrew=?,withdrew_reason=?,"
				+ "Fin_AMP=?,Fin_AMP_type=?,Fin_AMP_summer=?"
				+ " where user_id='" + student_id + "' and semester='"+queryYear+"'");
		System.out.println(activitiesList);
		System.out.println(updateSql);
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
				ps.setString(12, String.valueOf(bean.getChanged_major()));
				ps.setString(13, bean.getMajor());
				ps.setString(14, bean.getCourse_taken());
				ps.setFloat(15,bean.getGpa());
				ps.setFloat(16, bean.getSemester_gpa());
				ps.setInt(17, bean.getCredits());
				ps.setInt(18, bean.getSemester_credits());
				ps.setString(19,bean.getGraduated());
				ps.setString(20,bean.getGraduated_degree());
				ps.setString(21,bean.getGraduated_field());
				ps.setString(22,bean.getGraduated_semester());
				ps.setString(23,bean.getTransfered());
				ps.setString(24,bean.getTransfered_from());
				ps.setString(25,bean.getTransfered_to());
				ps.setString(26,bean.getTransfered_AA_degree());
				ps.setString(27,bean.getTransfered_credits());
				ps.setString(28,bean.getWithdrew());
				ps.setString(29,bean.getWithdrew_reason());
				ps.setString(30,bean.getFin_amp());
				ps.setString(31,bean.getFin_amp_type());
				ps.setString(32,bean.getFin_amp_summer());
				
			}
		});
	}
}
