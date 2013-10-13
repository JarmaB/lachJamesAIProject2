package aima.core.search.nursecsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a binary constraint which restricts the amount of shifts a Nurse can work in a period
 * 
 * @author Ruediger Lunde
 * @extended Lachlan Archibald
 */
public class StraightShiftsConstraint implements Constraint {

	private Variable[] variables;
	private List<Variable> scope;
	private int maxShifts = 5; // maximum shifts without a day off

	public StraightShiftsConstraint(Variable[]  vars) {
		this.variables = vars;
		this.maxShifts = maxShifts;
		scope = new ArrayList<Variable>(variables.length);
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
		int shiftCount = 0; // assuming always starts the week after a break
		for (int i = 0; i < variables.length; i++){
			values[i] = assignment.getAssignment(variables[i]);
			if(values[i].equals("DAY") || values[i].equals("NIGHT")){
				shiftCount++;
			} else {
				// must be OFF shift, reset the count
				shiftCount = 0;
			}
		}
		return shiftCount <= this.maxShifts;
	}
}
