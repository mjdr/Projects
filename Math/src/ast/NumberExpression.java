package ast;

public class NumberExpression implements Expression {
	
	private double value;

	public NumberExpression(double value) {
		this.value = value;
	}

	@Override
	public String print() {
		return value+"";
	}
	
	public double getValue(){
		return value;
	}
	
	@Override
	public Expression copy() {
		return new NumberExpression(getValue());
	}
}
