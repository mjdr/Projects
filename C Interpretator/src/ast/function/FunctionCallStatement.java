package ast.function;

import java.util.List;

import ast.Expression;
import ast.Statement;

public class FunctionCallStatement implements Statement {
	private String name;
	private List<Expression> args;
	
	public FunctionCallStatement(String name, List<Expression> args) {
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
