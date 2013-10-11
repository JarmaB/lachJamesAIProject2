package aima.gui.applications.search.nurse;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class NurseGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -388505794777779557L;
	Object[][] nurseData = {
		{"Nurse0","SRN","5","DN","O","0"},
		{"Nurse1","SRN","5","DN","O","0"},
		{"Nurse2","SRN","5","DN","O","0"},
		{"Nurse3","SRN","5","DN","O","0"},
		{"Nurse4","SRN","5","DN","O","0"}
	};
	
	int period = 7;

	public NurseGUI() {
		setupPanel();
	}

	private void setupPanel() {
		
		// Create main components
		JLabel title = new JLabel("Nurse Rostering Application", JLabel.CENTER);
	    title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
	    Font readable = new Font(Font.DIALOG, Font.PLAIN, 15);
	    EmptyBorder emptyBorder = new EmptyBorder(10,10,10,10);
	    //contactList = new JList(contactData);
	    //contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    //contactList.setSelectedIndex(-1);
	    //selectedContact = contactList.getSelectedIndex();
	    //contactList.addListSelectionListener(this);
	    //contactList.addMouseListener(new ListMouseEvent(contactList));
	    //contactList.setFont(readable);
	
    	JPanel nurseConfig = new JPanel(new BorderLayout());
        nurseConfig.setBorder(new EmptyBorder(10,10,3,10));
        //nurseConfig.setPreferredSize(new Dimension(200,300));
        	GridLayout fieldGrid = new GridLayout(0, 2);
        	JPanel fieldArea = new JPanel(fieldGrid);
	        	JLabel lblNumNurses = new JLabel("# Nurses: ", JLabel.LEFT);
	        		lblNumNurses.setFont(readable);
	        	JLabel lblPeriod = new JLabel("Period: ", JLabel.LEFT);
	        		lblPeriod.setFont(readable);
	        	JLabel lblNumSRN = new JLabel("# Senior RN: ", JLabel.LEFT);
	        		lblNumSRN.setFont(readable);
	            JTextField txtNumNurses = new JTextField(20);
	            JTextField txtPeriod = new JTextField(20);
	            JTextField txtNumSRN = new JTextField(20);
	            fieldArea.add(lblNumNurses);
	            fieldArea.add(txtNumNurses);
	            fieldArea.add(lblPeriod);
	            fieldArea.add(txtPeriod);
	            fieldArea.add(lblNumSRN);
	            fieldArea.add(txtNumSRN);
	       JTable nurseTable;
	    	String[] columnNames = {"Nurses", "Grade", "Shifts", "Shift Pattern", "Last Shift", "Last Off"};
	    	nurseTable = new JTable(nurseData, columnNames);
	       JScrollPane nurseScroll = new JScrollPane(nurseTable);
	       nurseConfig.add(fieldArea, BorderLayout.NORTH);
	       nurseConfig.add(nurseScroll,BorderLayout.CENTER);
	       nurseConfig.add(new JButton("Save Nurses"), BorderLayout.SOUTH);
	    
	       
	       
	    JPanel rosterPanel = new JPanel();
	    	rosterPanel.setPreferredSize(new Dimension(500,500));
		JPanel mainPanel = new JPanel(new BorderLayout());
	    	mainPanel.add(title, BorderLayout.NORTH);
	    	mainPanel.add(nurseConfig, BorderLayout.WEST);
	    	mainPanel.add(rosterPanel, BorderLayout.CENTER);
	    	mainPanel.setBackground(Color.LIGHT_GRAY);
	    	// Add components to frame
	        getContentPane().add(mainPanel);
	        pack();
	        this.setLocationRelativeTo(null); // create window at center
	     
		this.setVisible(true);
	}	
}
