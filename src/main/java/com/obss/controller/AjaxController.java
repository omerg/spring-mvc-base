package com.obss.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.obss.model.Account;
import com.obss.model.response.ResponseAccount;
import com.obss.model.response.ResponseAccountList;
import com.obss.model.response.ResponseGedikStockList;
import com.obss.service.AccountService;
import com.obss.service.GedikStockService;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * Controller class responsible for mapping AJAX requests
 * to services.
 */
@Controller
@RequestMapping("/ajax")
public class AjaxController {

	protected static Logger logger = Logger.getLogger("controller");

	@Autowired
	private AccountService accountService;

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Autowired
	private ImageCaptchaService captchaService;

	public void setCaptchaService(ImageCaptchaService captchaService) {
		this.captchaService = captchaService;
	}
	
	@Autowired
	private GedikStockService gedikStockService;
	
	public void setGedikStockService(GedikStockService gedikStockService) {
		this.gedikStockService = gedikStockService;
	}


	/**
	 * Handles request for saving / updating account
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public @ResponseBody
	void insert(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "surname", required = false) String surname,
			@RequestParam(value = "telephoneNumber", required = true) String telephoneNumber,
			@RequestParam(value = "verificationText", required = true) String verificationText,
			HttpServletRequest request, HttpServletResponse response) {

		logger.debug("Received a request to insert an account");

		// validate captcha
		Boolean isResponseCorrect = false;
		String sessionId = request.getSession().getId();

		// Call the Service method
		try {
			isResponseCorrect = captchaService.validateResponseForID(sessionId,
					verificationText);
			if (isResponseCorrect == false) {
				response.sendError(500, "Provided captcha text was wrong.");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		accountService.insert(new Account(username, name, surname,
				telephoneNumber));

	}

	/**
	 * Handles request for saving / updating account
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody
	void update(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "surname", required = false) String surname,
			@RequestParam(value = "telephoneNumber", required = false) String telephoneNumber) {
		logger.debug("Received request to update account");

		accountService.update(new Account(username, name, surname,
				telephoneNumber));

	}

	/**
	 * Handles request for deleting account
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	void delete(
			@RequestParam(value = "username", required = true) String username,
			HttpServletResponse response) {
		logger.debug("Received request to delete account");

		ResponseAccount responseAccount = accountService
				.getAccountByUsername(username);
		if (responseAccount.getAccount() == null) {
			try {
				response.sendError(500, "No such user exists: " + username);
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			return;
		}
		accountService.delete(responseAccount.getAccount());

	}

	/**
	 * Handles request for listing accounts
	 */
	@RequestMapping(value = "/listAllPagified", method = RequestMethod.GET)
	public @ResponseBody
	ResponseAccountList listAllPagified(
			@RequestParam(value = "pageNum", required = true) Integer pageNum, HttpServletResponse response) {
		logger.debug("Received request to list accounts");
		
		ResponseAccountList accountList = accountService
				.getAllAccountsPagified(pageNum);

		return accountList;

	}

	/**
	 * Handles request for getting an account by username
	 */
	@RequestMapping(value = "/findByUsername", method = RequestMethod.GET)
	public @ResponseBody
	ResponseAccount findByUsername(
			@RequestParam(value = "username", required = true) String username) {
		logger.debug("Received request to get account by username");

		ResponseAccount acconut = accountService.getAccountByUsername(username);

		return acconut;

	}
	
	/**
	 * Handles request for listing accounts
	 */
	@RequestMapping(value = "/listGedikStocks", method = RequestMethod.GET)
	public @ResponseBody
	ResponseGedikStockList listGedikStocks(
			HttpServletResponse response) {
		logger.debug("Received request to list gedik stocks");
		
		ResponseGedikStockList gedikStockList = null;
		try {
			gedikStockList = gedikStockService.getAllStocks();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return gedikStockList;

	}
}
