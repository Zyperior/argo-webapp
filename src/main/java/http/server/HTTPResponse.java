package http.server;

import java.io.*;
import java.util.Date;

class HTTPResponse {

	private static final File WEB_ROOT = new File(".");
	private static final String DEFAULT_FILE = "index.html";
	private static final String FILE_NOT_FOUND = "404.html";
	private static final String METHOD_NOT_SUPPORTED = "501.html";

	private File file;
	private int fileLength;
	private RequestType type;
	private String fileRequested;
	private String params;
	private String content;
	private PrintWriter out;
	private BufferedOutputStream dataOut;

	HTTPResponse(RequestType type, String fileRequested, String content, PrintWriter out, BufferedOutputStream dataOut)
			throws IOException {
		// check and retrieve params if they exist
		try {
			if (fileRequested.contains("?")) {
				String[] parts = fileRequested.split("\\?");

				this.fileRequested = parts[0];
				this.params = parts[1];
			} else {
				this.fileRequested = fileRequested;
			}

		} catch (ArrayIndexOutOfBoundsException e) {

		}

		this.type = type;
		this.out = out;
		this.dataOut = dataOut;

		// print out the url and params
		System.out.println(this.fileRequested);
		System.out.println(this.params);



		try {
			this.file = getRequestedFile();
		} catch (FileNotFoundException e) {
			fileNotFound();
		}

		this.fileLength = (int) file.length();

		this.content = content;

		switch (type) {
		case GET:
			get();
			break;
		case HEAD:
			head();
			break;
		case POST:
			post();
			break;
		case INVALID:
			invalid();
		}

	}

	private void get() throws IOException {
		out.println("HTTP/1.1 200 OK");
		standardOutToClient();
	}

	private void head() {

	}

	private void post() {

	}

	private void invalid() throws IOException {
		out.println("HTTP/1.1 501 Not Implemented");
		standardOutToClient();
	}

	private void fileNotFound() throws IOException {
		out.println("HTTP/1.1 404 File Not Found");
		standardOutToClient();

	}

	private void standardOutToClient() throws IOException {
		byte[] fileData = readFileData(fileLength);

		out.println("Server: Java HTTP Server from SSaurel : 1.0");
		out.println("Date: " + new Date());
		out.println("Content-type: " + content);
		out.println("Content-length: " + fileLength);
		out.println(); // blank line between headers and content, very important !
		out.flush(); // flush character output stream buffer
		dataOut.write(fileData, 0, fileLength);
		dataOut.flush();
		out.close();
	}

	private File getRequestedFile() throws FileNotFoundException {

		File file;

		if (fileRequested.endsWith("/")) {
			fileRequested += DEFAULT_FILE;
		}

		if (type == RequestType.INVALID) {
			fileRequested = METHOD_NOT_SUPPORTED;
		}

		file = new File(WEB_ROOT, fileRequested);

		if (!file.exists()) {
			fileRequested = FILE_NOT_FOUND;
			this.file = new File(WEB_ROOT, fileRequested);
			throw new FileNotFoundException();
		}

		return file;

	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	private byte[] readFileData(int fileLength) throws IOException {
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];

		try {
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		} finally {
			if (fileIn != null) {
				fileIn.close();
			}
		}

		return fileData;
	}
}
