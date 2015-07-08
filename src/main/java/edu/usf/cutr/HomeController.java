/*
 * Copyright (C) 2015 University of South Florida (cagricetin@mail.usf.edu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.usf.cutr;

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
	
	/**
	 * Returns trip-updates in protocol buffer format
	 */
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

	/**
	 * Returns vehicle-positions in protocol buffer format
	 */
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

	/**
	 * Updates original gtfs-rt files 
	 */
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
