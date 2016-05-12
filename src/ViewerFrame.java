import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ViewerFrame extends JFrame{

	private static JPanel mainPanel, filtersPanel, displayPanel;
	private static JLabel contentLabel;
	private static JComboBox<String> tablesBox;
	
	//TODO: Replace with a better solution
	private static String[] tables = {"Authors", "Awards", "Awards Categories", "Awards Types", "Languages", "Notes", "Publishers", "Publications", "Publications Series", "Publications Authors", "Publications Content", "Reviews", "Tags", "Title", "Title Awards", "Title Series", "Title Tags", "Webpages"};
	private static String[] fields = {"Name", "ID"}; 
	private static String[] operators = {" = ", " < ", " <= ", " > ", " >= "};
	
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
				
		filtersPanel = new JPanel();
		BoxLayout filterLayout = new BoxLayout(filtersPanel, BoxLayout.PAGE_AXIS);
		filtersPanel.setLayout(filterLayout);
		filtersPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JPanel filterIntroPanel = new JPanel();
		filterIntroPanel.setLayout(new BoxLayout(filterIntroPanel, BoxLayout.LINE_AXIS));
		filterIntroPanel.add(Box.createHorizontalGlue());
		
		JLabel lb = new JLabel("Search in the table ");
		filterIntroPanel.add(lb);
		tablesBox = new JComboBox<>(tables);
		tablesBox.setMaximumSize(tablesBox.getPreferredSize());
		filterIntroPanel.add(tablesBox);
		JLabel lb2 = new JLabel(" where:");
		filterIntroPanel.add(lb2);
		filterIntroPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		filterIntroPanel.add(Box.createHorizontalGlue());
		filtersPanel.add(filterIntroPanel);
		//filterPanel.add(Box.createVerticalGlue());

		filtersPanel.add(createFilterPanel());
		
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
	
	public JPanel createFilterPanel(){
		
		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.LINE_AXIS));
		filterPanel.add(Box.createHorizontalGlue());
		
		JComboBox fieldsBox = new JComboBox<>(fields);
		fieldsBox.setMaximumSize(fieldsBox.getPreferredSize());
		filterPanel.add(fieldsBox);
		JComboBox<String> operatorsCombox = new JComboBox<>(operators);
		operatorsCombox.setMaximumSize(operatorsCombox.getPreferredSize());
		filterPanel.add(operatorsCombox);
		
		JTextField valueTextField = new JTextField("value");
		valueTextField.setMaximumSize(operatorsCombox.getPreferredSize());
		filterPanel.add(valueTextField);
		
		BufferedImage trashIcon = null;
		try {
			trashIcon = ImageIO.read(getClass().getResource("images/trash_mini.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JButton trashButton = new JButton(new ImageIcon(trashIcon));
		trashButton.setBorder(BorderFactory.createEmptyBorder());
		trashButton.setContentAreaFilled(false);
		filterPanel.add(trashButton);
		
		trashButton.addActionListener(e -> {
			if(filtersPanel.getComponents().length > 3){
				filtersPanel.remove(filterPanel);
				mainPanel.validate();
				mainPanel.repaint();
			}
		});
		
		BufferedImage plusIcon = null;
		try {
			plusIcon = ImageIO.read(getClass().getResource("images/plus_mini.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JButton plusButton = new JButton(new ImageIcon(plusIcon));
		plusButton.setBorder(BorderFactory.createEmptyBorder());
		plusButton.setContentAreaFilled(false);
		filterPanel.add(plusButton);
		
		plusButton.addActionListener(e -> {
			filtersPanel.add(createFilterPanel());
			mainPanel.validate();
			mainPanel.repaint();
		});

	    filterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
	    filterPanel.add(Box.createHorizontalGlue());
	    
		return filterPanel;
	}
	
	
}
