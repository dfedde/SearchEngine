package searchengine;

import com.its.util.Stringer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchKeyWords extends SearchEngine
{

	private String[]    lineEnders  = {".","?","!"};
	private Scanner     keyboard    = new Scanner(System.in);

        public SearchKeyWords(String url) {
            super(url);
        }
	
	
	
	
	public String[] getWords()
	{
		return words;
	}
	
	public void setWords(String[] inputWords)
	{
        System.arraycopy(inputWords, 0, words, 0, inputWords.length);
		
	}
	
	public void setKeyWordsWithInput()
	{
                String keywords;
		System.out.println("Please enter your search criteria...");
		keywords = keyboard.nextLine();
		words = Stringer.split(" ", keywords);
	}
	
	
	public String searchTextSentences()
	{
		readWebPage();
                String[] sentences = Stringer.split(getPage(),  lineEnders, true);
		String results = "";
		List<String> relevanceList = new ArrayList<String>();
		int numberOfSentences = sentences.length;
		for (int i = 0; i < numberOfSentences; i++)
		{	
			//System.out.println(sentences[i]);
			String sentence = sentences[i];
			int counter = 0;
			for (int j = 0; j < words.length; j++)
			{
				if(sentence.indexOf(words[j]) >= 0) 
				{
					counter++;
				}
			}
			relevanceList.add(counter+"");
		}
		int sortColumnIndex = 1;
		List<String[]> listOfStringArrays = new ArrayList<String[]>();
		//concatanate sting with relavance counter 
                for(int i = 0; i< numberOfSentences; i++)
		{
			String sentence = sentences[i];
			String relevanceCounter = relevanceList.get(i);
			String[] sentenceAndCounter = {sentence, relevanceCounter};
			listOfStringArrays.add(sentenceAndCounter);
		}
		List<String[]> listOfSortedArrays = Stringer.sort(listOfStringArrays, sortColumnIndex);
		for(int i = 0; i < listOfSortedArrays.size(); i++)
		{
			String[] sentenceAndScore = listOfSortedArrays.get(i);
			String sentence = sentenceAndScore[0];
			String score = sentenceAndScore[1];
			if(!score.equals("0"))
			{
			results += sentence + " : " + score;
			}
		}
		return results;
	}
	

	// write the web sorse to a file to tomcat 
	public void resultsWriter()
	{
		
	}
	
	
	public static void main(String[] args)
	{
            SearchKeyWords search = new SearchKeyWords("www.google.com");
            search.setKeyWordsWithInput();
            System.out.print(search.searchTextSentences());
	}
	
}



