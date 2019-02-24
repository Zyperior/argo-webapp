package http.server.filehandling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FileStore {

	public static void storePlain(ArrayList<String> data, String u, String fileName) {
    	String path ="";	
		if(fileName.contains(".html")) {
    			path = "files/html";
    		}
    		else if(fileName.contains(".css")) {
    			path = "files/css";
    		}
    		else if(fileName.contains(".js")) {	
    			path = "files/js";
    		}
    		else {
    			path = "files/msc";
    		}
    		File dir = new File(path);
            dir.mkdirs();
            path = path + "/";
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + fileName))) {

            for (String s : data) {
                bw.write(s);
                bw.newLine();
            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

	public static void storePlain(String data, String u, String fileName) {
		String path ="";	
		if(fileName.contains(".html")) {
			path = "files/html";
		}
		else if(fileName.contains(".css")) {
			path = "files/css";
		}
		else if(fileName.contains(".js")) {	
			path = "files/js";
		}
		else {
			path = "files/msc";
		}
		File dir = new File(path);
		dir.mkdirs();
		path = path + "/";

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(path + fileName))) {

			bw.write(data);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}
