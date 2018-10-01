package edu.nmsu.nmamp.student.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.nmsu.nmamp.student.dao.UserDAO;
import edu.nmsu.nmamp.student.dao.impl.YearlyDaoImpl;
import edu.nmsu.nmamp.student.model.User;
import edu.nmsu.nmamp.student.model.YearlyBean;
import edu.nmsu.nmamp.student.service.ProgramCode;

@Controller
public class MentorManageController {
	@Autowired
	YearlyDaoImpl YearDao = new YearlyDaoImpl();
	@Autowired
	private UserDAO userDAO;
	
	@Autowired private ObjectMapper objectMapper; 
	private static final Logger logger = LoggerFactory.getLogger(AdminHomeController.class); 

	
	private static final String studentProfile = "/mentor_manage/mentor-profile";

	

	@GetMapping(value = { "mentor/profile/{mentor_id}"})
	public String studentProfile(ModelMap model, @PathVariable("mentor_id") int user_id, Principal principal) {
		model.addAttribute("schools", ProgramCode.CURRENT_ACADEMIC_SCHOOL);
		System.out.println(studentProfile+"!!"+ProgramCode.CURRENT_ACADEMIC_SCHOOL);
		return studentProfile;
	}
	

}
