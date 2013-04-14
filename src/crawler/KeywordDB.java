/*
 * adds a simple abstartion layer to the databse
 */
package crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * abstration for the database 
 * @author duncan
 */
public class KeywordDB {
	final private String DSN;
	final private String PASSWORD;
	final private String USERNAME;
	private Connection Conn = null;
	private Properties connoctionProperties = new Properties();
	static final protected boolean DEBUG = true; 
	
	/**
	 * define custom db paramitors
	 * @param dsn 
	 * @param password
	 * @param username 
	 */
	/*public KeywordDB(String dsn,String password, String username){
		DSN = dsn;
		connoctionProperties.put("password", password);
		connoctionProperties.put("username", username);
	}
	
	
	/**
	 *make a keywordDB with default DSN PASSWORD AND USERNAME
	 */
	public KeywordDB(){
		DSN = "jdbc:mysql://localhost/SearchEngineDB";
		USERNAME = "root";
                PASSWORD = "null";
		
                
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
	public String[][] searchKeyword(String[]... args){
	
	/**
	 * searches the database for 1 or more links 
	 * @param args list of links to search for
	 * @return the remaining links that did not exist 
	 */
            String sqlStatement;

            try{	
                Statement stmt = Conn.createStatement();
                for(int i = 0; i < args.length; i++)
                    {
                        sqlStatement = "SELECT * FROM Keywords WHERE url_ID = '" + args[i][0] + 
                                            "' AND keyword = '"+ args[i][1] +"';";       
                        stmt.addBatch(sqlStatement);
                    }

                int returnValue[] = stmt.executeBatch();
     
	       } catch (SQLException e) {
	        // TODO Auto-generated catch block
	       // e.printStackTrace();
	    	}
           return args;
	 }
        
        
        
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
	public boolean MakeDBConnection(){
            Conn = null;
            
            try{
		Class.forName("com.mysql.jdbc.Driver");
			
		if (DEBUG)
                    System.out.println("MySQL JDBC Driver Registered!");	
			
		//Make connection to the database
		Conn = DriverManager
		.getConnection(DSN,USERNAME, PASSWORD);
			
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
			if (Conn != null) {
			System.out.println("Connection Succesful!");
			} else {
			System.out.println("Failed to make connection!");
			}
		}
	return true;
	}
}
