package ast.statement;

import ast.Statement;
import ast.Variable.VariableType;

public class VariableDefStatement implements Statement {
	
	private VariableType type;
	private String name;
	public VariableDefStatement(VariableType type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	public VariableType getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	
	
	

}
