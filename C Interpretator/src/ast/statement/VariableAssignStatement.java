package ast.statement;

import ast.Expression;
import ast.Statement;

public class VariableAssignStatement implements Statement{
	
	private String varName;
	private Expression expression;
	
	public VariableAssignStatement(String varName, Expression expression) {
		this.varName = varName;
		this.expression = expression;
	}

	public String getVarName() {
		return varName;
	}

	public Expression getExpression() {
		return expression;
	}
	
	
	
	
}
