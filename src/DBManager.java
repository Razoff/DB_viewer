import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public static Map<String,String> getFieldList(Connection conn, String table){
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT COLUMN_NAME,DATA_TYPE FROM user_tab_cols WHERE table_name = ?");
			pstmt.setString(1, table);
			ResultSet rset = pstmt.executeQuery();

			HashMap<String,String> fields = new HashMap<>();
			while (rset.next()){
				fields.put(rset.getString(1), rset.getString(2));
			}
			
			return fields;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] getFieldTypeList(Connection conn, String table){
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
	
	private static String filterToString(Filter f, Map<String, String> fields){
		String result = f.left + f.op;
		if(fields.get(f.left).equals("VARCHAR2") || fields.get(f.left).equals("VARCHAR")){
			result += "'" + f.right + "'";
		} else{
			result += f.right;
		}
		return result;
	}
	
	public static ResultSet doQuery(Connection conn, Map<String,String> fields, String table, List<Filter> conditions, int limit){
		try {
			String query = "SELECT * FROM " + table;

			if(conditions.size() > 0 || limit > 0){
				query += " Where ";
			}
			
			if(conditions.size() > 0){
				query += filterToString(conditions.get(0), fields);
				
			}
			
			for(int i = 1; i < conditions.size(); i++){
				query += " AND ";
				query += filterToString(conditions.get(i), fields);
			}
			if(limit > 0){
				if(conditions.size() > 0){
					query += " AND ";
				}
				query += " rownum <= " + limit;
			}
			System.out.println(query);
			PreparedStatement pstmt = conn.prepareStatement(query);
			/*for(int i = 0; i < conditions.size(); i++){
				System.out.println(1 + 2*i + " " + conditions.get(i).left);
				System.out.println((1 + 2*i + 1) + " " + conditions.get(i).right);
				pstmt.setString(1 + 2*i, conditions.get(i).left);
				pstmt.setString(1 + 2*i + 1, conditions.get(i).right);
			}*/
			ResultSet rset = pstmt.executeQuery();
			return rset;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
