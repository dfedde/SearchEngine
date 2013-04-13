/*
 * adds a simple abstartion layer to the databse
 */
package crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.SQLException;


/**
 * abstration for the database 
 * @author duncan
 */
public class KeywordDB {
	final private String DSN;
	final private String PASSWORD = "";
	final private String USERNAME = "root";
	private Connection Conn = null;
	private Properties connoctionProperties = new Properties();
	static final protected boolean DEBUG = true; 
	
	/**
	 * define custom db paramitors
	 * @param dsn 
	 * @param password
	 * @param username 
	 */
	public KeywordDB(String dsn,String password, String username){
		DSN = dsn;
		connoctionProperties.put("password", password);
		connoctionProperties.put("username", username);
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
	 * checks the database for the keyword
	 * @param the keyword to check [url_id, keyword]
	 * @return a array of all the keyword that are unique 
	 */
	public String[][] searchKeyword(String[][]... args){
		return null;
	}
	
	/**
	 * searches the database for 1 or more links 
	 * @param args list of links to search for
	 * @return the remaining links that did not exist 
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
	private boolean MakeDBConnection(){
            Connection connection = null;
            
            try{
		Class.forName("com.mysql.jdbc.Driver");
			
		if (DEBUG)
                    System.out.println("MySQL JDBC Driver Registered!");	
			
		//Make connection to the database
		connection = DriverManager
		.getConnection("jdbc:mysql://localhost/SearchEngineDB"
				,"root", null);
			
		}catch (SQLException e){
			if (DEBUG)
			System.out.println("Connection Failed!! Check output console");
			return false;
			}catch (ClassNotFoundException e) {
			if (DEBUG)
			System.out.println("No MySQL JDBC Driver Found");
			return false;
			}
			
			//DB connection report block
		if (DEBUG){
			if (connection != null) {
			System.out.println("Connection Succesful!");
			} else {
			System.out.println("Failed to make connection!");
			}
		}
	return true;
	}
}
