package edu.nmsu.nmamp.student.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.nmsu.nmamp.student.dao.Schemacode;
import edu.nmsu.nmamp.student.model.MentorBean;
import edu.nmsu.nmamp.student.model.StudentSummaryBean;

@Repository("MentorDAOImpl")
public class MentorDAOImpl implements Schemacode {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public MentorBean getMentorInformationByMentorID(int mentor_id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.* \n");
		sql.append("FROM ").append(TABLE_PROFILE_MENTOR).append(" a ");
		sql.append("WHERE a.mentor_id = ?");

		try {
			MentorBean Bean = jdbcTemplate.queryForObject(sql.toString(), new Object[] { mentor_id },
					new RowMapper<MentorBean>() {
						@Override
						public MentorBean mapRow(ResultSet reSet, int rowNum) throws SQLException {
							MentorBean bean = new MentorBean();
							bean.setMentor_id(mentor_id);
							bean.setFirst_name(reSet.getString("mentor_first_name"));
							bean.setLast_name(reSet.getString("mentor_last_name"));
							bean.setPrefix(reSet.getString("mentor_prefix"));
							bean.setInstitution(reSet.getString("mentor_institution"));
							bean.setDepartment(reSet.getString("mentor_department"));
							return bean;
						}
					});
			return Bean;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void updateMentorInformationByMentorID(int mentor_id, MentorBean bean) {
		boolean existed = existedInMentorProfileTable(mentor_id);
		if (existed) {
			System.out.println("in table :" + bean);
			StringBuilder updateSql = new StringBuilder();
			updateSql.append("update " + TABLE_PROFILE_MENTOR
					+ " set mentor_first_name=?,mentor_last_name=?,mentor_prefix=?,"
					+ "mentor_institution=?,mentor_department=? where mentor_id='" + mentor_id + "'");
			jdbcTemplate.update(updateSql.toString(), new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, bean.getFirst_name());
					ps.setString(2, bean.getLast_name());
					ps.setString(3, bean.getPrefix());
					ps.setString(4, bean.getInstitution());
					ps.setString(5, bean.getDepartment());
				}
			});
		} else {
			System.out.println("not in table :" + bean);
			StringBuilder insertSql = new StringBuilder();
			insertSql.append("insert into " + TABLE_PROFILE_MENTOR
					+ " (mentor_id,mentor_first_name,mentor_last_name,mentor_prefix,"
					+ "mentor_institution,mentor_department"
					+ ") values " + "(?,?,?,?,?,?) ");
			jdbcTemplate.update(insertSql.toString(), new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setInt(1, bean.getMentor_id());
					ps.setString(2, bean.getFirst_name());
					ps.setString(3, bean.getLast_name());
					ps.setString(4, bean.getPrefix());
					ps.setString(5, bean.getInstitution());
					ps.setString(6, bean.getDepartment());
				}
			});
		}
	}

	private boolean existedInMentorProfileTable(int mentor_id) {
		int result = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM " + TABLE_PROFILE_MENTOR + " where mentor_id='" + mentor_id + "'", Integer.class);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<StudentSummaryBean> getStudentListSummaryFollowMentor(String ic, String condition,int mentor_id) {
		StringBuilder sql = new StringBuilder();
		Object[] params = null;

		if (ic.equals("admin") && condition.equals("all")) {
			sql.append("select distinct(b.user_id),b.first_name,b.last_name,b.middle_name,b.birth_date from "+TABLE_SELFREPORT_DATA+" a,");
			sql.append(TABLE_PROFILE_STUDENT+" b \n");
			sql.append("where mentor_id='"+mentor_id+"' and a.user_id = b.user_id;");
		}

		// System.out.println(sql);

		return jdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<StudentSummaryBean>>() {

			@Override
			public List<StudentSummaryBean> extractData(ResultSet reSet) throws SQLException, DataAccessException {
				List<StudentSummaryBean> studentList = new ArrayList<>();
				while (reSet.next()) {
					StudentSummaryBean bean = new StudentSummaryBean();
					bean.setUser_id(reSet.getInt("user_id"));
					bean.setFirst_name(reSet.getString("first_name"));
					bean.setLast_name(reSet.getString("last_name"));
					bean.setMiddle_name(reSet.getString("middle_name"));
					bean.setBirthDate(reSet.getDate("birth_date"));
					bean.setIsActive(getActiveStatusOfStudent(bean.getUser_id()));
					studentList.add(bean);
				}
				return studentList;
			}
		});
	}
	
	private String getActiveStatusOfStudent(int user_id) {
//		System.out.println(user_id + "    " + queryYear);
		StringBuilder sql = new StringBuilder();
		sql.append("select user_id,semester,graduated from selfreport_data where user_id = '" + user_id
				+ "' order by semester");
		List<Map<String, Object>> reSets = jdbcTemplate.queryForList(sql.toString());
		
		if (reSets.isEmpty()) {
			return "2";
		}
		
//		System.out.println(reSets.size());
		Map<String, Object> last_row = reSets.get(reSets.size()-1);
		return (String) last_row.get("graduated");
	}
}
