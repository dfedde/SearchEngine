/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

/**
 *
 * @author duncan
 */
public class Crawler {
	
	/**
	 * sets the link scraper to the net real page 
	 */
	public static void runLinkScraper(KeywordDB DB){
                String test = DB.getLastLink(0);
                System.out.println("LinkScraperLink:" + test);
            
                //set link to last array
		LinkScraper links = new LinkScraper(test);
		int counter = 1;
		while(!links.Scrape()){
			//set links to last array -1
			links.setUrl(DB.getLastLink(counter));
                        counter++;
		}
	}
	/**
	 * sets the word scraper to the next real page 
	 */
	public static void runwordScraper(KeywordDB DB){
                String test = DB.getFirstLink();
                System.out.println("WordScraperLnik:" + test);
		WordScraper words = new WordScraper(test);
		//set link = to first link with no date
	
                while(!words.Scrape()){
			words.setUrl(DB.getFirstLink());
                        
			//set link to next array without a date
		}
	}
	
	public static void main(String... args){
		KeywordDB DB = new KeywordDB();
		//TODO: add error protection
		DB.MakeDBConnection();
		for (int i = 0; i < 10; i++) {
			runLinkScraper(DB);
			runwordScraper(DB);
		}
	
        }
	
}
