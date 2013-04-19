/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author duncan
 */
public class LinkScraper extends Scraper{
	
        //Array list that will be used to check for duplicate data on each page
        List<String> checkForDuplicates = new ArrayList<String>();
    
	/**
	 * grabs all a keyword off a page making sure it is uniqe to the
	 * database 
	 * @return if the it was sucsesfull
	 */
        public LinkScraper(String url){
            super(url);
        }
       
	/**
	 * This method will take a list of urls and pull ALL of the
	 * words from each page into an array list, which will then be converted
	 * into an array. The returned array will be a two dimensional array which
	 * emulates the DB
	 * 
	 * @param String
	 *            [] a list of unique urls;
	 * @return String[String[][] an array of ["url", "keyword1"] for each url.
	 *         array is null if page has an error
	 */
	public boolean Scrape() {	 
		
		
		
                KeywordDB dbMethods = new KeywordDB();
                dbMethods.MakeDBConnection();
		//Read page for current url
		if(!super.readWebPage()) {
			return false;
		}
		
                //Parse web page into pageTxtSplit[]
		Document doc = super.getPage();
		Elements links = page.select("a[href]");
		String[] urls = new String[links.size()];
		for (int i = 0; i < links.size(); i++) {
			urls[i] = links.get(i).attr("href");
		}
                //Collect all unique words from the pageTxtSplit array into array list
                for(int i = 0; i < urls.length; i++) {
                    if(checkForDuplicates.contains(urls[i]))
                        {
                            continue; //already grabbed this word on this page
                        }
                        checkForDuplicates.add(urls[i]);
                }
                //Array that will be sent into te DB methods
		String[][] urlAndPageWords = new String[checkForDuplicates.size()][2];
                
		//For loop to add each item from the checkForDuplicates list into the urlAndPageWords
		for(int j = 0; j < checkForDuplicates.size(); j++) {
                      
                        urlAndPageWords[j][0] = String.valueOf(dbMethods.getUrlID(super.getUrl()));
                        urlAndPageWords[j][1] = checkForDuplicates.get(j);
		}
        //Check words against DB
        urlAndPageWords = dbMethods.searchLinks(urlAndPageWords);
        //Add words to DB
        dbMethods.createLink(urlAndPageWords);
        return true;
        }
        
        
      public static void main(String[] args){
            System.out.println("    ");
          
            LinkScraper myScraper = new LinkScraper("http://www.colorado.gov/");
            if(myScraper.Scrape());
            {
                System.out.println("That worked");
            }
        }
}
