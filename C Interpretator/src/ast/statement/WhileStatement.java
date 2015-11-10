package ast.statement;

import ast.Expression;
import ast.Statement;

public class WhileStatement implements Statement{
	
	private Expression condition;
	private Statement body;
	
	public WhileStatement(Expression condition, Statement body) {
		this.condition = condition;
		this.body = body;
	}

	public Expression getCondition() {
		return condition;
	}

	public Statement getBody() {
		return body;
	}
	
	
	
	
	
}
