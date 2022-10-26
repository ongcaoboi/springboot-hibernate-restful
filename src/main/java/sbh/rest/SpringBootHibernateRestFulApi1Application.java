package sbh.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootHibernateRestFulApi1Application {
	
	public static ApplicationContext appContext;
	
	public static void main(String[] args) {		
		appContext = SpringApplication.run(SpringBootHibernateRestFulApi1Application.class, args);
	}
	
}
