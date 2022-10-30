package sbh.rest.services;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import sbh.rest.entities.Customer;
import sbh.rest.entities.Role;
import sbh.rest.entities.User;

@Service
@Component
public class HibernateService {

	private String driver;

	private String url;

	private String user;
	
	private String pass;

	private String dialect;

	private boolean showSql;

	private String dllAuto;

	private SessionFactory sessionFactory;
	
	public HibernateService(
			@Value("${spring.datasource.driverClassName}")String driver,
			@Value("${spring.datasource.url}") String url,
			@Value("${spring.datasource.username}") String user,
			@Value("${spring.datasource.password}") String pass,
			@Value("${spring.jpa.database-platform}") String dialect,
			@Value("${spring.jpa.show-sql}") boolean showSql,
			@Value("${spring.jpa.hibernate.ddl-auto}") String dllAuto,
			ApplicationContext appContext) {
		super();
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.pass = pass;
		this.dialect = dialect;
		this.showSql = showSql;
		this.dllAuto = dllAuto;
		
		System.out.println("hihi");

		initService();
	}

	private void initService() {
		try {
			Configuration config = new Configuration();

			Properties settings = new Properties();
			settings.put(Environment.DRIVER, driver);
			settings.put(Environment.URL, url);
			settings.put(Environment.USER, user);
			settings.put(Environment.PASS, pass);
			settings.put(Environment.DIALECT, dialect);
			settings.put(Environment.SHOW_SQL, showSql);
			settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
			settings.put(Environment.HBM2DDL_AUTO, dllAuto);

			config.setProperties(settings);

			config.addAnnotatedClass(Customer.class);
			config.addAnnotatedClass(User.class);
			config.addAnnotatedClass(Role.class);

			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties())
					.build();

			sessionFactory = config.buildSessionFactory(serviceRegistry);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			initService();
		}
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
