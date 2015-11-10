package pakeges;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ast.Value;
import ast.Variable;
import ast.Variable.VariableType;
import ast.function.Function;
import ast.function.FunctionSignature;

public class fio implements Package {
	
	private List<BufferedReader> readers = new ArrayList<BufferedReader>();
	@Override
	public void init(Map<Variable, Value> variables,
			Map<FunctionSignature, Function> functions) {

		functions.put(new FunctionSignature(Value.ValueType.String,"openRead",VariableType.String), (Value... args) -> open(args[0].asString()));
		functions.put(new FunctionSignature(Value.ValueType.Boolean,"close",VariableType.String), (Value... args) -> close(args[0].asString()));
		functions.put(new FunctionSignature(Value.ValueType.String,"readLine",VariableType.String), (Value... args) -> readLine(args[0].asString()));
		
	}
	
	public Value open(String name){
		try {
			BufferedReader r = new BufferedReader(new FileReader(name));
			Value val = new Value(readers.size()+"");
			readers.add(r);
			return val;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Value();
	}
	public Value close(String fID){
		try {
			readers.get(Integer.parseInt(fID)).close();
			return new Value(true);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Value(false);
	}
	public Value readLine(String fID){
		try {
			String s = readers.get(Integer.parseInt(fID)).readLine();
			if(s != null)
				return new Value(s);
			return new Value();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Value();
	}
	

}
