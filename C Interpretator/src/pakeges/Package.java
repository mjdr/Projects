package pakeges;

import java.util.Map;

import ast.Value;
import ast.Variable;
import ast.function.Function;
import ast.function.FunctionSignature;

public interface Package {
	void init(Map<Variable,Value>variables , Map<FunctionSignature, Function> functions);
}
