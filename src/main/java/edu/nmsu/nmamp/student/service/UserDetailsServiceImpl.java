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
package edu.nmsu.nmamp.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.nmsu.nmamp.student.dao.UserDAO;
import edu.nmsu.nmamp.student.model.User;
import edu.nmsu.nmamp.student.model.UserImpl;

 
@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		System.out.println("-----------"+email);
		User user = userDAO.get(email); 
		if(user != null){
			return new UserImpl(user, getGrantedAuthorities(user)); 
		}
		throw new UsernameNotFoundException(String.format("User with email=%s was not found", email));
	} 
	 
	private List<GrantedAuthority> getGrantedAuthorities(User user){
		System.out.println("  ---- "+user.getAffiliation()+"  "+user.getEmail()+"   ROLE_" +user.getRole().toString());
		return AuthorityUtils.createAuthorityList("ROLE_" +user.getRole().toString());
		
	}
	 
 

}
