import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewerFrame extends JFrame{

	private static JPanel mainPanel, filterPanel, displayPanel;
	private static JLabel contentLabel;
	
	public ViewerFrame(){
		super();
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
				
		filterPanel = new JPanel();
		BoxLayout filterLayout = new BoxLayout(filterPanel, BoxLayout.PAGE_AXIS);
		filterPanel.setLayout(filterLayout);
		filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		//filterPanel.setBackground(Color.white);
		
		JPanel filterIntroPanel = new JPanel();
		filterIntroPanel.setLayout(new BoxLayout(filterIntroPanel, BoxLayout.LINE_AXIS));
		filterIntroPanel.add(Box.createHorizontalGlue());
		
		JLabel lb = new JLabel("Search in the table ");
		filterIntroPanel.add(lb);
		String[] tables = {"Authors", "Titles"};
		JComboBox<String> tableCombox = new JComboBox<>(tables);
		filterIntroPanel.add(tableCombox);
		JLabel lb2 = new JLabel(" where:");
		filterIntroPanel.add(lb2);
		filterIntroPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		filterIntroPanel.add(Box.createHorizontalGlue());
		filterPanel.add(filterIntroPanel);
		//filterPanel.add(Box.createVerticalGlue());
		filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JPanel filterPanel1 = new JPanel();
		filterPanel1.setLayout(new BoxLayout(filterPanel1, BoxLayout.LINE_AXIS));
		filterPanel1.add(Box.createHorizontalGlue());
		
		String[] fields = {"Name", "ID"};
		JComboBox<String> fieldsCombox = new JComboBox<>(fields);
		filterPanel1.add(fieldsCombox);
		String[] operators = {" = ", " < ", " <= ", " > ", " >= "};
		JComboBox<String> operatorsCombox = new JComboBox<>(operators);
		filterPanel1.add(operatorsCombox);
		JTextField valueTextField = new JTextField("value");
		valueTextField.setMaximumSize(operatorsCombox.getPreferredSize());
		filterPanel1.add(valueTextField);
		
	    java.net.URL trashUrl = getClass().getResource("images/trash_mini.png");
	    if (trashUrl != null) {
	        ImageIcon trashIcon = new ImageIcon(trashUrl, "trash");
	        filterPanel1.add(new JLabel(trashIcon));
	    }
		java.net.URL plusUrl = getClass().getResource("images/plus_mini.png");
	    if (plusUrl != null) {
	        ImageIcon plusIcon = new ImageIcon(plusUrl, "plus");
	        filterPanel1.add(new JLabel(plusIcon));
	    }

	    filterPanel1.setAlignmentX(Component.CENTER_ALIGNMENT);
	    filterPanel1.add(Box.createHorizontalGlue());
	    
	    //filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	    filterPanel.add(filterPanel1);
		
		
		mainPanel.add(filterPanel, BorderLayout.PAGE_START);
		
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
