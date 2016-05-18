
public class QueryManager {
	
	private String[] column;
	private String[] tables;
	private String[] where_array;
	private String select = "SELECT ";
	private String from = "FROM ";
	private String where = "WHERE ";
	
	public QueryManager(String[] column , String[] tables, String[] where) {
		this.column = column;
		this.tables = tables;
		this.where_array = where;
	}
	
	private void formateSelect(){
		for (int i = 0; i < column.length; i++) {
			select += column[i];
			
			if(i != column.length - 1){select += ", ";}
		}
	}
	
	private void formateFrom(){
		for (int i = 0; i < tables.length; i++) {
			from += tables[i];
			
			if (i != tables.length - 1){from += ", ";}
		}
	}
	
	private void formateWhere(){
		for(int i = 0; i < where_array.length; i++){
			where += where_array[i] + " ";
		}
	}

	public String formatQuery(){
		formateSelect();
		formateFrom();
		formateWhere();
		
		return (select + from + where);
	}
	
}
