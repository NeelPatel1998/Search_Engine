/**
 *  Developed by Sayee Shruthi
 */
package TextProcessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class HTMLtotext {
	
	public static boolean foldercreate(File folder) {
		try {
			if(folder.exists()) {
				return true;
			} else {
				folder.mkdir();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	public static void converttotext(String url, int i, File dir) throws IOException {
		try {
			Document document = Jsoup.connect(url).get();
			
			String fileText = url;
			fileText += "\n" + document.title();
			fileText += "\n" + document.text();
			
			PrintWriter writer = new PrintWriter(dir.getAbsolutePath() + "/" + i + ".txt");
			System.out.println(dir.getAbsolutePath() + "/" + i + ".txt");
			
			writer.println(fileText);
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	
	public static void main (String[] args) throws IOException { 
		BufferedReader bufReader = new BufferedReader(new FileReader("GeekCrawler.txt"));        
		ArrayList<String> linklist = new ArrayList<String>();
		String line = bufReader.readLine();
		while (line != null)
			{ 
			linklist.add(line); 
			line = bufReader.readLine(); 
			}
		bufReader.close();
		
		// to get only unique links
		Set<String> all = new HashSet<>(linklist);
		
		File dir = new File("./TextFiles"); 
		if (foldercreate(dir)) {
			int i = 1;
			for (String name : all) {
				converttotext(name, i, dir);
				i += 1;
			}
		}
	}

}
