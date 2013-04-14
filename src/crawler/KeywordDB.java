/*
 * adds a simple abstartion layer to the databse
 */
package crawler;

import com.its.util.DataService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
	private String DSN;
	private String PASSWORD = "";
	private String USERNAME = "";
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
		connoctionProperties.put("PASSWORD", password);
		connoctionProperties.put("USERNAME", username);
	}
	
	
	/**
	 *make a keywordDB with default DSN PASSWORD AND USERNAME
	 */
	public KeywordDB(){
		DSN = "jdbc:mysql://localhost/SearchEngineDB";
		USERNAME = "root";
                PASSWORD = null;
	}
	/**
	 * adds an array of keywords to the database and documents the url_ID 
         * that the keyword came from
	 * @param args expects a 3 element array [url_ID, keyword, weight]
	 * @return true if it could add it else false
	 */
	public boolean createKeywords(String[]... args) {
            
                try{	
                    for(int i = 0; i < args.length; i++){
                        if(args[i].equals(null)){
                            continue;
                        }
                    Statement stmt = Conn.createStatement();
                           String sqlStmt = "INSERT INTO Keywords (url_ID, keyword, Weight) VALUES " +
                                                        "(" + args[i][0] + ", '" + args[i][1] + "' , 0)";  
                           stmt.execute(sqlStmt);
                    }
                   } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                    }
            return false;
	}
        
	/**
	 * adds 1 or more stop-words to the database
	 * @param word array of word to create
	 * @return weather it worked or not 
	 */
	public boolean createStopWord(String... word){
		try{	
                    for(int i = 0; i < word.length; i++){
                        if(word[i].equals(null)){
                            continue;
                        }
                    Statement stmt = Conn.createStatement();

                           String sqlStmt = "INSERT INTO Stop_Words (word) VALUES ('" + word[i] + "')";  
                           stmt.execute(sqlStmt);
                    }
                   } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    // e.printStackTrace();
                   }
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
	 * Built to accepts an array which contains a Keyword along with the 
         * url_ID from which the word came form, and checks that word for its
         * existence in the database. If it exists, the word is removed from 
         * the array. The array is then passed back to the calling procedure
	 * @param the keyword to check [url_id, keyword]
	 * @return a array of all the keyword that are unique 
	 */
        
	public String[][] searchKeyword(String[]... args){
            try{	
                Statement stmt = Conn.createStatement();
                for(int i = 0; i < args.length; i++) {
                        String sqlStatement = "SELECT * FROM Keywords WHERE url_ID = '" + args[i][0] + 
                                            "' AND keyword = '"+ args[i][1] +"'";       
                        ResultSet result = stmt.executeQuery(sqlStatement);
                        if(result.next()){
                            args[i] = null;
                        }
                    }
                } catch (SQLException e) {
	        // TODO Auto-generated catch block
	       // e.printStackTrace();
                }
           return args;
	 }
        
        
	/**
	 * searches the database for 1 or more links 
	 * @param args list of links to search for
	 * @return the remaining links that did not exist 
	 */
	public String[][] searchLinks(String args){
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
		.getConnection(DSN, USERNAME, PASSWORD);
			
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
        
        /**
         * Returns the url_ID value from the DB of the string URL that
         * was passed into the method
         * @param args
         * @return 
         */
        public int getUrlID(String args){
            int url_ID;
	    try{
	    	Statement stmt = Conn.createStatement();	
	    	String sqlStatement = "SELECT * FROM Urls WHERE Name = '" + args + "'";
	        ResultSet result = stmt.executeQuery(sqlStatement);
	        result.next();
	        url_ID = result.getInt(1);
                    return url_ID;
	       } catch (SQLException e) {
	        // TODO Auto-generated catch block
	       // e.printStackTrace();
	    	}
	    return 0;
        }
        
        /**
         * Checks to see if each word within the passed array exist's within
         * the Keywords or Stop_Words database tables. If it does not, 
         * then the word is added to the DB
         * @param args
         * @return 
         */
        public String[][] AddKeywordUniqueToDB(String[]... args){
            
            try{	
                Statement stmt = Conn.createStatement();
                for(int i = 0; i < args.length; i++){
                        String sqlStatement = "SELECT * FROM Keywords WHERE url_ID = '" + args[i][0] + 
                                            "' AND keyword = '"+ args[i][1] +"'";       
                        ResultSet result = stmt.executeQuery(sqlStatement);
                        
                        //If keyword does not exist in Keyword Table, check stop words table
                        if(!result.next()){
                            
                            sqlStatement = "SELECT * FROM Stop_Words WHERE word = '" + args[i][1] + "'";       
                            result = stmt.executeQuery(sqlStatement);
                            
                            //if keyword does not exist in stop words table, add word to DB
                            if(!result.next()){
                                String insertStmt = "INSERT INTO Keywords (url_ID, keyword, Weight) VALUES " +
                                                        "(" + args[i][0] + ", '" + args[i][1] + "' , 0)";  
                                stmt.execute(insertStmt);
                                args[i][1] = "Data added";
                            }
                        }
                    }
                } catch (SQLException e) {
	        // TODO Auto-generated catch block
	       // e.printStackTrace();
                }
           return args;
        }
}
