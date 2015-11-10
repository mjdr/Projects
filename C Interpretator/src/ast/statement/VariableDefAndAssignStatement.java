package ast.statement;

import ast.Expression;
import ast.Statement;
import ast.Variable.VariableType;

public class VariableDefAndAssignStatement implements Statement {
	
	private VariableType type;
	private String varName;
	private Expression expression;
	
	public VariableDefAndAssignStatement(VariableType type, String varName, Expression expression) {
		this.type = type;
		this.varName = varName;
		this.expression = expression;
	}

	public String getVarName() {
		return varName;
	}
	public VariableType getType() {
		return type;
	}
	public Expression getExpression() {
		return expression;
	}
	
	
}
