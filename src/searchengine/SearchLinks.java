/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import com.its.util.Parser;
import com.its.util.Stringer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author duncan
 */
public class SearchLinks extends SearchEngine {
    
    private List<String[]> listOfLinks = new ArrayList<>();
    private int maxNumberOfLinks=100;
    private List<String> listOfUrls = new ArrayList<>();

    public SearchLinks(String url) {
        super(url);
    }

    public SearchLinks(String url, String keywords) {
        super(url, keywords);
    }

    public List<String[]> getListOfLinks() {
        return listOfLinks;
    }

    public void setListOfLinks(List<String[]> listOfLinks) {
        this.listOfLinks = listOfLinks;
    }

    public List<String[]> findLinks(List<String[]> listOfLinks) {
        //define strings    
        String[] links;
        readWebPage();
        
        if(page.startsWith("ERROR:")) { 
            return listOfLinks;
        } 
        
        //build an array out of the links in that page 
        links = Stringer.split("<a", getPage());

        // for every link in the array
        for (String link : links) {
            link = Parser.parseWithPatterns(link, "before,</a>");
            
            /*
             * get the URL
             */


            //get the index of the first carictor of the link destanation 
            int destIndex;
            if(link.indexOf("href=") >= 0) {
                destIndex = link.indexOf("href=") + 5;
            } else {
                continue;
            }
            int destIndexEnd;// the last caricto of the destanation

            String desntanation;

            //find the end of the string       
            //find the witch is closer a space or a end dimond 
            int space = link.indexOf(" ", destIndex);
            int openDimond = link.indexOf(">", destIndex); 
            
            if (space < openDimond && space > 0){
                destIndexEnd = space;
            }else{
                destIndexEnd = openDimond;
            }
            //if it has a qotation mark
            if (link.charAt(destIndex) == '"' || link.charAt(destIndex) == '\'') {
                desntanation = link.substring(destIndex + 1, destIndexEnd - 1);
            } else {
                desntanation = link.substring(destIndex, destIndexEnd);
            }
            System.out.print(desntanation+":  ");
            /*
             * get the lable 
             */

            //get the index of the first carictor of the link destanation 
            int labelIndex = link.indexOf(">") + 1;
            int labelIndexEnd; // the last caricto of the destanation

            String label;

            //find the end of the string 
            
            if(link.indexOf("<",labelIndex)<=0){
                labelIndexEnd = link.length();
            }
            else{
                labelIndexEnd = link.indexOf("<", labelIndex);
            }
            label = link.substring(labelIndex, labelIndexEnd);
            System.out.print(label+"\n");
            /*
             * get the score
             */

            int counter = 0;
            for (String word : words) {
                if (label.indexOf(word) >= 0) {
                    counter++;
                }
            }
            if (counter == 0) {
                continue;
            }
             

            //cancatanate the values 
            listOfLinks.add(new String[]{desntanation,label,counter+""});
            
            //do checks on the loop to see if it can be called again
            
            if(listOfLinks.size() >= maxNumberOfLinks) { 
                return listOfLinks; // enough links! we are done! 
            }
            
            if(desntanation.endsWith(".jpg") || desntanation.endsWith(".gif") ||    
                desntanation.endsWith(".png")) { 
                continue; // do not read an image page 
            }
            if(!page.startsWith("ERROR:")) { // check if page was readable 
                findLinks(listOfLinks); // recursive call 
            } 
        }//endo of for loop
        return listOfLinks;
    } 


    public void resultsWriter() {
        System.out.println(findLinks(listOfLinks));
    }

    public static void main(String[] Args) {
        SearchLinks test = new SearchLinks("http://netbeans.org/kb/docs/java/quickstart.html");
        test.setKeyWordsWithInput();
        test.resultsWriter();
    }
}
