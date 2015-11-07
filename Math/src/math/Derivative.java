package math;

import java.util.List;

import ast.BinaryOperation;
import ast.Brakets;
import ast.Expression;
import ast.FunctionOperator;
import ast.NumberExpression;
import ast.UnaryOperation;
import ast.VariableExpression;

public class Derivative {
	public Expression get(Expression e , String varName){
		
		if(e instanceof NumberExpression){ return new NumberExpression(0);}
		if(e instanceof VariableExpression){
			if(((VariableExpression)e).getName().equals(varName)) return new NumberExpression(1);
			 return new NumberExpression(0);
		}
		if(e instanceof UnaryOperation){
			UnaryOperation op = (UnaryOperation)e;
			return new UnaryOperation(op.getOperator(), get(op.getExpretion() , varName));
		}
		if(e instanceof BinaryOperation)
			return getBinaryOperation((BinaryOperation)e, varName);
		if(e instanceof FunctionOperator){
			return getFunction((FunctionOperator)e , varName);
		}
		if(e instanceof Brakets)
			return get(((Brakets)e).getExpretion().copy(),varName);
		else return null;
	}
	
	
	private Expression getFunction(FunctionOperator e ,String varName) {
		return new BinaryOperation('*', get(e.firstArg().copy(),varName), getdFunction(e));
	}
	private Expression getdFunction(FunctionOperator e) {
		
		if(e.getName().equals("sin")){
			return new FunctionOperator("cos", e.getArgs().get(0).copy());
		}
		if(e.getName().equals("cos")){
			return new UnaryOperation('-',new FunctionOperator("sin", e.firstArg().copy()));
		}
		if(e.getName().equals("ln")){
			return new BinaryOperation('/',new NumberExpression(1),e.firstArg().copy());
		}
		if(e.getName().equals("exp")){
			return e.copy();
		}
		if(e.getName().equals("tg")){
			Expression cos = new FunctionOperator("cos", e.firstArg().copy());
			Expression cos2 = new BinaryOperation('*', cos, cos.copy());
			return new BinaryOperation('/', new NumberExpression(1), cos2);
		}
		if(e.getName().equals("ctg")){
			Expression sin = new FunctionOperator("sin", e.firstArg().copy());
			Expression sin2 = new BinaryOperation('*', sin, sin.copy());
			return new UnaryOperation('-',new BinaryOperation('/', new NumberExpression(1), sin2));
		}
		if(e.getName().equals("sqrt")){
			Expression xx = new BinaryOperation('*', new NumberExpression(2), new FunctionOperator("sqrt",e.firstArg().copy()));
			return new BinaryOperation('/', new NumberExpression(1), xx);
		}
		
		return null;
		
	}


	private Expression getBinaryOperation(BinaryOperation op , String varName){
		if(op.getOperator() == '+')
			return new BinaryOperation('+', get(op.getExpretion1() , varName), get(op.getExpretion2(),varName));
		if(op.getOperator() == '-')
			return new BinaryOperation('-', get(op.getExpretion1() , varName), get(op.getExpretion2(),varName));
		if(op.getOperator() == '*'){
			Expression u8 = get(op.getExpretion1(),varName);
			Expression v = op.getExpretion2().copy();
			BinaryOperation mul1 = new BinaryOperation('*', u8, v);
			Expression u = op.getExpretion1().copy();
			Expression v8 = get(op.getExpretion2(),varName);
			BinaryOperation mul2 = new BinaryOperation('*', u, v8);
			return new BinaryOperation('+', mul1, mul2);
			
		}
		if(op.getOperator() == '/'){
			Expression u8 = get(op.getExpretion1(),varName);
			Expression v = op.getExpretion2().copy();
			BinaryOperation mul1 = new BinaryOperation('*', u8, v);
			Expression u = op.getExpretion1().copy();
			Expression v8 = get(op.getExpretion2(),varName);
			BinaryOperation mul2 = new BinaryOperation('*', u, v8);
			Expression x = new BinaryOperation('-', mul1, mul2);
			Expression y = new BinaryOperation('*', op.getExpretion2().copy(), op.getExpretion2().copy());
			return new BinaryOperation('/', x, y);
			
		}
		if(op.getOperator() == '^'){
			if(isConstant(op.getExpression1(), varName) && isConstant(op.getExpression2(), varName))
				return new NumberExpression(0);
			if(isConstant(op.getExpression1(), varName)){
				Expression lnA = new FunctionOperator("ln", op.getExpression1().copy());
				Expression mul1 = new BinaryOperation('*', op.copy(), lnA);
				return new BinaryOperation('*', mul1, get(op.getExpression2().copy(),varName));
			}
			if(isConstant(op.getExpression2(), varName)){
				Expression AmOne = new BinaryOperation('-', op.getExpression2().copy(), new NumberExpression(1));
				Expression newPow = new BinaryOperation('^', op.getExpression1().copy(), AmOne);
				Expression mul1 = new BinaryOperation('*', op.getExpression2().copy(), newPow);
				return new BinaryOperation('*', get(op.getExpression1().copy(),varName), mul1);
			}
			throw new RuntimeException("Expression f(x)^F(x) not supported");
		}
		throw new RuntimeException("Operation "+ op.getOperator() +" not supported");
	}
	
	private boolean isConstant(Expression e , String varName){
		
		if(e instanceof NumberExpression)
			return true;
		if(e instanceof VariableExpression)
			return !((VariableExpression) e).getName().equals(varName);
		if(e instanceof UnaryOperation)
			return isConstant(((UnaryOperation) e).getExpretion(), varName);
		if(e instanceof Brakets)
			return isConstant(((Brakets) e).getExpretion(), varName);
		if(e instanceof BinaryOperation){
			boolean b1 = isConstant(((BinaryOperation) e).getExpression1(), varName),
					b2 = isConstant(((BinaryOperation) e).getExpression2(), varName);
			return b1 && b2;
		}
		if(e instanceof FunctionOperator){
			List<Expression> args = ((FunctionOperator) e).getArgs();
			boolean res = true;
			for(int i = 0;i < args.size() && res;i++){
				boolean b = isConstant(args.get(i), varName);
				res = res && b;
			}
			return res;
		}
		throw new RuntimeException("Type " + e.getClass().getName() + " not supported!");
	}
	
}
