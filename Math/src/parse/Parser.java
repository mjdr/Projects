package parse;

import java.util.ArrayList;
import java.util.List;

import parse.Token.Type;
import ast.BinaryOperation;
import ast.Brakets;
import ast.Expression;
import ast.FunctionOperator;
import ast.Functions;
import ast.NumberExpression;
import ast.UnaryOperation;
import ast.VariableExpression;
import static java.lang.Double.*;

public class Parser 
{
	private List<Token> tokens;
	private int index = 0;
	
	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	
	public List<Expression> parse(){
		
		List<Expression> expretions = new ArrayList<Expression>();
		
		while(index < tokens.size()){
			expretions.add(parseExpretion());
		}
		
		return expretions;
	}
	
	
	private Expression parseExpretion() 
	{
		
		return parseSumation();
	}
	

	private Expression parseSumation() {
		
		Expression result = parseFactor();
		
		while(true){
			if(peek().getType() == Type.PLUS){
				read();
				result = new BinaryOperation('+', result, parseFactor());
			}
			else if(peek().getType() == Type.MINUS){
				read();
				result = new BinaryOperation('-', result, parseFactor());
			}
			else
				break;
		}
		return result;
	}
	private Expression parseFactor() {
		
		Expression result = parseUnari();
		
		while(true){
			if(peek().getType() == Type.STAR){
				read();
				result = new BinaryOperation('*', result, parseUnari());
			}
			else if(peek().getType() == Type.SLACH){
				read();
				result = new BinaryOperation('/', result, parseUnari());
			}
			else
				break;
		}
		return result;
	}
	private Expression parseUnari() {
		
		if(peek().getType() == Type.MINUS){
			read();
			return new UnaryOperation('-', parseNumber());
		}
		else if(peek().getType() == Type.PLUS){
			read();
			return new UnaryOperation('+', parseNumber());
		}
		else
			return parseNumber();
		
	}	
	private Expression parseNumber() {
		
		if(peek().getType() == Type.LPARENT)
			return parseBlock();
		
		if(peek().getType() == Type.ID && peek(1).getType() == Type.LPARENT)
			return parseFunction();
		if(peek().getType() == Type.ID)
			return parseVariable();
		if(peek().getType() != Type.NUMBER)
			throw new ParseException("Invalid token type "+ peek() +" expected Number");
		return new NumberExpression(parseDouble(read().getText()));
	}
	private Expression parseVariable() {
		if(peek().getType() == Type.ID)
			return new VariableExpression(read().getText());
		throw new ParseException("Invalid token " + peek());
	}


	private Expression parseBlock() {
		if(peek().getType() == Type.LPARENT){
			read();
			Expression expretion = new Brakets(parseExpretion());
			if(read().getType() != Type.RPARENT)
				throw new ParseException("Invalid token " + peek());
			return expretion;
		}
		throw new ParseException("Invalid token " + peek());
	}
	private Expression parseFunction() {
		if(peek().getType() == Type.ID && peek(1).getType() == Type.LPARENT){
			
			List<Expression> args = new ArrayList<Expression>();
			String name = read().getText();
			read();
			do{
				
				args.add(parseExpretion());
				if(peek().getType() == Type.COMMA){
					read();
					continue;
				}
				if(peek().getType() != Type.RPARENT)
					throw new ParseException("Invalid token " + peek());
				read();
				break;
				
			}while(true);
			if(Functions.getNumberOfArgs(name) != args.size())
				throw new ParseException("Function "+ name+" with " + args.size()+" arguments!");
			
			return new FunctionOperator(name, args);
		}
		throw new ParseException("Invalid token " + peek());
		
	}

	public Token peek(int idx)
	{
		if(idx + index < tokens.size())
			return tokens.get(idx + index);
		return new Token(Token.Type.END);
	}
	
	public Token peek(){
		return peek(0);
	}
	
	public Token read(){
		if(index < tokens.size())
			return tokens.get(index++);
		throw new ParseException("Unexpected EOF");
		
	}
	
}
