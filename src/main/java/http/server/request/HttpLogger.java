package http.server.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HttpLogger {

	
	public static List<String> log = Collections.synchronizedList(new ArrayList<String>());

	static public void log(String s) {
		log.add(s);
	}

	static public List<String> getLog() {
		return log;
	}
}
