package pakeges;

import static java.lang.Math.E;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.signum;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

import java.util.Map;

import ast.Value;
import ast.Variable;
import ast.Variable.VariableType;
import ast.function.Function;
import ast.function.FunctionSignature;

public class math implements Package {

	@Override
	public void init(Map<Variable, Value> variables,
			Map<FunctionSignature, Function> functions) {
		

		variables.put(new Variable(VariableType.Number,"PI"), new Value(PI));
		variables.put(new Variable(VariableType.Number,"E"), new Value(E));

		functions.put(new FunctionSignature(Value.ValueType.Number,"sin", Variable.VariableType.Number), (Value...args) -> new Value(sin(args[0].asNumber())));
		functions.put(new FunctionSignature(Value.ValueType.Number,"cos", Variable.VariableType.Number), (Value...args) -> new Value(cos(args[0].asNumber())));
		functions.put(new FunctionSignature(Value.ValueType.Number,"tan", Variable.VariableType.Number), (Value...args) -> new Value(tan(args[0].asNumber())));
		functions.put(new FunctionSignature(Value.ValueType.Number,"ctg", Variable.VariableType.Number), (Value...args) -> new Value(1/tan(args[0].asNumber())));
		functions.put(new FunctionSignature(Value.ValueType.Number,"exp", Variable.VariableType.Number), (Value...args) -> new Value(exp(args[0].asNumber())));
		functions.put(new FunctionSignature(Value.ValueType.Number,"ln", Variable.VariableType.Number), (Value...args) -> new Value(log(args[0].asNumber())));
		functions.put(new FunctionSignature(Value.ValueType.Number,"atan2", Variable.VariableType.Number), (Value...args) -> new Value(atan2(args[0].asNumber(),args[1].asNumber())));
		functions.put(new FunctionSignature(Value.ValueType.Number,"sqrt", Variable.VariableType.Number), (Value...args) -> new Value(sqrt(args[0].asNumber())));
		functions.put(new FunctionSignature(Value.ValueType.Number,"abs", Variable.VariableType.Number), (Value...args) -> new Value(abs(args[0].asNumber())));
		functions.put(new FunctionSignature(Value.ValueType.Number,"signum", Variable.VariableType.Number), (Value...args) -> new Value(signum(args[0].asNumber())));
		
	}

}
