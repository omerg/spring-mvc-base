package com.obss.integration.ajax;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import com.obss.config.CaptchaConfiguration;
import com.obss.config.DaoConfiguration;
import com.obss.config.ServiceConfiguration;
import com.obss.controller.AjaxController;
import com.obss.model.Account;
import com.obss.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfiguration.class,
		DaoConfiguration.class, CaptchaConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListAllPagifiedTest {

	private static final String CORRECT_PHONE_NUMBER = "111-222-33-34";
	private static final String USERNAME = "TEST_USER";

	@Autowired
	private AccountService accountService;

	private AjaxController ajaxController;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	AnnotationMethodHandlerAdapter adapter;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);
		request.setRequestURI("/ajax/listAllPagified");
		request.setMethod("GET");
		response = new MockHttpServletResponse();

		adapter = new AnnotationMethodHandlerAdapter();
		MappingJacksonHttpMessageConverter messageConverter = new MappingJacksonHttpMessageConverter();
		adapter.setMessageConverters(new HttpMessageConverter[] { messageConverter });
		ajaxController = new AjaxController();
		ajaxController.setAccountService(accountService);

	}

	@Test
	public void missingPageNum() {

		// add test data
		try {
			accountService.insert(new Account(USERNAME, null, null,
					CORRECT_PHONE_NUMBER));
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		// start test
		try {
			adapter.handle(request, response, ajaxController);
			Assert.fail();
		} catch (Exception e) {
			//success
		}

		// rollback
		try {
			accountService.delete(accountService.getAccountByUsername(USERNAME)
					.getAccount());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

	}

	@Test
	public void validRequest() {
		

		// initial rowCount
		Integer preRowCount = accountService.getAllAccounts().getAccountList().size();

		// add test data
		try {
			accountService.insert(new Account(USERNAME, null, null,
					CORRECT_PHONE_NUMBER));
			Assert.assertEquals(preRowCount + 1, accountService.getAllAccounts()
					.getAccountList().size());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}

		// start test
		request.addParameter("pageNum", "0");
		try {
			response.setHeader(
					"Accept",
					"text/html,application/xhtml+xml,application/xml,application/json;q=0.9,*/*;q=0.8");
			adapter.handle(request, response, ajaxController);
			Assert.assertEquals(preRowCount + 1, accountService.getAllAccounts()
					.getAccountList().size());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		} finally {
			// rollback
			accountService.delete(accountService.getAccountByUsername(USERNAME)
					.getAccount());
		}
	}

	@After
	public void tearDown() {

		response = null;
		adapter = null;
		ajaxController = null;
	}

}
