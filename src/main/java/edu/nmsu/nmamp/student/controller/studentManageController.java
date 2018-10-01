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
public class studentManageController {
	@Autowired
	YearlyDaoImpl YearDao = new YearlyDaoImpl();
	@Autowired
	private UserDAO userDAO;
	
	@Autowired private ObjectMapper objectMapper; 
	private static final Logger logger = LoggerFactory.getLogger(AdminHomeController.class); 

	
	private static final String studentYearlyReport = "/student_manage/yearlyReport";
	private static final String studentProfile = "/student_manage/student-profile";

	
	
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

}
