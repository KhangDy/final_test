package sop.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import sop.model.User;
import sop.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginfrm() {
		return "auth/login";
	}

	@PostMapping("/chklogin")
	public String checkLogin(@RequestParam("uid") String username, 
	                         @RequestParam("pwd") String password, 
	                         HttpServletRequest request) {
	    User user = userService.findFirstByUid(username);

	    if (user != null) {
	        // Kiểm tra mật khẩu nhập vào với mật khẩu đã mã hóa trong cơ sở dữ liệu
	        if (passwordEncoder.matches(password, user.getPwd())) {
	            HttpSession session = request.getSession();
	            session.setAttribute("user", user);

	            logger.info("User authenticated: " + username + ", UserType: " + user.getUsrType());

	            if (user.getUsrType() == 1) {
	                return "redirect:/admin/dashboard";
	            } else if (user.getUsrType() == 0) {
	                return "redirect:/user/profile";
	            }
	        } else {
	            logger.error("Password check failed for user: " + username);
	            return "redirect:/auth/login?error=true";
	        }
	    } else {
	        logger.error("User not found: " + username);
	        return "redirect:/auth/login?error=true";
	    }

	    return "redirect:/auth/login";
	}

	@GetMapping("/register")
	public String showRegisterForm() {
		logger.info("Accessing /auth/register");
		return "auth/register"; // Trả về trang đăng ký
	}

	@PostMapping("/register")
	public String registerUser(@RequestParam("name") String name, @RequestParam("uid") String username,
			@RequestParam("email") String email, @RequestParam("pwd") String password,
			@RequestParam("phone") String phone, @RequestParam("address") String address, HttpServletRequest request) {

		String encodedPassword = passwordEncoder.encode(password);

		// Tạo đối tượng User mới
		User newUser = new User();
		newUser.setName(name);
		newUser.setUid(username);
		newUser.setEmail(email);
		newUser.setPwd(encodedPassword);
		newUser.setPhone(phone);
		newUser.setAddress(address);

		// Set userType là 0 (Admin)
		newUser.setUsrType(0); // Gán mặc định là Admin (hoặc giá trị khác)

		// Set thời gian tạo và trạng thái
		newUser.setCreatedAt(LocalDateTime.now());
		newUser.setStatus(1); // Kích hoạt tài khoản

		// Lưu user mới
		userService.saveUser(newUser);

		return "redirect:/home/index";
	}

	@GetMapping("/user/profile")
	public String userProfile() {
		return "user/profile"; // Trả về view user/profile.html
	}

	@GetMapping("/admin/dashboard")
	public String adminDashboard() {
		return "admin/dashboard"; // Trả về view admin/dashboard.html
	}
}
