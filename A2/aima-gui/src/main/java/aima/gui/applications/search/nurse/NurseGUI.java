package aima.gui.applications.search.nurse;

import javax.swing.*;
import javax.swing.border.*;

import aima.core.search.nursecsp.NurseCSP;
import aima.core.search.nursecsp.Nurse;

import java.awt.*;
import java.awt.event.*;

public class NurseGUI extends JFrame implements ActionListener {
	/**s
	 * 
	 */
	private static final long serialVersionUID = -388505794777779557L;
	
	/*
	Object[][] nurseData = {
		{"Nurse0","SRN","5","DN","O","0"},
		{"Nurse1","SRN","5","DN","O","0"},
		{"Nurse2","SRN","5","DN","O","0"},
		{"Nurse3","SRN","5","DN","O","0"},
		{"Nurse4","SRN","5","DN","O","0"}
	};
	*/
	
	private Nurse[] nurseData;
	private Object[][] userInput;
	private int numNurses;
	private int nurseTableValues = 6;
	private int period;
	private int srn;
	private JScrollPane nurseScroll;
    private JButton saveInput;
    private JPanel nurseConfig;
    private JPanel mainPanel;
    private JPanel rosterPanel;
    private JPanel fieldArea; 
    private JTable nurseTable;
    private JLabel title; 
    private JLabel lblNumNurses;
	private JLabel lblPeriod;
	private JLabel lblNumSRN;
	private JTextField txtNumNurses;
	private JTextField txtPeriod;
	private JTextField txtNumSRN;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JButton btnGenerateNurses;

	public NurseGUI() {
		setupPanel();
	}

	private void setupPanel() {
		
		// Create main components
		title = new JLabel("Nurse Rostering Application", JLabel.CENTER);
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
	  //nurseConfig.setPreferredSize(new Dimension(200,300));
	    
	    nurseConfig = new JPanel(new BorderLayout());
	    nurseConfig.setBorder(new EmptyBorder(10,10,3,10));
		GridLayout fieldGrid = new GridLayout(0, 2);
	    
		fieldArea = new JPanel(fieldGrid);
		lblNumNurses = new JLabel("# Nurses: ", JLabel.LEFT);
		lblNumNurses.setFont(readable);
		lblPeriod = new JLabel("Period: ", JLabel.LEFT);
		lblPeriod.setFont(readable);
		lblNumSRN = new JLabel("# Senior RN: ", JLabel.LEFT);
		lblNumSRN.setFont(readable);
		txtNumNurses = new JTextField(20);
		txtNumNurses.setText("10");
		txtPeriod = new JTextField(20);
		txtPeriod.setText("7");
		txtNumSRN = new JTextField(20);
		txtNumSRN.setText("4");
		saveInput = new JButton("Save Nurses");
	    saveInput.addActionListener(this);
	    fieldArea.add(lblNumNurses);
	    fieldArea.add(txtNumNurses);
	    fieldArea.add(lblPeriod);
	    fieldArea.add(txtPeriod);
	    fieldArea.add(lblNumSRN);
	    fieldArea.add(txtNumSRN);
	    
	    nurseScroll = new JScrollPane(nurseTable);
	    nurseConfig.add(fieldArea, BorderLayout.NORTH);
	    nurseConfig.add(nurseScroll,BorderLayout.CENTER);
	    nurseConfig.add(saveInput, BorderLayout.SOUTH);
	    rosterPanel = new JPanel();
		rosterPanel.setPreferredSize(new Dimension(500,500));
		
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(title, BorderLayout.NORTH);
		mainPanel.add(nurseConfig, BorderLayout.WEST);
		mainPanel.add(rosterPanel, BorderLayout.CENTER);
		mainPanel.setBackground(Color.LIGHT_GRAY);
		// Add components to frame
		getContentPane().add(mainPanel);
		
		scrollPane = new JScrollPane();
		mainPanel.add(scrollPane, BorderLayout.SOUTH);
		
		panel = new JPanel();
		mainPanel.add(panel, BorderLayout.SOUTH);
		
		btnGenerateNurses = new JButton("Generate Roster");
		btnGenerateNurses.setHorizontalAlignment(SwingConstants.LEFT);
		btnGenerateNurses.addActionListener(this);
		panel.add(btnGenerateNurses);
	    this.setLocationRelativeTo(null); // create window at center
	    pack();
		this.setVisible(true);
	}
	
    public void actionPerformed(ActionEvent e) {
    	/*Action Listener*/
        if(e.getSource() == saveInput) {
        	numNurses = Integer.parseInt(txtNumNurses.getText());
        	period = Integer.parseInt(txtPeriod.getText());
        	srn = Integer.parseInt(txtNumSRN.getText());
        	System.out.println("Number of Nurses is: " + numNurses);
        	System.out.println("Period Number is: " + period);
        	System.out.println("SRN number is: " + srn);
        	userInput = new Object[numNurses][nurseTableValues];
        	for(int i = 0; i < srn; i++) {
        		userInput[i][0] = "Nurse" + i;
    				userInput[i][1] = "SRN";
    				userInput[i][2] = "5";
    				userInput[i][3] = "DN";
    				userInput[i][4] = "OFF";
    				userInput[i][5] = "0";
        	}
        	for(int i = srn; i < numNurses; i++) {
        		userInput[i][0] = "Nurse" + i;
    				userInput[i][1] = "RN";
    				userInput[i][2] = "5";
    				userInput[i][3] = "DN";
    				userInput[i][4] = "OFF";
    				userInput[i][5] = "0";
        	}
    		constructNurseTable(userInput);
       } else if(e.getSource() == btnGenerateNurses) {
    	   String id;
    	   String grade;
    	   String numShifts;
    	   String lastOff;
    	   String lastShift;
    	   String shiftPattern;
    	   for (int i = 0; i < numNurses; i++){
    				id = nurseTable.getValueAt(i, 0).toString();
    				grade = nurseTable.getValueAt(i, 1).toString();
    				numShifts = nurseTable.getValueAt(i, 2).toString();
    				shiftPattern = (String) nurseTable.getValueAt(i, 3);
    				lastShift = (String) nurseTable.getValueAt(i, 4);
    				lastOff = nurseTable.getValueAt(i, 5).toString();
    			//shiftPattern = period;
 		   Nurse newNurse = new Nurse(Integer.parseInt(id), grade, shiftPattern, Integer.parseInt(numShifts), Integer.parseInt(lastOff), lastShift);
    	   }
       }
    }
 
    
    
    private void constructNurseTable(Object[][] userInput) {
    	
		   String[] columnNames = {"Nurses", "Grade", "Shifts", "Shift Pattern", "Last Shift", "Last Off"};
		   nurseConfig.remove(nurseScroll);
		   nurseTable = new JTable(userInput, columnNames);
		   nurseScroll = new JScrollPane(nurseTable);
		   nurseConfig.add(nurseScroll,BorderLayout.CENTER);
		   this.revalidate();
			// Add components to frame	
    } 
}

