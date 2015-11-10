package ast.statement;

import ast.Expression;
import ast.Statement;

public class EchoOperator implements Statement{
	
	private Expression expression;

	public EchoOperator(Expression expression) {
		super();
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}
}
