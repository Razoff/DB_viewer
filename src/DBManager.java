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
		String result = "";
		if(fields.get(f.left).equals("VARCHAR2") || fields.get(f.left).equals("VARCHAR")){
			result += f.left + f.op + "'" + f.right + "'";
		} else if(fields.get(f.left).equals("CLOB")){
			result += " dbms_lob.compare("+f.left+", to_clob('"+f.right+"')) = 0";
		}
		else{
			result += f.left + f.op + f.right;
		}
		return result;
	}
	
	public static ResultSet doAndQuery(Connection conn, Map<String,String> fields, String table, List<Filter> conditions, int limit){
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
			ResultSet rset = pstmt.executeQuery();
			return rset;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ResultSet doOrQuery(Connection conn, Map<String,String> fields, String table, List<Filter> conditions, int limit){
		try {
			String query = "SELECT * FROM " + table;

			if(conditions.size() > 0 || limit > 0){
				query += " Where ";
			}
			
			if(conditions.size() > 0){
				query += filterToString(conditions.get(0), fields);
				
			}
			
			for(int i = 1; i < conditions.size(); i++){
				query += " OR ";
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
			ResultSet rset = pstmt.executeQuery();
			return rset;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/*public static void doUpdate(Connection conn, String table, Map<String,String> fields, Map<String, String> oldValues, Map<String, String> Newvalues){
		//try {
			String query = "UPDATE " + table + " (" ;
			String columns = "";
			String datas = "";
			for(String k : fields.keySet()){
				columns += k + ",";
				if(values.get(k).equals("null")){
					datas += "null,";
				}
				else if(fields.get(k).equals("NUMBER")){
					datas += values.get(k) + ",";
				} else {
					datas += "'" + values.get(k) + "',";
				}
			}
			query += columns.substring(0, columns.length()-1) + ")";
			query += " (" + datas.substring(0, datas.length()-1) + ")";
			System.out.println(query);
			//PreparedStatement pstmt = conn.prepareStatement(query);
			//ResultSet rset = pstmt.executeQuery();
		/*} catch (SQLException e) {
			e.printStackTrace();
		}*/
	//}
	
	public static void doInsert(Connection conn, String table, Map<String,String> fields, Map<String, String> values){
		//try {
			String query = "INSERT INTO " + table + " (" ;
			String columns = "";
			String datas = "";
			for(String k : fields.keySet()){
				columns += k + ",";
				if(values.get(k).equals("null")){
					datas += "null,";
				}
				else if(fields.get(k).equals("NUMBER")){
					datas += values.get(k) + ",";
				} else {
					datas += "'" + values.get(k) + "',";
				}
			}
			query += columns.substring(0, columns.length()-1) + ")";
			query += " (" + datas.substring(0, datas.length()-1) + ")";
			System.out.println(query);
			//PreparedStatement pstmt = conn.prepareStatement(query);
			//ResultSet rset = pstmt.executeQuery();
		/*} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
	
}
