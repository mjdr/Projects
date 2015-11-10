package ast;

public class Variable {
	
	public enum VariableType{
		Number,
		String,
		Boolean
	}
	
	private VariableType type;
	private String name;
	public Variable(VariableType type, String name) {
		this.type = type;
		this.name = name;
	}
	public Variable(String varName) {
		this.name = varName;
	}
	public VariableType getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Variable)
			return name.equals(((Variable)obj).name);
		return false;
	}
	
	@Override
	public String toString() {
		return name + " : " + type.name();
	}
	
}
