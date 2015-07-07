package edu.usf.cutr;

import javax.servlet.http.HttpServletRequest;

public class UrlUtils {

	public static String getResourcePath(HttpServletRequest request,
			String fileName) {
		return request.getSession().getServletContext()
				.getRealPath("/resources/" + fileName);
	}
}
