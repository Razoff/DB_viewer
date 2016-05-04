import java.sql.Connection;

public class getConnection {
	
	private String url;
	private String username;
	private String password;
	
	public getConnection(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public Connection get_connec(){
		return null;
		
	}
}