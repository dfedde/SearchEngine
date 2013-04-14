/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import org.jsoup.nodes.Document;

/**
 *
 * @author duncan
 */
public class WordScraper extends Scraper{
	
	/**
	 * grabs all a keyword off a page making sure it is uniqe to the
	 * database 
	 * @return if the it was sucsesfull
	 */
        public WordScraper(String url){
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
		//paring words twice
		String bText = doc.body().text();
		//all <p> tags are in the body
		String pText = doc.select("p").text();
		//need <title> all <h{}> <ul> <ol> tags not shure of the best way as some will be duplaates
		String allText = doc.text();
		String pageText = allText + pText + bText;
		String[] pageTxtSplit = pageText.split("\\s+");
                String[][] urlAndPageWords = new String[pageTxtSplit.length][2];
                urlAndPageWords[0][0] = String.valueOf(dbMethods.getUrlID(super.getUrl()));
                
					 
		//For loop to add each item from the currentUrlDataArray into the currentURLDataList
		for(int i = 0; i < pageTxtSplit.length; i++) {
			
                       urlAndPageWords[i][0] = urlAndPageWords[0][0];
                       urlAndPageWords[i][1] = pageTxtSplit[i];
		}
        dbMethods.AddKeywordUniqueToDB(urlAndPageWords);
        return true;
        }
        
        
        public static void main(String[] args)
        {
            WordScraper myScraper = new WordScraper("http://www.colorado.gov/");
            if(myScraper.Scrape());
            {
                System.out.println("That worked");
            }
            
        }
}

