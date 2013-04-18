package crawler;

import java.util.ArrayList;
import java.util.List;

import com.its.util.IOMaster;
import com.its.util.Stringer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class LinkScraper
{
	//DATA
	public String page;
	public String url;
	public String DBUrl;
	private List<String> listOfUrls = new ArrayList<String>();
	public static int maxNumberOfLinks = 10; // limit the resulting links	
	
	
	//Constructors --------------------------------------------------------------------
	public LinkScraper()
	{
		url = "http://www.colorado.gov";
	}
	
	public LinkScraper(String webpage)
	{
		url = webpage;
	}
	
	//Get methods-----------------------------------------------------------------------
	public String getPage() 
	{
		return page;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	
	public List<String> getlistOfUrls()
	{
		return listOfUrls;
	}
	
	public String getDBUrl()
	{
		return DBUrl;
	}

	//Set Methods-----------------------------------------------------------------------
	public void setPage(String inputPage) 
	{
		page = inputPage;
	}
	
	public void setUrl(String inputUrl)
	{
		url = inputUrl;
	}
	
	public void setDBUrl(String lnk)
	{
		DBUrl = lnk;
	}
	
	
	//Utility Methods-------------------------------------------------------------------
	/**
	 * readWebPage saves the target web page as a text string 
	 * (target based on the url variable) 
	 */
	public void readWebPage()
	{
		page = IOMaster.readTextFile(url);
	}
	
	
	public void compareLink(String webLink)
	{
	String host = "jdbc:mysql://localhost/SearchEngineDB";
	String uName = "root";
	String uPass = "";
	String link = webLink;
	try{
		Connection conn = DriverManager.getConnection(host, uName, uPass);
		Statement stmt = conn.createStatement();
		String sqlStatement = "Select * FROM links WHERE links =" + "'" + link + "'";
		ResultSet results = stmt.executeQuery(sqlStatement);
		
		while(results.next()){
			setDBUrl(results.getString("links"));
		}
		
		conn.close();
		
		}
	catch(Exception ex){
		System.out.println("error: " + ex.getMessage());
	}
	}
	
	public void addStringDB(String webLink)
	{
		String host = "jdbc:mysql://localhost/SearchEngineDB";
		String uName = "root";
		String uPass = "";
		String link = webLink;
		
		try{
			Connection conn = DriverManager.getConnection(host, uName, uPass);
			Statement stmt = conn.createStatement();
			String sqlStatement = "INSERT INTO Urls " + "(name)" + " VALUES ('" + link + "')";
			stmt.executeUpdate(sqlStatement);
			//sqlStatement = "INSERT INTO linkstable " + "(Link)" + " VALUE ('" + link + "')";
			//stmt.executeUpdate(sqlStatement);
			}
		catch(Exception ex){
			System.out.println("error: " + ex.getMessage());
		}
		
	}
	
	public List<String[]> findLinks(List<String[]> listOfLinks) 
	{ 
		if(page.startsWith("ERROR:")) 
		{ 
			return listOfLinks; 
		} 
		
		String[] links = Stringer.split("<a ", page); 
		for(int i=1; i < links.length; i++) 
		{ 
			// find a link 
			String link = links[i]; 
			int endLinkIndex = link.indexOf("</a>"); 
			if(endLinkIndex <= 0) 
			{ 
				continue; // not a valid link 
			} 
			
			link = link.substring(0, endLinkIndex); 
			
			// find a url 
			int startUrlIndex = link.indexOf("href="); 
			if(startUrlIndex < 0) 
			{ 
				continue; // not a valid url 
			} 
			startUrlIndex += 5; // count for 5 characters in the "href=" 
			int endUrlIndex = link.indexOf(">", startUrlIndex); 
			String url = link.substring(startUrlIndex, endUrlIndex-1); 
			if(url.startsWith("\"")) // check for the starting quote, for example, href=“http://easy.com”>
			{ 
				 url = url.substring(1); // remove the starting quote }
			}
			
			String urlInLowCase = url.toLowerCase();
			if(!urlInLowCase.startsWith("http") && !urlInLowCase.startsWith("www.")) 
			{
				continue; // this is a fancy internal URL, cannot be used to read the page
			}
			
			int indexOfSpace = url.indexOf(" "); // look for the space after the URL
			if(indexOfSpace > 0) // get the URL before the space
			{ 
				url = url.substring(0, indexOfSpace);
			}
			if(url.endsWith("\"")) // check for the ending quote
			{ 
				url = url.substring(0, url.length() -1); // remove the ending quote
			}
			//initiate the KeywordDB
			
			//Create and if statement to check if the link is unique if so add the link to the database
			//all links will be lowercase
			
			//
			
			/*
			//System.out.println("going to compare");
			if(listOfUrls.contains(url.toLowerCase())) 
			{
				continue; // been there, done that
			}
			compareLink(url.toLowerCase());
			if(getDBUrl() == url.toLowerCase())
			{
				continue;
			}
			*/
			//System.out.println("going to add");
			
			//addStringDB(url.toLowerCase());
			
			listOfUrls.add(url.toLowerCase()); // remember all urls visited
			String[] arrayOfLinkParts = new String[] {url+""}; // score=number of keywords
			listOfLinks.add(arrayOfLinkParts);
			// check if enough links found
			if(listOfLinks.size() >= maxNumberOfLinks) 
			{
				return listOfLinks; // enough links! we are done!
			}
			if(urlInLowCase.endsWith(".jpg") || urlInLowCase.endsWith(".gif") ||
				urlInLowCase.endsWith(".png"))
			{
				continue; // do not read an image page
			}
			// read the page with the link
			setUrl(url);
			readWebPage();
			if(!page.startsWith("ERROR:")) // check if page was readable
			{ 
				findLinks(listOfLinks); // recursive call
			}
			} // end the for loop for all links
				return listOfLinks;
		} // end of the method
	
	




public static void main (String[] args){
	
	LinkScraper search = new LinkScraper("http://www.google.com/");
	search.readWebPage();
	List<String[]> listOfLinks = search.findLinks(new ArrayList<String[]>());
	System.out.println("done");
	
	
}
}
