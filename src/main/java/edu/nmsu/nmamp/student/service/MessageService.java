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

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
 
@Service("messageService")
@PropertySource("classpath:messages.properties")
public class MessageService {
	
	@Autowired
	private Environment env; 
 
	public Map<String, Object> getConferenceAddress(String location){
		Map<String, Object> map = new HashMap<>(); 
		switch(location){
		case "LCCC":
			map.put("name", env.getProperty("Conference.lccc.name"));
			map.put("address", env.getProperty("Conference.lccc.address"));
			map.put("lng", Double.parseDouble(env.getProperty("Conference.lccc.lng")));
			map.put("lat", Double.parseDouble(env.getProperty("Conference.lccc.lat")));
			break; 
		case "CCSU":
			map.put("name", env.getProperty("Conference.ccsu.name"));
			map.put("address", env.getProperty("Conference.ccsu.address"));
			map.put("lng", Double.parseDouble(env.getProperty("Conference.ccsu.lng")));
			map.put("lat", Double.parseDouble(env.getProperty("Conference.ccsu.lat")));
			break; 
		}
		map.put("center", true); 
		return map; 
	}
	
	public String getCouchDocumentID(){
		return env.getProperty("couchdb.document.id"); 
	}

}
