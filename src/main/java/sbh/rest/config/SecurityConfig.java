package sbh.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private EndPointFilterConfig enpoint;
	
	@Autowired
	private RequestFilterConfig filter;
	
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.cors();
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/api/login").permitAll().and()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/api/customers/**").access("hasRole('ROLE_EDIT')")
			.antMatchers(HttpMethod.PUT, "/api/customers/**").access("hasRole('ROLE_EDIT')")
			.antMatchers(HttpMethod.DELETE, "/api/customers/**").access("hasRole('ROLE_DELETE')")
				
			.anyRequest().authenticated().and().exceptionHandling()
			.authenticationEntryPoint(enpoint).and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}
}