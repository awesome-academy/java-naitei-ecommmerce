package vn.triplet.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value = "userControllerOfWeb")
public class UserController {
	@RequestMapping("/profile")
	public String redering(Model model) {
		return "views/web/profile";
	}

}
