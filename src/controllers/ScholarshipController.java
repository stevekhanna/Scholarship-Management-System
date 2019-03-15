package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import displayScholarship.*;
import objects.*;
import myJStuff.MyController;

public class ScholarshipController extends MyController{
	
	private JPanel viewScholarshipPanel;
	private JPanel allScholarshipsPanel;
	
	
	private ViewScholarshipPanel vsp;
	private AllScholarshipsPanel asp;
	
	private HashMap<Integer, Scholarship> scMap;
	
	
	private boolean isAdmin;
	
	public ScholarshipController(ActionListener globalListener,JFrame frame) {
		super(globalListener, frame);
	}
	
	public void start(boolean isAdmin, HashMap<Integer, Scholarship> scMap) {
		this.isAdmin = isAdmin;
		this.scMap = scMap;
		
		vsp = new ViewScholarshipPanel(this,globalListener,this.isAdmin);
		asp = new AllScholarshipsPanel(this,globalListener);
		viewScholarshipPanel = vsp.getContentPane();
		allScholarshipsPanel = asp.getContentPane();
		
		scholarshipLoop(scMap);
		switchPanel(allScholarshipsPanel);
	}
	
	/**
	 * Loop through the list of scholarships and add them to the screen
	 * @param scMap
	 */
	public void scholarshipLoop(HashMap<Integer, Scholarship> scMap) {
		for(Integer ID: scMap.keySet()) {
			Scholarship value = scMap.get(ID);
			asp.displayScholarship(value);
		}
	}
	
	private Scholarship searchForScholarship(String name) {
		for(Integer ID: scMap.keySet()) {
			if(scMap.get(ID).getName().equals(name)) {
				return scMap.get(ID);
			}
		}	
		return null;
	}
	
	private void switchToViewScholarshipPanel(Scholarship scholarship) {
		vsp.dispalyScholarship(scholarship);
		switchPanel(viewScholarshipPanel);
	}
	
	
	public HashMap<Integer, Scholarship> getScMap(){
		return scMap;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();
		String name = source.getName();
		
		switch(name){
		case"ViewScholarship_AllScholarshipsPanel":
			int id = Integer.parseInt(source.getActionCommand());
			Scholarship s = scMap.get(id);
			switchToViewScholarshipPanel(s);
			break;
		case"Back_ViewScholarshipPanel":
			switchPanel(allScholarshipsPanel);
			break;
		case"Search_AllScholarshipsPanel":
			String x = asp.getSearchResult();
			
		// Case for search button 
			Scholarship sr = searchForScholarship(x);
			
			if(sr != null) {
				switchToViewScholarshipPanel(sr);
				asp.displayErrorMessage( "");

	
			}else {
				//Display label saying could'nt find scholarship
				asp.displayErrorMessage("Error, could not find scholarship with name " + x);
			}
			break;
		
			
		// Have the scholarship
		// switchToViewScholarhipPanel( The scholarship that you found)
		default:
			break;
		}
		
	}

}
