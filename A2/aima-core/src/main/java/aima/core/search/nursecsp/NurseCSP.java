package aima.core.search.nursecsp;

import java.util.ArrayList;
import java.util.List;

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
	public static final int minSRN = 1;		// minimum SRN required per shift
	
	// data
	public int period;
	public int maxShifts;
	public int numNurses;
	

	/**
	 * Returns the principle states and territories of Australia as a list of
	 * variables.
	 * 
	 * @return the principle states and territories of Australia as a list of
	 *         variables.
	 */
	private static List<Variable> collectVariables() {
		List<Variable> variables = new ArrayList<Variable>();
		variables.add(NSW);
		variables.add(WA);
		variables.add(NT);
		variables.add(Q);
		variables.add(SA);
		variables.add(V);
		variables.add(T);
		return variables;
	}

	/**
	 * Constructs a map CSP for the principal states and territories of
	 * Australia, with the colors Red, Green, and Blue.
	 */
	public NurseCSP(int period) {
		super(collectVariables());

		Domain shiftType = new Domain(new Object[] { DAY, NIGHT, OFF });

		for (Variable var : getVariables())
			setDomain(var, shiftType);

		addConstraint(new NotEqualConstraint(WA, NT));
		addConstraint(new NotEqualConstraint(WA, SA));
		addConstraint(new NotEqualConstraint(NT, SA));
		addConstraint(new NotEqualConstraint(NT, Q));
		addConstraint(new NotEqualConstraint(SA, Q));
		addConstraint(new NotEqualConstraint(SA, NSW));
		addConstraint(new NotEqualConstraint(SA, V));
		addConstraint(new NotEqualConstraint(Q, NSW));
		addConstraint(new NotEqualConstraint(NSW, V));
	}
}