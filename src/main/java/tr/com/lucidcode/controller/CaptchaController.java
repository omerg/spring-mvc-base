package tr.com.lucidcode.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

	protected static Logger logger = Logger.getLogger("controller");

	@Resource(name = "captchaService")
	private ImageCaptchaService captchaService;

	@RequestMapping(value = "/getImage", method = RequestMethod.GET)
	public @ResponseBody
	Model showImage(HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		
		logger.debug("Received a request to show captcha image");

		byte[] captchaChallengeAsJpeg = null;

		// the output stream to render the captcha image as jpeg into
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

		try {
			// get the session id that will identify the generated captcha.
			// the same id must be used to validate the response, the session id
			// is a good candidate!

			String captchaId = request.getSession().getId();

			logger.debug("Captcha ID which gave the image::" + captchaId);

			// call the ImageCaptchaService getChallenge method
			BufferedImage challenge = captchaService.getImageChallengeForID(
					captchaId, request.getLocale());

			// a jpeg encoder
			ImageIO.write(challenge, "jpeg", jpegOutputStream);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} catch (CaptchaServiceException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		// flush it in the response
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		// response.setContentType("image/jpeg");
		// response.getOutputStream().write(jpegOutputStream);
		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();

		return null;
	}

}
