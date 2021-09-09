/**
 *  Developed by Sayee Shruthi
 */

package TextProcessing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopAnalyzer;
//import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
//import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;


public class Suggestions {

	
	public static void writeToFile(String filename, HashSet<String> URLS) {
	      FileWriter writer;
	      try {
	          writer = new FileWriter(filename);
	          URLS.forEach(a -> {
	              try {
	                  writer.write(a + "\n");
	              } catch (IOException e) {
	                  System.err.println(e.getMessage());
	              }
	          });
	          writer.close();
	      } catch (IOException e) {
	          System.err.println(e.getMessage());
	      }
	  }
	
	public static void writeToFile(String filename, List<String> list) {
		try {
			FileWriter writer = new FileWriter(filename); 
			for(String str: list) {
			  writer.write(str.trim() + System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
	
	public static  List<String> getFromFile(String file) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(file));
		List<String> words = new ArrayList<String>();
		while(scan.hasNextLine()){
	    	String line = scan.nextLine();
	        
	    	words.add(line);
	    }
		return words;
	}
	public static String stemming(String text) throws IOException{
        StringBuffer result = new StringBuffer();
        if (text!=null && text.trim().length()>0){
            StringReader tReader = new StringReader(text);
            Analyzer testAnalyzer = new StopAnalyzer();
            TokenStream tokenStream = testAnalyzer.tokenStream("contents", tReader);
           
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            //TermAttribute term = tokenStream.addAttribute(TermAttribute.Class);
            tokenStream.reset(); 
            try {
                while (tokenStream.incrementToken()){
                    result.append(charTermAttribute.toString());
                    result.append(" ");
                }
            } catch (IOException ioe){
                System.out.println("Error: "+ioe.getMessage());
            }
        }

        // If, for some reason, the stemming did not happen, return the original text
        if (result.length()==0)
            result.append(text);
        return result.toString().trim();
    }
	
	public static int editDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
	 
		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
	 
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
	 
					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}
	 
		return dp[len1][len2];
	}
	
	
	private static void makeDictioanry( ) throws FileNotFoundException {
		File dir = new File("TextFiles");
		List<String> words = new ArrayList<String>();		
		File[] allFiles = dir.listFiles();
		System.out.println("Total Files: " + allFiles.length);
		String[] files = new String[allFiles.length];
		for (int i = 0; i < allFiles.length; i++) {
			files[i] = allFiles[i].getName();
		}
		int index = 1;
		for (String name : files) {
			Scanner scan = new Scanner(new File("./cleaned-text/" + name));
			System.out.println(index + " " + name);
			String body  = "";
		    while(scan.hasNextLine()){
		    	String line = scan.nextLine().toLowerCase();
		        
//		    	if (i == 2) {
		    		body = line.toLowerCase();
//		    	}
		    }
		    
		    String[] body_words = body.split(" ");
		    
		    Collections.addAll(words, body_words);
		}
		
//		Collections.sort(words);
		
		HashSet<String> set = new HashSet<String>(words);
		writeToFile("dictionary.txt", set);
		
	}
	
	public static List<String> suggestions(String input) throws FileNotFoundException {
		
		List<String> words = getFromFile("dictionary.txt");
		
		List<String> suggestions = new ArrayList<String>();
		
		for (String word : words) {
			if (editDistance(input.toLowerCase(), word) == 1) {
				suggestions.add(word);
			}
		}
		
		return suggestions;
	}
	
	public static void main(String[] args) throws IOException {
		
	makeDictioanry();
		
		
		
		String input = "play";
		List<String> sugg = suggestions(stemming(input));
		
		for (String word : sugg) {
			if (sugg.size() > 1) {
				System.out.print(word + " | " );
			} else  {

				System.out.println(word);		
			}
		}
		
	}
}
