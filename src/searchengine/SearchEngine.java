/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import com.its.util.IOMaster;
import com.its.util.Stringer;
import java.util.List;

/**
 *
 * @author duncan
 */
public class SearchEngine {
    private String page;
    private String url;
    protected String[] words;
        
        
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
        public void setKeywordsWithInput(String inputKeywords)
	{
		
		
	}
    
}
