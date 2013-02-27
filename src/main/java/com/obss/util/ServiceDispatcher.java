package com.obss.util;

import com.obss.service.AccountService;

/**
 * 
 * Utility method for dispatching service requests.
 *
 */
public class ServiceDispatcher {

	private static AccountService singletonAccountService;

	public static AccountService getAccountService() {
		if (singletonAccountService == null) {
			singletonAccountService = new AccountService();
		}
		return singletonAccountService;
	}
}
