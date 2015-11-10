package ast.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ast.Value.ValueType;
import ast.Variable.VariableType;

public class FunctionSignature {
	
	private ValueType returnType;
	private List<VariableType>args;
	private String name;
	private String signature;
	public FunctionSignature(ValueType returnType, List<VariableType> args,
			String name) {
		this.returnType = returnType;
		this.args = args;
		this.name = name;
	}
	public FunctionSignature(ValueType returnType, String name,
			VariableType... args) {
		this.returnType = returnType;
		this.name = name;
		this.args = new ArrayList<VariableType>(Arrays.asList(args));
	}
	public FunctionSignature(String signature) {
		this.signature = signature;
	}
	
	@Override
	public boolean equals(Object obj) {
		return toString().equals(obj.toString());
	}
	
	@Override
	public String toString() {
		if(signature != null) return signature; 
		StringBuffer sb = new StringBuffer(name + "(");
		for(VariableType t : args){
			sb.append(t.name()).append(',');
		}
		String res = sb.toString();
		res = res.substring(0, res.length() - (args.size()==0?0:1));
		return res + ")";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ValueType getReturnType() {
		return returnType;
	}

	public List<VariableType> getArgs() {
		return args;
	}

	
}
