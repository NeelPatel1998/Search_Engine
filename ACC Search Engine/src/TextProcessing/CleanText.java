/**
 *  Developed by Sayee Shruthi
 */


package TextProcessing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;



public class CleanText {
	
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
	
	public static String[] getfilesindir(File dir) {
		File[] files = dir.listFiles();
		String[] allfiles = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			allfiles[i] = files[i].getName();
		}
		
		return allfiles;
	}
	
	public static String stemming(String text) throws IOException{
        StringBuffer result = new StringBuffer();
        if (text!=null && text.trim().length()>0){
            StringReader tReader = new StringReader(text);
            Analyzer testAnalyzer = new StopAnalyzer();
            TokenStream tokenStream = testAnalyzer.tokenStream("contents", tReader);
            
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
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
	
	
	public static void main (String[] args) throws IOException {

		File dir = new File("./TextFiles");

	    StringUtils st =new StringUtils();
		String[] filenames =  getfilesindir(dir);
		
		for (String name : filenames) {
			Scanner scan = new Scanner(new File("./TextFiles/" + name));
			int i = 0;
			String body  = "";
		    while(scan.hasNextLine()){
		    	String line = scan.nextLine().replaceAll("\\p{P}", "");
		        
		    	if (i == 2) {
		    		body = line;
		    	}
		        i += 1;
		       
		    }
		    
		    
		    body = st.removeStopWords(body);
		    body = stemming(body);
		    
		    File dest = new File ("cleaned-text");
		    if(dest.exists()) {
		    	List<String> str = new ArrayList<String>();
				str.add(body);
				writeToFile(dest.getAbsolutePath() + "\\" + name, str);
				System.out.println("Saved " + dest.getAbsolutePath() + "\\" + name);
		    }
		    else {
		    	dest.mkdir();
		    	List<String> str = new ArrayList<String>();
				str.add(body);
				writeToFile(dest.getAbsolutePath() + "\\" + name, str);
				System.out.println("Saved " + dest.getAbsolutePath() + "\\" + name);
		    	
		    }
			
		}
	}
}
