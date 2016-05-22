import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DB_viewer {

	private static final String url = "jdbc:oracle:thin:@diassrv2.epfl.ch:1521:orcldias";
	private static final String username = "DB2016_G44";
	private static final String password = "DB2016_G44";
	
	private static ConnectionManager connManager;
	
	public static void main(String[] args) throws SQLException {
		connManager = new ConnectionManager(url, username, password);
		Connection conn = connManager.getConnection();
		new ViewerFrame(conn);
	}

}
