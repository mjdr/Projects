package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import ast.Statement;
import ast.Value;
import ast.Variable;
import ast.function.Function;
import ast.function.FunctionSignature;
import parser.Parser;
import tools.Executor;
import tools.MyMap;
import tools.Printer;
import lexer.Lexer;
import lexer.Rules;
import lexer.Token;

public class Main {
	public static void main(String[] args0) {
		
		boolean debug = !true;
		
		StringBuffer sb = new StringBuffer();
		try{
		BufferedReader br = new BufferedReader(new FileReader("src/sources/program.osl"));
		String line;
		
		while((line = br.readLine()) != null)
			sb.append(line).append('\n');
		br.close();
		}catch(Exception e){System.out.println(e);}
		Lexer lexer = new Lexer();
		
		Rules.setRules(lexer);
		long time;
		
		time = System.currentTimeMillis();
		List<Token>tokens = lexer.parse(sb.toString());
		System.out.println("#Lexer time: " + (System.currentTimeMillis() - time));
		if(debug){
			for(Token t : tokens){
				System.out.println(t);
			}
			System.out.println("-----------------------------");
		}
		//----------------------
		Parser parser = new Parser(tokens);
		
		Map<Variable, Value> vars = new MyMap<Variable, Value>();
		Map<FunctionSignature, Function> functions = new MyMap<FunctionSignature, Function>();

		Executor executor = new Executor(vars , functions);
		Printer printer = new Printer();

		time = System.currentTimeMillis();
		List<Statement> statements = parser.parse();
		System.out.println("#Parse time: " + (System.currentTimeMillis() - time));
		for(Statement s : statements){
			if(debug)
				System.out.println(printer.print(s));
			executor.execute(s);
			if(debug){
				System.out.println("+-----------------------------------------------------------------------------------");
				for(Variable key : vars.keySet()){
					System.out.println("	" + key + " : " + vars.get(key));
				}
			}
		}
		
	}
	
	
	
	
}
