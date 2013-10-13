package aima.core.search.nursecsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a binary constraint which restricts the amount of shifts a Nurse can work in a period
 * 
 * @author Ruediger Lunde
 * @extended Lachlan Archibald
 */
public class NightShiftsConstraint implements Constraint {

	private Variable[] variables;
	private List<Variable> scope;

	public NightShiftsConstraint(Variable[]  vars) {
		this.variables = vars;
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
		for (int i = 0; i < variables.length; i++){
			values[i] = assignment.getAssignment(variables[i]);
			if(values[i].equals("NIGHT")){
				if((values[i+1]).equals("NIGHT") || values[i+1].equals("OFF")){
					//acceptable
				} else {
					// unacceptable
					return false;
				}	
			}
		}
		return true;
	}
}
