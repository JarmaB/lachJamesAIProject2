package aima.core.search.nursecsp;
// ------------------------------------------------------
// File: Nurse.java 
// Author: Lachlan Archibald
// Description: Store data relating to individual Nurse
// ------------------------------------------------------
public class Nurse {
	private int ID;
	private String grade;
	private int numShifts;
	private int lastOff;
	private String lastShift;		// Domain: {"DAY" | "NIGHT" | "OFF" }
	private String shiftPattern;	// Domain: {"D" | "N" | "DN" }
	private String[] fixedShifts; 	// Domain: {"DAY" | "NIGHT" | "OFF" }
	
	public Nurse(int ID, String grade, String shiftPattern, int numShifts, int lastOff, String lastShift){
		this.ID = ID;
		this.grade = grade;
		this.shiftPattern = shiftPattern;
		this.numShifts = numShifts;
		this.lastOff = lastOff;
		this.lastShift = lastShift;
	}
	
	public Nurse(int ID, String grade, String shiftPattern, int numShifts, int lastOff, String lastShift, String[] fixedShifts){
		this.ID = ID;
		this.grade = grade;
		this.shiftPattern = shiftPattern;
		this.numShifts = numShifts;
		this.lastOff = lastOff;
		this.lastShift = lastShift;
		this.fixedShifts = new String[fixedShifts.length];
		for(int i = 0; i < fixedShifts.length; i++){
			this.fixedShifts[i] = fixedShifts[i];
		}
	}
	
	public int getID(){
		return this.ID;
	}
	
	public String getGrade(){
		return this.grade;
	}
	
	public String getShiftPattern(){
		return this.shiftPattern;
	}
	public int getNumShifts(){
		return this.numShifts;
	}
	public int getLastOff(){
		return this.lastOff;
	}
	public String getLastShift(){
		return this.lastShift;
	}
	public String[] getFixedShifts(){
		return this.fixedShifts;
	}
}
