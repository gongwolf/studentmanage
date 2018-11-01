package edu.nmsu.nmamp.student.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import edu.nmsu.nmamp.student.dao.Schemacode;
import edu.nmsu.nmamp.student.model.MentorSummaryBean;
import edu.nmsu.nmamp.student.model.StudentSummaryBean;

@Repository("AdminDAOImpl")
public class AdminDAOImpl implements Schemacode {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<StudentSummaryBean> getStudentListSummary(String ic, String condition, String queryYear) {
		StringBuilder sql = new StringBuilder();
		Object[] params = null;

		if (ic.equals("admin") && condition.equals("all")) {
			sql.append("select a.*,c.graduated from profile_student a \n");
			sql.append("left join (select user_id,graduated from selfreport_data b where b.semester='")
					.append(queryYear).append("') c \n");
			sql.append("on a.user_id=c.user_id");
		}

		System.out.println(sql);

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
//					bean.setIsActive(reSet.getString("graduated"));
					bean.setIsActive(getActiveStatusOfStudent(bean.getUser_id(), queryYear));
					studentList.add(bean);
				}
				return studentList;
			}

		});
	}

	private String getActiveStatusOfStudent(int user_id, String queryYear) {
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

	public List<MentorSummaryBean> getMentortListSummary(String ic, String condition) {
		StringBuilder sql = new StringBuilder();
		Object[] params = null;

		if (ic.equals("admin") && condition.equals("all")) {
			sql.append("select * from profile_mentor");
		}

		// System.out.println(sql);

		return jdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<MentorSummaryBean>>() {

			@Override
			public List<MentorSummaryBean> extractData(ResultSet reSet) throws SQLException, DataAccessException {
				List<MentorSummaryBean> mentorList = new ArrayList<>();
				while (reSet.next()) {
					MentorSummaryBean bean = new MentorSummaryBean();
					bean.setMentor_id(reSet.getInt("mentor_id"));
					String prefix = reSet.getString("mentor_prefix") == null ? "" : reSet.getString("mentor_prefix");
					String first_name = reSet.getString("mentor_first_name") == null ? ""
							: reSet.getString("mentor_first_name");
					String last_name = reSet.getString("mentor_last_name") == null ? ""
							: reSet.getString("mentor_last_name");
					String mentor_name = "";
					if (!prefix.equals("")) {
						mentor_name = prefix + " " + first_name + " " + last_name;

					} else {
						mentor_name = first_name + " " + last_name;
					}
					bean.setName(mentor_name);
					bean.setEmail(reSet.getString("mentor_email"));
					bean.setDeportment(reSet.getString("mentor_department"));
					bean.setIntitution(reSet.getString("mentor_institution"));
					mentorList.add(bean);
				}
				return mentorList;
			}
		});
	}

	public List<StudentSummaryBean> getApplicantsByPerson(String firstName, String lastName, String birthDate,
			String email) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT user_id, first_name, last_name, birth_date, middle_name \n");
		sql.append("FROM ").append(TABLE_PROFILE_STUDENT).append(" \n");

		if (!firstName.isEmpty() || !lastName.isEmpty() || !birthDate.isEmpty() || !email.isEmpty()) {

			sql.append("WHERE");

			if (!firstName.isEmpty()) {
				sql.append(" first_name LIKE '%" + firstName + "%' AND");
			}

			if (!lastName.isEmpty()) {
				sql.append(" last_name LIKE '%" + lastName + "%' AND");
			}

			if (!email.isEmpty()) {
				sql.append(" email LIKE '%" + email + "%' AND");
			}

			if (!birthDate.isEmpty()) {
				sql.append(" DATE_FORMAT(birth_date, '%m/%d/%Y')='" + birthDate + "' AND");
			}

			sql.delete(sql.length() - 3, sql.length());
		}
		Object[] params = new Object[] {};
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
					studentList.add(bean);
				}
				return studentList;
			}
		});
	}

	public List<StudentSummaryBean> getApplicantsByProgram(String program) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT user_id, first_name, last_name, birth_date, middle_name \n");

		sql.append("FROM ").append(TABLE_PROFILE_STUDENT).append(" \n");
		sql.append("WHERE user_id in (SELECT user_id FROM ").append(TABLE_APPLICATION_LIST);

		Object[] params;
		if (program != null && program.length() > 1) {
			sql.append(" WHERE program=?)\n");
			params = new Object[] { program };
		} else {
			sql.append(" )\n");
			params = new Object[] {};
		}

		System.out.println(sql);

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
					studentList.add(bean);
				}
				return studentList;
			}
		});
	}

	public List<MentorSummaryBean> getMentorsByPerson(String firstName, String lastName, String email,
			String intitutionAbbr, String department) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * \n");
		sql.append("FROM ").append(TABLE_PROFILE_MENTOR).append(" \n");

		if (!firstName.isEmpty() || !lastName.isEmpty() || !intitutionAbbr.isEmpty() || !email.isEmpty()
				|| !department.isEmpty()) {

			sql.append("WHERE");

			if (!firstName.isEmpty()) {
				sql.append(" mentor_first_name LIKE '%" + firstName + "%' AND");
			}

			if (!lastName.isEmpty()) {
				sql.append(" mentor_last_name LIKE '%" + lastName + "%' AND");
			}

			if (!email.isEmpty()) {
				sql.append(" mentor_email LIKE '%" + email + "%' AND");
			}

			if (!department.isEmpty()) {
				sql.append(" mentor_department LIKE '%" + department + "%' AND");
			}

			if (!intitutionAbbr.isEmpty()) {
				sql.append(" mentor_institution = '" + intitutionAbbr + "' AND");
			}

			sql.delete(sql.length() - 3, sql.length());
		}
		Object[] params = new Object[] {};
		System.out.println(sql);
		return jdbcTemplate.query(sql.toString(), params, new ResultSetExtractor<List<MentorSummaryBean>>() {

			@Override
			public List<MentorSummaryBean> extractData(ResultSet reSet) throws SQLException, DataAccessException {
				List<MentorSummaryBean> mentorList = new ArrayList<>();
				while (reSet.next()) {
					MentorSummaryBean bean = new MentorSummaryBean();
					bean.setMentor_id(reSet.getInt("mentor_id"));
					String prefix = reSet.getString("mentor_prefix") == null ? "" : reSet.getString("mentor_prefix");
					String first_name = reSet.getString("mentor_first_name") == null ? ""
							: reSet.getString("mentor_first_name");
					String last_name = reSet.getString("mentor_last_name") == null ? ""
							: reSet.getString("mentor_last_name");
					String mentor_name = "";
					if (!prefix.equals("")) {
						mentor_name = prefix + " " + first_name + " " + last_name;

					} else {
						mentor_name = first_name + " " + last_name;
					}
					bean.setName(mentor_name);
					bean.setEmail(reSet.getString("mentor_email"));
					bean.setDeportment(reSet.getString("mentor_department"));
					bean.setIntitution(reSet.getString("mentor_institution"));
					mentorList.add(bean);
				}
				return mentorList;
			}
		});
	}

}
