package edu.nmsu.nmamp.student.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.nmsu.nmamp.student.dao.Schemacode;
import edu.nmsu.nmamp.student.model.StudentProfileBean;

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
							System.out.println(bean);
							return bean;
						}
					});
			return applicationBean;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

}
