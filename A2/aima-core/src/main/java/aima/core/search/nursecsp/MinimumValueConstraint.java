package aima.core.search.nursecsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a binary constraint which requires a minimum number of variables with a particular value
 * 
 * @author Ruediger Lunde
 * @extended Lachlan Archibald
 */
public class MinimumValueConstraint implements Constraint {

	private Variable[] variables;
	private int minVal;
	private String value;
	private List<Variable> scope;

	public MinimumValueConstraint(Variable[] vars, String value, int minVal) {
		this.variables = vars;
		this.value = value;
		this.minVal = minVal;
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
			if(values[i].equals(this.value)){
				
			}
		}
		return numVal >= this.minVal;
	}
}
