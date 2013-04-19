/*
 * adds a simple abstartion layer to the databse
 */
package crawler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * abstration for the database 
 * @author duncan
 */
public class KeywordDB {
	private String DSN;
	private String PASSWORD = "";
	private String USERNAME = "";
	private Connection Conn = null;
	static final protected boolean DEBUG = true; 
	
	/**
	 * define custom db paramitors
	 * @param dsn 
	 * @param password
	 * @param username 
	 */
	public KeywordDB(String dsn,String password, String username){
		DSN = dsn;
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
	public boolean createLink(String[]... args){
		try{	
                    for(int i = 0; i < args.length; i++){
                        if(args[i].equals(null)){
                            continue;
                        }
                    Statement stmt = Conn.createStatement();

                           String sqlStmt = "INSERT INTO Urls (name) VALUES ('" + args[0][i] + "')";  
                           stmt.execute(sqlStmt);
                    }
                   } catch (SQLException e) {

                   }
            return false;
	}
        
       public void createLink(String webLink)
	{
	
		String link = webLink;
		
		try{
			Statement stmt = Conn.createStatement();
			String sqlStatement = "INSERT INTO links " + "(links)" + " VALUE ('" + link + "')";
			stmt.executeUpdate(sqlStatement);
			//sqlStatement = "INSERT INTO linkstable " + "(Link)" + " VALUE ('" + link + "')";
			//stmt.executeUpdate(sqlStatement);
			}
		catch(Exception ex){
			System.out.println("error: " + ex.getMessage());
		}
		
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
            
            List<String> wordsUniqueToDB = new ArrayList<String>();
            try{	
                Statement stmt = Conn.createStatement();
                for(int i = 0; i < args.length; i++) {
                        String sqlStatement = "SELECT * FROM Keywords WHERE url_ID = '" + args[i][0] + 
                                            "' AND keyword = '"+ args[i][1] +"'";       
                        ResultSet result = stmt.executeQuery(sqlStatement);
                        if(!result.next()){
                            wordsUniqueToDB.add(args[i][1]);
                        }
                    }
                } catch (SQLException e) {
	        // TODO Auto-generated catch block
	       // e.printStackTrace();
                }
                
                String[][] uniqueWords = new String[wordsUniqueToDB.size()][2];
                for(int j = 0; j < wordsUniqueToDB.size(); j++){
                    uniqueWords[j][0] = args[0][0];
                    uniqueWords[j][1] = wordsUniqueToDB.get(j);
                }
           return uniqueWords;
	 }
        
        /**
	 * Search tool for the searchFrame application. This method will be
         * used by the GUI to search the database for keywords
	 * @param the keyword to check [url_id, keyword]
	 * @return a array of all the keyword that are unique 
	 */
        
	public String[][] searchKeyword_GUI(String[]args){
            
            List<String> webPageIDValues = new ArrayList<String>();
            try{	
                Statement stmt = Conn.createStatement();
                for(int i = 0; i < args.length; i++) {
                        String sqlStatement = "SELECT * FROM Keywords WHERE keyword = '"+ args[i] +"'";       
                        ResultSet result = stmt.executeQuery(sqlStatement);
                        
                        if(result.next()){
                            webPageIDValues.add(result.getString(2));
                        }
                    }
                } catch (SQLException e) {
	        // TODO Auto-generated catch block
	       // e.printStackTrace();
                }
                
                String[][] urlIDArray = new String[webPageIDValues.size()][2];
                for(int j = 0; j < webPageIDValues.size(); j++){
                    urlIDArray[j][0] = webPageIDValues.get(j);
                }
           return urlIDArray;
	 }
        
        
	/**
	 * searches the database for 1 or more links 
	 * @param args list of links to search for
	 * @return the remaining links that did not exist 
	 */
	public String[][] searchLinks(String[]... args){
		try{	
                Statement stmt = Conn.createStatement();
                for(int i = 0; i < args.length; i++) {
                        String sqlStatement = "SELECT * FROM Urls WHERE url_ID = '" + args[i][0] + 
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
         * Returns the url_ID value from the DB of the string URL that
         * was passed into the method
         * @param args
         * @return 
         */
        public String[][] getUrlByID(String[]... args){
            
            List<String> urls = new ArrayList<String>();
	    try{
	    	Statement stmt = Conn.createStatement();
                
                for (int i = 0; i < args.length; i++){
	    	String sqlStatement = "SELECT * FROM Urls WHERE ID = " + args[i][0];
	        ResultSet result = stmt.executeQuery(sqlStatement);
	        
                
                while(result.next()){
                    urls.add(result.getNString(2));
                    }
                }
	       }catch (SQLException e) {
	        // TODO Auto-generated catch block
	       // e.printStackTrace();
	    	}
            
            String[][] finalUrlArray = new String[urls.size()][1];
                for (int j = 0; j < args.length; j++){
                    finalUrlArray[j][0] = urls.get(j);
                }
            
	    return finalUrlArray;
        }
        
        
	
	/**
	 * gets the last link
	 * @param int modfier for witch link to return
	 * @return String the last Url in the array
	 */
	public String getLastLink(int refindex){
		try{	
                Statement stmt = Conn.createStatement();
                        String sqlStatement = "SELECT Name FROM Urls WHERE ID >= RAND() * (SELECT MAX(ID) FROM Urls) LIMIT 1";       
                        ResultSet result = stmt.executeQuery(sqlStatement);
			return result.getNString(0);
                } catch (SQLException e) {
			return null;
                }
	}
	/**
	 * get last link that has not been updated if all have grab latetest
	 * @retun String the Url
	 */
	public String getFirstLink(){
		try{	
                Statement stmt = Conn.createStatement();
                        String sqlStatement = "SELECT Name FROM Urls WHERE ID >= RAND() * (SELECT MAX(ID) FROM Urls) LIMIT 1";       
                        ResultSet result = stmt.executeQuery(sqlStatement);
			return result.getNString(0);
                } catch (SQLException e) {
			return null;
                }
	} 
}
