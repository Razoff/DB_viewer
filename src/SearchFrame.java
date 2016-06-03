import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.math.NumberUtils;

public class SearchFrame extends JFrame{

	private static Connection conn;

	public static JPanel mainPanel, searchPanel, displayPanel, tablesPanel;
	public static JTextField searchTextField;
	public static JComboBox<String> tablesBox;
	//public static JTable resultTable;
	public static String[] tables;
	//public static Map<String,String> fields;
	public static ResultSet resultSet;

	public SearchFrame(Connection conn){
		super();
		this.conn = conn;
		tables = DBManager.getTablesList(conn);


		setTitle("Search");
		setSize(800, 600);
		setResizable(true);
		setLocationRelativeTo(null); //center
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(initLayout());
		setVisible(true);

	}

	private JPanel initLayout(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel containerSearch = new JPanel();
		containerSearch.setLayout(new BoxLayout(containerSearch, BoxLayout.PAGE_AXIS));
		containerSearch.add(Box.createRigidArea(new Dimension(0, 10)));

		searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.LINE_AXIS));
		searchPanel.add(Box.createHorizontalGlue());

		JLabel searchLabel = new JLabel("Search ");
		searchPanel.add(searchLabel);

		searchTextField = new JTextField("Something To Search");
		searchPanel.add(searchTextField);

		JButton validateButton = new JButton("Validate");
		validateButton.addActionListener(e -> {
			try {
				doSearch(searchTextField.getText());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		searchPanel.add(validateButton);

		searchPanel.add(Box.createHorizontalGlue());
		containerSearch.add(searchPanel);
		mainPanel.add(containerSearch, BorderLayout.PAGE_START);

		displayPanel = new JPanel();
		displayPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK)));
		displayPanel.setLayout(new BorderLayout());
		//resultTable = new JTable();

		tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.PAGE_AXIS));

		JScrollPane scrollPane = new JScrollPane(tablesPanel);
		//resultTable.setFillsViewportHeight(true);
		displayPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.add(displayPanel, BorderLayout.CENTER);
		return mainPanel;
	}

	public void doSearch(String text) throws SQLException{
		//Search for exact word in any table
		boolean is_number = NumberUtils.isNumber(text);
		tablesPanel.removeAll();

		for(int i = 0; i < tables.length; i++){
			Map<String, String> fields = DBManager.getFieldList(conn, tables[i]);

			ArrayList<Filter> filters = new ArrayList<>();
			for(String field : fields.keySet()){
				/*if(!(fields.get(field).equals("NUMBER") && !is_number)){
				filters.add(new Filter(field, " = ", value));
				}*/
				if(!(fields.get(field).equals("NUMBER"))){
					filters.add(new Filter(field, " = ", text));
				}
			}
			if(!filters.isEmpty()){
				ResultSet res = DBManager.doOrQuery(conn, fields, tables[i], filters, 20);
				DefaultTableModel model = buildTableModel(res);
				if(model != null) {
					String tableName = tables[i];
					tablesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
					JLabel lbl = new JLabel("Found in " + tables[i] );
					tablesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
					lbl.setHorizontalAlignment(SwingConstants.LEFT);
					tablesPanel.add(lbl);
					JTable resultTable = new JTable(model);
					//on double click open Details
					resultTable.addMouseListener(new MouseAdapter() {
					    public void mousePressed(MouseEvent me) {
					        JTable table =(JTable) me.getSource();
					        Point p = me.getPoint();
					        int row = table.rowAtPoint(p);
					        if (me.getClickCount() == 2) {
					            Map<String, Object> rowData = new HashMap<>();
					        	for(int i = 0; i < table.getColumnCount(); i++){
					        		rowData.put(table.getColumnName(i), table.getValueAt(row, i));
					            }
					        	new EditFrame(conn, tableName, fields, rowData);
					        }
					    }
					});
					JScrollPane tempScoll = new JScrollPane(resultTable);
					Dimension d = resultTable.getPreferredSize();
					tempScoll.setPreferredSize( new Dimension(d.width,100));
					tablesPanel.add(tempScoll);
				}
			}
			
		}
		mainPanel.validate();
		mainPanel.repaint();
	}




	public static DefaultTableModel buildTableModel(ResultSet rs)  throws SQLException {
		if(rs == null){
			return null;
		}
		
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

		if(data.size() == 0){
			return null;
		}

		return new DefaultTableModel(data, columnNames);

	}
}
