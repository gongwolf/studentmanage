package edu.nmsu.nmamp.student.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import edu.nmsu.nmamp.student.dao.UserDAO;
import edu.nmsu.nmamp.student.dao.impl.YearlyDaoImpl;
import edu.nmsu.nmamp.student.model.User;
import edu.nmsu.nmamp.student.model.YearlyBean;

@Controller
public class studentManageController {
	@Autowired
	YearlyDaoImpl YearDao = new YearlyDaoImpl();
	@Autowired
	private UserDAO userDAO;
	@GetMapping(value = { "student/yearlyreport/{user_id}"})
	
	public ModelAndView searchApplicantByPersonProgram(ModelMap model, @PathVariable("user_id") int user_id, Principal principal) {
		User userDetails = userDAO.get(principal.getName()); 
		String ic = "";
		if(userDetails.getRole().toString().equals("ADMIN")){
			ic="admin";
		}
		
		List<Integer> YearList = YearDao.getSchoolYearList(ic, user_id);
		
		for(int y:YearList)
		{
			System.out.println(y);
			YearlyBean yb = YearDao.getYearlyBeanInfoByYear(y,user_id);
		}
		
		System.out.println(user_id);
		return null;
	}

}
