package searchengine;

import com.its.util.Stringer;
import java.util.ArrayList;
import java.util.List;
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
/**
 * this function takes a list of urls and 
 * list of unique keywords from each. the key word must also not be in the
 * stop-words table
 * @param String[] a list of unique urls;
 * @return String[String[]]  an array of ["url", "keyword1", "keyword2", ...]
 *	for each url. array is null if page has an error
 */
    
    
    public static void main(String[] args) {
        SearchKeyWords search = new SearchKeyWords("http://www.pageinc.org/");
        search.setKeyWordsWithInput();
        System.out.print(StringResults(search.searchTextSentences()));
    }
}
