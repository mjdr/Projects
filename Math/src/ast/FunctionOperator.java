package ast;

import java.util.ArrayList;
import java.util.List;

public class FunctionOperator implements Expression {
	
	private List<Expression> args;
	private String name;
	
	public FunctionOperator(String name , List<Expression> args) {
		this.args = args;
		this.name = name;
	}
	public FunctionOperator(String name , Expression arg) {
		this.args = new ArrayList<Expression>();
		args.add(arg);
		this.name = name;
	}
	
	public List<Expression> getArgs() {
		return args;
	}

	public String getName() {
		return name;
	}

	@Override
	public String print() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(name).append('(').append(args.get(0).print());
		for(int i = 1;i < args.size();i++)
			sb.append(",").append(args.get(i).print());
		sb.append(')');
		return sb.toString();
	}
	
	@Override
	public Expression copy() {
		List<Expression>argCpy = new ArrayList<Expression>();
		for(Expression e: args)
			argCpy.add(e.copy());
		return new FunctionOperator(name, argCpy);
	}

	public Expression firstArg(){ return args.get(0);}
}
