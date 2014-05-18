package tr.com.lucidcode.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import tr.com.lucidcode.dao.AccountDao;
import tr.com.lucidcode.model.Account;
import tr.com.lucidcode.model.ResponseAccount;
import tr.com.lucidcode.model.ResponseAccountList;

/**
 * 
 * Main service associated with operations on accounts table
 * 
 */
@Service("accountService")
public class AccountService extends BaseService<Account> {

	protected static Logger logger = Logger.getLogger("sessionListener");

	AccountDao accountDao = new AccountDao();

	/**
	 * updates an account object
	 * if an account is to be updated, it has to be persistent,
	 * not detached.
	 * 
	 * @param account
	 */
	public void update(Account account) {
		accountDao.update(account);
	}

	/**
	 * 
	 * retrieve the account with the given username
	 * 
	 * @param username
	 * @return List of Accounts
	 */
	public ResponseAccount getAccountByUsername(String username) {
		ResponseAccount response = new ResponseAccount();
		response.setAccount(accountDao.findByUsername(username));
		return response;
	}

	/**
	 * 
	 * retrieve a window of accounts
	 * 
	 * @return List of Accounts
	 */

	public ResponseAccountList getAllAccountsPagified(Integer pageNum) {
		ResponseAccountList response = new ResponseAccountList();
		response.setAccountList(accountDao.findAllPagified(pageNum));
		response.setRowCount(accountDao.getRowCount());
		return response;
	}

	/**
	 * 
	 * retrieve all account data
	 * 
	 * @return all account's list
	 */
	public ResponseAccountList getAllAccounts() {

		ResponseAccountList response = new ResponseAccountList();
		response.setAccountList(accountDao.findAll());
		response.setRowCount(accountDao.getRowCount());
		return response;

	}

	/**
	 * 
	 * perform a text based search
	 * 
	 * @param searchText
	 * @return List of Accounts
	 */
	public List<Account> searchAccount(String searchText) {
		return accountDao.textSearch(searchText);
	}

}
