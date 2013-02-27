package com.obss.integration.ajax;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

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
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 
 * Integration tests of /ajax/insert calls. Captcha service is mocked.
 * Introspect type mapping is set explicityly since JUnit cannot resolve
 * RequestMapping annotations.
 * 
 * @author omerg
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceConfiguration.class,
		DaoConfiguration.class, CaptchaConfiguration.class })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InsertTest {

	@Autowired
	private AccountService accountService;

	private ImageCaptchaService captchaService;

	private AjaxController ajaxController;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	AnnotationMethodHandlerAdapter adapter;

	private static final String INCORRECT_CAPTCHA_KEY = "INCORRECT_KEY";
	private static final String CORRECT_CAPTCHA_KEY = "CORECT_KEY";
	private static final String CORRECT_PHONE_NUMBER = "111-222-33-34";
	private static final String USERNAME = "TEST_USER";

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);
		request.setRequestURI("/ajax/insert");
		request.setMethod("POST");
		response = new MockHttpServletResponse();
		adapter = new AnnotationMethodHandlerAdapter();
		ajaxController = new AjaxController();
		ajaxController.setAccountService(accountService);

		// mock captcha service
		captchaService = createMock(ImageCaptchaService.class);
		expect(
				captchaService.validateResponseForID(request.getSession()
						.getId(), CORRECT_CAPTCHA_KEY)).andReturn(true);
		expect(
				captchaService.validateResponseForID(request.getSession()
						.getId(), INCORRECT_CAPTCHA_KEY)).andReturn(false);
		ajaxController.setCaptchaService(captchaService);

	}

	@Test
	public void _1_usernameMissing() {

		replay(captchaService);

		request.addParameter("telephoneNumber", CORRECT_PHONE_NUMBER);
		request.addParameter("verificationText", CORRECT_CAPTCHA_KEY);
		try {
			adapter.handle(request, response, ajaxController);
			Assert.fail("username not present");
		} catch (Exception e) {
			// success
		}
	}

	@Test
	public void _2_phoneNumberMissing() {

		replay(captchaService);

		request.addParameter("username", USERNAME);
		request.addParameter("verificationText", CORRECT_CAPTCHA_KEY);
		try {
			adapter.handle(request, response, ajaxController);
			Assert.fail("username not present");
		} catch (Exception e) {
			// success
		}
	}

	@Test
	public void _3_captchaWrong() {

		replay(captchaService);

		request.addParameter("username", USERNAME);
		request.addParameter("telephoneNumber", CORRECT_PHONE_NUMBER);
		request.addParameter("verificationText", INCORRECT_CAPTCHA_KEY);
		try {
			adapter.handle(request, response, ajaxController);
			if (response.getErrorMessage().isEmpty()) {
				Assert.fail(response.getErrorMessage());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void _4_validRequest() {

		replay(captchaService);

		request.addParameter("username", USERNAME);
		request.addParameter("telephoneNumber", CORRECT_PHONE_NUMBER);
		request.addParameter("verificationText", CORRECT_CAPTCHA_KEY);
		try {
			adapter.handle(request, response, ajaxController);

			// success
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void _5_userAlreadyExists() {

		replay(captchaService);

		request.addParameter("username", USERNAME);
		request.addParameter("telephoneNumber", CORRECT_PHONE_NUMBER);
		request.addParameter("verificationText", CORRECT_CAPTCHA_KEY);
		try {
			adapter.handle(request, response, ajaxController);
			Assert.fail();
		} catch (Exception e) {
			// success
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
		captchaService = null;
	}

}
