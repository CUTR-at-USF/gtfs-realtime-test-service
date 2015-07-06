package edu.usf.cutr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;

public abstract class BaseController {

	/**
	 * Size of a byte buffer to read/write file
	 */
	private static final int BUFFER_SIZE = 4096;
	
	Logger logger = Logger.getLogger(getClass().getName());
	
	protected void createJSONResponse(HttpServletResponse response,
			Object jsonBean) {
		createJSONResponse(response, jsonBean, MediaType.APPLICATION_JSON);
	}

	protected void createJSONResponse(HttpServletResponse response,
			Object jsonBean, MediaType jsonMimeType) {
		MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
		if (jsonConverter.canWrite(jsonBean.getClass(), jsonMimeType)) {
			try {
				jsonConverter.write(jsonBean, jsonMimeType,
						new ServletServerHttpResponse(response));
			} catch (IOException ex) {
//				logger.error(ex);
			} catch (HttpMessageNotWritableException ex) {
//				logger.error(ex);
			}
		}
	}

	protected void createStaticProtoBufFile(HttpServletResponse response,
			String filename) throws IOException {
		
		ClassLoader classLoader = getClass().getClassLoader();
		File downloadFile = new File(classLoader.getResource(filename)
				.getFile());

		FileInputStream inputStream = new FileInputStream(downloadFile);

		// get MIME type of the file
		String mimeType = "application/x-google-protobuf";

		// set content attributes for the response
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outStream.close();

	}

}
