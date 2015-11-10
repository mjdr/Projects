package ast;

public class Value {
	
	public enum ValueType{
		Number,
		String,
		Boolean, // 0 - false
		Null
	}
	private ValueType type;
	
	private Double number;
	private String string;
	
	public Value(double value) {
		number = value;
		string = ""+value;
		type = ValueType.Number;
	}
	
	public Value(String value) {
		string = value;
		type = ValueType.String;
	}
	public Value(boolean value) {
		number = value?1D:0D;
		string = number+"";
		type = ValueType.Boolean;
	}
	public Value() {
		type = ValueType.Null;
	}
	
	public ValueType getType(){ return type; }
	public double asNumber(){
		if(type == ValueType.Null) throw new RuntimeException("Can't convert Null to Number");
		if(number == null) throw new RuntimeException("Can't convert String to Number"); 
		return number; }
	public String asString(){ 
		if(type == ValueType.Null) throw new RuntimeException("Can't convert Null to Number");
		return string; 
	}
	public boolean asBoolean(){ 
		if(type == ValueType.Null) throw new RuntimeException("Can't convert Null to Boolean");
		return number != 0; 
	}
	
	
	@Override
	public String toString() {
		String str = type.name();
		if(type == ValueType.Number) str += " : " + asNumber();
		else if(type == ValueType.String) str += " : " + asString();
		return str;
	}
	
}
