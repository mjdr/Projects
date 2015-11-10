package ast.math;

import ast.Expression;

public class BinaryOperation implements Expression{
	
	private String operation;
	private Expression expression1;
	private Expression expression2;
	public BinaryOperation(String operation, Expression expression1 , Expression expression2) {
		this.operation = operation;
		this.expression1 = expression1;
		this.expression2 = expression2;
	}
	public String getOperation() {
		return operation;
	}
	public Expression getExpression1() {
		return expression1;
	}
	public Expression getExpression2() {
		return expression2;
	}

}
