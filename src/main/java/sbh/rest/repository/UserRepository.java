package sbh.rest.repository;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sbh.rest.entities.User;
import sbh.rest.services.HibernateService;

@Component
public class UserRepository {

	@Autowired
	private HibernateService hbnService;
	
	public User getUserByUserNameAndPassword(String userName, String password) {
		Session session = hbnService.getSessionFactory().openSession();
		
		Criteria cr = session.createCriteria(User.class);
		
		cr.add(Restrictions.eq("userName", userName));
		cr.add(Restrictions.eq("password", password));
		
		return (User) cr.uniqueResult();
	}
	
	public User getUserByUserName(String userName) {
		Session session = hbnService.getSessionFactory().openSession();
		
		Criteria cr = session.createCriteria(User.class);
		
		cr.add(Restrictions.eq("userName", userName));
		
		return (User) cr.uniqueResult();
	}
}
