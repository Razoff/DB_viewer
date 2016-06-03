import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {

	public static String[] getTablesList(Connection conn){
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("SELECT table_name FROM user_tables");

			ArrayList<String> tables = new ArrayList<>();
			while (rset.next()){
				tables.add(rset.getString(1));
			}
			 java.util.Collections.sort(tables);
			return tables.toArray(new String[tables.size()]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] getFieldList(Connection conn, String table){
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT column_name FROM user_tab_cols WHERE table_name = ?");
			pstmt.setString(1, table);
			ResultSet rset = pstmt.executeQuery();

			ArrayList<String> fields = new ArrayList<>();
			while (rset.next()){
				fields.add(rset.getString(1));
			}
			 java.util.Collections.sort(fields);
			return fields.toArray(new String[fields.size()]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static ResultSet doQuery(Connection conn, Filter[] conditions){
		try {
			String query = "SELECT column_name FROM ? WHERE ";
			if(conditions.length > 1){
				query += "? "+ conditions[0].op  + " ?";
			}
			
			for(int i = 1; i < conditions.length; i++){
				query += " AND ";
				query += "? "+ conditions[i].op  + " ?";
			}
			query += ";";
					
			PreparedStatement pstmt = conn.prepareStatement(query);
			for(int i = 0; i < conditions.length; i++){
				pstmt.setString(2*i, conditions[i].left);
				pstmt.setString(2*i+1, conditions[i].right);
			}
			ResultSet rset = pstmt.executeQuery();

			return rset;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
