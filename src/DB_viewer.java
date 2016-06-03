import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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
		JFrame frame = new JFrame("DB Interface");
		
		frame.setTitle("DB Viewer");
		//frame.setSize(800, 600);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null); //center
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		JButton searchButton =  new JButton("View / Insert");
		searchButton.addActionListener(e -> {
			new ViewerFrame(conn);
		});
		content.add(searchButton);
		JButton globalButton =  new JButton("Global Search");
		globalButton.addActionListener(e -> {
			new SearchFrame(conn);
		});
		content.add(globalButton);

		frame.setContentPane(content);
		frame.pack();
		frame.setVisible(true);
		
	}

}
