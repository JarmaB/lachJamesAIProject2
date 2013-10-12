package aima.core.search.nursecsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a binary constraint which requires a minimum number of variables with a particular value
 * 
 * @author Ruediger Lunde
 * @extended Lachlan Archibald
 */
public class MinimumSRNConstraint implements Constraint {

	private Variable[] variables;
	private String value;
	private int minVal;
	private Nurse[] nurseArray;
	private List<Variable> scope;

	public MinimumSRNConstraint(Variable[] vars, String value, int minVal, Nurse[] nurses) {
		this.variables = vars;
		this.minVal = minVal;
		this.nurseArray = nurses;
		scope = new ArrayList<Variable>(vars.length);
		for(Variable var : variables)
		scope.add(var);
	}

	@Override
	public List<Variable> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment assignment) {
		Object[] values = new Object[variables.length];
		int numVal = 0;
		for (int i = 0; i < variables.length; i++){
			values[i] = assignment.getAssignment(variables[i]);
			if(this.nurseArray[i].getGrade().equals("SRN") && values[i].equals(this.value)){
				numVal++;
			}
		}
		return numVal >= this.minVal;
	}
}
