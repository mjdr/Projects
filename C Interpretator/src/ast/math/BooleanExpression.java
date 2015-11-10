package ast.math;

import ast.Expression;

public class BooleanExpression implements Expression {
	
	private boolean value;
	
	public BooleanExpression(boolean value) {
		this.value = value;
	}
	
	public boolean getValue(){ return value; }
}
