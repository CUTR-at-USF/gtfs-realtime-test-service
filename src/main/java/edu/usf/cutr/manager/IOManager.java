package edu.usf.cutr.manager;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IOManager {

	public void downloadFile(String url, String path)
			throws MalformedURLException, IOException;
}
