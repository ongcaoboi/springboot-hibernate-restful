package sbh.rest.repository;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		cq.from(Customer.class);
		return session.createQuery(cq).getResultList();
		
//		cr.add(Restrictions.gt)
//		return loadAllData(Customer.class, session);
	}
	
	public List<Customer> getFilter(String name, String sex) {
		Session session = hbnService.getSessionFactory().openSession();
		Criteria cr = session.createCriteria(Customer.class);
		
		if (name != null) {
			cr.add(Restrictions.like("name", "%" + name + "%"));
		}
		
		if (sex != null) {
			cr.add(Restrictions.eq("sex", sex));
		}
		
		cr.addOrder(Order.asc("id"));
		return cr.list();
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
}
