package searchengine;

import com.its.util.IOMaster;
import com.its.util.Parser;
import com.its.util.Stringer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The WebPageReader class will:
 * - Read from the Internet
 * - Take User Input with keywords
 * - Parse data for the keywords
 * - Form nice output
 * @author jeff.zhuk
 *
 */
public class WebPageReader {
	// data
	private String url;
	private String keywords; // input keywords
	private String[] words;  // individual words from the input
	private String page; // page content from the web
	private Scanner scanner = new Scanner(System.in);
	public String[] lineEnders = {".", "?", "!", "\n"};
	
	// methods
	public WebPageReader(String url) {
		this.url = url;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String[] getWords() {
		return words;
	}
	public void setWords(String[] words) {
		this.words = words;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	
	public void setKeywordsWithInput() {
		System.out.println("\nEnter search keywords:");
		keywords = scanner.nextLine();
		words = Stringer.split(" ", keywords);
	}
	
	public void readWebPage() {
		page = IOMaster.readTextFile(url);
	}
	/**
	 * Breaking a string of text into sentences
	 * Searching each sentence for a set of words
	 * Count number of keywords in each sentence and
	 * Store this number in the relevanceList
	 * Display the relevant sentences according to the relevance
	 * @return results
	 */
	public String searchTextSentences() {
		String[] sentences = Stringer.split(page, lineEnders, true);
		String results = "<html><body><table border=\"3\"><tr><th colspan=\"2\">"+
		"Developed by [your name]</th>";
		// loop to search for keywords
		List<String> relevanceList = new ArrayList<String>();
		int numberOfSentences = sentences.length;
		for(int i=0; i < numberOfSentences; i++) {
			String sentence = sentences[i];
			// in the loop check all key words if they are present in the sentence
			int counter = 0;
			for(int j=0; j < words.length; j++) {
				String word = words[j];
				word = word.trim();
				if(sentence.indexOf(word) >= 0) {
					// yes! we found at least one key word, let's count this sentence into the results
					counter++;
				}
			}
			relevanceList.add(counter+"");			
		}
		int sortColumnIndex = 1;
		List<String[]> listOfStringArrays = new ArrayList<String[]>();
		for(int i=0; i < numberOfSentences; i++) {
			String sentence = sentences[i];
			String relevaceCounter = relevanceList.get(i);
			String[] sentenceAndCounter = {sentence, relevaceCounter};
			listOfStringArrays.add(sentenceAndCounter);
		}
		List<String[]> listOfSortedArrays = Stringer.sort(listOfStringArrays, sortColumnIndex);
		String[] headers = {"Sentence", "Score"};
		
		String html = formHtml(headers, listOfSortedArrays);
		IOMaster.writeTextFile("c:/ccdhomework/webSearchResults.html", html);
		for(int i=0; i < numberOfSentences; i++) {
			String[] sentenceAndScore = listOfSortedArrays.get(i);
			String sentence = sentenceAndScore[0];
			String score = sentenceAndScore[1];
			if(!score.equals("0")) {
				results += "\n\n Score=" + score + ": "+sentence;
			}
		}
		return results;
	}
	/**
	 * Breaking a string of text into sentences
	 * Searching each sentence for a set of words
	 * @return results of the search
	 */
	public String parseWebPage() {
		String results = "";
		// get index of the end of the first sentence
		int indexOfNextLineEnder = Stringer.indexOfPattern(page, lineEnders);
		//
		for(int startingIndex = 0; indexOfNextLineEnder >= 0; ) {
			// get the next sentence into the text variable
			String text = page.substring(startingIndex, indexOfNextLineEnder);
			startingIndex = indexOfNextLineEnder + 1;
			int[] indexOfNextLineEnderAndPatternIndex = Stringer.getClosestIndexOf(page, startingIndex, lineEnders);
			indexOfNextLineEnder = indexOfNextLineEnderAndPatternIndex[0];
			int patternIndex = indexOfNextLineEnderAndPatternIndex[1];

			// in the loop check all key words if they are present in the sentence
			for(int j=0; j < words.length; j++) {
				if(text.indexOf(words[j]) >= 0) {
					// yes! we found at least one key word, let's collect this sentence into the results
					results += "\n\n" + text + lineEnders[patternIndex];
					// done with this sentence, no more key words to check
					break;
				}
			}
		}
		
		return results;
	}
	public String findLinks() {
		String results = "";
		String[] links = Stringer.split("<a ", page);
		for(int i=1; i < links.length; i++) {
			String link = links[i];
			link = Parser.parseWithPatterns(link, "before,</a>");
			link = "<a " + link + "</a>";
			results += "\n\n" + link;
		}
		return results;
	}
	/**
	 * Instantiate the reader, read a web page, 
	 * ask a user about keywords, parse and print results
	 * @param args
	 */
	public static void testSearch() {
		WebPageReader pageReader = new WebPageReader("http://www.colorado.gov/");
		pageReader.readWebPage();
		pageReader.setKeywordsWithInput();
		//String searchResults = pageReader.parseWebPage();
		//System.out.println(searchResults);
		String searchResults = pageReader.searchTextSentences();
		System.out.println(searchResults);
	}
	/**
	 * The plan: a) read the page b) find links c) display results
	 */
	public static void testFindLinks() {
		WebPageReader pageReader = new WebPageReader("http://www.colorado.gov/");
		pageReader.readWebPage();
		String results = pageReader.findLinks();
		System.out.println(results);

	}
	public static void main(String[] args) {
		testSearch();
		
		//testFindLinks();
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
}
