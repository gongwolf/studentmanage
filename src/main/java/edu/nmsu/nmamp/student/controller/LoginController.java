package edu.nmsu.nmamp.student.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	private static final String WelcomeLoginPage = "/base/index";
	private static final String HomePage = "/base/home";

	@GetMapping(value = { "/", "/welcome" })
	public String login() {
		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		return WelcomeLoginPage;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginPage(BindingResult errors, Model model) {
		System.out.println("aaaaaaaaaaaaaaaaaaaa");
		return HomePage;
	}
}