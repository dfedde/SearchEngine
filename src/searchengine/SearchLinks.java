/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author duncan
 */
public class SearchLinks extends SearchEngine {

	private List<String[]> listOfLinks = new ArrayList<>();
	private int maxNumberOfLinks = 5;
	private List<String> listOfUrls = new ArrayList<>();

	public SearchLinks(String url) {
		super(url);
	}

	public SearchLinks(String url, String keywords) {
		super(url, keywords);
	}

	public List<String[]> findLinks(List<String[]> listOfLinks) {
		// define strings
		Elements links;
		// if you cant read the page dont bother trying to read it
		if (readWebPage()) {

			// build an array out of the links in that page
			links = page.select("a[href]");

			// for every link in the array
			for (Element link : links) {
				String desntanation;
				String label;

				// get the link destonation try to convert relitive locations to
				// absulute
				desntanation = link.attr("abs:href");
				System.out.print(desntanation + ":  ");

				// if we already seached it
				if (listOfUrls.contains(desntanation.toLowerCase())) {
					continue;
				} else {
					listOfUrls.add(desntanation.toLowerCase());
				}

				// get the lable
				label = link.text();
				System.out.print(label + ": ");

				// get the score
				//
				int counter = 0;
				for (String word : words) {
					if (label.indexOf(word) >= 0) {
						counter++;
					}
				}
				if (counter == 0) {
					continue;
				}
				System.out.println(counter);

				// cancatanate the values
				listOfLinks.add(new String[]{desntanation, label,
					counter + ""});

				// do checks on the loop to see if it can be called again

				if (listOfLinks.size() >= maxNumberOfLinks) {
					return listOfLinks; // enough links! we are done!
				}

				if (desntanation.endsWith(".jpg")
					|| desntanation.endsWith(".gif")
					|| desntanation.endsWith(".png")) {
					continue; // do not read an image page
				}
				// TODO check if link is usabule

				setUrl(desntanation);
				findLinks(listOfLinks); // recursive call
			}// endo of for loop
		}
		return listOfLinks;
	}

	/**
	 * returns a list of links that are unque both to themselves and the
	 * link table in the database. the urls must also point to a real
	 * location;
	 *
	 * @return String[] with the layout of [ "url1", "url2", ...]
	 */
	public List<String[]> findLinksDB(List<String[]> listOfLinks) {
		Connection connection = super.getConnection();

		// define strings
		Elements links;
		// if you cant read the page dont bother trying to read it
		if (readWebPage()) {

			// build an array out of the links in that page
			links = page.select("a[href]");

			// for every link in the array
			for (Element link : links) {
				String desntanation;

				// get the link destonation try to convert relitive locations to
				// absulute
				desntanation = link.attr("abs:href");
				System.out.print(desntanation + ":  ");


				// if we already searched it
				if (super.CheckDataBaseData(connection, "Urls", "Name", desntanation.toLowerCase())) {
					continue;
				} else {
					//Add current url to listOfUrls array
					//Add current url to database
					String[] listOfUrls = {desntanation.toLowerCase()};
					listOfLinks.add(listOfUrls);
					try {
						Statement stmt = connection.createStatement();
						String sqlStatement = "INSERT INTO Urls VALUES ( 0, '" + desntanation + "')";
						stmt.execute(sqlStatement);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}


				// do checks on the loop to see if it can be called again
				if (listOfLinks.size() >= maxNumberOfLinks) {
					return listOfLinks; // enough links! we are done!
				}

				if (desntanation.endsWith(".jpg")
					|| desntanation.endsWith(".gif")
					|| desntanation.endsWith(".png")) {
					continue; // do not read an image page
				}
				// TODO check if link is usabule

				setUrl(desntanation);
				findLinksDB(listOfLinks); // recursive call
			}// endo of for loop
		}

		return listOfLinks;
	}

	public void resultsWriter() {
		System.out.println(findLinks(listOfLinks));
	}

	public static void main(String[] Args) {
		SearchLinks test = new SearchLinks(
			"http://netbeans.org/kb/docs/java/quickstart.html");
		test.setKeyWordsWithInput();
		test.resultsWriter();
	}
}
