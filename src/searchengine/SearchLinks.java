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

//    public List<String[]> findLinks(List<String[]> listOfLinks) {
//        //define strings    
//        String[] links;
//        readWebPage();
//        
//        if(page.startsWith("ERROR:")) { 
//            return listOfLinks;
//        } 
//        
//        //build an array out of the links in tha page 
//        links = Stringer.split("<a", getPage());
//
//        // for every link in the array
//        for (String link : links) {
//            link = Parser.parseWithPatterns(link, "before,</a>");
//            
//            /*
//             * get the URL
//             */
//
//
//            //get the index of the first carictor of the link destanation 
//            int destIndex;
//            if(link.indexOf("href=") >= 0) {
//                destIndex = link.indexOf("href=") + 5;
//            } else {
//                continue;
//            }
//            int destIndexEnd;// the last caricto of the destanation
//
//            String desntanation;
//
//            //find the end of the string       
//            //find the witch is closer a space or a end dimond 
//            destIndexEnd = link.indexOf(">", destIndex);
//            //if it has a qotation mark
//            if (link.charAt(destIndex) == '"' || link.charAt(destIndex) == '\'') {
//                desntanation = link.substring(destIndex + 1, destIndexEnd - 1);
//            } else {
//                desntanation = link.substring(destIndex, destIndexEnd);
//            }
//            System.out.print(desntanation+":  ");
//            /*
//             * get the lable 
//             */
//
//            //get the index of the first carictor of the link destanation 
//            int labelIndex = link.indexOf(">") + 1;
//            int labelIndexEnd; // the last caricto of the destanation
//
//            String label;
//
//            //find the end of the string 
//            
//            if(link.indexOf("<",labelIndex)<=0){
//                labelIndexEnd = link.length();
//            }
//            else{
//                labelIndexEnd = link.indexOf("<", labelIndex);
//            }
//            label = link.substring(labelIndex, labelIndexEnd);
//            System.out.print(label+"\n");
//            /*
//             * get the score
//             */
//
//            int counter = 0;
//            for (String word : words) {
//                if (label.indexOf(word) >= 0) {
//                    counter++;
//                }
//            }
//            if (counter == 0) {
//                continue;
//            }
//             
//
//            //cancatanate the values 
//            listOfLinks.add(new String[]{desntanation,label,counter+""});
//            
//            //do checks on the loop to see if it can be called again
//            
//            if(listOfLinks.size() >= maxNumberOfLinks) { 
//                return listOfLinks; // enough links! we are done! 
//            }
//            
//            if(desntanation.endsWith(".jpg") || desntanation.endsWith(".gif") ||    
//                desntanation.endsWith(".png")) { 
//                continue; // do not read an image page 
//            }
//            if(!page.startsWith("ERROR:")) { // check if page was readable 
//                findLinks(listOfLinks); // recursive call 
//            } 
//        }//endo of for loop
//        return listOfLinks;
//    } 
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
       readWebPage(); 
       if(!page.startsWith("ERROR:")) { // check if page was readable 
         findLinks(listOfLinks); // recursive call 
       } 
      } // end the for loop for all links 
      return listOfLinks;  
      } // end of the method 

    public void resultsWriter() {
        System.out.println(findLinks(listOfLinks));
    }

    public static void main(String[] Args) {
        SearchLinks test = new SearchLinks("http://netbeans.org/kb/docs/java/quickstart.html");
        test.setKeyWordsWithInput();
        test.resultsWriter();
    }
}
