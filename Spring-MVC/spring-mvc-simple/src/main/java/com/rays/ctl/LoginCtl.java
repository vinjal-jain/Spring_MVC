package com.rays.ctl;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rays.dto.UserDTO;
import com.rays.form.LoginForm;
import com.rays.service.UserService;

@Controller
@RequestMapping(value="Login")
public class LoginCtl {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public String display(@ModelAttribute("form") LoginForm form ,Model model,@RequestParam (required=false) String operation, HttpSession session ){
		
		if(operation !=null && operation.equalsIgnoreCase("logout")) {
			session.invalidate();
			model.addAttribute("success", "User Logout Successfully"); 
			return "LoginView";
		}
		return "LoginView";
	}
	
	@PostMapping
	public String submit(@ModelAttribute("form")@Valid LoginForm form, BindingResult bindingResult,@RequestParam(required=false) String  operation, HttpSession session,Model model ) {
		
		if(operation.equalsIgnoreCase("signUp")){
		
			return "redirect:Register";
		}
		
		if(bindingResult.hasErrors()) {
			
			return "LoginView";
			
		}
		
		UserDTO dto = service.authenticate(form.getLogin(),form.getPassword());
		
		if(dto!=null) {
			session.setAttribute("user", dto);
			return "redirect:Welcome";
		}
		else {
			model.addAttribute("error", "Login-Id and Password is invalid...!!!!");
		}
		
		return "LoginView";
		
	}
	

}