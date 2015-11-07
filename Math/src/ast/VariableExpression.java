package ast;

public class VariableExpression implements Expression{

	
	private String name;
	
	public VariableExpression(String name) {
		this.name = name;
	}

	@Override
	public String print() {
		return name;
	}

	@Override
	public Expression copy() {
		return new VariableExpression(name);
	}

	public String getName() {
		return name;
	}
	
}
