import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class EditFrame extends JFrame{

	private static JPanel mainPanel;
	private Map<String, String> fields;
	private Map<String, Object> values;
	private Map<String, JTextField> compos;
	private String table;
	
	private static Connection conn;
	
	public EditFrame(Connection conm, String table, Map<String, String> fields, Map<String, Object> values){
		super();
		this.conn = conn;
		this.table = table;
		this.values = values;
		this.fields = fields;
		compos = new HashMap<>();
		setTitle("Details");
		//setSize(800, 600);
		setResizable(true);
		setLocationRelativeTo(null); //center
		setContentPane(initLayout());
		setVisible(true);
		pack();
	}

	private JPanel initLayout(){
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		//Create and populate the panel.
		JPanel p = new JPanel(new SpringLayout());

		
		for(String k : values.keySet()){
			if(values.get(k) != null){
				System.out.println(values.get(k).getClass().getName());
			}
			System.out.println(fields.get(k));
			System.out.println("----------");
			
		    JLabel l = new JLabel(k, JLabel.TRAILING);
		    p.add(l);
		    if(values.get(k) != null){
		    	JTextField textField = new JTextField(values.get(k).toString());
		    	l.setLabelFor(textField);
		    	p.add(textField);
		    	compos.put(k, textField);
		    } else{
		    	JTextField textField = new JTextField("null");
		    	l.setLabelFor(textField);
		    	p.add(textField);
		    	compos.put(k, textField);
		    }
		    
			
		}
		
		//Lay out the panel.
		SpringUtilities.makeGrid(p,
				values.keySet().size(), 2, //rows, cols
				0, 0,        //initX, initY
				6, 6);       //xPad, yPad

		
		mainPanel.add(p, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		JButton validateButton = new JButton("Validate");
		validateButton.addActionListener(e -> {
			HashMap<String, String> val = new HashMap<>();
			for(String k : values.keySet()){
				String text = compos.get(k).getText();
				val.put(k, text);
			}
			DBManager.doInsert(conn, table, val, val);
		});
		buttonPanel.add(validateButton);
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		buttonPanel.add(Box.createHorizontalGlue());
		
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		
		return mainPanel;
	}

}
