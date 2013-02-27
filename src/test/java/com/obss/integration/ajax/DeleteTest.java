package com.obss.integration.ajax;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.obss.service.AccountService;

/**
 * 
 * Integration tests of /ajax/insert calls Captcha service is mocked.
 * 
 * @author omerg
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfiguration.class,
		DaoConfiguration.class, CaptchaConfiguration.class })
public class DeleteTest {

	private static final String NON_EXISTING_USER = "NON_EXISTING_USER";

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
		request.setRequestURI("/ajax/delete");
		request.setMethod("POST");
		response = new MockHttpServletResponse();
		adapter = new AnnotationMethodHandlerAdapter();
		ajaxController = new AjaxController();
		ajaxController.setAccountService(accountService);
	}

	@Test
	public void usernameMissing() {
		try {
			adapter.handle(request, response, ajaxController);
			Assert.fail("username not present");
		} catch (Exception e) {
			// success
		}
	}

	@Test
	public void userNotExisting() {

		request.addParameter("username", NON_EXISTING_USER);
		try {
			adapter.handle(request, response, ajaxController);
			Assert.assertEquals("No such user exists: " + NON_EXISTING_USER,
					response.getErrorMessage());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void validRequest() {

		request.addParameter("username", NON_EXISTING_USER);
		try {
			adapter.handle(request, response, ajaxController);
			Assert.assertEquals("No such user exists: " + NON_EXISTING_USER,
					response.getErrorMessage());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@After
	public void tearDown() {

		response = null;
		adapter = null;
		ajaxController = null;
	}

}
