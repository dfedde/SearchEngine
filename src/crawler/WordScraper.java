/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import com.its.util.StringMaster;
import com.its.util.Stringer;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;

/**
 *
 * @author Julian
 */
public class WordScraper extends Scraper{
	
        //Array list that will be used to check for duplicate data on each page
        List<String> checkForDuplicates = new ArrayList<String>();
    
	/**
	 * grabs all a keyword off a page making sure it is uniqe to the
	 * database 
	 * @return if the it was succesfull
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
                //All table text in the body
                String tableTxt = doc.select("table").text();
		//need <title> all <h{}> <ul> <ol> tags not shure of the best way as some will be duplaates
		String allText = doc.text();
		String pageText = allText + pText + bText;
		String[] pageTxtSplit = allText.split("\\s+");
                
                //Collect all unique words from the pageTxtSplit array into array list
                for(int i = 0; i < pageTxtSplit.length; i++) {
                    if(checkForDuplicates.contains(pageTxtSplit[i]))
                        {
                            continue; //already grabbed this word on this page
                        }
                        checkForDuplicates.add(pageTxtSplit[i]);
                }
                //Array that will be sent into te DB methods
		String[][] urlAndPageWords = new String[checkForDuplicates.size()][2];
                
		//For loop to add each item from the checkForDuplicates list into the urlAndPageWords
		for(int j = 0; j < checkForDuplicates.size(); j++) {
                      
                        urlAndPageWords[j][0] = String.valueOf(dbMethods.getUrlID(super.getUrl()));
                        urlAndPageWords[j][1] = checkForDuplicates.get(j);
		}
        //Check words against DB
        urlAndPageWords = dbMethods.searchKeyword(urlAndPageWords);
        //Add words to DB
        dbMethods.createKeywords(urlAndPageWords);
        return true;
        }
        
        public boolean insertStopWords(){
                KeywordDB dbMethods = new KeywordDB();
                if (!dbMethods.MakeDBConnection()){
                    System.out.println("DB Connection Failes");
                    return false;
                }
		//Read page for current url
		if(!super.readWebPage()) {
			System.out.println("Ooooopppppsssss! Couldn't read the web page");
                        return false;
		}
		
                //Parse web page into pageTxtSplit[]
		Document doc = super.getPage();
                String pageText = doc.select("table").text();
		String[] pageTxtSplit = pageText.split("\\s+");
                for(int i = 0; i < pageTxtSplit.length; i ++){
                    if (pageTxtSplit[i].contains("'")){
                        int length = pageTxtSplit[i].length();
                        int aposLoc = pageTxtSplit[i].indexOf("'");
                        pageTxtSplit[i] = pageTxtSplit[i].substring(0, aposLoc) + "'" + 
                                            pageTxtSplit[i].substring(aposLoc, length);
                        
                    }
                }
                dbMethods.createStopWord(pageTxtSplit);
            
            return true;
        }
        
        
      public static void main(String[] args){
                WordScraper myScraper = new WordScraper(" ");
                KeywordDB dbMethods = new KeywordDB();
                dbMethods.MakeDBConnection();
		//Read page for current url
		if(!myScraper.readWebPage()) {
			System.out.println("Ooooopppppsssss!");
		}
                        
        }
}



