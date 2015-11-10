package parse.main;

import java.util.HashMap;
import java.util.List;

import codegen.JASMCodeGen;
import math.Derivative;
import math.Executor;
import math.Optimization;
import ast.Expression;
import parse.Lexer;
import parse.Parser;
import parse.Token;

public class Main 
{
	public static void main(String[] args) 
	{
		String program = "2";
		
		
		
		System.out.println(program);
		System.out.println();
		System.out.println("----------------------------");
		System.out.println();
		
		List<Token> tokens = new Lexer(program).parse();
		
		for(Token token : tokens){
			System.out.println(token);
		}
		
		System.out.println();
		System.out.println("--------------------------------------------------------------------");
		System.out.println();
		
		List<Expression> expressions = new Parser(tokens).parse();
		
		Executor executor = new Executor();
		Optimization optimization = new Optimization();
		Derivative derivative = new Derivative();
		
		
		HashMap<String, Double>vars = new HashMap<String, Double>();

		vars.put("pi", Math.PI);
		vars.put("e", Math.E);
		vars.put("x", 1D);
		
		
		
		for(Expression expression : expressions){
			System.out.println("Expr");
			System.out.println(expression.print() +  " = " + executor.execute(expression,vars));
			Expression dExpression = derivative.get(expression, "x");
			System.out.println("dExpr");
			System.out.println(dExpression.print() + " = " +executor.execute(dExpression,vars));
			System.out.println(optimization.optimize(dExpression.copy()).print() + " = " + executor.execute(optimization.optimize(dExpression.copy()),vars));
		}

		System.out.println();
		System.out.println("--------------------------------------------------------------------");
		System.out.println();

		for(Expression expression : expressions){
			JASMCodeGen codeGen = new JASMCodeGen(expression);
			System.out.println(codeGen.printCode());
		}
	}
	
	
	
}
