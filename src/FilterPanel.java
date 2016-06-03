import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FilterPanel extends JPanel {
	public JComboBox<String> fieldsBox;
	public JComboBox<String> operatorsCombox;
	public JTextField valueTextField;
	public JButton trashButton;
	public JButton plusButton;
	
	public static String[] operators = {" = ", " < ", " <= ", " > ", " >= "};
	
	
	public FilterPanel(ViewerFrame frame){
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(Box.createHorizontalGlue());
		
		fieldsBox = new JComboBox<>(frame.fields);
		//fieldsBox.setMaximumSize(fieldsBox.getPreferredSize());
		add(fieldsBox);
		frame.fieldsBoxes.add(fieldsBox);
		operatorsCombox = new JComboBox<>(operators);
		//operatorsCombox.setMaximumSize(operatorsCombox.getPreferredSize());
		add(operatorsCombox);
		
		valueTextField = new JTextField("value");
		valueTextField.setMaximumSize(operatorsCombox.getPreferredSize());
		add(valueTextField);
		
		BufferedImage trashIcon = null;
		try {
			trashIcon = ImageIO.read(getClass().getResource("images/trash_mini.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		trashButton = new JButton(new ImageIcon(trashIcon));
		trashButton.setBorder(BorderFactory.createEmptyBorder());
		trashButton.setContentAreaFilled(false);
		add(trashButton);
		
		trashButton.addActionListener(e -> {
			if(frame.fieldsBoxes.size() > 1){
				frame.filtersPanel.remove(this);
				frame.fieldsBoxes.remove(fieldsBox);
				frame.mainPanel.validate();
				frame.mainPanel.repaint();
			}
		});
		
		BufferedImage plusIcon = null;
		try {
			plusIcon = ImageIO.read(getClass().getResource("images/plus_mini.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		plusButton = new JButton(new ImageIcon(plusIcon));
		plusButton.setBorder(BorderFactory.createEmptyBorder());
		plusButton.setContentAreaFilled(false);
		add(plusButton);
		
		plusButton.addActionListener(e -> {
			frame.filtersPanel.remove(frame.buttonPanel);
			frame.filtersPanel.add(new FilterPanel(frame));
			frame.filtersPanel.add(frame.buttonPanel);
			frame.mainPanel.validate();
			frame.repaint();
		});

	    setAlignmentX(Component.CENTER_ALIGNMENT);
	    setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
	    add(Box.createHorizontalGlue());
	}
}

class Filter{
	public String left;
	public String op;
	public String right;
	
	public Filter(String left, String op, String right){
		this.left = left;
		this.op = op;
		this.right = right;
	}
}