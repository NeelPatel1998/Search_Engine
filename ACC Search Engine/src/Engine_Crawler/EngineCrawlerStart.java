/**
 *  Developed by Abishek Rajagopal
 */

package Engine_Crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EngineCrawlerStart {
	
	public CrawlerSplay<String> urlCrawler = new CrawlerSplay<String>( );

    public int index=0;
    public int Length;
    ArrayList<String> urllist;
    FileWriter myWriter;
    
    public EngineCrawlerStart(int len)
    {
    	urllist = new ArrayList<String>();
    	Length = len;
    	
    	try {
		      File myObj = new File("Output/GeekCrawler.txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
    	
    	try {
    		myWriter = new FileWriter("Output/GeekCrawler.txt");
    		
    	}
    		
    	catch (IOException e) {
    			System.out.println("An error occurred.");
    			e.printStackTrace();
    	}
    }
    
    public void startgeekBase(String url, String regex, int Len)  throws IOException{
    	Length = Len;
    	geekBase(url, regex);
    }
    

	public void geekBase(String url, String regex) throws IOException {
		
		if(index >= Length)
		{
			return;
		}

		try {
	        Document document = Jsoup.connect(url).get();

	        Elements hrefs = document.select("a[href]");
	        
	        for (Element href : hrefs) {
	        	String hrefLink = href.attr("abs:href").toString();
	        	if (hrefLink.matches("[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)") && hrefLink.matches(regex) )  {
	        		if (urllist.add(hrefLink))
	        		{
	        			index++;
	        		}
	        		
//	        		System.out.println(hrefLink);
	        		geekBase(hrefLink, regex);
	        		
	        		
	        	}
	        }
    	} catch (IOException e) {
          System.err.println("For '" + url + "': " + e.getMessage());
      }
		
	}
	
	public void CreateTree()
	{
		 
		 for(String url:urllist) 
		 {     
			 
			    urlCrawler.insert(url);
			   
		 } 
	 
	}
	
	public void print()
	{

	      urlCrawler.printTreeinFile(myWriter);
	      System.out.println("Successfully wrote to the file.");
	 
	}
	
	public void closeFile()
	{

		try {
			myWriter.close();
    		
    	}
    		
    	catch (IOException e) {
    			System.out.println("An error occurred.");
    			e.printStackTrace();
    	}
	 
	}
	
	public void Reset(int len)
	{

		index=0;
	    Length = len;
	 
	}
}
