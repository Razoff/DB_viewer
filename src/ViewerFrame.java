import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ViewerFrame extends JFrame{

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
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
				
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.PAGE_AXIS));
		filterPanel.setBackground(Color.white);
		
		JPanel filterIntroPanel = new JPanel();
		filterIntroPanel.setLayout(new BoxLayout(filterIntroPanel, BoxLayout.LINE_AXIS));
		
		JLabel lb = new JLabel("Search in the table ");
		filterIntroPanel.add(lb);
		String[] tables = {"Authors", "Titles"};
		JComboBox<String> tableCombox = new JComboBox<>(tables);
		filterIntroPanel.add(tableCombox);
		JLabel lb2 = new JLabel(" where:");
		filterIntroPanel.add(lb2);
		filterIntroPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		filterPanel.add(filterIntroPanel);
		

		JPanel filterPanel1 = new JPanel();
		filterPanel1.setLayout(new BoxLayout(filterPanel1, BoxLayout.LINE_AXIS));
		
		String[] fields = {"Name", "ID"};
		JComboBox<String> fieldsCombox = new JComboBox<>(fields);
		filterPanel1.add(fieldsCombox);
		String[] operators = {" = ", " < ", " <= ", " > ", " >= "};
		JComboBox<String> operatorsCombox = new JComboBox<>(operators);
		filterPanel1.add(operatorsCombox);
		JTextField valueTextField = new JTextField();
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
		filterPanel.add(filterPanel1);
		

		mainPanel.add(filterPanel, BorderLayout.PAGE_START);
		
		
		return mainPanel;
	}
	
}
