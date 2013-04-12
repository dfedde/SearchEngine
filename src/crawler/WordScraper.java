/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;

//import search_engine.SearchKeywords;
//////////////
//////////////

public class WordScraper extends Scraper {

	public WordScraper (String url) {
		super(url);
	}
        
        public WordScraper (){
            super();
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
                
		//Read page for current url
		if(!super.readWebPage()) {
			return false;
		}
		
                //Parse web page into pageTxtSplit[]
		Document doc = super.getPage();
		String bText = doc.body().text();
		String pText = doc.select("p").text();
		String allText = doc.text();
		String pageText = allText + pText + bText;
		String[] pageTxtSplit = pageText.split("\\s+");
					 
		//For loop to add each item from the currentUrlDataArray into the currentURLDataList
		for(int i = 0; i < pageTxtSplit.length; i++) {
			
                        
		return true;
			
		}
	
}