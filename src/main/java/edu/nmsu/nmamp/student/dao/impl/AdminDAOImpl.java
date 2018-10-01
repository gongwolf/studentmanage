package edu.nmsu.nmamp.student.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import edu.nmsu.nmamp.student.model.MentorSummaryBean;
import edu.nmsu.nmamp.student.model.StudentSummaryBean;

@Repository("AdminDAOImpl")
public class AdminDAOImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<StudentSummaryBean> getStudentListSummary(String ic, String condition) {
		StringBuilder sql = new StringBuilder();
		Object[] params = null;

		if (ic.equals("admin") && condition.equals("all")) {
			sql.append("select * from profile_student");
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
					studentList.add(bean);
				}
				return studentList;
			}
		});
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

}
