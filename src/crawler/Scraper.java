/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import com.mysql.jdbc.Connection;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author duncan
 */
public class Scraper {
	protected Document page;
	protected String url;
	protected Connection conn;
	protected long url_id;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		//get url id as well 
		this.url = url;
	}
	
	/**
	 * parses the web page based on jsoups parse()
	 * @param String  string to parse
	 */ 
	protected void setPage(String page) {
		this.page = Jsoup.parse(page);
	}
	
	/**
	 * reads web page from url field to page
	 */
	final protected boolean readWebPage() {
		// if the url is not emtey
		if (!url.isEmpty()) {
			try {
				// get a documet from to the webpage
				this.page = Jsoup.connect(url).get();
			} catch (IOException ex) {
				return false;
			}
			// horay you read the web page
			return true;
		}
		
		return false;
	}
	
}
