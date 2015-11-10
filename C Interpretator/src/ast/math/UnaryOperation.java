package ast.math;

import ast.Expression;

public class UnaryOperation implements Expression {
	
	private char operation;
	private Expression expression;
	public UnaryOperation(char operation, Expression expression) {
		super();
		this.operation = operation;
		this.expression = expression;
	}
	public char getOperation() {
		return operation;
	}
	public Expression getExpression() {
		return expression;
	}
	
	
	
	
}
