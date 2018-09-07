/*
 * Copyright (c) 2018 Feng Liu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.nmsu.nmamp.student.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nmsu.nmamp.student.dao.Schemacode;
import edu.nmsu.nmamp.student.dao.UserDAO;

import edu.nmsu.nmamp.student.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;
import java.sql.*;
import java.util.*;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO, Schemacode {

	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	static {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Denver"));
	}

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	public User get(String email) {
		// System.out.println("email "+email);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT user_id, email, first_name, last_name, birth_date, password, role,institution FROM ");
		sql.append(TABLE_USER).append(" WHERE email = ? LIMIT 1");
		System.out.println(sql);
		try {
			User user = jdbcTemplate.queryForObject(sql.toString(), new Object[] { email }, new RowMapper<User>() {
				@Override
				public User mapRow(ResultSet reSet, int rowNum) throws SQLException {
					User user = new User();
					user.setUserID(reSet.getInt("user_id"));
					user.setEmail(reSet.getString("email"));
					user.setFirstName(reSet.getString("first_name"));
					user.setLastName(reSet.getString("last_name"));
					user.setBirthDate(reSet.getDate("birth_date"));
					user.setPassword(reSet.getString("password"));
					user.setAffiliation(reSet.getString("Institution"));
					user.setRole(Role.valueOf(reSet.getString("role")));
					return user;
				}
			});

//			switch (user.getRole()) {
//			case USER:
//				break;
//			case STAFF:
//			case ADMIN:
//				String sql2 = "SELECT affiliation FROM " + TABLE_PROFILE_STAFF + " WHERE user_id = ? LIMIT 1";
//				String affiliation = jdbcTemplate.queryForObject(sql2, new Object[] { user.getUserID() },
//						new RowMapper<String>() {
//							@Override
//							public String mapRow(ResultSet reSet, int rowNum) throws SQLException {
//								return reSet.getString("affiliation");
//							}
//						});
//				user.setAffiliation(affiliation);
//				break;
//			default:
//				break;
//			}
			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/** List all user */ 
	@Override
	public List<User> list() {
		String SQL_SELECT = "SELECT user_id, email, first_name, last_name FROM " + "user";
		List<User> listUser = jdbcTemplate.query(SQL_SELECT, new RowMapper<User>(){
			//use of RowMapper to map a row in the result set to a POJO object.
			@Override
			public User mapRow(ResultSet reSet, int rowNum) throws SQLException {
				User user = new User(); 
				user.setUserID(reSet.getInt("user_id"));
				user.setEmail(reSet.getString("email"));
				user.setFirstName(reSet.getString("first_name"));
				user.setLastName(reSet.getString("last_name"));
				return user;
			}
		}); 
		return listUser;
	}

}
