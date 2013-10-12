package aima.core.search.nursecsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a binary constraint which restricts the amount of shifts a Nurse can work in a period
 * 
 * @author Ruediger Lunde
 * @extended Lachlan Archibald
 */
public class MaximumShiftsConstraint implements Constraint {

	private Variable[] variables;
	private List<Variable> scope;
	private int maxShifts;

	public MaximumShiftsConstraint(Variable[]  vars, int maxShifts) {
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
		//Object value1 = assignment.getAssignment(var1);
		//return value1 == null && value1.equals(assignment.getAssignment(var2));
		Object[] values = new Object[variables.length];
		int shiftCount = 0;
		for (int i = 0; i < variables.length; i++){
			values[i] = assignment.getAssignment(variables[i]);
			if(values[i].equals("DAY") || values[i].equals("NIGHT")){
				shiftCount++;
			}
		}
		return shiftCount <= this.maxShifts;
	}
}
