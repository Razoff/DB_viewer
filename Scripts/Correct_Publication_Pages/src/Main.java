import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.math.NumberUtils;

import oracle.jdbc.pool.OracleDataSource;
 
 public class Main {
 
 	public static void main(String[] args) throws SQLException {
 		String url = "jdbc:oracle:thin:@diassrv2.epfl.ch:1521:orcldias";
 		//"jdbc:mysql://localhost:3306/javabase";
 		String username = "DB2016_G44";
 		String password = "DB2016_G44";
 
 		OracleDataSource ods = new OracleDataSource();
 	    ods.setURL(url);
 	    ods.setUser(username);
 	    ods.setPassword(password);
 	    Connection conn = ods.getConnection();
 	    System.out.println("connected.");
 
 	    Statement stmt = conn.createStatement();
 
 	    ResultSet rset = stmt.executeQuery("SELECT P_ID, PUBLICATION_PAGES FROM PUBLICATIONS WHERE PUBLICATION_PAGES IS NOT NULL");
 
 	    while (rset.next()){
 	    	long p_id = rset.getLong(1);
 	    	String pages = rset.getString(2);
 	    	if(pages.indexOf('+') != -1){
 	    		System.out.print(p_id + " " + pages); 	    	
 	    		String[] parts = pages.split("\\+");
 	    		int sum = 0;
 	    		for(String part : parts){
 	    			part.replace("[", ""); // remove the [
 	    			part.replace("]", ""); // remove the ]
 	    			part.replace("}", ""); // remove the } <= mistake that is in the bdd
 	    			if(NumberUtils.isNumber(part)){
 	    				sum += Integer.parseInt(part);
 	    			}
 	    			else {
 	    				sum += Roman.decode(part);
 	    			}
 	    		}
 	    		//Not using Prepared statement because of no user manipulation in the inputs
 	    		Statement stmt2 = conn.createStatement();
 	    		stmt2.executeQuery("UPDATE PUBLICATIONS SET PUBLICATION_PAGES=" + sum + " WHERE P_ID="+p_id);
 	    		stmt2.close();
 	    		System.out.println(" sum : " + sum);
 	    	}
 	    }
 	    // close the result set, the statement and connect
 	    rset.close();
 	    stmt.close();
 	    conn.close();
 	    System.out.println("Done");
 	  }
 
 }