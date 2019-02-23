package http.server.filehandling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FileStore {

	public static void storePlain(ArrayList<String> data, String path, String fileName) {
		if (path.length() > 1) {
			path = path.substring(1);
		}
		if (path.equals("/")) {
			path = "";
		} else {
			File dir = new File(path);
			dir.mkdirs();
			path = path + "/";
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + fileName))) {

			for (String s : data) {
				bw.write(s);
				bw.newLine();
			}

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
