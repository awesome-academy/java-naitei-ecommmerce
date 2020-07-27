package vn.triplet.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	@RequestMapping("/admin/users")
	public String index(Model model) {
		return "views/admin/user/users";
	}

}