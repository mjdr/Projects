package pakeges;

import java.util.Map;

import javax.swing.JOptionPane;

import ast.Value;
import ast.Value.ValueType;
import ast.Variable.VariableType;
import static ast.Value.ValueType.*;
import ast.Variable;
import ast.function.Function;
import ast.function.FunctionSignature;

public class std implements Package {

	@Override
	public void init(Map<Variable, Value> variables,
			Map<FunctionSignature, Function> functions) {
		
		functions.put(new FunctionSignature(Null, "sleep",VariableType.Number),(Value...args) -> {try{Thread.sleep((long)args[0].asNumber());}catch(InterruptedException e){} return new Value();});
		functions.put(new FunctionSignature(Null,"print",VariableType.Number),(Value...args) -> {System.out.print(args[0].asNumber());return new Value();});
		functions.put(new FunctionSignature(Null,"print",VariableType.String),(Value...args) -> {System.out.print(args[0].asString());return new Value();});
		functions.put(new FunctionSignature(Null,"print",VariableType.Boolean),(Value...args) -> {System.out.print(args[0].asNumber() == 0? "false" : "true");return new Value();});
		functions.put(new FunctionSignature(ValueType.Number,"strlen",VariableType.String),(Value...args) -> {return new Value(args[0].asString().length());});
		functions.put(new FunctionSignature(ValueType.String,"input",VariableType.String),(Value...args) -> {return new Value(JOptionPane.showInputDialog(args[0].asString()));});
		functions.put(new FunctionSignature(ValueType.Number,"str2num",VariableType.String),(Value...args) -> {return new Value(Double.parseDouble(args[0].asString()));});
		functions.put(new FunctionSignature(ValueType.Number,"time"),(Value...args) -> {return new Value(System.currentTimeMillis());});
	}

}
