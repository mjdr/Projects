package parser;

import java.util.ArrayList;
import java.util.List;

import lexer.Token;
import lexer.Token.Type;
import ast.Expression;
import ast.Statement;
import ast.Value.ValueType;
import ast.Variable;
import ast.function.FunctionCallExpression;
import ast.function.FunctionCallStatement;
import ast.function.FunctionDefStatement;
import ast.math.BinaryOperation;
import ast.math.BooleanExpression;
import ast.math.NullExpression;
import ast.math.NumberExpression;
import ast.math.UnaryOperation;
import ast.math.VariableExpression;
import ast.statement.Block;
import ast.statement.EchoOperator;
import ast.statement.IfOperator;
import ast.statement.UseStatement;
import ast.statement.VariableAssignStatement;
import ast.statement.VariableDefAndAssignStatement;
import ast.statement.VariableDefStatement;
import ast.statement.WhileStatement;
import ast.string.StringExpression;

public class Parser {
	
	private List<Token> tokens;
	private int index = 0;
	
	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	public List<Statement> parse(){
		
		List<Statement> list = new ArrayList<Statement>();
		
		while(index < tokens.size())
			list.add(parseStatement());
		
		return list;
	}
	private Statement parseStatement() {

		if(peek().getType() == Type.DEF)
			return parseFunctionDefStatement();
		if(peek().getType() == Type.ECHO)
			return parseEchoOperator();
		if(peek().getType() == Type.IF)
			return parseIfOperator();
		if(peek().getType() == Type.USE)
			return parseUseStatement();
		if(peek().getType() == Type.WHILE)
			return parseWhileOperator();
		
		if(peek().getType() == Type.LCParent){
			return parseBlock();
		}
		if(peek().getType() == Type.ID && peek(1).getType() == Type.LParent){
			return parseFunctionCallStatement();
		}
		if(peek().getType() == Type.ID && peek(1).getType() == Type.ID && peek(2).getType() == Type.Equal){
			return parseVariableDefAndAssignStatement();
		}
		if(peek().getType() == Type.ID && peek(1).getType() == Type.ID && peek(2).getType() == Type.Semicolon){
			return parseVariableDefStatement();
		}
		if(peek().getType() == Type.ID && peek(1).getType() == Type.Equal){
			return parseVariableAssignStatement();
		}
		
		throw new RuntimeException("Invalid token " + peek());
	}
	private Statement parseFunctionDefStatement() {
		read();
		String type = read().getText();
		String name = read().getText();
		if(Variable.VariableType.valueOf(type) == null)
			throw new RuntimeException("Invalid type " + type);
		readIs(Type.LParent);
		List<VariableDefStatement> exprs = new ArrayList<>();
		while(peek().getType() != Type.RParent)
		{
			String vtype = read().getText();
			String vname = read().getText();
			if(Variable.VariableType.valueOf(vtype) == null)
				throw new RuntimeException("Invalid type " + type);
				
			exprs.add(new VariableDefStatement(Variable.VariableType.valueOf(vtype), vname));
			if(peek().getType() == Type.RParent) break;
			readIs(Type.Comma);
		}
		readIs(Type.RParent);
		Statement body = parseBlock();
		return new FunctionDefStatement(ValueType.valueOf(type), name, exprs, body);
	}

	private Statement parseWhileOperator() {
		read();
		readIs(Type.LParent);
		Expression expr = parseExpression();
		readIs(Type.RParent);
		return new WhileStatement(expr, parseBlock());
	}
	private Statement parseBlock() {
		
		if(peek().getType() != Type.LCParent)
			return parseStatement();
		read();
		List<Statement> list = new ArrayList<>();
		
		while(peek().getType() != Type.RCParent)
			list.add(parseStatement());
		read();
		return new Block(list);
	}
	private Statement parseIfOperator() {
		read();
		Expression expr = parseExpression();
		Statement trueBr = parseBlock();
		Statement elseBr =  null;
		if(peek().getType() == Type.ELSE){
			read();
			elseBr = parseBlock();
		}
		return new IfOperator(expr, trueBr, elseBr);
	}
	private Statement parseUseStatement(){
		read();
		if(peek(1).getType() != Type.AS)
			return new UseStatement(read().getText());
		String pName = read().getText();
		read();
		return new UseStatement(pName, read().getText());
	}
	private Statement parseFunctionCallStatement() {
		String name = read().getText();
		read();
		List<Expression> exprs = new ArrayList<>();
		while(peek().getType() != Type.RParent)
		{
			exprs.add(parseExpression());
			if(peek().getType() == Type.RParent) break;
			readIs(Type.Comma);
		}
		readIs(Type.RParent);
		readIs(Type.Semicolon);
		return new FunctionCallStatement(name, exprs);
	}
	private Statement parseEchoOperator() {
		
		read();
		Expression expr = parseExpression();
		readIs(Type.Semicolon);
		
		return new EchoOperator(expr);
	}
	private Statement parseVariableAssignStatement() {
		
		String name = read().getText();
		read();
		Expression expr = parseExpression();
		readIs(Type.Semicolon);
		return new VariableAssignStatement(name, expr);
	}
	private Statement parseVariableDefStatement() {
		String type = read().getText();
		String name = read().getText();
		if(Variable.VariableType.valueOf(type) == null)
			throw new RuntimeException("Invalid type " + type);

		readIs(Type.Semicolon);
		return new VariableDefStatement(Variable.VariableType.valueOf(type), name);
	}
	private Statement parseVariableDefAndAssignStatement() {
		String type = read().getText();
		String name = read().getText();
		if(Variable.VariableType.valueOf(type) == null)
			throw new RuntimeException("Invalid type " + type);
		read();//=
		Expression expr = parseExpression();
		readIs(Type.Semicolon);
		return new VariableDefAndAssignStatement(Variable.VariableType.valueOf(type), name, expr);
	}
	private Expression parseExpression() {
		return parseBooleanOperation();
	}
	private Expression parseBooleanOperation(){
		Expression result = parseBinaryOperationAddSub();
		if(peek().getType() == Type.LT || peek().getType() == Type.GT){
			
			if(peek().getType() == Type.LT){
				read();
				return new BinaryOperation("<", result, parseBinaryOperationAddSub());
			}
			if(peek().getType() == Type.GT){
				read();
				return new BinaryOperation(">", result, parseBinaryOperationAddSub());
			}
		}
		return result;
	}
	private Expression parseBinaryOperationAddSub(){
		Expression result = parseBinaryOperationMulDiv();
		while(peek().getType() == Type.Minus || peek().getType() == Type.Plus){
			result = new BinaryOperation(read().getType() == Type.Plus ? "+" : "-", result, parseBinaryOperationMulDiv());
		}
		while(peek().getType() == Type.VbarVbar){
			read();
			result = new BinaryOperation("||" , result, parseBinaryOperationDivOst());
		}
		return result;
	}
	private Expression parseBinaryOperationMulDiv(){
		Expression result = parseBinaryOperationDivOst();
		if(peek().getType() == Type.Star || peek().getType() == Type.Slash)
			while(peek().getType() == Type.Star || peek().getType() == Type.Slash)
				result = new BinaryOperation(read().getType() == Type.Star ? "*" : "/", result, parseBinaryOperationDivOst());
		while(peek().getType() == Type.AMPAMP){
			read();
			result = new BinaryOperation("&&" , result, parseBinaryOperationDivOst());
		}
		return result;
	}
	private Expression parseBinaryOperationDivOst(){
		Expression result = parseUnaryOperation();
		if(peek().getType() == Type.Percent){
			read();
			return new BinaryOperation("%", result, parseUnaryOperation());
		}
		return result;
	}
	private Expression parseUnaryOperation(){
		if(peek().getType() == Type.Minus || peek().getType() == Type.Plus){
			return new UnaryOperation(read().getType() == Type.Minus ? '-':'+', parseLiterlsOrParentsOrVariablesOrFunctions());
		}else if(peek().getType() == Type.ExclamationMark){
			read();
			return new UnaryOperation('!', parseLiterlsOrParentsOrVariablesOrFunctions());
		} 
		else
			return parseLiterlsOrParentsOrVariablesOrFunctions();
		
	}
	private Expression parseLiterlsOrParentsOrVariablesOrFunctions(){
		if(peek().getType() == Type.NULL){
			read();
			return new NullExpression();
		}
		if(peek().getType() == Type.Number)
			return new NumberExpression(Double.parseDouble(read().getText()));
		if(peek().getType() == Type.HexNumber)
			return new NumberExpression(Long.parseLong(peek().getText().substring(2, read().getText().length()),16));
		if(peek().getType() == Type.BinaryNumber)
			return new NumberExpression(Long.parseLong(peek().getText().substring(2, read().getText().length()),2));
		if(peek().getType() == Type.String)
			return new StringExpression(peek().getText().substring(1, read().getText().length() - 1));
		if(peek().getType() == Type.TRUE || peek().getType() == Type.FALSE)
			return parseBooleanLiteral();
		if(peek().getType() == Type.ID && peek(1).getType() == Type.LParent){
			return parseFunctionCallExpretion();
		}
		else if(peek().getType() == Type.LParent){
			read();
			Expression expr = parseExpression();
			readIs(Type.RParent);
			return expr;
		}
		else if(peek().getType() == Type.ID){
			return new VariableExpression(read().getText());
		}
		else
			throw new RuntimeException("Invalid token " + peek().getType() + " expected Number");
	}
	private Expression parseBooleanLiteral() {
		if(read().getType() == Type.TRUE)
			return new BooleanExpression(true);
		return new BooleanExpression(false);
	}
	private Expression parseFunctionCallExpretion() {
		String name = read().getText();
		read();
		List<Expression> exprs = new ArrayList<>();
		while(peek().getType() != Type.RParent)
		{
			exprs.add(parseExpression());
			if(peek().getType() == Type.RParent) break;
			readIs(Type.Comma);
		}
		readIs(Type.RParent);
		return new FunctionCallExpression(name, exprs);
	}
	private Token peek(int idx){
		if(index + idx < tokens.size()) return tokens.get(index + idx);
		else return new Token(Type.End);
	}
	private Token peek(){
		return peek(0);
	}
	private Token read(){
		if(index < tokens.size()) return tokens.get(index++);
		return new Token(Type.End);
	}
	private void readIs(Type type){
		if(peek().getType() != type)
			throw new RuntimeException("Expected token " + type + " has " + peek());
		read();
	}
}
