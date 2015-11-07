package ast;

public class UnaryOperation implements Expression
{
	private char operator;
	private Expression expretion;
	
	
	public UnaryOperation(char operator, Expression expretion) {
		this.operator = operator;
		this.expretion = expretion;
	}
	
	public char getOperator() {
		return operator;
	}
	@Override
	public String print() {
		return String.format("[%c%s]", operator , expretion.print());
	}
	public Expression getExpretion() {
		return expretion;
	}
	
	@Override
	public Expression copy() {
		return new UnaryOperation(getOperator(), getExpretion().copy());
	}
	public void setExpretion(Expression expretion) {
		this.expretion = expretion;
	}
	
}
