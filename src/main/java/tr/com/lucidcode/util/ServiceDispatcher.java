package tr.com.lucidcode.util;

import tr.com.lucidcode.service.AccountService;

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
