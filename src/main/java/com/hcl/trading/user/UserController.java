package com.hcl.trading.user;

import com.hcl.trading.entity.User;
import com.hcl.trading.service.ConfirmationTokenService;
import com.hcl.trading.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * Created on September, 2019
 *
 * @author kamer
 */
@Controller
@AllArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;

	private final ConfirmationTokenService confirmationTokenService;

//	@GetMapping("/sign-in")
//	String signIn() {
//		log.info("inside signin get");
//		return "sign-in";
//	}

	@GetMapping("/sign-up")
	String signUpPage(User user) {
		return "sign-up";
	}

	@PostMapping("/sign-up")
	String signUp(User user) {
		userService.signUpUser(user);
		return "redirect:/home";
	}

	@GetMapping("/home")
	String home(HttpSession session, Principal principal) {
		log.info("inside home get" + principal.getName() + "**" + principal);
		User userDetails = userService.getUserDetails(principal.getName());
		userService.auditLogin(userDetails,session);
		return "home";
	}

//	@GetMapping(value="/logout")
//	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth != null){
//			new SecurityContextLogoutHandler().logout(request, response, auth);
//		}
//		log.info("inside logout");
//		return "redirect:/login?logout";
//	}

//	@PostMapping("/home")
//	String homePost(HttpSession session, Principal principal) {
//		log.info("inside home post" + principal.getName() + "**" + principal);
//
//		return "home";
//	}

//	@PostMapping("/sign-in-post")
//	String signInPost() {
//		log.info("inside signinpost");
//		return "home";
//	}

//	@PostMapping("/login-post")
//	public String validateLoginInfo(Model model, @Valid LoginForm loginForm, BindingResult bindingResult) {
//		log.info("inside validateLoginInfo");
//		if (bindingResult.hasErrors()) {
//			return "login";
//		}
//		model.addAttribute("user", loginForm.getuserName());
//		return "home";
//	}



//	@GetMapping("/sign-up/confirm")
//	String confirmMail(@RequestParam("token") String token) {
//
//		Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);
//
//		optionalConfirmationToken.ifPresent(userService::confirmUser);
//
//		return "redirect:/sign-in";
//	}

}
