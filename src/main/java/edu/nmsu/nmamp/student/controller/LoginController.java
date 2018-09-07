package edu.nmsu.nmamp.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	private static final String WelcomeLoginPage = "/base/index";
	private static final String HomePage = "/base/home";

	@GetMapping(value = { "/", "/login" })
	public String login() {
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		return WelcomeLoginPage;
	}
	
	@RequestMapping(value="home/homepage", method=RequestMethod.GET)
	public String loginPage(Model model) {
		System.out.println("aaaaaaaaaaaaaaaaaaaa");
		return HomePage;
	}
	
	
//	@RequestMapping(value="/login", method=RequestMethod.GET)
//	public String loginPageTest(Model model) {
//		System.out.println("cccccccccccccccc");
//		return HomePage;
//	}
}