/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import com.its.util.IOMaster;
import com.its.util.Stringer;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author duncan
 */
public class SearchEngine {
    protected String page;
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
        
        //methods
        
        public String getPage() 
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
                this.page = page;
        }
        
        
        final public void readWebPage()
        {
                page = IOMaster.readTextFile(url);
                
                
        }
        public void setKeywords(String keywords){
            words = Stringer.split(" ", keywords);
        }
        
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
        public String formHtml(String[] headers, List<String[]> listOfSortedArrays) {
            String html = "<table class='results'><tr>";
            int numberOfCellsInTheText=headers.length;
            for (int i=0;i<numberOfCellsInTheText;i++){
                html+="<th>"+headers[i]+"</th>";
            }
            html+="</tr>";
            for(int i=0; i<listOfSortedArrays.size();i++){
                html += "<tr>";
                String[] data = listOfSortedArrays.get(i);
                for(int j=0;j<data.length;j++){
                    html += "<td>"+data[i]+"</td>";
                }
                html+="</tr>";
            }
            html+="</table>";
            return html;
        }
    
}
