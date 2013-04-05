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

public class SearchKeyWords extends SearchEngine {

    private String[] lineEnders = {".", "?", "!", "\n"};

    public SearchKeyWords(String url) {
        super(url);
    }

    public SearchKeyWords(String url, String keywords) {
        super(url, keywords);
    }

    public List<String[]> searchTextSentences() {
        List<String[]> listOfStringArrays = new ArrayList<>();
        //if you cant read the page dont bother reading it 
        if (readWebPage()) {
            //get all the text from the body
            Elements pagetext = page.getElementsByTag("body");
            String pageContent = pagetext.text();
            //split the page up 
            String[] sentences = Stringer.split(pageContent, lineEnders, true);

            for (String sentence : sentences) {
                sentence = sentence
                        .replace("\n", "")
                        .trim()
                        .toLowerCase();
                //System.out.println(sentence);
                int counter = 0;
                for (String word : words) {
                    //make it not case sensitive
                    word = word.toLowerCase();

                    if (sentence.indexOf(word) >= 0) {
                        counter++;
                    }
                }
                if (counter == 0) {
                    continue;
                }
                //print some debug info
                System.out.println(sentence + ": " + counter);
                listOfStringArrays.add(new String[]{sentence, counter + ""});
                //order the results
                listOfStringArrays = Stringer.sort(listOfStringArrays, 1);
            }
        }
        return listOfStringArrays;
    }

    /**
     * returns a list of links with a snipit off the page
     *
     * @param list of links
     * @return link and snipit of page {url,snipit}
     * @calls searchTextSenteces
     */
    public List<String[]> searchPages(List<String[]> listOfLinks) {
        List<String[]> resaults = new ArrayList<>();
        //call searchtextsentics for evry link and make aray for each
        for (String[] link : listOfLinks) {
            //array to store the data from search textsentices
            List<String[]> relaventResaults = new ArrayList<>();
            String url;
            //get the url
            //set the new url 
            setUrl(url = link[0]);
            //get the resaults
            relaventResaults = searchTextSentences();
            // get the fist to resalts and concatanate them
            //if the page actual contains the keywords
            if (!relaventResaults.isEmpty()) {
                resaults.add(new String[]{
                            url, link[1], relaventResaults.get(0)[0], link[2]
                        });
            } else {
                resaults.add(new String[]{
                            url, link[1],
                            "the page dose not contain your keywords", link[2]
                        });
            }

        }
        return resaults;
    }


    /**   version 2
	 * This function will take a list of urls and 
	 * pull ALL of the words from each page into an array list, 
	 * which will then be converted into an array. 
	 * The returned array will be a two dimensional array 
	 * which emulates the DB
	 * @param String[] a list of unique urls;
	 * @return String[String[]  an array of ["url", "keyword1", "keyword2", ...]
	 * for each url. array is null if page has an error
	 */	 
		public String[] scrapeWords3(String[] listOfUrls) 
		{
			//final array list that will contain all urls and words
			 String[] allUrlsAndWordsArr;
			 
			 //Array list to help process the webpage
			 List<String[]> allUrlsAndWordsList = new ArrayList<>();	
			
			//Default string[] to return in case DB connection fails
			 String[] defaultReturn = {"Connection to DB could not be established"};
			 
			 System.out.println("--------- MySql JDBC Connetion Testing ------------");
				
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
				} 
				
				catch (ClassNotFoundException e) 
				{
					System.out.println("Where is your MySQL JDBC Driver?");
					return defaultReturn;
				}
				
				System.out.println("MySQL JDBC Driver Registered!");
				Connection connection = null;
				
				try
				{
					connection = DriverManager
							.getConnection("jdbc:mysql://localhost/javaDatabase","jwhitley", "password");
				}
				catch (SQLException e)
				{
					System.out.println("Connection Failed!! Check output console");
					e.printStackTrace();
					return defaultReturn;
				}
				
				if (connection != null) 
				{
					System.out.println("Connection Succesful!");
				} 
				else 
				{
					System.out.println("Failed to make connection!");
				}
				
		
	//cycle through all of the urls passed into the method
	 for(int i = 0; i < listOfUrls.length; i++)
	 {
		 	//Set url in super class to current url
			super.setUrl(listOfUrls[i]);
					 
			//Read page for current url
			super.readWebPage();
					 
			//Create a string array and parse the current url web page by spaces
			Document doc = Jsoup.parse(super.getPage());
					 
					 
			//Create a check to make sure that the url's are valid
					 
			//For loop to add each item from the currentUrlDataArray into the currentURLDataList
			for(int j = 0; j < currentUrlDataArray.length; j++)
				{
					 allUrlsAndWordsList.add
				}
					 	
					 	
					 		
					
	 }
				 
				 
			return allUrlsAndWordsArr;
		}

    public static String[][] findKeywords(String[] urls){
	    return null; 
    }
    
    
    public static void main(String[] args) {
        SearchKeyWords search = new SearchKeyWords("http://www.pageinc.org/");
        search.setKeyWordsWithInput();
        System.out.print(StringResults(search.searchTextSentences()));
    }
}
