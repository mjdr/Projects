package ast.function;

import ast.Value;

public interface Function {
	Value execute(Value... params);
}
