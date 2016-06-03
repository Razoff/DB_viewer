import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class ViewerFrame extends JFrame{

	public static JPanel mainPanel, filtersPanel, buttonPanel, displayPanel;
	public static JLabel contentLabel;
	public static JTable resultTable;
	public static JSpinner limitSpinner;
	public static JComboBox<String> tablesBox;
	public static ArrayList<FilterPanel> filterList;
	
	private static Connection conn;
	
	//private static String[] tables = {"Authors", "Awards", "Awards Categories", "Awards Types", "Languages", "Notes", "Publishers", "Publications", "Publications Series", "Publications Authors", "Publications Content", "Reviews", "Tags", "Title", "Title Awards", "Title Series", "Title Tags", "Webpages"};
	public static String[] tables;
	public static Map<String,String> fields;
	public static ResultSet resultSet;
	
	public ViewerFrame(Connection conn){
		super();
		this.conn = conn;
		tables = DBManager.getTablesList(conn);
		fields = DBManager.getFieldList(conn, tables[0]);
		filterList = new ArrayList<>();
		
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
			String[] fieldsNames= fields.keySet().toArray(new String[fields.keySet().size()]);
			Arrays.sort(fieldsNames);
			for(FilterPanel p : filterList){
				DefaultComboBoxModel model = new DefaultComboBoxModel(fieldsNames);
				p.fieldsBox.setModel(model);
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
			List<Filter> filters = new ArrayList<>();
			for(FilterPanel p : filterList){
				String left = (String)p.fieldsBox.getSelectedItem();
				String op =  (String)p.operatorsCombox.getSelectedItem();
				String right = p.valueTextField.getText();
				if(!right.equals(" Default value ")){
					filters.add(new Filter(left,op,right));
				}
			}
			ResultSet res = DBManager.doQuery(conn, fields,(String)tablesBox.getSelectedItem(), filters, (Integer)limitSpinner.getValue());
			try {
				resultTable.setModel(buildTableModel(res));
				mainPanel.validate();
				mainPanel.repaint();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			  
		});
		buttonPanel.add(validateButton);
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		buttonPanel.add(Box.createHorizontalGlue());
		filtersPanel.add(buttonPanel);
		
		mainPanel.add(filtersPanel, BorderLayout.PAGE_START);
		
		//Center part of the window
		
		displayPanel = new JPanel();
		displayPanel.setBorder(BorderFactory.createCompoundBorder(
	    	       BorderFactory.createEmptyBorder(10, 10, 10, 10),
	    	       BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK)));
		//centerPanel.setSize(800,400);
		//contentLabel = new JLabel("Test");
		//displayPanel.add(contentLabel);
		displayPanel.setLayout(new BorderLayout());
		resultTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(resultTable);
		resultTable.setFillsViewportHeight(true);
		displayPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(displayPanel, BorderLayout.CENTER);
		
		//Bottom
		JPanel bottomPanel = new JPanel();
		JLabel limitLabel = new JLabel("Limite (0 = inf) ");
		bottomPanel.add(limitLabel);
		limitSpinner = new JSpinner(new SpinnerNumberModel(20, 0, Integer.MAX_VALUE,1));
		bottomPanel.add(limitSpinner);
		mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
				
		return mainPanel;
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs)  throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    
	    return new DefaultTableModel(data, columnNames);

	}
}
