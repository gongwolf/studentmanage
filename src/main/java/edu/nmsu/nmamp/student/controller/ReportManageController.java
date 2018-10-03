package edu.nmsu.nmamp.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.nmsu.nmamp.student.model.ReportStudentSearchBean;

@Controller
public class ReportManageController {
	private static final String ReportrpStuNameLForm = "/report_manage/report-student-from-rpStuNameL";
	private static final String sampleChat = "/report_manage/charts";

	@PostMapping("report/report-student/selectdCat")
	public ModelAndView searchApplicantByPersonProgram(ModelMap model,
			@ModelAttribute("ReportStudentSearchBean") ReportStudentSearchBean searchBean,
			RedirectAttributes redirectAttributes) {
		String category = searchBean.getCategory();
		System.out.println(category+" "+ReportrpStuNameLForm);
		ModelAndView view = new ModelAndView();
		if (category.equals("rpStuNameL")) {

		}
		view.setViewName(ReportrpStuNameLForm);
		return view;
	}
	
	@PostMapping("report/report-student/show/{subCate}")
	public ModelAndView showDetail(ModelMap model,
			@PathVariable("subCate") String subCate,
			@ModelAttribute("ReportStudentSearchBean") ReportStudentSearchBean searchBean,
			RedirectAttributes redirectAttributes) {
		String category = searchBean.getCategory();
		System.out.println("---------- "+subCate+" "+sampleChat);
		ModelAndView view = new ModelAndView();
		view.setViewName(sampleChat);
		return view;
	}

}
