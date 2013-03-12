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
        
	
	public String[] getWords()
	{
		return words;
	}
	
	public void setWords(String[] inputWords)
	{
        System.arraycopy(inputWords, 0, words, 0, inputWords.length);
		
	}
	
	public List<String[]> searchTextSentences()
	{
		readWebPage();
                String[] sentences = Stringer.split(getPage(),  lineEnders, true);
		List<String[]> listOfStringArrays = new ArrayList<>();
                
                for (String sentence : sentences){ 	
                    //System.out.println(sentence);
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
                //get the url
                String url = link[1];
                //set the new url 
                setUrl(url);
                //get the resaults
                relaventResaults = searchTextSentences();
                // get the fist to resalts and concatanate them
                resaults.add(new String[]{url, relaventResaults.get(1)[1]+
                        "\n"+relaventResaults.get(2)[1] });
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



