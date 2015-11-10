package ast.function;

import java.util.List;

import ast.Expression;

public class FunctionCallExpression implements Expression {
	
	private String name;
	private List<Expression> args;
	
	public FunctionCallExpression(String name, List<Expression> args) {
		this.name = name;
		this.args = args;
	}
	public String getName() {
		return name;
	}
	public List<Expression> getArgs() {
		return args;
	}
	
	
	
}
