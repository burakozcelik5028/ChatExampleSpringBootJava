package net.javaguides.springboot.api;

import java.awt.PageAttributes.MediaType;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.javaguides.springboot.entity.ActivityLogEntity;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.enums.ActivityType;
import net.javaguides.springboot.repository.ActivityLogEntityRepo;
import net.javaguides.springboot.service.UserServiceInterface;

@Controller
@CrossOrigin
public class UserController {

	private final UserServiceInterface userServiceInterface;
	private final ActivityLogEntityRepo activityLogEntityRepo;
	
	@Autowired
	public UserController(UserServiceInterface userServiceInterface, ActivityLogEntityRepo activityLogEntityRepo) {
		this.userServiceInterface = userServiceInterface;
		this.activityLogEntityRepo = activityLogEntityRepo;
	}
	
	@RequestMapping("/")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/register")
	@ResponseStatus(HttpStatus.OK)
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "signup_form";
	}
	
	@PostMapping("/processregister")
	public String processRegister(User user) throws Exception {
		userServiceInterface.save(user);
		return "register_success";
	}

	@GetMapping(value="/chats", consumes=org.springframework.http.MediaType.ALL_VALUE)
	public String chat() {
		return "chat";
	}
	
	@GetMapping("/fetchAllUsers")
	@ResponseBody
    public Set<String> fetchAll() {
        return userServiceInterface.findAllByName();
    }
	
	@GetMapping("/registration/{userName}")
    public ResponseEntity<Void> register(@PathVariable String userName) {
		System.out.println("handling register user request: " + userName);
        User user = userServiceInterface.findByUserName(userName);
        ActivityLogEntity entity = new ActivityLogEntity();
		entity.setUserName(user.getUserName());
		entity.setActivity(ActivityType.LOGIN);
		activityLogEntityRepo.save(entity);
        return ResponseEntity.ok().build();
    
    }
	
	@GetMapping("/blockUser")
	public ResponseEntity<Void> block(@RequestParam String angryUser, @RequestParam String blockedUser) throws Exception {
		userServiceInterface.block(angryUser, blockedUser);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/unblockUser")
	public ResponseEntity<Void> unblock(@RequestParam String angryUser, @RequestParam String blockedUser) {
		Boolean b = userServiceInterface.unblock(angryUser, blockedUser);
		if(b.equals(Boolean.TRUE)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
}
