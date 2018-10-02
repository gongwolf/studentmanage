package edu.nmsu.nmamp.student.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nmsu.nmamp.student.dao.UserDAO;
import edu.nmsu.nmamp.student.dao.impl.AdminDAOImpl;
import edu.nmsu.nmamp.student.dao.impl.UserDAOImpl;
import edu.nmsu.nmamp.student.model.ApplicationBean;
import edu.nmsu.nmamp.student.model.MentorSearchBean;
import edu.nmsu.nmamp.student.model.MentorSummaryBean;
import edu.nmsu.nmamp.student.model.StudentSummaryBean;
import edu.nmsu.nmamp.student.model.User;
import edu.nmsu.nmamp.student.service.PortalData;
import edu.nmsu.nmamp.student.service.ProgramCode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AdminHomeController {
	@Autowired
	private PortalData pd;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private AdminDAOImpl adminDAO;
	@Autowired
	private ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger(AdminHomeController.class);

	private static final String HomePage = "/base/home";
	private static final String StudentListPage = "/student_manage/student-list";
	private static final String StudentSearchPage = "/student_manage/student-search";
	private static final String MentorListPage = "/mentor_manage/mentor-list";
	private static final String MentorSearchPage = "/mentor_manage/mentor-search";

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
	public String studentAllList(ModelMap model, Principal principal) {
		User userDetails = userDAO.get(principal.getName());
		// System.out.println("Student all list " + principal.getName()+"
		// "+userDetails.getPassword()+" "+userDetails.getRole());

		String ic = "";
		if (userDetails.getRole().toString().equals("ADMIN")) {
			ic = "admin";
		}

		String condition = "all";

		List<StudentSummaryBean> studentList = adminDAO.getStudentListSummary(ic, condition);

		// for(StudentSummaryBean sb:studentList)
		// {
		// System.out.println(sb);
		// }
		try {
			model.addAttribute("studentList", objectMapper.writeValueAsString(studentList));
			System.out.println(objectMapper.writeValueAsString(studentList));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}

		return StudentListPage;
	}

	@GetMapping(value = { "home/mentor-all-list" })
	public String MentorAllList(ModelMap model, Principal principal) {
		User userDetails = userDAO.get(principal.getName());
		// System.out.println("Student all list " + principal.getName()+"
		// "+userDetails.getPassword()+" "+userDetails.getRole());

		String ic = "";
		if (userDetails.getRole().toString().equals("ADMIN")) {
			ic = "admin";
		}

		String condition = "all";

		List<MentorSummaryBean> mentorList = adminDAO.getMentortListSummary(ic, condition);

		// for(StudentSummaryBean sb:studentList)
		// {
		// System.out.println(sb);
		// }
		try {
			model.addAttribute("MentorList", objectMapper.writeValueAsString(mentorList));
			System.out.println(objectMapper.writeValueAsString(mentorList));
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}

		return MentorListPage;
	}

	@GetMapping(value = { "home/student-search" })
	public String studentsearch(ModelMap model, Principal principal) {
		model.addAttribute("programs", ProgramCode.PROGRAMS);
		if (!model.containsAttribute("applicationBean")) {
			model.addAttribute("applicationBean", new ApplicationBean());
		}
		return StudentSearchPage;
	}
	
	@GetMapping(value = { "home/mentor-search" })
	public String mentorsearch(ModelMap model, Principal principal) {
		model.addAttribute("schools", ProgramCode.CURRENT_ACADEMIC_SCHOOL);
		if (!model.containsAttribute("mentorSearchBean")) {
			model.addAttribute("MentorSearchBean", new MentorSearchBean());
		}
		return MentorSearchPage;
	}

	@GetMapping(value = { "home/mentor-all-list-json" })
	@ResponseBody
	public String MentorAllListJson(ModelMap model, Principal principal) {
		User userDetails = userDAO.get(principal.getName());
		// System.out.println("Student all list " + principal.getName()+"
		// "+userDetails.getPassword()+" "+userDetails.getRole());

		String ic = "";
		if (userDetails.getRole().toString().equals("ADMIN")) {
			ic = "admin";
		}

		String condition = "all";

		List<MentorSummaryBean> mentorList = adminDAO.getMentortListSummary(ic, condition);

		// for(StudentSummaryBean sb:studentList)
		// {
		// System.out.println(sb);
		// }
		String result = "";
		try {
			model.addAttribute("MentorList", objectMapper.writeValueAsString(mentorList));
			System.out.println(objectMapper.writeValueAsString(mentorList));
			result = objectMapper.writeValueAsString(mentorList);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}

		return result;
	}

}
