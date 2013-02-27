package com.obss.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.obss.model.Account;
import com.obss.model.ResponseAccountList;
import com.obss.service.AccountService;
import com.obss.util.ServiceDispatcher;

@Controller
public class HomeController {

	protected static Logger logger = Logger.getLogger("sessionListener");

	@Resource(name = "accountService")
	private AccountService accountService;

	@RequestMapping(value = "/")
	public ModelAndView listAll() {
		
		logger.debug("Homepage requested");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");

		// add sample data
		List<Account> accountList = new ArrayList<Account>();
		accountList.add(new Account("gurarslan", "Omer", "Gurarslan",
				"111-222-33-34"));

		try {
			for (Account newAccount : accountList) {
				if (ServiceDispatcher.getAccountService()
						.getAccountByUsername(newAccount.getUsername())
						.getAccount() == null) {
					ServiceDispatcher.getAccountService().insert(newAccount);
				}
			}
		} catch (DataException e) {
			logger.debug(e.getMessage());
		}

		ResponseAccountList resultList = accountService
				.getAllAccountsPagified(0);

		modelAndView.addObject("accountList", resultList.getAccountList());
		modelAndView.addObject("rowCount", resultList.getRowCount());

		return modelAndView;
	}
}
