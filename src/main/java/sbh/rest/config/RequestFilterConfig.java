package sbh.rest.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import sbh.rest.services.JwtService;

@Component
public class RequestFilterConfig extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String userName = null;
		String token = request.getHeader("Authorization");
		
		if (jwtService.validateToken(token)) {
			userName = jwtService.getUserNameFromToken(token);
			
			UserDetails user = userDetailService.loadUserByUsername(userName);
			
			UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			
			upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(upat);
		}
		
		filterChain.doFilter(request, response);
	}
}
