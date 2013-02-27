package com.obss.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.obss.config.DaoConfiguration;
import com.obss.config.ServiceConfiguration;
import com.obss.dao.AccountDao;
import com.obss.model.Account;
import com.obss.model.ResponseAccount;
import com.obss.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, DaoConfiguration.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountServiceTest {

	private static final String CORRECT_PHONE_NUMBER = "111-222-33-34";
	private static final String USERNAME = "TEST_USER";
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	AccountDao accountDao;
	
	@Before
	public void setUp() {
		ResponseAccount response = accountService.getAccountByUsername(USERNAME);
		if (response.getAccount() != null) {
			accountService.delete(response.getAccount());
		}
	}
	
	@Test
	public void _1_queryReturnsNull() {
		ResponseAccount response = accountService.getAccountByUsername(USERNAME);
		Assert.assertNull(response.getAccount());
	}
	
	@Test
	public void _2_insertRowAndCheck() {
		
		Account account = new Account(USERNAME, null, null, CORRECT_PHONE_NUMBER);
		//insert
		accountService.insert(account);
		ResponseAccount response = accountService.getAccountByUsername(USERNAME);
		Assert.assertEquals(null, response.getAccount().getSurname());
	}
	
	@Test
	public void _3_updateRowAndCheck() {
		
		Account account = new Account(USERNAME, null, null, CORRECT_PHONE_NUMBER);
		//insert
		accountService.insert(account);
		ResponseAccount response = accountService.getAccountByUsername(USERNAME);
		if (response.getAccount() != null) {
			//modify name field
			response.getAccount().setName("Ozkan");
			accountService.update(response.getAccount());
		}
		
		response = accountService.getAccountByUsername(USERNAME);
		Assert.assertEquals("Ozkan", response.getAccount().getName());
	}
	
	@Test
	public void _4_deleteRowAndCheck() {
		
		ResponseAccount response = accountService.getAccountByUsername(USERNAME);
		if (response.getAccount() != null) {
			accountService.delete(response.getAccount());
		}
		response = accountService.getAccountByUsername(USERNAME);
		Assert.assertNull(response.getAccount());
	}
	
	@After
	public void tearDown() {
		accountService = null;
	}

}
