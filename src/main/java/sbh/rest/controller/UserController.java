package sbh.rest.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sbh.rest.entities.Role;
import sbh.rest.entities.User;
import sbh.rest.models.UserModel;
import sbh.rest.repository.UserRepository;
import sbh.rest.services.JwtService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

	@Autowired
	private UserRepository ur;

	@Autowired
	private JwtService jwtService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserModel userLogin) {
		try {
			User user = ur.getUserByUserNameAndPassword(userLogin.getUserName(), userLogin.getPassword());
			if (user == null) {
				return new ResponseEntity<>("Username or password not found!", HttpStatus.BAD_REQUEST);
			}
			String token = jwtService.generateTokenUsingUserName(user.getUserName());
			return new ResponseEntity<>(token, HttpStatus.OK);

		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
