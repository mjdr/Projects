package ast.statement;

import ast.Statement;

public class UseStatement implements Statement {

	private String packageName;
	private String usePackageName;
	
	
	
	public UseStatement(String packageName ,String usePackageName) {
		this.packageName = packageName;
		this.usePackageName = usePackageName;
	}
	public UseStatement(String packageName) {
		this.packageName = packageName;
		usePackageName = packageName;
	}
	public String getPackageName() {
		return packageName;
	}
	public String getUsePackageName() {
		return usePackageName;
	}
	
	
	
	
}
