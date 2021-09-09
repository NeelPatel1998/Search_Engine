/**
 *  Developed by Sayee Shruthi
 */

package TextProcessing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.Version;

public class StringUtils {
	
	private static List <String> stop_words = new ArrayList<String>();
	private static final String stop_words_file = "stop_words.txt";
	
	public StringUtils () throws FileNotFoundException {
		try {
			Scanner sw = new Scanner(new File("stop_words.txt"));
		    while(sw.hasNextLine()){
		    	String line = sw.nextLine();
		    	if (line.matches(".*\\p{P}")) {
		    		stop_words.add(line.replaceAll(".*\\p{Punct}", ""));
		    	}
		    	stop_words.add(line);
		    }
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	public String removeStopWords(String text) {
		String[] word_array = text.split(" ");
		List <String> word_list = new ArrayList<String>(Arrays.asList(word_array));
		
		word_list.removeAll(stop_words);
		
		String cleanedText = String.join(" ", word_list);
		return cleanedText;
	}

}
