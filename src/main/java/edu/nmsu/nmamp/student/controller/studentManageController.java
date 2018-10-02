package edu.nmsu.nmamp.student.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bitbucket.lvncnt.utilities.Parse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.nmsu.nmamp.student.dao.UserDAO;
import edu.nmsu.nmamp.student.dao.impl.AdminDAOImpl;
import edu.nmsu.nmamp.student.dao.impl.YearlyDaoImpl;
import edu.nmsu.nmamp.student.model.ApplicationBean;
import edu.nmsu.nmamp.student.model.StudentSummaryBean;
import edu.nmsu.nmamp.student.model.User;
import edu.nmsu.nmamp.student.model.YearlyBean;
import edu.nmsu.nmamp.student.service.ProgramCode;

@Controller
public class studentManageController {
	@Autowired
	YearlyDaoImpl YearDao = new YearlyDaoImpl();
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private AdminDAOImpl adminDAO;
	
	@Autowired private ObjectMapper objectMapper; 
	private static final Logger logger = LoggerFactory.getLogger(AdminHomeController.class); 

	
	private static final String studentYearlyReport = "/student_manage/yearlyReport";
	private static final String studentProfile = "/student_manage/student-profile";
	private static final String studentPostAMPActs = "/student_manage/student-post-activities";
	private static final String StudentListPage = "/student_manage/student-list";

	
	
	@GetMapping(value = { "student/yearlyreport/{user_id}"})
	public String searchApplicantByPersonProgram(ModelMap model, @PathVariable("user_id") int user_id, Principal principal) {
//		User userDetails = userDAO.get(principal.getName()); 
//		String ic = "";
//		if(userDetails.getRole().toString().equals("ADMIN")){
//			ic="admin";
//		}
//		
//		List<Integer> YearList = YearDao.getSchoolYearList(ic, user_id);
//		
//		HashMap<Integer,YearlyBean> yearlybeans = new HashMap<>();
//		
//		for(int y:YearList)
//		{
//			System.out.println(y);
//			YearlyBean yb = YearDao.getYearlyBeanInfoByYear(y,user_id);
//			yearlybeans.put(y, yb);
//		}
//		
//		try {
//			model.addAttribute("yearlyBeans", objectMapper.writeValueAsString(yearlybeans));
//			System.out.println(objectMapper.writeValueAsString(yearlybeans));
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
		model.addAttribute("schools", ProgramCode.CURRENT_ACADEMIC_SCHOOL);
		System.out.println(studentYearlyReport);
		return studentYearlyReport;
	}
	
	@GetMapping(value = { "student/profile/{user_id}"})
	public String studentProfile(ModelMap model, @PathVariable("user_id") int user_id, Principal principal) {
		System.out.println(studentProfile+"!!");
		return studentProfile;
	}
	
	@GetMapping(value = { "student/postAMPActivities/{user_id}"})
	public String studentPostAMPACTs(ModelMap model, @PathVariable("user_id") int user_id, Principal principal) {
		System.out.println(studentPostAMPActs+"!!");
		return studentPostAMPActs;
	}
	
	
	@PostMapping("student/search-student/{criteria}")
	public ModelAndView searchApplicantByPersonProgram(ModelMap model,
			@PathVariable("criteria") String criteria, 
			@ModelAttribute("applicationBean") ApplicationBean applicationBean, 
			RedirectAttributes redirectAttributes){
		
		List<StudentSummaryBean> studentList = new ArrayList<>(); 
		
		if(criteria.equalsIgnoreCase("person")){
			String firstName = applicationBean.getFirstName();
			String lastName = applicationBean.getLastName();
			String email = applicationBean.getEmail();
			String birthDate = ""; 
			if(applicationBean.getBirthDate() != null){
				birthDate = Parse.FORMAT_DATE_MDY.format(applicationBean.getBirthDate());
			}
			studentList = adminDAO.getApplicantsByPerson(
					firstName, lastName, birthDate, email); 
		}else if(criteria.equalsIgnoreCase("program")){
			String program = applicationBean.getProgramNameAbbr();
			studentList = adminDAO.getApplicantsByProgram(program);
			System.out.println(program);
		}
		
		ModelAndView view = new ModelAndView();
		if (studentList.isEmpty()) {
			redirectAttributes.addFlashAttribute("status", "No result found."); 
			redirectAttributes.addFlashAttribute("applicationBean", applicationBean);
			view.setView(new RedirectView("/home/student-search", true));
		}else {
			view.addObject("studentList", studentList);
			view.setViewName(StudentListPage);
		}
		return view; 
	}

}
