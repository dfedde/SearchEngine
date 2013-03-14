/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author duncan
 */
public class SearchKeyWordsTest {

    private SearchKeyWords test;
    private List<String[]> results;
    private List<String[]> listOfLinks;

    public SearchKeyWordsTest() {
    }

    @Before
    public void setUp() {
        this.test = new SearchKeyWords("http://www.kbuxton.com/discordia/fnord.html");
        this.test.setKeywords("fnord milkshake slurpee");
        this.listOfLinks = new ArrayList();
        this.listOfLinks.add(new String[]{"http://www.kbuxton.com/discordia/song.html", "this link I found", "2"});
        this.listOfLinks.add(new String[]{"http://www.kbuxton.com/discordia/posting.html", "this link I found", "3"});
        this.listOfLinks.add(new String[]{"http://www.kbuxton.com/discordia/cyberpass.html", "this link I found", "3"});

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of searchTextSentences method, of class SearchKeyWords.
     */
    @Test
    public void testSearchTextSentences() {
        char previous = '0';
        char counter;
        System.out.println("searchTextSentences");
        results = this.test.searchTextSentences();
        //check if the last number is an actualy a number 
        for (String[] result : results) {
            counter = 0; 
            for (char ch : result[1].toCharArray()) {
                assertTrue("the second value is not a number", Character.isDigit(ch));
                counter += ch;
            }
            boolean flag = previous <= counter;
            assertTrue("the results are not in order", previous <= counter );
            previous = counter;
        }

    }

    /**
     * Test of searchPages method, of class SearchKeyWords.
     */
    @Test
    public void testSearchPages() {
        System.out.println("searchPages");
        results = this.test.searchPages(listOfLinks);
        assertTrue("the arrays are not the same length", results.size() == listOfLinks.size());
    }
}
