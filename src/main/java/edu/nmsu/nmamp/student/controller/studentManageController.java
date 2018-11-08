package edu.nmsu.nmamp.student.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bitbucket.lvncnt.utilities.Parse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.nmsu.nmamp.student.dao.UserDAO;
import edu.nmsu.nmamp.student.dao.impl.AdminDAOImpl;
import edu.nmsu.nmamp.student.dao.impl.StudentDAOImpl;
import edu.nmsu.nmamp.student.dao.impl.YearlyDaoImpl;
import edu.nmsu.nmamp.student.model.ApplicationBean;
import edu.nmsu.nmamp.student.model.StudentPostActiveBean;
import edu.nmsu.nmamp.student.model.StudentProfileBean;
import edu.nmsu.nmamp.student.model.StudentSummaryBean;
import edu.nmsu.nmamp.student.model.StudentYearlyReportBean;
import edu.nmsu.nmamp.student.model.User;
import edu.nmsu.nmamp.student.model.YearlyBean;
import edu.nmsu.nmamp.student.service.ProgramCode;

@Controller
public class studentManageController {
	@Autowired
	YearlyDaoImpl YearDao;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private AdminDAOImpl adminDAO;

	@Autowired
	private StudentDAOImpl studentDAO;

	@Autowired
	private ObjectMapper objectMapper;
	private static final Logger logger = LoggerFactory.getLogger(AdminHomeController.class);

	private static final String studentYearlyReport = "/student_manage/yearlyReport";
	private static final String studentProfile = "/student_manage/student-profile";
	private static final String studentPostAMPActs = "/student_manage/student-post-activities";
	private static final String StudentListPage = "/student_manage/student-list";
	private static final String studentProfileForm = "/student_manage/student-profile-form";
	private static final String studentHighschoolForm = "/student_manage/student-highschool-form";
	private static final String studentYearlyReportForm = "/student_manage/student-yearlyreport-form";

	@GetMapping(value = { "student/yearlyreport/{student_id}" })
	public String studentYearlyReport(ModelMap model, @PathVariable("student_id") int student_id, Principal principal)
			throws ParseException, JsonProcessingException {
		User userDetails = userDAO.get(principal.getName());
		String ic = "";
		if (userDetails.getRole().toString().equals("ADMIN")) {
			ic = "admin";
		}

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		System.out.println(year + "  " + month);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(year + "-09-01");
		Date nowdate = new Date();

		String queryYear;
		if (date1.before(nowdate)) {
			c.add(Calendar.YEAR, -1);
			int byear = c.get(Calendar.YEAR);
			queryYear = "Fall " + byear + " - Summer " + year;
		} else {
			c.add(Calendar.YEAR, -1);
			year = c.get(Calendar.YEAR);
			c.add(Calendar.YEAR, -1);
			int byear = c.get(Calendar.YEAR);
			queryYear = "Fall " + byear + " - Summer " + year;
		}

		System.out.println(student_id + ":" + year + "  " + month + "     " + nowdate + "    " + date1.before(nowdate)
				+ "  " + queryYear);

		if (!model.containsAttribute("YearlyBean")) {
			StudentYearlyReportBean bean = YearDao.getYearBeanByUseIdAndYear(student_id, queryYear);
			if (bean == null) {
				StudentSummaryBean sbean = studentDAO.getStudentSummaryByStudentID(student_id);

				bean = new StudentYearlyReportBean(student_id, sbean.getFirst_name(), sbean.getMiddle_name(),
						sbean.getLast_name());
			}
			model.addAttribute("YearlyBean", bean);

			if (bean.getActivities_list() != null) {
				model.addAttribute("activities_list", bean.getActivities_list());
			} else {
				model.addAttribute("activities_list", "null");
			}
		}

		model.addAttribute("id", student_id);
		model.addAttribute("schools", ProgramCode.ACADEMIC_SCHOOL);
		model.addAttribute("schools_level", objectMapper.writeValueAsString(ProgramCode.SCHOOL_LEVEL));
		model.addAttribute("queryYear", queryYear);
		model.addAttribute("yesno", ProgramCode.YES_NO);
		model.addAttribute("gradu_semester", ProgramCode.Graduated_Semeter);
		model.addAttribute("fin_amp_sup_type", ProgramCode.Finacial_AMP_Type);
		model.addAttribute("college_acts", ProgramCode.COLLEGE_ACTIVITIES);
		model.addAttribute("university_acts", ProgramCode.UNIVERSITY_ACTIVITIES);

		// System.out.println(studentYearlyReport);
		return studentYearlyReport;
	}

	@PostMapping("student/yearlyreport/update/{student_id}")
	public RedirectView studentYearlyReportUpdate(ModelMap model, @PathVariable("student_id") int student_id,
			StudentYearlyReportBean bean, Principal principal, HttpServletRequest request) {
		System.out.println("=================  update yearly report:" + student_id + "=================");
		String activitiesList = request.getParameter("activitiesList");
		String queryYear = request.getParameter("queryYear");
		String intern_json = request.getParameter("internList");
		// System.out.println(activitiesList);
		// System.out.println(queryYear);
		bean.setIntern_json(intern_json);
		bean.setConference_json(request.getParameter("confsList"));
		bean.setPublication_json(request.getParameter("publicationList"));
		bean.setVolunteer_json(request.getParameter("volunteerList"));
		bean.setTravel_json(request.getParameter("travelList"));
		// System.out.println(bean.getVolunteer_json());
		// System.out.println(bean.getTravel_json());
		YearDao.UpdateYearBeanByUseIdAndYear(bean, activitiesList, student_id, queryYear);
		System.out.println("=========================================================");

		return new RedirectView("/studentmanage/student/yearlyreport/" + student_id);

	}

	@RequestMapping(value = { "student/profile/{user_id}" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String studentProfile(ModelMap model, @PathVariable("user_id") int user_id, Principal principal) {
		if (!model.containsAttribute("studentBean")) {
			StudentProfileBean bean = studentDAO.getStudentProfileByStudentID(user_id);
			model.addAttribute("studentBean", bean);
		}
		model.addAttribute("gender", ProgramCode.GENDER);
		model.addAttribute("state", ProgramCode.STATE_CODE);
		model.addAttribute("yesno", ProgramCode.YES_NO);
		model.addAttribute("familyincome", ProgramCode.FAMILY_INCOME);
		model.addAttribute("race", ProgramCode.CHECKBOX_RACE);
		model.addAttribute("disability", ProgramCode.DISABILITY_STATUS);
		model.addAttribute("dis_type", ProgramCode.DISABILITY_TYPE);
		model.addAttribute("hs_testing", ProgramCode.HIGH_SCHOOL_TESTINGS);
		model.addAttribute("id", user_id);
		return studentProfile;
	}

	@GetMapping(value = { "student/postAMPActivities/{student_id}" })
	public String studentPostAMPACTs(ModelMap model, @PathVariable("student_id") int student_id, Principal principal) {
		if (!model.containsAttribute("studentPostBean")) {
			StudentPostActiveBean bean = studentDAO.getPostActiveByStudentID(student_id);
			if (bean == null) {
				StudentSummaryBean sbean = studentDAO.getStudentSummaryByStudentID(student_id);
				String fname = sbean.getFirst_name() + " ";
				String lname = sbean.getMiddle_name() == null ? sbean.getLast_name()
						: sbean.getMiddle_name() + " " + sbean.getLast_name();
				bean = new StudentPostActiveBean(student_id, fname + lname);
			}
			model.addAttribute("studentPostBean", bean);
		}
		model.addAttribute("state", ProgramCode.STATE_CODE);
		model.addAttribute("id", student_id);
		return studentPostAMPActs;
	}

	@PostMapping(value = { "student/postAMPActivities/update/{student_id}" })
	public RedirectView studentUpdatePostAMPACTs(ModelMap model, @PathVariable("student_id") int student_id,
			Principal principal, StudentPostActiveBean bean) {
		studentDAO.updateStudentPostActiveByStudentID(student_id, bean);
		return new RedirectView("/studentmanage/student/postAMPActivities/" + student_id);
	}

	@PostMapping("student/search-student/{criteria}")
	public ModelAndView searchApplicantByPersonProgram(ModelMap model, @PathVariable("criteria") String criteria,
			@ModelAttribute("applicationBean") ApplicationBean applicationBean, RedirectAttributes redirectAttributes) {

		List<StudentSummaryBean> studentList = new ArrayList<>();

		if (criteria.equalsIgnoreCase("person")) {
			String firstName = applicationBean.getFirstName();
			String lastName = applicationBean.getLastName();
			String email = applicationBean.getEmail();
			String birthDate = "";
			if (applicationBean.getBirthDate() != null) {
				birthDate = Parse.FORMAT_DATE_MDY.format(applicationBean.getBirthDate());
			}
			studentList = adminDAO.getApplicantsByPerson(firstName, lastName, birthDate, email);
		} else if (criteria.equalsIgnoreCase("program")) {
			String program = applicationBean.getProgramNameAbbr();
			studentList = adminDAO.getApplicantsByProgram(program);
			System.out.println(program);
		}

		ModelAndView view = new ModelAndView();
		if (studentList.isEmpty()) {
			redirectAttributes.addFlashAttribute("status", "No result found.");
			redirectAttributes.addFlashAttribute("applicationBean", applicationBean);
			view.setView(new RedirectView("/home/student-search", true));
		} else {
			view.addObject("studentList", studentList);
			view.setViewName(StudentListPage);
		}
		return view;
	}

	@PostMapping("student/profile/update/{student_id}")
	public RedirectView studentProfileUpdate(ModelMap model, @PathVariable("student_id") int student_id,
			StudentProfileBean bean, Principal principal, HttpServletRequest request) {
		System.out.println("=================  update:" + student_id + "=================");
		System.out.println(bean);
		model.addAttribute("studentBean", bean);
		System.out.println(request.getParameter("activitiesList"));
		String activitiesList = request.getParameter("activitiesList");
		studentDAO.updateStudentProfile(bean, activitiesList, student_id);
		System.out.println(bean);
		System.out.println("=========================================================");

		return new RedirectView("/studentmanage/student/profile/" + student_id);

	}

	@ResponseBody
	@PostMapping("student/profile/recommendationFileUpload/{student_id}")
	public ResponseEntity<Object> studentProfilerecommendationFileUpload(ModelMap model,
			@PathVariable("student_id") int student_id, @RequestParam("file") MultipartFile file, Principal principal) {
		String result = "";
		System.out.println("=================  update:" + student_id + "=================");
		if (!file.getOriginalFilename().isEmpty()) {
			studentDAO.uploadRecommendationFile(file, student_id);
			System.out.println(file.getOriginalFilename());
			System.out.println(file.getSize());
			System.out.println(file.getContentType());
			result = "uploaded successed";
		} else {
			result = "there is no uploaded file ";
		}
		System.out.println(result);
		System.out.println("=========================================================");

		return new ResponseEntity<>("File Uploaded Successfully.", HttpStatus.OK);
	}

	@GetMapping("student/profile/downloadRecommendationFile/{student_id}")
	public void downloadRecommendationFile(@PathVariable("student_id") int student_id, HttpServletResponse response)
			throws IOException {
		StudentSummaryBean ssb = studentDAO.getStudentSummaryByStudentID(student_id);
		String fileName = String.format("%s_%s_%s", ssb.getFirst_name(), ssb.getLast_name(),
				"recommendation_letter.pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName.toLowerCase() + "\"");
		response.setContentType("application/pdf");
		byte[] content = studentDAO.downloadRecommendationFile(student_id);
		FileCopyUtils.copy(content, response.getOutputStream());
	}

	@ResponseBody
	@PostMapping("student/profile/transcriptFileUpload/{student_id}")
	public ResponseEntity<Object> studentProfiletranscriptFileUpload(ModelMap model,
			@PathVariable("student_id") int student_id, @RequestParam("file") MultipartFile file, Principal principal) {
		String result = "";
		System.out.println("=================  update:" + student_id + "=================");
		if (!file.getOriginalFilename().isEmpty()) {
			studentDAO.uploadTranscriptFile(file, student_id);
			System.out.println(file.getOriginalFilename());
			System.out.println(file.getSize());
			System.out.println(file.getContentType());
			result = "uploaded successed";
		} else {
			result = "there is no uploaded file ";
		}
		System.out.println(result);
		System.out.println("=========================================================");

		return new ResponseEntity<>("File Uploaded Successfully.", HttpStatus.OK);
	}

	@GetMapping("student/profile/downloadTranscriptFile/{student_id}")
	public void downloadTranscriptFile(@PathVariable("student_id") int student_id, HttpServletResponse response)
			throws IOException {
		StudentSummaryBean ssb = studentDAO.getStudentSummaryByStudentID(student_id);
		String fileName = String.format("%s_%s_%s", ssb.getFirst_name(), ssb.getLast_name(), "transcript.pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName.toLowerCase() + "\"");
		response.setContentType("application/pdf");
		byte[] content = studentDAO.downloadTranscript(student_id);
		FileCopyUtils.copy(content, response.getOutputStream());
	}

	@RequestMapping(value = { "student/{user_id}/profile" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String studentProfileForm(ModelMap model, @PathVariable("user_id") int user_id, Principal principal) {
		if (!model.containsAttribute("studentBean")) {
			StudentProfileBean bean = studentDAO.getStudentProfileByStudentID(user_id);
			model.addAttribute("studentBean", bean);
		}
		model.addAttribute("gender", ProgramCode.GENDER);
		model.addAttribute("state", ProgramCode.STATE_CODE);
		model.addAttribute("yesno", ProgramCode.YES_NO);
		model.addAttribute("familyincome", ProgramCode.FAMILY_INCOME);
		model.addAttribute("race", ProgramCode.CHECKBOX_RACE);
		model.addAttribute("disability", ProgramCode.DISABILITY_STATUS);
		model.addAttribute("dis_type", ProgramCode.DISABILITY_TYPE);
		model.addAttribute("hs_testing", ProgramCode.HIGH_SCHOOL_TESTINGS);
		model.addAttribute("ethnicity_list", ProgramCode.Ethnicity_List);
		model.addAttribute("lang_at_home", ProgramCode.LANGUAGE_AT_HOME);
		model.addAttribute("id", user_id);
		return studentProfileForm;
	}

	@PostMapping("student/{student_id}/profile/update")
	public RedirectView studentProfileFormUpdate(ModelMap model, @PathVariable("student_id") int student_id,
			StudentProfileBean bean, Principal principal, HttpServletRequest request) {
		System.out.println("=================  update:" + student_id + "=================");
		System.out.println(bean);
		model.addAttribute("studentBean", bean);
		// System.out.println(request.getParameter("activitiesList"));
		// String activitiesList = request.getParameter("activitiesList");
		studentDAO.updateStudentProfileForm(bean, student_id);
		System.out.println(bean);
		System.out.println("=========================================================");

		return new RedirectView("/studentmanage/student/" + student_id + "/profile/");
	}

	@RequestMapping(value = { "student/{user_id}/highschool" }, method = { RequestMethod.GET })
	public String studentHighschoolForm(ModelMap model, @PathVariable("user_id") int user_id, Principal principal) {
		if (!model.containsAttribute("studentBean")) {
			StudentProfileBean bean = studentDAO.getStudentProfileByStudentID(user_id);
			model.addAttribute("studentBean", bean);
		}
		model.addAttribute("gender", ProgramCode.GENDER);
		model.addAttribute("state", ProgramCode.STATE_CODE);
		model.addAttribute("yesno", ProgramCode.YES_NO);
		model.addAttribute("familyincome", ProgramCode.FAMILY_INCOME);
		model.addAttribute("race", ProgramCode.CHECKBOX_RACE);
		model.addAttribute("disability", ProgramCode.DISABILITY_STATUS);
		model.addAttribute("dis_type", ProgramCode.DISABILITY_TYPE);
		model.addAttribute("hs_testing", ProgramCode.HIGH_SCHOOL_TESTINGS);
		model.addAttribute("ethnicity_list", ProgramCode.Ethnicity_List);
		model.addAttribute("lang_at_home", ProgramCode.LANGUAGE_AT_HOME);
		model.addAttribute("id", user_id);
		return studentHighschoolForm;
	}

	@PostMapping("student/{student_id}/highschool/update")
	public RedirectView studentHighschoolFormUpdate(ModelMap model, @PathVariable("student_id") int student_id,
			StudentProfileBean bean, Principal principal, HttpServletRequest request) {
		System.out.println("=================  update:" + student_id + "=================");
		model.addAttribute("studentBean", bean);
		System.out.println(bean.getHigh_school_testing());
		String total_score = request.getParameter("total_score");
		String reading_score = request.getParameter("reading_score");
		String writing_score = request.getParameter("writing_score");
		String sci_score = request.getParameter("sci_score");
		String math_score = request.getParameter("math_score");
		System.out.println("score:   " + total_score + " " + reading_score + " " + writing_score + " " + sci_score + " "
				+ math_score);
		if (bean.getHigh_school_testing().equals("ACT")) {
			bean.setHigh_school_act_total_score(total_score);
			bean.setHigh_school_act_reading_score(reading_score);
			bean.setHigh_school_act_math_score(math_score);
			bean.setHigh_school_act_sci_score(sci_score);
			bean.setHigh_school_act_writing_score(writing_score);
		} else if (bean.getHigh_school_testing().equals("SAT")) {
			bean.setHigh_school_sat_total_score(total_score);
			bean.setHigh_school_sat_reading_score(reading_score);
			bean.setHigh_school_sat_math_score(math_score);
			bean.setHigh_school_sat_sci_score(sci_score);
			bean.setHigh_school_sat_writing_score(writing_score);
		} else {
			bean.setHigh_school_sat_total_score("");
			bean.setHigh_school_sat_reading_score("");
			bean.setHigh_school_sat_math_score("");
			bean.setHigh_school_sat_sci_score("");
			bean.setHigh_school_sat_writing_score("");
			bean.setHigh_school_act_total_score("");
			bean.setHigh_school_act_reading_score("");
			bean.setHigh_school_act_math_score("");
			bean.setHigh_school_act_sci_score("");
			bean.setHigh_school_act_writing_score("");
		}
		String activitiesList = request.getParameter("activitiesList");
		bean.setHighschool_activities(activitiesList);
		studentDAO.updateStudentHighSchoolForm(bean, student_id);

		System.out.println("=========================================================");

		return new RedirectView("/studentmanage/student/" + student_id + "/highschool/");
	}

	@RequestMapping(value = { "student/{student_id}/yearlyreport" }, method = { RequestMethod.GET })
	public String studentYearlyReportForm(ModelMap model, @PathVariable("student_id") int student_id,
			Principal principal) throws ParseException, JsonProcessingException {
		User userDetails = userDAO.get(principal.getName());
		String ic = "";
		if (userDetails.getRole().toString().equals("ADMIN")) {
			ic = "admin";
		}

		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		System.out.println(year + "  " + month);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(year + "-09-01");
		Date nowdate = new Date();

		String queryYear;
		if (date1.before(nowdate)) {
			c.add(Calendar.YEAR, -1);
			int byear = c.get(Calendar.YEAR);
			queryYear = "Fall " + byear + " - Summer " + year;
		} else {
			c.add(Calendar.YEAR, -1);
			year = c.get(Calendar.YEAR);
			c.add(Calendar.YEAR, -1);
			int byear = c.get(Calendar.YEAR);
			queryYear = "Fall " + byear + " - Summer " + year;
		}

		System.out.println(student_id + ":" + year + "  " + month + "     " + nowdate + "    " + date1.before(nowdate)
				+ "  " + queryYear);

		ArrayList<String> queryyear_list = new ArrayList<>();
		queryyear_list.add(queryYear);
		for (int i = 1; i <= 6; i++) {
			Calendar c_tmp = Calendar.getInstance();
			c_tmp.set(Calendar.YEAR, year);
			c_tmp.add(Calendar.YEAR, -1);
			year = c_tmp.get(Calendar.YEAR);
			c_tmp.add(Calendar.YEAR, -1);
			int byear = c_tmp.get(Calendar.YEAR);
			String t_queryyear = "Fall " + byear + " - Summer " + year;
			queryyear_list.add(t_queryyear);
		}

		if (!model.containsAttribute("YearlyBean")) {
			StudentYearlyReportBean bean = YearDao.getYearBeanByUseIdAndYear(student_id, queryYear);
			if (bean == null) {
				StudentSummaryBean sbean = studentDAO.getStudentSummaryByStudentID(student_id);

				bean = new StudentYearlyReportBean(student_id, sbean.getFirst_name(), sbean.getMiddle_name(),
						sbean.getLast_name());
			}
			model.addAttribute("YearlyBean", bean);

			if (bean.getActivities_list() != null) {
				model.addAttribute("activities_list", bean.getActivities_list());
			} else {
				model.addAttribute("activities_list", "null");
			}
		}

		model.addAttribute("id", student_id);
		model.addAttribute("schools", ProgramCode.ACADEMIC_SCHOOL);
		model.addAttribute("schools_level", objectMapper.writeValueAsString(ProgramCode.SCHOOL_LEVEL));
		model.addAttribute("queryYear", queryYear);
		model.addAttribute("yesno", ProgramCode.YES_NO);
		model.addAttribute("gradu_semester", ProgramCode.Graduated_Semeter);
		model.addAttribute("fin_amp_sup_type", ProgramCode.Finacial_AMP_Type);
		model.addAttribute("college_acts", ProgramCode.COLLEGE_ACTIVITIES);
		model.addAttribute("university_acts", ProgramCode.UNIVERSITY_ACTIVITIES);
		model.addAttribute("yearlist", queryyear_list.toArray(new String[queryyear_list.size()]));
		model.addAttribute("college_list", ProgramCode.ACADEMIC_YEAR);
		model.addAttribute("discipline_list", ProgramCode.Discipline_List);

		// System.out.println(studentYearlyReport);
		return studentYearlyReportForm;
	}

	@PostMapping("student/{student_id}/yearlyreport/update")
	public RedirectView studentYearlyReportFormtUpdate(ModelMap model, @PathVariable("student_id") int student_id,
			StudentYearlyReportBean bean, Principal principal, HttpServletRequest request) {
		System.out.println("=================  update yearly report:" + student_id + "=================");
		String activitiesList = request.getParameter("activitiesList");
		String queryYear = request.getParameter("queryYear");
		String intern_json = request.getParameter("internList");
		// System.out.println(activitiesList);
		// System.out.println(queryYear);
		bean.setIntern_json(intern_json);
		bean.setConference_json(request.getParameter("confsList"));
		bean.setPublication_json(request.getParameter("publicationList"));
		bean.setVolunteer_json(request.getParameter("volunteerList"));
		bean.setTravel_json(request.getParameter("travelList"));
		bean.setCourse_taken(request.getParameter("coursetakenjson"));
		// System.out.println(bean.getVolunteer_json());
		// System.out.println(bean.getTravel_json());
		YearDao.UpdateYearBeanAcdemicByUseIdAndYear(bean, activitiesList, student_id, queryYear);
		System.out.println("=========================================================");

		return new RedirectView("/studentmanage/student/" + student_id+"/yearlyreport");

	}

}
