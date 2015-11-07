package codegen;

import ast.BinaryOperation;
import ast.Brakets;
import ast.Expression;
import ast.FunctionOperator;
import ast.NumberExpression;
import ast.UnaryOperation;
import ast.VariableExpression;

public class JASMCodeGen 
{
	private Expression expretion;
	
	
	
	public JASMCodeGen(Expression expretion) {
		this.expretion = expretion;
	}



	public String printCode(){
		return printExpretion(expretion);
	}
	
	private String printExpretion(Expression expretion){
		
		if(expretion instanceof NumberExpression){
			return "PUSH " + ((NumberExpression)expretion).getValue()+"\n";
		}
		else if(expretion instanceof VariableExpression){
			return "PUSH " + ((VariableExpression)expretion).getName()+"\n";
		}
		
		else if(expretion instanceof Brakets){
			return printExpretion(((Brakets)expretion).getExpretion());
		}
		else if(expretion instanceof UnaryOperation){
			String operation = "";
			
			switch(((UnaryOperation)expretion).getOperator()){
			case '+': operation = "ADD";
			case '-': operation = "MINUS";
		}
			return "PUSH 0\n" + printExpretion(((UnaryOperation)expretion).getExpretion()) + operation + "\n";
		}
		else if(expretion instanceof BinaryOperation){
			BinaryOperation binOp = (BinaryOperation)expretion;
			
			String operation = "";
			
			switch(binOp.getOperator()){
				case '+': operation = "ADD";   break;
				case '-': operation = "MINUS"; break;
				case '*': operation = "MUL";   break;
				case '/': operation = "DIV";   break;
				case '^': operation = "POW";   break;
			}
			
			return printExpretion(binOp.getExpretion1()) + printExpretion(binOp.getExpretion2()) + operation +"\n";
		}
		else if(expretion instanceof FunctionOperator){
			FunctionOperator func = (FunctionOperator)expretion;
			
			StringBuilder sb = new StringBuilder();
			for(Expression expression : func.getArgs())
				sb.append(printExpretion(expression));
			
			
			sb.append("CALL :").append(func.getName()).append(' ').append(func.getArgs().size()).append('\n');
			
			return sb.toString();
			
		}
		
		else return null;
		
	}
	
}
