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
import edu.nmsu.nmamp.student.dao.impl.MentorDAOImpl;
import edu.nmsu.nmamp.student.dao.impl.YearlyDaoImpl;
import edu.nmsu.nmamp.student.model.ApplicationBean;
import edu.nmsu.nmamp.student.model.MentorBean;
import edu.nmsu.nmamp.student.model.MentorSearchBean;
import edu.nmsu.nmamp.student.model.MentorSummaryBean;
import edu.nmsu.nmamp.student.model.StudentPostActiveBean;
import edu.nmsu.nmamp.student.model.StudentSummaryBean;
import edu.nmsu.nmamp.student.model.User;
import edu.nmsu.nmamp.student.model.YearlyBean;
import edu.nmsu.nmamp.student.service.ProgramCode;

@Controller
public class MentorManageController {
	@Autowired
	YearlyDaoImpl YearDao = new YearlyDaoImpl();
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AdminDAOImpl adminDAO;
	
	@Autowired
	private MentorDAOImpl mentorDAO;

	@Autowired private ObjectMapper objectMapper; 
	private static final Logger logger = LoggerFactory.getLogger(AdminHomeController.class); 

	
	private static final String MentorProfile = "/mentor_manage/mentor-profile";
	private static final String MentorListPage = "/mentor_manage/mentor-list";
	private static final String StudentListPage = "/student_manage/student-list";


	@GetMapping(value = { "mentor/profile/{mentor_id}"})
	public String MentorProfile(ModelMap model, @PathVariable("mentor_id") int mentor_id, Principal principal) {
		if (!model.containsAttribute("MentorBean")) {
			MentorBean bean = mentorDAO.getMentorInformationByMentorID(mentor_id);
			if (bean == null) {
				bean = new MentorBean();
			}
			model.addAttribute("MentorBean", bean);
		}
		model.addAttribute("state", ProgramCode.STATE_CODE);
		model.addAttribute("id", mentor_id);
		model.addAttribute("schools", ProgramCode.CURRENT_ACADEMIC_SCHOOL);
		model.addAttribute("prefix",ProgramCode.MENTOR_PREFIX);
		return MentorProfile;
	}
	
	@PostMapping(value = { "mentor/profile/update/{mentor_id}"})
	public RedirectView UpdateMentorProfile(ModelMap model, @PathVariable("mentor_id") int mentor_id, Principal principal,MentorBean bean) {
		mentorDAO.updateMentorInformationByMentorID(mentor_id,bean);
		return new RedirectView("/studentmanage/mentor/profile/" + mentor_id);
	}
	
	
	@PostMapping("mentor/search-mentor/{criteria}")
	public ModelAndView searchApplicantByPersonProgram(ModelMap model,
			@PathVariable("criteria") String criteria, 
			@ModelAttribute("applicationBean") MentorSearchBean searchBean, 
			RedirectAttributes redirectAttributes){
		
		List<MentorSummaryBean> MentorList = new ArrayList<>(); 
		
		if(criteria.equalsIgnoreCase("person")){
			String firstName = searchBean.getFirstName();
			String lastName = searchBean.getLastName();
			String email = searchBean.getEmail();
			String intitutionAbbr=searchBean.getIntitutionAbbr();
			String department=searchBean.getDepartment();

			MentorList = adminDAO.getMentorsByPerson(firstName, lastName, email,intitutionAbbr,department); 
		}
		
		ModelAndView view = new ModelAndView();
//		if (MentorList.isEmpty()) {
//			redirectAttributes.addFlashAttribute("status", "No result found."); 
//			redirectAttributes.addFlashAttribute("MentorSearchBean", searchBean);
//			view.setView(new RedirectView("/home/mentor-search", true));
//		}else {
			view.addObject("MentorList", MentorList);
			view.setViewName(MentorListPage);
//		}
		return view; 
	}
	
	@GetMapping(value = { "mentor/student-list/{mentor_id}" })
	public String studentAllList(ModelMap model, Principal principal,@PathVariable("mentor_id") int mentor_id) {
		User userDetails = userDAO.get(principal.getName());
		// System.out.println("Student all list " + principal.getName()+"
		// "+userDetails.getPassword()+" "+userDetails.getRole());

		String ic = "";
		if (userDetails.getRole().toString().equals("ADMIN")) {
			ic = "admin";
		}

		String condition = "all";

		List<StudentSummaryBean> studentList = mentorDAO.getStudentListSummaryFollowMentor(ic, condition,mentor_id);

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


}
