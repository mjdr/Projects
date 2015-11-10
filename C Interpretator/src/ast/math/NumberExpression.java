package ast.math;

import ast.Expression;

public class NumberExpression implements Expression {
	
	private double value;
	
	public NumberExpression(double value) {
		this.value = value;
	}
	
	public double getValue() { return value; }
	
}
