package math;

import ast.BinaryOperation;
import ast.Brakets;
import ast.Expression;
import ast.NumberExpression;
import ast.UnaryOperation;

public class Optimization {
	
	
	private Executor executor = new Executor();
	private boolean change;
	
	public Expression optimize(Expression e) { 
		
		e = new UnaryOperation('+', e);
		do{
			change = false;
			trace(e);
		}while(change);
		return ((UnaryOperation)e).getExpretion();}
	private void trace(Expression e){
		if(e instanceof UnaryOperation){
			UnaryOperation u = (UnaryOperation)e;
			u.setExpretion(opt(u.getExpretion()));
			trace(u.getExpretion());
			return;
		}
		if(e instanceof BinaryOperation){
			BinaryOperation b = (BinaryOperation) e;

			b.setExpression1(opt(b.getExpression1()));
			b.setExpression2(opt(b.getExpression2()));

			trace(b.getExpression1());
			trace(b.getExpression2());
		}
		if(e instanceof Brakets)
			trace(((Brakets)e).getExpretion());
	}
	
	private Expression opt(Expression e){
		if(e instanceof BinaryOperation)
			return optBinaryOperation((BinaryOperation)e);
		else if(e instanceof UnaryOperation){
			if(((UnaryOperation) e).getExpretion() instanceof NumberExpression && 
					((NumberExpression)((UnaryOperation) e).getExpretion()).getValue() == 0)
				return new NumberExpression(0);
			else return e;
		}
		else return e;
	}
	
	private Expression optBinaryOperation(BinaryOperation b){
		
		if(b.getExpression1() instanceof NumberExpression && b.getExpression2() instanceof NumberExpression){
			change = true;
			return new NumberExpression(executor.execute(b));
		}
		
		
		if(b.getOperator() == '*'){
			if(
					(b.getExpression1() instanceof NumberExpression && ((NumberExpression)b.getExpression1()).getValue() == 0) ||
					(b.getExpression2() instanceof NumberExpression && ((NumberExpression)b.getExpression2()).getValue() == 0)){
				change = true;
				return new NumberExpression(0);
			}
			else if(b.getExpression1() instanceof NumberExpression && ((NumberExpression)b.getExpression1()).getValue() == 1){
				change = true;
				return b.getExpression2();
			}
			else if(b.getExpression2() instanceof NumberExpression && ((NumberExpression)b.getExpression2()).getValue() == 1){
				change = true;
				return b.getExpression1();
			}
			else return b;
		}
		else if(b.getOperator() == '+'){
			if(b.getExpression1() instanceof NumberExpression && ((NumberExpression)b.getExpression1()).getValue() == 0){
				change = true;
				return b.getExpression2();
			}
			else if(b.getExpression2() instanceof NumberExpression && ((NumberExpression)b.getExpression2()).getValue() == 0){
				change = true;
				return b.getExpression1();
			}
			else return b;
		}
		else if(b.getOperator() == '-'){
			if(b.getExpression1() instanceof NumberExpression && ((NumberExpression)b.getExpression1()).getValue() == 0){
				change = true;
				return b.getExpression2();
			}
			else if(b.getExpression2() instanceof NumberExpression && ((NumberExpression)b.getExpression2()).getValue() == 0){
				change = true;
				return b.getExpression1();
			}
			else return b;
		}
		else if(b.getOperator() == '^'){
			if(b.getExpression2() instanceof NumberExpression && ((NumberExpression)b.getExpression2()).getValue() == 0)
				return new NumberExpression(1);
			if(b.getExpression2() instanceof NumberExpression && ((NumberExpression)b.getExpression2()).getValue() == 1)
				return b.getExpression1();
			else return b;	
		}
		else return b;
	}
	
	
}
