package ast.function;

import java.util.ArrayList;
import java.util.List;

import ast.Statement;
import ast.Value;
import ast.Value.ValueType;
import ast.Variable;
import ast.Variable.VariableType;
import ast.statement.VariableDefStatement;

public class FunctionDefStatement implements Statement{
	
	private ValueType returnType;
	private String name;
	private List<VariableDefStatement> args;
	private Statement body;
	private FunctionSignature signature;
	
	public FunctionDefStatement(ValueType returnType, String name,
			List<VariableDefStatement> args, Statement body) {
		this.returnType = returnType;
		this.name = name;
		this.args = args;
		this.body = body;
		
		List<VariableType>argsT = new ArrayList<VariableType>();
		for(int i = 0;i < args.size();i++)
			argsT.add(i, args.get(i).getType());
		signature = new FunctionSignature(returnType, argsT, name);
	}

	public ValueType getReturnType() {
		return returnType;
	}

	public String getName() {
		return name;
	}

	public List<VariableDefStatement> getArgs() {
		return args;
	}

	public Statement getBody() {
		return body;
	}

	public FunctionSignature getSignature() {
		return signature;
	}
}
