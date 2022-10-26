package sbh.rest.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sbh.rest.entities.Customer;
import sbh.rest.services.HibernateService;

@Component
public class CustomerRepository {

	@Autowired
	private HibernateService hbnService;

	public List<Customer> getAll() {		
		Session session = hbnService.getSessionFactory().openSession();
		return loadAllData(Customer.class, session);
	}
	
	public Customer getById(int id) {
		Session session = hbnService.getSessionFactory().openSession();
		return session.get(Customer.class, id);
	}
	
	public boolean add(Customer customer) {
		Session session = hbnService.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.save(customer);
			session.flush();
			transaction.commit();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
			return false;
		}
	}
	
	public boolean update(Customer customer) {
		Session session = hbnService.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.update(customer);
			session.flush();
			transaction.commit();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
			return false;
		}
	}

	public boolean delete(Customer customer) {
		Session session = hbnService.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.delete(customer);
			session.flush();
			transaction.commit();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
			return false;
		}
	}

	private static <T> List<T> loadAllData(Class<T> type, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(type);
		criteria.from(type);
		List<T> data = session.createQuery(criteria).getResultList();
		return data;
	}
}
