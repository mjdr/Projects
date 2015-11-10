package ast.string;

import ast.Expression;

public class StringExpression implements Expression{
	private String value;

	public StringExpression(String value) {
		this.value = value.replace("\\n", '\n'+"").replace("\\\\", '\\'+"").replace("\\t", '\t'+"");
	}

	public String getValue() {
		return value;
	}
	
	
	
}
