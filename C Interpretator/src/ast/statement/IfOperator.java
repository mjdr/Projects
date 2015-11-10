package ast.statement;

import ast.Expression;
import ast.Statement;

public class IfOperator implements Statement {
	
	private Expression condition;
	private Statement trueBranch;
	private Statement falseBranch;
	public IfOperator(Expression condition, Statement trueBranch,
			Statement falseBranch) {
		this.condition = condition;
		this.trueBranch = trueBranch;
		this.falseBranch = falseBranch;
	}
	public IfOperator(Expression condition, Statement trueBranch) {
		this.condition = condition;
		this.trueBranch = trueBranch;
	}
	public Expression getCondition() {
		return condition;
	}
	public Statement getTrueBranch() {
		return trueBranch;
	}
	public Statement getFalseBranch() {
		return falseBranch;
	}
	
	
	
	
	
}
