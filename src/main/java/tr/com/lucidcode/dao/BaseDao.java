package tr.com.lucidcode.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Service;

import tr.com.lucidcode.util.HibernateUtil;

@Service("baseDao")
public class BaseDao<T> {

	protected static Logger logger = Logger.getLogger("sessionListener");

	/**
	 * 
	 * persist a generic object
	 * 
	 * @param t type of object
	 * @return inserted object
	 * @throws ConstraintViolationException
	 */
	public T insert(T t) throws ConstraintViolationException {
		
		if (t == null) {
			throw new IllegalArgumentException();
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		try {
			session.save(t);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new HibernateException(e.getMessage());
		}

		return t;
	}

	/**
	 * 
	 * update an existing generic object
	 * 
	 * @param t type of object
	 * @throws DataException
	 */
	public void delete(T t) throws DataException {
		
		if (t == null) {
			throw new IllegalArgumentException();
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		try {
			session.delete(t);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new HibernateException(e.getMessage());
		}

	}

}
