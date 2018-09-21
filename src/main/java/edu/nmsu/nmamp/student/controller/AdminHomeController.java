package edu.nmsu.nmamp.student.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nmsu.nmamp.student.dao.UserDAO;
import edu.nmsu.nmamp.student.dao.impl.UserDAOImpl;
import edu.nmsu.nmamp.student.model.User;
import edu.nmsu.nmamp.student.service.PortalData;

@Controller
public class AdminHomeController {
	@Autowired
	private PortalData pd;
	
	@Autowired
	private UserDAO userDAO;
	
	private static final String HomePage = "/base/home";
	private static final String StudentListPage = "/student_manage/student-list";

	@GetMapping(value = { "home" })
	public String homepage() {
		System.out.println("I am in home page !!");
		return HomePage;
	}

	@GetMapping(value = { "home/SynPortalData" })
	@ResponseBody
	public String SynPortalData() {
		System.out.println("I am in SynPortalData in home page");
		pd.SynPortalStudenData();
		return "{\"text\":\"Finished the sync of student profile data\"}";
	}

	@GetMapping(value = { "home/SynPortalApplicationData" })
	@ResponseBody
	public String SynPortaApplicationlData() {
		pd.SynPortalApplicationData();
		return "{\"text\":\"Finished the sync of application data\"}";
	}

	@GetMapping(value = { "home/SynPortalMentorData" })
	@ResponseBody
	public String SynPortalMentorData() {
		pd.SynPortalMentorData();
		return "{\"text\":\"Finished the sync of mentor data\"}";
	}

	@GetMapping(value = { "home/SynPortalSelfReportData" })
	@ResponseBody
	public String SynPortalSelfReportData() {
		pd.SynPortalSelfReportData();
		return "{\"text\":\"Finished the sync of self report data\"}";
	}

	@GetMapping(value = { "home/student-all-list" })
	public String studentAllList(Principal principal) {
		User userDetails = userDAO.get(principal.getName()); 
		System.out.println("Student all list " + principal.getName()+" "+userDetails.getPassword()+" "+userDetails.getRole());
		return StudentListPage;
	}

}
