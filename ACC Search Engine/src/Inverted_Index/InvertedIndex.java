/**
 *  Developed by Dishant Shah
 */


package Inverted_Index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import TextProcessing.Suggestions;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class InvertedIndex {
    Map<Integer,String> source; 				// for storing text files
    HashMap<String, HashSet<Integer>> index; 	// for storing words
    Ranking rank;
    Suggestions similar;
    private int patternNumber = 80;
	private String format = "%-10s%-10s%-20s%-20s\n";
    
    public InvertedIndex() {
        source = new HashMap<Integer,String>();
        index = new HashMap<String, HashSet<Integer>>();
        rank = new Ranking();
    }

    public void initializeIndex() throws IOException{
        
		File directory = new File("cleaned-text");
		File[] files = directory.listFiles();
       	
		for(int i = 0; i < files.length; i++) {
			try(BufferedReader file = new BufferedReader(new FileReader(files[i])))
	            {
	                String line;
	                source.put(i, files[i].getName().trim()); 	
	                
	                while((line = file.readLine()) != null) {   
	                	String[] words = line.split("\\W+");
	                    
	                    for(String word : words){
	                    	word = word.toLowerCase();
	                        
	                    	if (!index.containsKey(word))  	
	                            index.put(word, new HashSet<Integer>());
	                    	index.get(word).add(i);
	                    }
	                }
	         } catch (IOException e){
	        	 System.out.println("Error! " + files[i].getName() + " file not found!");
	           }
	        }
		this.initializeFrequency();
    	}
    
    // Initialize Frequency of each word
    public void initializeFrequency() throws IOException{
    	// Directory for cleaned text files
        File webSites = new File("cleaned-text");
		File[] sites = webSites.listFiles();
       	
		for(int i = 0; i < sites.length; i++) {
			try(BufferedReader file = new BufferedReader(new FileReader(sites[i])))
	            {
	                String line;
	                source.put(i, sites[i].getName().trim()); 	
	                
	                while((line = file.readLine()) != null) {   
	                    String[] words = line.split("\\W+");    
	                    
	                    for(String word : words){
	                    	word = word.toLowerCase();
	                        
	                    	if (!rank.frequency.containsKey(word)) {
	                    		HashMap<Integer,Integer> freq = new HashMap<Integer,Integer>();
	                    		freq.put(i,1);
	                    		
	                    		rank.frequency.put(word,freq);
	                    	} else {
	                    		HashMap<Integer,Integer> freq = rank.frequency.get(word); //get word source
	                    		
	                    		if(!freq.containsKey(i)) {	//if source word was not recorded
	                    			freq.put(i, 1);			//source, appeared once
	                    		} else {
	                    			freq.replace(i, freq.get(i)+1);//frequency +1
	                    		}
	                    		
	                    	}
	                    }
	                    
	                }
	         } catch (IOException e){
	        	 System.out.println(sites[i].getName() + " file not found!");
	           }
	        }
    	}
    
        public List<String> search(String search) throws IOException {
        String[] words = search.split("\\W+");
        
        if(!index.containsKey(words[0].toLowerCase())) {
            List<String> sugg = similar.suggestions(similar.stemming(words[0].toLowerCase()));
	    	for (String word : sugg) {
	    		if (sugg.size() > 1) {
	    			System.out.println("Did you mean any of these words? ");
	    			System.out.print(word + " | " );
	    	    	return null;
	    		} 
	    		else {
	    			System.out.println("Showing results for " + word);
	    			words[0] = word;
	    		}
	    	}
	    	if(sugg.size() == 0)
	    	{
	    		System.out.println("Could not find results. Please try again with another search");
	    		return null;
	    	}
        }
        else {
        	System.out.println("Showing results for " + search);
        }
        List<String> files = new ArrayList<String>();
	    List<Entry<Integer,Integer>> results = rank.sortMultipleWords(words);
	    int index = 1;
	    System.out.println("Total Results: " + results.size());
	    System.out.print("-".repeat(patternNumber ) + "\n");
	    System.out.format(format , "Index", "ID", "Frequency", "Title");
        System.out.print("-".repeat(patternNumber ) + "\n");
	        
	    HashSet<String> hs = new HashSet<String>();
	    for (Entry<Integer, Integer> entry : results) {
	    	String filename = source.get(entry.getKey());
	        final List<String> lines = Files.readAllLines(Paths.get("TextFiles/" + filename), StandardCharsets.ISO_8859_1);
	        if (!hs.contains(lines.get(1))) {
	        	System.out.format(format, index, filename, entry.getValue(), lines.get(1));
	        	files.add(lines.get(0));
	        	files.add(lines.get(1));
	        	hs.add(lines.get(1));
	        }
	        index += 1;
	    }
	    System.out.print("-".repeat(patternNumber) + "\n");
	    return files;
    }
    public static void main(String[] args) throws IOException{
		InvertedIndex index = new InvertedIndex();
		index.initializeIndex();
		
		System.out.print("Enter a term to search? ");
	    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	    String search = input.readLine();
	    index.search(search);
	}
}

