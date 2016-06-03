import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewerFrame extends JFrame{

	public static JPanel mainPanel, filtersPanel, buttonPanel, displayPanel;
	public static JLabel contentLabel;
	public static JComboBox<String> tablesBox;
	public static ArrayList<JComboBox<String>> fieldsBoxes;
	
	private static Connection conn;
	
	//private static String[] tables = {"Authors", "Awards", "Awards Categories", "Awards Types", "Languages", "Notes", "Publishers", "Publications", "Publications Series", "Publications Authors", "Publications Content", "Reviews", "Tags", "Title", "Title Awards", "Title Series", "Title Tags", "Webpages"};
	private static String[] tables;
	public static String[] fields;
	
	public ViewerFrame(Connection conn){
		super();
		this.conn = conn;
		tables = DBManager.getTablesList(conn);
		fields = DBManager.getFieldList(conn, tables[0]);
		fieldsBoxes = new ArrayList<>();
		
		setTitle("DB Viewer");
		setSize(800, 600);
		setResizable(true);
		setLocationRelativeTo(null); //center
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(initLayout());
		setVisible(true);
		
	}
 
	private JPanel initLayout(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		
		//Add Filter top line (table selection)
		filtersPanel = new JPanel();
		BoxLayout filterLayout = new BoxLayout(filtersPanel, BoxLayout.PAGE_AXIS);
		filtersPanel.setLayout(filterLayout);
		filtersPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JPanel filterIntroPanel = new JPanel();
		filterIntroPanel.setLayout(new BoxLayout(filterIntroPanel, BoxLayout.LINE_AXIS));
		filterIntroPanel.add(Box.createHorizontalGlue());
		
		JLabel lb = new JLabel("Search in the table ");
		filterIntroPanel.add(lb);
		tablesBox = new JComboBox<>(tables);//DBManager.tables.keySet().toArray(new String[0]));
		tablesBox.setMaximumSize(tablesBox.getPreferredSize());
		tablesBox.addActionListener(e -> {
			fields = DBManager.getFieldList(conn, (String)tablesBox.getSelectedItem());
			DefaultComboBoxModel model = new DefaultComboBoxModel(fields);
			for(JComboBox<String> f : fieldsBoxes){
				f.setModel(model);
			}
		});
		filterIntroPanel.add(tablesBox);
		JLabel lb2 = new JLabel(" where:");
		filterIntroPanel.add(lb2);
		filterIntroPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		filterIntroPanel.add(Box.createHorizontalGlue());
		filtersPanel.add(filterIntroPanel);
		//filterPanel.add(Box.createVerticalGlue());

		//Add initial Filter
		filtersPanel.add(new FilterPanel(this));
		
		//Add Button line
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		JButton validateButton = new JButton("Validate");
		validateButton.addActionListener(e -> {
			
			//filtersPanel.add(createFilterPanel());
			//mainPanel.validate();
			//mainPanel.repaint();
		});
		buttonPanel.add(validateButton);
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		buttonPanel.add(Box.createHorizontalGlue());
		filtersPanel.add(buttonPanel);
		
		mainPanel.add(filtersPanel, BorderLayout.PAGE_START);
		
		displayPanel = new JPanel();
		displayPanel.setBorder(BorderFactory.createCompoundBorder(
	    	       BorderFactory.createEmptyBorder(10, 10, 10, 10),
	    	       BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK)));
		//centerPanel.setSize(800,400);
		contentLabel = new JLabel("Test");
		displayPanel.add(contentLabel);
		mainPanel.add(displayPanel, BorderLayout.CENTER);
		
		
		return mainPanel;
	}
}
