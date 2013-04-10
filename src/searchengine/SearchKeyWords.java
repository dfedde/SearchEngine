package searchengine;

import com.its.util.Stringer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

//import search_engine.SearchKeywords;
//////////////
//////////////

public class SearchKeyWords extends SearchEngine {

	private String[] lineEnders = { ".", "?", "!", "\n" };

	public SearchKeyWords(String url) {
		super(url);
	}

	public SearchKeyWords(String url, String keywords) {
		super(url, keywords);
	}

	public List<String[]> searchTextSentences() {
		List<String[]> listOfStringArrays = new ArrayList<>();
		// if you cant read the page dont bother reading it
		if (readWebPage()) {
			// get all the text from the body
			Elements pagetext = page.getElementsByTag("body");
			String pageContent = pagetext.text();
			// split the page up
			String[] sentences = Stringer.split(pageContent, lineEnders, true);

			for (String sentence : sentences) {
				sentence = sentence.replace("\n", "").trim().toLowerCase();
				// System.out.println(sentence);
				int counter = 0;
				for (String word : words) {
					// make it not case sensitive
					word = word.toLowerCase();

					if (sentence.indexOf(word) >= 0) {
						counter++;
					}
				}
				if (counter == 0) {
					continue;
				}
				// print some debug info
				System.out.println(sentence + ": " + counter);
				listOfStringArrays.add(new String[] { sentence, counter + "" });
				// order the results
				listOfStringArrays = Stringer.sort(listOfStringArrays, 1);
			}
		}
		return listOfStringArrays;
	}

	/**
	 * returns a list of links with a snipit off the page
	 * 
	 * @param list
	 *            of links
	 * @return link and snipit of page {url,snipit}
	 * @calls searchTextSenteces
	 */
	public List<String[]> searchPages(List<String[]> listOfLinks) {
		List<String[]> resaults = new ArrayList<>();
		// call searchtextsentics for evry link and make aray for each
		for (String[] link : listOfLinks) {
			// array to store the data from search textsentices
			List<String[]> relaventResaults = new ArrayList<>();
			String url;
			// get the url
			// set the new url
			setUrl(url = link[0]);
			// get the resaults
			relaventResaults = searchTextSentences();
			// get the fist to resalts and concatanate them
			// if the page actual contains the keywords
			if (!relaventResaults.isEmpty()) {
				resaults.add(new String[] { url, link[1],
						relaventResaults.get(0)[0], link[2] });
			} else {
				resaults.add(new String[] { url, link[1],
						"the page dose not contain your keywords", link[2] });
			}

		}
		return resaults;
	}

	/**
	 * This function will take a list of urls and pull ALL of the
	 * words from each page into an array list, which will then be converted
	 * into an array. The returned array will be a two dimensional array which
	 * emulates the DB
	 * 
	 * @param String
	 *            [] a list of unique urls;
	 * @return String[String[][] an array of ["url", "keyword1"] for each url.
	 *         array is null if page has an error
	 */
	public String[][] ScrapeWords(List<String[]> listOfUrls) {	 
			 //Array list to help process the webpage
			 List<String[]> allUrlsAndWordsList = new ArrayList<>();	
			 //Counter variable which is used at the end of the code to help populate final array
			 int count = 0;
			//Default string[] to return in case DB connection fails
			 String[][] defaultReturn = {{"Connection to DB could not be established"}, {""}};

			 	//Try-Catch to register the JDBC Driver
				try{
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("MySQL JDBC Driver Registered!");
				} catch (ClassNotFoundException e) {
					System.out.println("Where is your MySQL JDBC Driver?");
					return defaultReturn;
				}
				
				Connection connection = null;
				
				//Make connection to the database
				try{
					connection = DriverManager
							.getConnection("jdbc:mysql://localhost/SearchEngineDB","root", null);
				} catch (SQLException e){
					System.out.println("Connection Failed!! Check output console");
					e.printStackTrace();
					return defaultReturn;
				}
				
				//DB connection report block
				if (connection != null) {
					System.out.println("Connection Succesful!");
				} else {
					System.out.println("Failed to make connection!");
				}
		
				//Cycle through all of the urls passed into the method
				for(int i = 0; i < listOfUrls.size(); i++){
						//Set url in super class to current url
						super.setUrl(listOfUrls.get(i)[0]);
					 
						//Read page for current url
						if(!super.readWebPage()) {
							continue;
						}
						//Parse web page into pageTxtSplit[]
						Document doc = super.getPage();
						String bText = doc.body().text();
						String pText = doc.select("p").text();
						String allText = doc.text();
						String pageText = allText + pText + bText;
						String[] pageTxtSplit = pageText.split("\\s+");
					 
						//For loop to add each item from the currentUrlDataArray into the currentURLDataList
						for(int j = 0; j < pageTxtSplit.length; j++) {
								/**
								 * Create a new two dimensional array to contain the current URL being searched 
								 * as wells as the current word being searched
								 */
								String[] currentUrlAndWordArray = {listOfUrls.get(i)[0], pageTxtSplit[j]};
									
									//Check DB to see if the current word already exists.
									//If it does not, then collect the word into our 
									boolean checkDB = super.CheckDataBaseData(connection, "Keywords", "keyword", pageTxtSplit[j]);
									if(checkDB){
										continue; //Word has already been added to DB
									}
									checkDB = super.CheckDataBaseData(connection, "Stop_Words", "word", pageTxtSplit[j]);
									if(checkDB){
										continue; //Word has already been added to DB
									}
									
								//Add the currentUrlAndWordArray to the allUrlsAndWordsList
								//Increment count so we know how big to make finalUrlsAndwordsArr
								allUrlsAndWordsList.add(currentUrlAndWordArray);
								count++;
						}
				} //End cycle through all of the urls
				
				//Creat and populate finalUrlsAndWordsArr with data from allUrlsAndWordsList
				//
				String[][] finalUrlsAndWordsArr = new String[count][2];
				for(int k = 0; k < count; k++) {
					finalUrlsAndWordsArr[k][0] = allUrlsAndWordsList.get(k)[0];
					finalUrlsAndWordsArr[k][1] = allUrlsAndWordsList.get(k)[1];
					System.out.println(finalUrlsAndWordsArr[k][0] +" : "+ finalUrlsAndWordsArr[k][1]);
				}
			
			//Close DB connection
			try{
				connection.close();
		    } catch (Exception ex) {
		    	  System.out.println("Error during DB connection.close");
		    }
			
			return finalUrlsAndWordsArr;
		}
	
	


	public static String[][] findKeywords(String[] urls) {
		return null;
	}

	public static void main(String[] args) {
		SearchLinks testUrlSearch = new SearchLinks("http://www.colorado.gov");
		SearchKeyWords testWordSearch = new SearchKeyWords("http://www.colorado.gov");
		List<String[]> urlList = new ArrayList<>();
		testUrlSearch.findLinksDB(urlList);
		String[][]test = testWordSearch.ScrapeWords(urlList);
		System.out.println("Complete");

	}
}
