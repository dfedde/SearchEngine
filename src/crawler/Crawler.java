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
	public static void runLinkScraper(){
		LinkScraper links = new LinkScraper();
		//set link to last array
		while(links.scrape()){
		//set links to last array -1 	
		}
	}
	/**
	 * sets the word scraper to the next real page 
	 */
	public static void runwordScraper(){
		WordScraper words = new WordScraper(null);
		//set link = to first link with no date
		while(words.Scrape()){
			//set link to next array without a date
		}
	}
	
	public static void main(String... args){
		for (int i = 0; i < 10; i++) {
			runLinkScraper();
			runwordScraper();
		}
	}
	
}
