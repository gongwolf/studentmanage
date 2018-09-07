package edu.nmsu.nmamp.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nmsu.nmamp.student.service.PortalData;

@Controller
public class AdminHomeController {
	private static final String HomePage = "/base/home";
	
	@GetMapping(value = {"home"})
	public String homepage() {
		System.out.println("I am in home page !!");
		return HomePage;
	}
	
	
	@GetMapping(value = { "home/SynPortalData"})
	@ResponseBody
	public String SynPortalData() {
		System.out.println("I am in SynPortalData in home page");
		PortalData pd = new PortalData();
		pd.SynPortalStudenData();
		return "{\"text\":\"Thanks For Posting\"}";
	}


}
