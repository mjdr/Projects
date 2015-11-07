package ast;

public class Brakets implements Expression {
	
	private Expression expression;
	
	public Brakets(Expression expression) {
		this.expression = expression;
	}

	@Override
	public String print() {
		return String.format("{%s}", expression.print());
	}
	
	public Expression getExpretion(){
		return expression;
	}

	@Override
	public Expression copy() {
		return new Brakets(expression.copy());
	}

}
