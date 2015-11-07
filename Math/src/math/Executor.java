package math;

import java.util.HashMap;
import java.util.Map;

import ast.BinaryOperation;
import ast.Brakets;
import ast.Expression;
import ast.FunctionOperator;
import ast.NumberExpression;
import ast.UnaryOperation;
import ast.VariableExpression;

public class Executor {
	
	
	public double execute(Expression e,Map<String, Double>variables){
		return exec(e , variables);
	}
	public double execute(Expression e){
		return exec(e , new HashMap<String, Double>());
	}
	private double exec(Expression e,Map<String, Double>variables){
		
		
		if(e instanceof NumberExpression)
			return ((NumberExpression) e).getValue();
		
		if(e instanceof VariableExpression){
			if(variables.containsKey(((VariableExpression) e).getName()))
				return variables.get(((VariableExpression) e).getName());
			else{
				variables.put(((VariableExpression) e).getName(), 0D);
				return 0;
			}
		}
		
		if(e instanceof UnaryOperation){
			switch (((UnaryOperation) e).getOperator()) {
			case '+':return exec(((UnaryOperation) e).getExpretion(),variables);
			case '-':return -exec(((UnaryOperation) e).getExpretion(),variables);

			default:return Double.POSITIVE_INFINITY;
			}
		}
		if(e instanceof BinaryOperation){

			double expr1 = exec(((BinaryOperation) e).getExpression1(), variables);
			double expr2 = exec(((BinaryOperation) e).getExpression2(), variables);
			switch(((BinaryOperation) e).getOperator()){
				case '+': return expr1 + expr2;
				case '-': return expr1 - expr2;
				case '*': return expr1 * expr2;
				case '/': return expr1 / expr2;
				case '^': return Math.pow(expr1 , expr2);
			}
			return Double.POSITIVE_INFINITY;
			
		}
		
		if(e instanceof Brakets)
			return exec(((Brakets) e).getExpretion(),variables);
		
		if(e instanceof FunctionOperator){
			double expr1 = exec(((FunctionOperator) e).firstArg(),variables);
			switch (((FunctionOperator) e).getName()) {
				case "sin": return Math.sin(expr1);
				case "cos": return Math.cos(expr1);
				case "tg":  return Math.tan(expr1);
				case "ctg": return 1D/Math.tan(expr1);
				case "sqrt": return Math.sqrt(expr1);
				case "ln": return Math.log(expr1);
				case "exp": return Math.exp(expr1);
			}
			return Double.POSITIVE_INFINITY;
		}
		return Double.POSITIVE_INFINITY;
	}
	
}
