/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import com.its.util.Parser;
import com.its.util.Stringer;
import java.util.List;

/**
 *
 * @author duncan
 */
public class SearchLinks extends SearchEngine {
        
        public SearchLinks(String url)
        {
                super(url); 
        }
        
        public SearchLinks()
        {
                
        }
        public List<String[]> findLinks()
        {
            //define strings    
            String[] links;
            List<String[]> results = null;
            readWebPage();
            
            //build an array out of the links in tha page 
            links = Stringer.split("<a", getPage());
            
            // for every link in the array
            for (String link : links) {    
                    link = Parser.parseWithPatterns(link,"before,</a>");
                    /*
                     * get the URL
                     */    
                    
                    
                    //get the index of the first carictor of the link destanation 
                    int destIndex = link.indexOf("href=")+5;//TODO what if href= dose not exits?
                    int destIndexEnd;// the last caricto of the destanation

                    String desntanation;

                    //find the end of the string       
                    //find the witch is closer a space or a end dimond 
                    destIndexEnd = (link.indexOf(" ", destIndex)<link.indexOf(">", destIndex))?
                             link.indexOf(" ", destIndex): link.indexOf(">", destIndex);

                    //if it has a qotation mark
                    if(link.charAt(destIndex+1)=='"'||link.charAt(destIndex+1)=='\''){
                        destIndex+=1;
                        destIndexEnd-=1;
                    }
                    desntanation = link.substring(destIndex, destIndexEnd);
                    
                    /*
                     * get the lable 
                     */
                    
                    //get the index of the first carictor of the link destanation 
                    int labelIndex = link.indexOf(">");//TODO what if href= dose not exits?
                    int labelIndexEnd;// the last caricto of the destanation

                    String label;

                    //find the end of the string 
                    labelIndexEnd = link.indexOf("<", labelIndex);
                    label = link.substring(labelIndex, labelIndexEnd);
                    
                    /*
                     * get the score
                     */
                    	
			//System.out.println(sentences[i]);
			int counter = 0;
			for (String word: words)
			{
				if(link.indexOf(word) >= 0) 
				{
					counter++;
				}
			}

                        //cancatanate the values 
                    
            }

            return results;
        }
        //turns results into a string TODO
        public void resultsWriter()
        {
                System.out.println(findLinks());
        }
        
        public static void main (String[] Args)
        {
                SearchLinks test = new SearchLinks("http://netbeans.org/kb/docs/java/quickstart.html");
                test.resultsWriter();
        }

    

}
