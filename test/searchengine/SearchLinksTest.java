/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
                ""
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
        List<String[]> results = new ArrayList<>();
        results = this.test.findLinks(results);
        testOrder(results);
        
    }
    
    /**
     * checks if the order of the array is corect
     * @param array to check the order of 
     */
    private void testOrder(Iterable<String[]> results){
        char counter;
        char previous = 0;
        for (String[] result : results) {
            counter = '0'; 
            //for each of the relivens marker
            for (char ch : result[2].toCharArray()) {
                assertTrue("the second value is not a number", Character.isDigit(ch));
                // add togeter the nuber(leve it as a char) 
                counter += ch;
            }
            
            boolean flag = previous <= counter;
            assertTrue("the results are not in order", previous <= counter );
            previous = counter;
        }
    }
}
