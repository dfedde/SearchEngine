/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author duncan
 */
public class SearchLinksTest {
    private SearchLinks test;
    
    public SearchLinksTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.test = new SearchLinks(
                "http://www.kbuxton.com/discordia/fnord.html"
                );
        this.test.setKeywords(
                "fnord milkshake slurpee"
                );
       
    }
    
    @After
    public void tearDown() {
    }

    
    /**
     * Test of findLinks method, of class SearchLinks.
     */
    @Test
    public void testFindLinks() {
        char counter;
        char previous = '0';
        System.out.println("findLinks");
        List<String[]> results;
        results = this.test.findLinks(this.test.getListOfLinks());
        for (String[] result : results) {
            counter = 0; 
            for (char ch : result[2].toCharArray()) {
                assertTrue("the second value is not a number", Character.isDigit(ch));
                counter += ch;
            }
            boolean flag = previous <= counter;
            assertTrue("the results are not in order", previous <= counter );
            previous = counter;
        }
    }    
}
