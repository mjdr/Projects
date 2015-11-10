package ast.statement;

import java.util.List;

import ast.Statement;

public class Block implements Statement{
	
	private List<Statement> statements;
	
	public Block(List<Statement> statements) {
		
		this.statements = statements;
		
	}

	public List<Statement> getStatements() {
		return statements;
	}
}
