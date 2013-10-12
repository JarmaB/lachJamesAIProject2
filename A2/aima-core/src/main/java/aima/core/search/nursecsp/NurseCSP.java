package aima.core.search.nursecsp;

import java.util.ArrayList;
import java.util.List;
import aima.core.search.nursecsp.Nurse;

/**
 * Artificial Intelligence A Modern Approach (3rd Ed.): Figure 6.1, Page 204.<br>
 * <br>
 * The principal states and territories of Australia. Coloring this map can be
 * viewed as a constraint satisfaction problem (CSP). The goal is to assign
 * colors to each region so that no neighboring regions have the same color.
 * 
 * @author Ruediger Lunde
 * @author Mike Stampone
 */
public class NurseCSP extends CSP {
	public static final Variable NSW = new Variable("NSW");
	public static final Variable NT = new Variable("NT");
	public static final Variable Q = new Variable("Q");
	public static final Variable SA = new Variable("SA");
	public static final Variable T = new Variable("T");
	public static final Variable V = new Variable("V");
	public static final Variable WA = new Variable("WA");
	public static final String RED = "RED";
	public static final String BLUE = "BLUE";
	public static final String GREEN = "GREEN";
	// shift types
	public static final String DAY = "DAY";
	public static final String NIGHT = "NIGHT";
	public static final String OFF = "OFF";
	// grades
	public static final String RN = "RN";	// registered nurse
	public static final String SRN = "SRN";	// senior registered nurse
	//shift patterns
	public static final String D = "D";		// day shifts only
	public static final String N = "N";		// night shifts only
	public static final String DN = "DN";	// day and night shifts
	public static final int MINSRN = 1;		// minimum SRN required per shift
	
	// data
	private static int period;
	private int maxShifts;
	private static int numNurses;
	private Nurse[] nurseArray;
	//private Variable[][] variables = new Variable[numNurses][7];

	/**
	 * Returns the principle states and territories of Australia as a list of
	 * variables.
	 * 
	 * @return the principle states and territories of Australia as a list of
	 *         variables.
	 */
	
	private static List<Variable> collectVariables() {
		List<Variable> variables = new ArrayList<Variable>();
		for (int i = 0; i< numNurses; i++){
			for(int day = 0; day < period; day++){
			 variables.add(new Variable("Nurse" + i + "_" + day));
			}
		}
		return variables;
	}
	
	private static Variable[][] transpose2DVarArray(Variable[][] vars2D){
		int rows = vars2D.length;
		int columns = vars2D[0].length;
		Variable[][] transVariable = new Variable[columns][rows];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				transVariable[i][j] = vars2D[j][i];
			}
		}
		return transVariable;
	}

	/**
	 * Constructs a map CSP for the principal states and territories of
	 * Australia, with the colors Red, Green, and Blue.
	 */
	public NurseCSP(int p, Nurse[] nurseArray) {
		super(collectVariables());
		
		Domain shiftType = new Domain(new Object[] { DAY, NIGHT, OFF });
		period = p;
		this.nurseArray = nurseArray;
		numNurses = nurseArray.length;
		
		if(this.period == 7)
			this.maxShifts = 5;
		else if (this.period == 14)
			this.maxShifts = 10;
		
		for (Variable var : getVariables())
			setDomain(var, shiftType);
		
		// Transform Variable Array
		Variable[][] nurse2D = new Variable[numNurses][period];
		for(int n=0; n < numNurses; n++){
			for (int day = 0; day < period; day++){
				nurse2D[n][day] = getVariables().get(n*period + day);
			}
		}
		// Transpose Variable Array
		Variable[][] transNurse2D = transpose2DVarArray(nurse2D);
		
		// add more constraints now?
		for(int n = 0; n < numNurses; n++){
			addConstraint(new MaximumShiftsConstraint(nurse2D[n], this.maxShifts)); // this Nurse can only work a maximum of maxShifts per period
		}
		
		for(int day = 0; day < period; day++){
			//addConstraint(new MinimumValueConstraint(transNurse2D[day], NIGHT, 3));
			addConstraint(new MinimumSRNConstraint(transNurse2D[day], DAY, MINSRN, nurseArray));	// must be at least one SRN on day shifts
			addConstraint(new MinimumSRNConstraint(transNurse2D[day], NIGHT, MINSRN, nurseArray));	// must be at least one SRN on night shifts
		}
		//addConstraint(new MinimumValueConstraint(nurse2D, MINSRN));

	}
	
	
	
}