/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import com.its.util.IOMaster;
import com.its.util.Stringer;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author duncan
 */
public class SearchEngine {
    protected Document page;
    private String url;
    protected String[] words;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
        
        
        //constructor
        public SearchEngine(String url )
        {
                this.url = url;
        }
        
        public SearchEngine(String url, String keywords )
        {
                this.url = url;
                words = Stringer.split(" ", keywords);
        }
        
        public SearchEngine()
        {
                url = "";
        }
        
        //methodsd
        
        public Document getPage() 
        {
                return page;
        }
        
        //builds a string fron the results of a search
        public static String StringResults(List<String[]> results){
            String returns = "";
            for (int i = 0; i <  results.size(); i++){
                for (String string : results.get(i)){
                    returns += string+"\t\t";
                }
                returns += "\n\n";
            }
            return returns;
        }
        
        public void setPage(String page) 
        {
            this.page = Jsoup.parse(page);
        }
        
        /**
         * reads web page to a document 
         */
        final public boolean readWebPage()
        {
            // if the url is not emtey
            if(!url.isEmpty()){
                try {
                // get a documet from to the webpage
                    this.page = Jsoup.connect(url).get(); 
                } catch (IOException ex) {
                    Logger.getLogger(SearchEngine.class.getName()).log(Level.INFO, "could not conect to webpage", ex);
                    return false;
                }
                //horay you read the web page
                return true;
            }
            // it was emty
            return false;
        }
        /**
         * parses space sprated keywords;
         * @param keywords 
         */
        public void setKeywords(String keywords){
            words = Stringer.split(" ", keywords);
        }
        
        /**
         * gets the keyword form imput separates on a space
         */
        public void setKeyWordsWithInput()
	{
            	Scanner keyboard = new Scanner(System.in); 
                String keywords;
		System.out.println("Please enter your search criteria:");
		keywords = keyboard.nextLine();
		words = Stringer.split(" ", keywords);
	}
        
        /**
         * genarates a string based 2 arrays 
         * @param the titles of the table 
         * @param the content of the table  
         * @return html string of a table
         */
        public static String formHtml(String[] headers, List<String[]> listOfSortedArrays) {
            String html = "<table class='results'><tr>";
            int numberOfCellsInTheText=headers.length;
            for (int i=0;i<numberOfCellsInTheText;i++){
                html+="<th>"+headers[i]+"</th>";
            }
            html+="</tr>";
            for(int i=0; i<listOfSortedArrays.size()-1;i++){
                html += "<tr>";
                String[] data = listOfSortedArrays.get(i);
                for(int j=0;j<data.length;j++){
                    html += "<td>"+data[j]+"</td>";
                }
                html+="</tr>";
            }
            html+="</table>";
            return html;
        }
        public static String resultsToHtml(List<String[]> results){
            String placeHolder = "<a herf='%s'>%s</a><br/><p>%s</p>";
            String html = "<h1>Search Reasults</h1>";
            html+="<ul>";
            for(String[] result: results){
                html += "<li>";
                html += String.format(placeHolder, result[0], result[1], result[2]);
                html+="</li>";
            }
            html+="</ul>";
            return html;
        }
    
}
