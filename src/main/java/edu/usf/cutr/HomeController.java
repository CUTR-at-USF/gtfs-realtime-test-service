package edu.usf.cutr;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.usf.cutr.constants.URLConstants;
import edu.usf.cutr.manager.IOManager;

/**
 * Handles requests for the application home page.
 */

@Controller
public class HomeController extends BaseController {

	@Autowired
	private IOManager ioManager;
	
	@RequestMapping(value = "/trip-updates.do", method = RequestMethod.GET)
	public void tripUdates(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.log(Level.INFO, "loading trips");
			String path = UrlUtils.getResourcePath(request, URLConstants.TRIPS_NAME);
			createStaticProtoBufFile(response, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/vehicle-positions.do", method = RequestMethod.GET)
	public void vehiclePositions(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.log(Level.INFO, "loading vehicles");
			String path = UrlUtils.getResourcePath(request, URLConstants.VEHICLES_NAME);
			createStaticProtoBufFile(response, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/update-files.do", method = RequestMethod.GET)
	public void updateFiles(HttpServletRequest request, HttpServletResponse response) {
		try {
			String savePath = UrlUtils.getResourcePath(request, URLConstants.TRIPS_NAME);
			ioManager.downloadFile(URLConstants.TRIPS, savePath);
			savePath = UrlUtils.getResourcePath(request, URLConstants.VEHICLES_NAME);
			ioManager.downloadFile(URLConstants.VEHICLES, savePath);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			createJSONResponse(response, "Status: ERROR \n" + e );
		} catch (IOException e) {
			e.printStackTrace();
			createJSONResponse(response, "Status: ERROR \n" + e );
		}
		createJSONResponse(response, "Status: OK");
	}

}
