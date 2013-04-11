/*
 * adds a simple abstartion layer to the databse
 */
package crawler;

//This is a test

import com.mysql.jdbc.Connection;

/**
 * abstration for the database 
 * @author duncan
 */
public class KeywordDB {
	final private String DSN,PASSWORD,USERNAME;
	private Connection conn;
	
	/**
	 * define custom db paramitors
	 * @param dsn 
	 * @param password
	 * @param username 
	 */
	public KeywordDB(String dsn,String password, String username){
		DSN = dsn;
		PASSWORD = password;
		USERNAME = username;
	}
	
	/**
	 *make a keywordDB with default DSN PASSWORD AND USERNAME
	 */
	public KeywordDB(){
		DSN = "some DSN";
		PASSWORD = "some password";
		USERNAME = "some username";
	}
	/**
	 * adds a keyword to that database
	 * @param args expects a 3 element array [url_ID, keyword, weight]
	 * @return true if it could add it else false
	 */
	public boolean createKeywords(String[]... args){
		return false;
	}
	/**
	 * adds 1 or more stop-words to the database
	 * @param word array of word to create
	 * @return wether it worked or not 
	 */
	public boolean createStopWord(String... word){
		return false;
	}
	/**
	 * adds 1 or more link to database
	 * @param args
	 * @return weather it worked or not 
	 */
	public boolean createLink(String... args){
		return false;
	}
	/**
	 * searches the database for 1 or more links 
	 * @param args list of links to search for
	 * @return the remaing links that did not exist 
	 */
	public String[] searchLinks(String... args){
		return null;
	}
	/**
	 * searches the database for 1 or more stopwords 
	 * @param args list of stopwords to search for
	 * @return the remaing words that did not exist 
	 */
	public String[] searchStopwords(String... args){
		return null;
	}
	/**
	 * deleat the inputed keywords
	 * @param args list of keywords to be deleated by id 
	 * @return 
	 */
	public boolean deleteKeywords(String... args){
		return false;
	}
	/**
	 * deleat the inputed stopwords
	 * @param args list of stopwords to be deleated by id or word 
	 * @return 
	 */
	public boolean deleteStopWords(String... args){
		return false;
	}
	/**
	 * deleat the inputed links
	 * @param args list of links to be deleated by id or link
	 * @return 
	 */
	public boolean deleteLinks(String... args){
		return false;
	}
	/**
	 * get the connection to the database
	 * @return 
	 */
	private Connection getdbConnection(){
		return null;
	}
}
