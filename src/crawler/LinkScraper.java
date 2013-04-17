
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import searchengine.SearchEngine;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.its.util.IOMaster;
import com.its.util.Stringer;
/**
 *
 * @author Oralndo
 */
public class LinkScraper extends Scraper {
	
        public String page;
	public String url;
	private List<String> listOfUrls = new ArrayList<String>();
	
	/**
	 * scrapes a link off a page making sure it is uniqe to the
	 * database 
	 * @return the word it found [url_id, word]
	 */

	public boolean scrape(){
            
                KeywordDB dbMethods = new KeywordDB();
            
            
		if(page.startsWith("ERROR:")) 
		{ 
			return false; 
		} 
		
		String[] links = Stringer.split("<a ", page); 
		for(int i=1; i < links.length; i++) 
		{ 
			// find a link 
			String link = links[i]; 
			int endLinkIndex = link.indexOf("</a>"); 
			if(endLinkIndex <= 0) 
			{ 
				continue; // not a valid link 
			} 
			
			link = link.substring(0, endLinkIndex); 
			
			// find a url 
			int startUrlIndex = link.indexOf("href="); 
			if(startUrlIndex < 0) 
			{ 
				continue; // not a valid url 
			} 
			startUrlIndex += 5; // count for 5 characters in the "href=" 
			int endUrlIndex = link.indexOf(">", startUrlIndex); 
			String url = link.substring(startUrlIndex, endUrlIndex-1); 
			if(url.startsWith("\"")) // check for the starting quote, for example, href=“http://easy.com”>
			{ 
				 url = url.substring(1); // remove the starting quote }
			}
			
			String urlInLowCase = url.toLowerCase();
			if(!urlInLowCase.startsWith("http") && !urlInLowCase.startsWith("www.")) 
			{
				continue; // this is a fancy internal URL, cannot be used to read the page
			}
			
			int indexOfSpace = url.indexOf(" "); // look for the space after the URL
			if(indexOfSpace > 0) // get the URL before the space
			{ 
				url = url.substring(0, indexOfSpace);
			}
			if(url.endsWith("\"")) // check for the ending quote
			{ 
				url = url.substring(0, url.length() -1); // remove the ending quote
			}
                        if(urlInLowCase.endsWith(".jpg") || urlInLowCase.endsWith(".gif") ||
				urlInLowCase.endsWith(".png"))
			{
				continue; // do not read an image page
			}
                        if(listOfUrls.contains(url.toLowerCase())) 
			{
				continue; // been there, done that
			}
                        
			//initiate the KeywordDB
			//Create and if statement to check if the link is unique if so add the link to the database
			//all links will be lowercase
			if(dbMethods.searcsearchLiks.toLowerCase() == url.toLowerCase()){
                            continue;
                        }
                        dbMethods.createLink(url.toLowerCase());
			//
                        listOfUrls.add(url.toLowerCase()); // remember all urls visited
			//String[] arrayOfLinkParts = new String[] {url+""}; // score=number of keywords
			//listOfLinks.add(arrayOfLinkParts);
			// check if enough links found
			
			
			} // end the for loop for all links
				return false;
		} // end of the method
}