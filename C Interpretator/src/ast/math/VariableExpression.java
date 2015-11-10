package ast.math;

import ast.Expression;

public class VariableExpression implements Expression {
	
	private String name;

	public VariableExpression(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
