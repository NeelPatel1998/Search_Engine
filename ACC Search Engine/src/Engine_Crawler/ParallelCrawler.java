/**
 *  Developed by Abishek Rajagopal
 */

package Engine_Crawler;

import java.io.IOException;

public class ParallelCrawler extends Thread{  
	
	public EngineCrawlerStart geeks;
	
	ParallelCrawler()
	   {
		geeks = new EngineCrawlerStart(1000);
	     start();
	     
	   }
	   public void run() 
	   {
	     
		   try
		   {
		  
			geeks.startgeekBase("https://www.geeksforgeeks.org/", "https?:\\/\\/(([^\\/]*\\.)|)geeksforgeeks\\.org(|\\/.*)",1000);
				
			geeks.CreateTree();
			
			
			geeks.Reset(10);
			 
			geeks.startgeekBase("https://www.javatpoint.com/", "https?:\\/\\/(([^\\/]*\\.)|)javatpoint\\.com(|\\/.*)",1000);
			
			geeks.CreateTree();
			
			
			geeks.Reset(10);
			   
			geeks.startgeekBase("https://www.w3schools.com/", "https?:\\/\\/(([^\\/]*\\.)|)w3schools\\.com(|\\/.*)",1000);
			
			geeks.CreateTree();
			
			
			geeks.Reset(10);
			   
			
			geeks.startgeekBase("https://www.tutorialspoint.com/", "https?:\\/\\/(([^\\/]*\\.)|)tutorialspoint\\.com(|\\/.*)",1000);
			
			geeks.CreateTree();
			
				    
		   }
		   
		   catch (IOException e) {
   			System.out.println("An error occurred.");
   			e.printStackTrace();
   	}
	     
	     System.out.println("My thread run is over" );
	     geeks.print();
	     geeks.closeFile();
	   }
	}
