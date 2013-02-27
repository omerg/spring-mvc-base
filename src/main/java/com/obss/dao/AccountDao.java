package com.obss.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Service;

import com.obss.model.Account;
import com.obss.util.HibernateUtil;

@SuppressWarnings("unchecked")
@Service("accountDao")
public class AccountDao extends BaseDao<Account> {

	/**
	 * Page size should be synchronized to this value on the front-end.
	 */
	private static final int DEFAULT_PAGE_SIZE = 5;

	protected static Logger logger = Logger.getLogger("sessionListener");

	/**
	 * 
	 * updates given fields of a user other than the userName
	 * 
	 * @param account
	 * @return updated account
	 * @throws ConstraintViolationException
	 */
	public Account update(Account account) throws ConstraintViolationException {

		if (account == null) {
			throw new IllegalArgumentException();
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		//get existing user
		
		Criteria crit = session.createCriteria(Account.class);
		crit.add(Restrictions.eq("username", account.getUsername()));
			
		Account existing;
		try {
			existing = (Account) crit.uniqueResult();
			if (existing == null) {
				logger.debug("User could not be found");
				session.getTransaction().rollback();
				return null;
			}
			
			//update user
			
			existing.setName(account.getName());
			existing.setSurname(account.getSurname());
			existing.setPhoneNumber(account.getPhoneNumber());
			session.save(existing);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new HibernateException(e.getMessage());
		} 

		return account;
	}

	/**
	 * 
	 * get a window of results from accounts table.
	 * page number starts with 0.
	 * 
	 * @return list of accounts
	 * @throws DataException
	 */
	public List<Account> findAllPagified(Integer pageNum) throws DataException {

		//fix page number if null
		if (pageNum == null) {
			pageNum = 0;
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		final Criteria crit = session.createCriteria(Account.class);
		crit.setMaxResults(DEFAULT_PAGE_SIZE);
		crit.setFirstResult(DEFAULT_PAGE_SIZE * pageNum);
		List<Account> accountList;
		try {
			accountList = crit.list();
			session.flush();
			session.getTransaction().commit();
		} catch (DataException e) {
			session.getTransaction().rollback();
			throw e;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new HibernateException(e.getMessage());
		}
		return accountList;

	}

	/**
	 * 
	 * get all results unpagified.
	 * 
	 * @return List of accounts
	 * @throws DataException
	 */
	public List<Account> findAll() throws DataException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		final Criteria crit = session.createCriteria(Account.class);
		List<Account> accountList;
		try {
			accountList = crit.list();
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new HibernateException(e.getMessage());
		}
		return accountList;

	}

	/**
	 * 
	 * get number of rows in the accounts table
	 * 
	 * @return Long - number of rows
	 * @throws DataException
	 */
	public Long getRowCount() throws DataException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		final Criteria crit = session.createCriteria(Account.class);
		crit.setProjection(Projections.rowCount());
		Long count;
		try {
			count = (Long) crit.uniqueResult();
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new HibernateException(e.getMessage());
		}
		return count;

	}

	/**
	 * 
	 * get account data by userName, which is unique.
	 * 
	 * @param username
	 * @return Account
	 * @throws DataException
	 */
	public Account findByUsername(String username) throws DataException {

		//validate input
		if (username == null) {
			throw new IllegalArgumentException();
		}

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(Account.class);
		crit.add(Restrictions.eq("username", username));
		Account account;
		try {
			account = (Account) crit.uniqueResult();
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new HibernateException(e.getMessage());
		}
		return account;
	}

	/**
	 * 
	 * search account by a keyword which is then
	 * compared to name and surname fields
	 * 
	 * @param text
	 * @return list of accounts
	 * @throws DataException
	 */
	public List<Account> textSearch(String text) throws DataException {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Criteria crit = session.createCriteria(Account.class);
		Criterion name = Restrictions.like("name", text);
		Criterion surname = Restrictions.like("surname", text);
		LogicalExpression expression = Restrictions.or(name, surname);

		crit.add(expression);
		List<Account> accountList;
		try {
			accountList = crit.list();
			session.flush();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new HibernateException(e.getMessage());
		}
		return accountList;
	}

}
