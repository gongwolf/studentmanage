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

import edu.nmsu.nmamp.student.model.StudentSummaryBean;

@Repository("AdminDAOImpl")
public class AdminDAOImpl {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	public List<StudentSummaryBean> getStudentListSummary(String ic, String condition) {
		StringBuilder sql=new StringBuilder();
		Object[] params = null;
		
		if(ic.equals("admin") && condition.equals("all"))
		{
			sql.append("select * from profile_student");
		}
		
//		System.out.println(sql);
		
		
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

}
