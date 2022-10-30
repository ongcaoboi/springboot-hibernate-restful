package sbh.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import sbh.rest.entities.User;
import sbh.rest.repository.UserRepository;

public class AuthController {

	@Autowired
	private UserRepository ur;
	
	protected User getUserLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetails = (UserDetails) auth.getPrincipal();
			return ur.getUserByUserName(userDetails.getUsername());
		} else {
			return null;
		}
	}
}
