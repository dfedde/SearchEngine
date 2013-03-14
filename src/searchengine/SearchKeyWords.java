package searchengine;

import com.its.util.Stringer;
import java.util.ArrayList;
import java.util.List;

public class SearchKeyWords extends SearchEngine
{

    private String[]    lineEnders  = {".","?","!"};


    public SearchKeyWords(String url) {
        super(url);
    }

    public SearchKeyWords(String url, String keywords) {
        super(url, keywords);
    }

    public List<String[]> searchTextSentences()
    {
            readWebPage();
            String[] sentences = Stringer.split(getPage(),  lineEnders, true);
            
            List<String[]> listOfStringArrays = new ArrayList<>();

            for (String sentence : sentences){ 	
                //System.out.println(sentence);
                sentence = Stringer.remove(sentence, new String[]{"<",">"});
                int counter = 0;
                for (String word: words)
                {
                        if(sentence.indexOf(word) >= 0) 
                        {
                                counter++;
                        }
                }
                if(counter == 0 ){
                    continue;
                }
                System.out.println(sentence+": "+counter);
                listOfStringArrays.add(new String[]{sentence, counter+""});
            }
            return listOfStringArrays;
    }
    
    /**
     * returns a list of links with a snipit off the page
     * @param list of links
     * @return link and snipit of page {url,snipit}
     * @calls searchTextSenteces
     */
    public List<String[]> searchPages(List<String[]> listOfLinks){
        List<String[]> resaults = new ArrayList<>();
        //call searchtextsentics for evry link and make aray for each
        for(String[] link: listOfLinks){
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
            if(!relaventResaults.isEmpty()){
                resaults.add(new String[]{
                    url, link[1], relaventResaults.get(0)[0], link[2]
                });     
            }else{
                resaults.add(new String[]{
                    url, link[1], 
                    "the page dose not contain your keywords", link[2]
                });
            }
            
        }
        return resaults;
    }
    
    public static void main(String[] args)
    {
        SearchKeyWords search = new SearchKeyWords("http://www.pageinc.org/");
        search.setKeyWordsWithInput();
        System.out.print(StringResults(search.searchTextSentences()));
    }
	
}



