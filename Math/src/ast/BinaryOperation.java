package ast;

public class BinaryOperation implements Expression{
	
	private char operator;
	private Expression expression1;
	private Expression expression2;
	
	
	
	public BinaryOperation(char operator, 
			Expression expression1,
			Expression expression2) {
		this.operator = operator;
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	@Override
	public String print() {
		return String.format("(%s %c %s)", expression1.print() , operator , expression2.print());
	}

	public Expression getExpretion1() {
		return expression1;
	}

	public char getOperator() {
		return operator;
	}

	public Expression getExpretion2() {
		return expression2;
	}
	@Override
	public Expression copy() {
		return new BinaryOperation(operator, expression1.copy(), expression2.copy());
	}

	public Expression getExpression1() {
		return expression1;
	}

	public void setExpression1(Expression expression1) {
		this.expression1 = expression1;
	}

	public Expression getExpression2() {
		return expression2;
	}

	public void setExpression2(Expression expression2) {
		this.expression2 = expression2;
	}
	
	
}
