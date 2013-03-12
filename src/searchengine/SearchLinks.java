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

    public List<String[]> findLinks(List<String[]> listOfLinks) { 
        readWebPage();
        if(page.startsWith("ERROR:")) { 
            return listOfLinks; 
        } 
        String[] links = Stringer.split("<a ", page); 
        for(int i=1; i < links.length; i++) { 
          // find a link 
          String link = links[i]; 
          int endLinkIndex = link.indexOf("</a>"); 
          if(endLinkIndex <= 0) { 
            continue; // not a valid link 
          } 
          link = link.substring(0, endLinkIndex); 

          // find a url 
          int startUrlIndex = link.indexOf("href="); 
          if(startUrlIndex < 0) { 
            continue; // not a valid url 
          } 
          startUrlIndex += 5; // count for 5 characters in the "href=" 
          int endUrlIndex = link.indexOf(">", startUrlIndex); 
          String url = link.substring(startUrlIndex, endUrlIndex-1); 
          if(url.startsWith("\"")) { // check for the starting quote, for example, href=“http://easy.com”> 
             url = url.substring(1); // remove the starting quote 
          }  
         String urlInLowCase = url.toLowerCase(); 
         if(!urlInLowCase.startsWith("http") && !urlInLowCase.startsWith("www.")) { 
           continue; // this is a fancy internal URL, cannot be used to read the page 
         } 
         int indexOfSpace = url.indexOf(" "); // look for the space after the URL 
         if(indexOfSpace > 0) {  // get the URL before the space 
           url = url.substring(0, indexOfSpace); 
         } 
         if(url.endsWith("\"")) {  // check for the ending quote 
           url = url.substring(0, url.length() -1); // remove the ending quote 
         } 
         if(listOfUrls.contains(url.toLowerCase())) { 
           continue; // been there, done that 
         } 
         listOfUrls.add(url.toLowerCase()); // remember all urls visited 
         String label = link.substring(endUrlIndex+1); 
         // count a score 
         int score = 0; 
         for(int j=0; j < words.length; j++) { 
           if(label.toLowerCase().indexOf(words[j].toLowerCase()) >= 0) { 
             score++; // count the number of keywords 
           } 
         }  
       if(score == 0) { 
         continue; // skip this link, no keywords found 
       } 
       String[] arrayOfLinkParts = new String[] {url, label, score+""}; // score=number of keywords 
       listOfLinks.add(arrayOfLinkParts); 
       // check if enough links found 
       if(listOfLinks.size() >= maxNumberOfLinks) { 
         return listOfLinks; // enough links! we are done! 
       } 
       if(urlInLowCase.endsWith(".jpg") || urlInLowCase.endsWith(".gif") ||    
          urlInLowCase.endsWith(".png")) { 
         continue; // do not read an image page 
       } 
       // read the page with the link  
       if(!page.startsWith("ERROR:")) { // check if page was readable 
         findLinks(listOfLinks); // recursive call 
       } 
      } // end the for loop for all links 
      return listOfLinks;  
      } // end of the method 
    
    /**
     * checks if the URL is a page
     * @param url
     * @return error code in the form of an integer
     */
    public static int checkLink(String url){
        int errorCode = 0;
        String LowerCaseUrl = url.toLowerCase();
        if(LowerCaseUrl.endsWith(".jpg") || LowerCaseUrl.endsWith(".gif") || LowerCaseUrl.endsWith(".png")){
            return 1;
        }
        if(url.indexOf(' ') > 0){
            return 2;
        }
        if()
        return errorCode;
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
