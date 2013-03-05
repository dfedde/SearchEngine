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

    public SearchLinks(String url) {
        super(url);
    }

    public SearchLinks(String url, String keywords) {
        super(url, keywords);
    }

    public List<String[]> findLinks() {
        //define strings    
        String[] links;
        List<String[]> results = new ArrayList<>();
        readWebPage();

        //build an array out of the links in tha page 
        links = Stringer.split("<a", getPage());

        // for every link in the array
        for (String link : links) {
            link = Parser.parseWithPatterns(link, "before,</a>");
            /*
             * get the URL
             */


            //get the index of the first carictor of the link destanation 
            int destIndex;//TODO what if href= dose not exits?
            if(link.indexOf("href=") >= 0) {
                destIndex = link.indexOf("href=") + 5;
            } else {
                continue;
            }
            int destIndexEnd;// the last caricto of the destanation

            String desntanation;

            //find the end of the string       
            //find the witch is closer a space or a end dimond 
            destIndexEnd = link.indexOf(">", destIndex);
            //if it has a qotation mark
            if (link.charAt(destIndex) == '"' || link.charAt(destIndex) == '\'') {
                desntanation = link.substring(destIndex + 1, destIndexEnd - 1);
            } else {
                desntanation = link.substring(destIndex, destIndexEnd);
            }
            /*
             * get the lable 
             */

            //get the index of the first carictor of the link destanation 
            int labelIndex = link.indexOf(">") + 1;//TODO what if href= dose not exits?
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

            /*
             * get the score
             */

            //System.out.println(sentences[i]);
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
            results.add(new String[]{desntanation,label,counter+""});
        }
        return results;
    }

    public void resultsWriter() {
        System.out.println(findLinks());
    }

    public static void main(String[] Args) {
        SearchLinks test = new SearchLinks("http://netbeans.org/kb/docs/java/quickstart.html");
        test.setKeyWordsWithInput();
        test.resultsWriter();
    }
}
