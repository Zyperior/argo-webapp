/*This class takes the bulk of the http reads it and then stores the body as a file 
 * IT ONLY HANDLES PLAIN TEXT
 * NO BINARY!!!!
 * 
 * 
 * 
 */
package http.server.filehandling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileStore {

	public static void arrToFile(ArrayList<String> requestData, String diskLocation) {
		String fileName = "";
		String location = diskLocation;
		int fileBegins = -1;
		int fileEnd = -1;
		for (String s : requestData) {

			if (s.contains("filename")) {
				String[] stage1 = s.split("filename=\"");
				String[] stage2 = stage1[1].split("\"");
				fileName = stage2[0];
				fileBegins = requestData.indexOf(s) + 2;
			}

			if (s.contains("WebKitFormBoundary")) {
				fileEnd = requestData.indexOf(s) - 1;
			}
		}

		int counter = 0;
		if (fileBegins != -1 && fileEnd != -1) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
				for (String s : requestData) {
					if (counter >= fileBegins && counter <= fileEnd) {
						bw.write(s);
						bw.newLine();
					}

					counter++;
				}

			} catch (IOException e) {

				e.printStackTrace();

			}

		}
	}

}
