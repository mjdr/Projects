package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pakeges.Package;
import ast.*;
import ast.Value.ValueType;
import ast.function.Function;
import ast.function.FunctionCallExpression;
import ast.function.FunctionCallStatement;
import ast.function.FunctionDefStatement;
import ast.function.FunctionSignature;
import ast.math.*;
import ast.statement.*;
import ast.string.StringExpression;

public class Executor {

	private Map<Variable, Value> vars;
	private Map<FunctionSignature, Function> functions;
	private List<FunctionSignature> userFunctions;
	
	
	
	public Executor(Map<Variable, Value> vars,Map<FunctionSignature, Function> functions) {
		this.vars = vars;
		this.functions = functions;
		userFunctions = new ArrayList<FunctionSignature>();
	}

	public void execute(Statement statement){
		if(statement instanceof VariableDefStatement) executeVariableDefStatement((VariableDefStatement)statement);
		else if(statement instanceof VariableAssignStatement) executeVariableAssignStatement((VariableAssignStatement)statement);
		else if(statement instanceof VariableDefAndAssignStatement) executeVariableDefAndAssignStatement((VariableDefAndAssignStatement)statement);
		else if(statement instanceof EchoOperator) executeEchoOperator((EchoOperator)statement);
		else if(statement instanceof Block) executeBlock((Block)statement);
		else if(statement instanceof IfOperator) executeIfOperator((IfOperator)statement);
		else if(statement instanceof WhileStatement) executeWhileStatement((WhileStatement)statement);
		else if(statement instanceof UseStatement) executeUseStatement((UseStatement)statement);
		else if(statement instanceof FunctionCallStatement) executeFunctionCallStatement((FunctionCallStatement)statement);
		else if(statement instanceof FunctionDefStatement) executeFunctionDefStatement((FunctionDefStatement)statement);
		
		
		else throw new RuntimeException("Unknow statement type " + statement.getClass().getName());
		
	}
	private void executeFunctionDefStatement(FunctionDefStatement statement) {
		if(userFunctions.contains(statement.getSignature()) || functions.containsKey(statement.getSignature()))
			throw new RuntimeException("Function " + statement.getSignature() + "already define!");
		userFunctions.add(statement.getSignature());
		
	}

	private void executeFunctionCallStatement(FunctionCallStatement statement) {
		List<Value>values = new ArrayList<Value>();
		StringBuilder sb = new StringBuilder(statement.getName()+"(");
		for(int i = 0;i < statement.getArgs().size();i++){
			values.add(i, execute(statement.getArgs().get(i)));
			sb.append(values.get(i).getType()).append(",");
		}
		String signature = sb.toString();
		signature = signature.substring(0, signature.length()-(statement.getArgs().size() == 0?0:1)) + ")";
		
		if(!functions.containsKey(new FunctionSignature(signature)))
		{
			
			throw new RuntimeException("Function " + signature + " not found!");
		}
		functions.get(new FunctionSignature(signature)).execute(values.toArray(new Value[0]));
	}

	@SuppressWarnings("unused")
	private void executeUserFunctionCallStatement(FunctionCallStatement statement,FunctionSignature signature) {
		
		
		
	}

	private void executeUseStatement(UseStatement statement) {
		String className = "pakeges." + statement.getPackageName();
		Map<Variable, Value>vars = new HashMap<Variable, Value>();
		Map<FunctionSignature, Function> functions = new MyMap<FunctionSignature, Function>();
		try {
			((Package)getClass().getClassLoader().loadClass(className).newInstance()).init(vars, functions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(Variable v : vars.keySet()){
			Value val = vars.get(v);
			v.setName(statement.getUsePackageName()+"_"+v.getName());
			if(this.vars.containsKey(v))
				throw new RuntimeException("Import pakage "+statement.getPackageName()+" variable " + v.getName() + " is already use ");
			this.vars.put(v, val);
		}
		for(FunctionSignature key : functions.keySet()){
			Function func = functions.get(key);
			key.setName(statement.getUsePackageName()+"_"+key.getName());
			if(this.functions.containsKey(key))
				throw new RuntimeException("Import pakage "+statement.getPackageName()+" function " + key + " is already use ");
			this.functions.put(key, func);
		}
		
	}

	private void executeWhileStatement(WhileStatement statement) {
		
		while(true){
			
			Value val = execute(statement.getCondition());
			if(val.getType() != ValueType.Boolean)
				throw new RuntimeException("Expected type Boolean has " + val.getType());
			if(val.asNumber() == 0) break;
			execute(statement.getBody());
		}
		
	}

	private void executeIfOperator(IfOperator statement) {
		Value val = execute(statement.getCondition());
		if(val.getType() != ValueType.Boolean)
			throw new RuntimeException("Expected type Boolean had " + val.getType());
		if(val.asNumber() != 0){
			execute(statement.getTrueBranch());
			return;
		}
		if(statement.getFalseBranch() != null)
			execute(statement.getFalseBranch());
	}

	private void executeBlock(Block statement) {
		for(Statement s : statement.getStatements())
			execute(s);
	}

	private void executeEchoOperator(EchoOperator statement) {
		Value val = execute(statement.getExpression());
		if(val.getType() == ValueType.Null)
			System.out.print("null");
		else if(val.getType() == ValueType.Number || val.getType() == ValueType.String)
			System.out.print(val.asString());
		else if(val.getType() == ValueType.Boolean)
			System.out.print(val.asBoolean());
	}

	private void executeVariableDefAndAssignStatement(
			VariableDefAndAssignStatement statement) {
		execute(new VariableDefStatement(statement.getType(), statement.getVarName()));
		execute(new VariableAssignStatement(statement.getVarName(), statement.getExpression()));
	}

	private void executeVariableAssignStatement(
			VariableAssignStatement statement) {
			Variable key = null;
			for(Variable vs : vars.keySet())
			{
				if(vs.getName().equals(statement.getVarName())){
					key = vs;
					break;
				}
			}
			if(key != null){
			Value val = execute(statement.getExpression());
			if(!val.getType().name().equals(key.getType().name()) && val.getType() != ValueType.Null){
				throw new RuntimeException("Expected expression type " + key.getType() + " has " + val.getType());
			}
			
			vars.computeIfPresent(key, (Variable k, Value v) -> val);
		}
		else throw new RuntimeException("Variable not exist");
		
	}

	private void executeVariableDefStatement(VariableDefStatement statement) {
		if(vars.containsKey(new Variable(statement.getName())))
			throw new RuntimeException("Variable "+statement.getName()+" alredy exist!");
		vars.put(new Variable(statement.getType(),statement.getName()), new Value());
	}

	public Value execute(Expression expression){

		if(expression instanceof NumberExpression) return executeNumberExpression((NumberExpression)expression);
		else if(expression instanceof UnaryOperation) return executeUnaryOperation((UnaryOperation)expression);
		else if(expression instanceof BinaryOperation) return executeBinaryOperation((BinaryOperation)expression);
		else if(expression instanceof VariableExpression) return executeVariableExpression((VariableExpression)expression);
		else if(expression instanceof StringExpression) return executeStringExpression((StringExpression)expression);
		else if(expression instanceof FunctionCallExpression) return executeFunctionCallExpression((FunctionCallExpression)expression);
		else if(expression instanceof BooleanExpression) return executeBooleanExpression((BooleanExpression)expression);
		else if(expression instanceof NullExpression) return executeNullExpression((NullExpression)expression);
		
		
		else throw new RuntimeException("Unknow expression type " + expression.getClass().getName());
	}

	private Value executeNullExpression(NullExpression expression) {
		return new Value();
	}

	private Value executeBooleanExpression(BooleanExpression expression) {
		return new Value(expression.getValue());
	}

	private Value executeFunctionCallExpression(
			FunctionCallExpression expression) {
		List<Value>values = new ArrayList<Value>();
		StringBuilder sb = new StringBuilder(expression.getName()+"(");
		for(int i = 0;i < expression.getArgs().size();i++){
			values.add(i, execute(expression.getArgs().get(i)));
			sb.append(values.get(i).getType()).append(',');
		}
		String signature = sb.toString();
		signature = signature.substring(0, signature.length()-(expression.getArgs().size() == 0?0:1)) + ")";
		if(!functions.containsKey(signature))
			throw new RuntimeException("Function " + signature + " not found!");
		return functions.get(signature).execute(values.toArray(new Value[0]));
	}

	private Value executeStringExpression(StringExpression expression) {
		return new Value(expression.getValue());
	}

	private Value executeVariableExpression(VariableExpression expression) {
		Variable key = null;
		for(Variable vs : vars.keySet())
		{
			if(vs.getName().equals(expression.getName())){
				key = vs;
				break;
			}
		}
		if(key != null) return vars.get(key);
		throw new RuntimeException("Variable " + expression.getName() + " not found!");
	}

	private Value executeBinaryOperation(BinaryOperation op) {

		Value op1 = execute(op.getExpression1());
		Value op2 = execute(op.getExpression2());
		if(op1.getType() == ValueType.Number && op2.getType() == ValueType.Number){
			switch (op.getOperation()) {
				case "*" : return new Value(op1.asNumber() * op2.asNumber());
				case "/" : return new Value(op1.asNumber() / op2.asNumber());
				case "+" : return new Value(op1.asNumber() + op2.asNumber());
				case "%" : return new Value(op1.asNumber() % op2.asNumber());
				case "-" : return new Value(op1.asNumber() - op2.asNumber());
				case "<" : return new Value(op1.asNumber() < op2.asNumber());
				case ">" : return new Value(op1.asNumber() > op2.asNumber());
			}
			throw new RuntimeException("Unknow operator " + op.getOperation() + " for Number Number");
		}else if(op1.getType() == ValueType.String && op2.getType() == ValueType.Number){
			switch (op.getOperation()) {
			case "*" : 
				StringBuilder sb = new StringBuilder();
				for(int i = 0;i < op2.asNumber();i++)
					sb.append(op1.asString());
				return new Value(sb.toString());

				case "+" : return new Value(op1.asString() + op2.asString());
			}
			throw new RuntimeException("Unknow operator " + op.getOperation() + " for String Number");
		}
		else if(op1.getType() == ValueType.Number && op2.getType() == ValueType.String){
			switch (op.getOperation()) {
				case "+" : return new Value(op1.asString() + op2.asString());
			}
			throw new RuntimeException("Unknow operator " + op.getOperation() + " for String String");
		}
		else if(op1.getType() == ValueType.String && op2.getType() == ValueType.String){
			switch (op.getOperation()) {
				case "+" : return new Value(op1.asString() + op2.asString());
			}
			throw new RuntimeException("Unknow operator " + op.getOperation() + " for String String");
		}
		else if(op1.getType() == ValueType.Boolean && op2.getType() == ValueType.Boolean){
			switch (op.getOperation()) {
			case "&&" : return new Value(op1.asBoolean() && op2.asBoolean());
			case "||" : return new Value(op1.asBoolean() || op2.asBoolean());
			}
			throw new RuntimeException("Unknow operator " + op.getOperation() + " for Boolean Boolean");
		}
		throw new RuntimeException("Unknow operator " + op.getOperation());
	}

	private Value executeUnaryOperation(UnaryOperation op) {
		Value val = execute(op.getExpression());
		if(val.getType() == ValueType.Number && (op.getOperation() == '+' || op.getOperation() == '-'))
			return new Value(val.asNumber() * (op.getOperation() == '-'?-1D:1D));
		if(val.getType() == ValueType.Boolean && op.getOperation() == '!')
			return new Value(val.asNumber() == 0);
		throw new RuntimeException("Invalid operation "+op.getOperation()+" for type " + val.getType());
		
	}

	private Value executeNumberExpression(NumberExpression expression) {
		return new Value(expression.getValue());
	}
	
	
}
