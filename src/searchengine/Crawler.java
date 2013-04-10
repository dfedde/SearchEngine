/*
 * this gets a list of all the keywords on a page and wrigtes them to the array
 */
package searchengine;

import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author duncan
 */
public class Crawler {
	private SearchLinks links = new SearchLinks(null);
	private SearchKeyWords keywords = new SearchKeyWords(null);
	//get the list of keywords
	// create it in the databse
	//gnearate a sql string

	public void crawl (){
		Connection con = SearchEngine.getConnection();
		String sql = "INSERT INTO"
				+ " SearchEngine.Keywords (keyword url_ID, Weight)"
				+ " VALUES "
				+ "(?, (SELECT ID FROM SearchEngine.Urls WHERE Name = ?),1)";
		String [][] keywordarray = keywords.ScrapeWords
			(links.findLinksDB(new ArrayList<String[]>()));
		for (String[] keyword : keywordarray) {
			try {
				java.sql.PreparedStatement pst = con.prepareStatement(sql);
				pst.setString(1, keyword[1]);
				pst.setString(2, keyword[0]);
				pst.execute();
			} catch (SQLException ex) {
				Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
	}
	
}
