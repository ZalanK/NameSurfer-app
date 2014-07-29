/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		setInteractors();
		
		//Read in the names data from the text file.
		database = new NameSurferDataBase(NAMES_DATA_FILE);
    
		//Adds the graphical display.
		canvas = new NameSurferGraph();
		add(canvas);
	}
	
	private void setInteractors() {
		JLabel label = new JLabel("Name");
		nameField = new JTextField(10);
		grp = new JButton("Graph");
		clr = new JButton("Clear");
		
		nameField.setActionCommand("name");
		
		/* Interactors are all displayed in the southern region, in the order of 
		 * when they're added from left to right. */
		add(label, SOUTH);
		add(nameField, SOUTH);
		add(grp, SOUTH);
		add(clr, SOUTH);
		
		addActionListeners();
		nameField.addActionListener(this); //Lets the text field generate actions
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		
		// Refer a string to what action was performed 
		String cmd = e.getActionCommand();
		
		//Action for pressing Graph button
		if(cmd.equals("Graph")) {
			NameSurferEntry rank = database.findEntry(nameField.getText());
			 if(rank != null) {
				 canvas.addEntry(rank);
				 canvas.update();
	         }			
		}
		
		//Action for pressing Clear button
		else if(cmd.equals("Clear")){
			canvas.clear();
		    canvas.update();
		}
	}
	
	private JTextField nameField;
	private JButton grp;
	private JButton clr;
	private NameSurferDataBase database;
	private NameSurferGraph canvas;
}
	