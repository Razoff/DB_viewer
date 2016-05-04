import java.sql.*;
import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;

public class DB_viewer {

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

	    // Create a statement
	    Statement stmt = conn.createStatement();

	    // Do the SQL "Hello World" thing
	    ResultSet rset = stmt.executeQuery("select aut_name from AUTHOR where aut_id <= 12");

	    while (rset.next())
	      System.out.println(rset.getString(1));
	    // close the result set, the statement and connect
	    rset.close();
	    stmt.close();
	    conn.close();
	    System.out.println("Your JDBC installation is correct.");
	  }

}
