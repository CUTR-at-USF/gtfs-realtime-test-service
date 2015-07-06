package edu.usf.cutr;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */

@Controller
public class HomeController extends BaseController {

	@RequestMapping(value = "/trip-updates.do", method = RequestMethod.GET)
	public void tripUdates(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			logger.log(Level.INFO, "loading trips");
			createStaticProtoBufFile(response, "trips");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/vehicle-positions.do", method = RequestMethod.GET)
	public void vehiclePositions(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			logger.log(Level.INFO, "loading vehicles");
			createStaticProtoBufFile(response, "vehicles");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
