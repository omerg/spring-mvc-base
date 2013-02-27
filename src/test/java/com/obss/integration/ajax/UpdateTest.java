package com.obss.integration.ajax;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfiguration.class,
		DaoConfiguration.class, CaptchaConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UpdateTest {

	private static final String CORRECT_PHONE_NUMBER = "1112223334";
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
		request.setRequestURI("/ajax/update");
		request.setMethod("POST");
		response = new MockHttpServletResponse();

		adapter = new AnnotationMethodHandlerAdapter();
		ajaxController = new AjaxController();
		ajaxController.setAccountService(accountService);

	}

	@Test
	public void missingUsername() {

		request.addParameter("telephoneNumber", CORRECT_PHONE_NUMBER);
		try {
			adapter.handle(request, response, ajaxController);
			Assert.fail("username not present");
		} catch (Exception e) {
			// success
		}
	}

	@Test
	public void usernameChanged() {

	}

	@Test
	public void missingPhoneNumber() {

	}

	@Test
	public void validRequest() {

	}

	@After
	public void tearDown() {

		response = null;
		adapter = null;
		ajaxController = null;
	}

}
