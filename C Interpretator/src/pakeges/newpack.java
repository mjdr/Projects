package pakeges;

import java.util.Map;

import ast.Value;
import ast.Variable;
import ast.Variable.VariableType;
import ast.function.Function;
import ast.function.FunctionSignature;

public class newpack implements Package {

	@Override
	public void init(Map<Variable, Value> variables, Map<FunctionSignature, Function> functions) {
		
		variables.put(new Variable(VariableType.Number, "K"), new Value(42));
	}

}
