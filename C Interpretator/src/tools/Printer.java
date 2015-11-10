package tools;

import ast.Expression;
import ast.Statement;
import ast.function.FunctionCallExpression;
import ast.function.FunctionCallStatement;
import ast.math.BinaryOperation;
import ast.math.NullExpression;
import ast.math.NumberExpression;
import ast.math.UnaryOperation;
import ast.math.VariableExpression;
import ast.statement.Block;
import ast.statement.EchoOperator;
import ast.statement.UseStatement;
import ast.statement.VariableAssignStatement;
import ast.statement.VariableDefAndAssignStatement;
import ast.statement.VariableDefStatement;
import ast.statement.WhileStatement;
import ast.string.StringExpression;
import static java.lang.String.format;

public class Printer {
	
	
	public String print(Statement statement){
		if(statement instanceof VariableDefStatement) return printVariableDefStatement((VariableDefStatement)statement);
		if(statement instanceof VariableAssignStatement) return printVariableAssignStatement((VariableAssignStatement)statement);
		if(statement instanceof VariableDefAndAssignStatement) return printVariableDefAndAssignStatement((VariableDefAndAssignStatement)statement);
		if(statement instanceof EchoOperator) return printEchoOperator((EchoOperator)statement);
		if(statement instanceof WhileStatement) return printWhileStatement((WhileStatement)statement);
		if(statement instanceof Block) return printBlock((Block)statement);
		if(statement instanceof UseStatement) return printUseStatement((UseStatement)statement);
		if(statement instanceof FunctionCallStatement) return printFunctionCallStatement((FunctionCallStatement)statement);
		
		throw new RuntimeException("Unknow statement type " + statement.getClass().getName());
	}
	private String printFunctionCallStatement(FunctionCallStatement statement) {
		StringBuffer sb = new StringBuffer(statement.getName()+"(");
		for(Expression e : statement.getArgs())
			sb.append(print(e)).append(",");
		String res = sb.toString();
		res = res.substring(0, res.length() - 1);
		return res + ");";
	}
	private String printUseStatement(UseStatement statement) {
		return "import " + statement.getPackageName() + "\n";
	}
	private String printBlock(Block statement) {
		StringBuffer sb = new StringBuffer();
		for(Statement s : statement.getStatements())
			sb.append(print(s)).append('\n');
		return sb.toString();
	}
	private String printWhileStatement(WhileStatement statement) {
		return format("while(%s){%s}" , statement.getCondition(),print(statement.getBody()));
	}
	private String printEchoOperator(EchoOperator statement) {
		return format("echo %s", print(statement.getExpression()));
	}
	private String printVariableDefAndAssignStatement(
			VariableDefAndAssignStatement statement) {
		return format("%s %s = %s", statement.getType() , statement.getVarName() , print(statement.getExpression()));
	}
	private String printVariableAssignStatement(
			VariableAssignStatement statement) {
		return format("%s = %s", statement.getVarName(),print(statement.getExpression()));
	}
	private String printVariableDefStatement(VariableDefStatement statement) {
		return format("%s %s\n",statement.getType(),statement.getName());
	}
	public String print(Expression expression){

		if(expression instanceof NumberExpression) return printNumberExpression((NumberExpression)expression);
		else if(expression instanceof UnaryOperation) return printUnaryOperation((UnaryOperation)expression);
		else if(expression instanceof BinaryOperation) return printBinaryOperation((BinaryOperation)expression);
		else if(expression instanceof VariableExpression) return printVariableExpression((VariableExpression)expression);
		else if(expression instanceof StringExpression) return printStringExpression((StringExpression)expression);
		else if(expression instanceof FunctionCallExpression) return printFunctionCallExpression((FunctionCallExpression)expression);
		else if(expression instanceof NullExpression) return printNullExpression((NullExpression)expression);
		
		
		else
			throw new RuntimeException("Unknow expression type " + expression.getClass().getName());
	}

	private String printNullExpression(NullExpression expression) {
		return "null";
	}
	private String printFunctionCallExpression(FunctionCallExpression expression) {
		StringBuffer sb = new StringBuffer(expression.getName()+"(");
		for(Expression e : expression.getArgs())
			sb.append(print(e)).append(",");
		String res = sb.toString();
		res = res.substring(0, res.length() - 1);
		return res + ")";
	}
	private String printStringExpression(StringExpression expression) {
		return format("\"%s\"" , expression.getValue());
	}
	private String printVariableExpression(VariableExpression expression) {
		return expression.getName();
	}

	private String printBinaryOperation(BinaryOperation op) {
		return format("(%s %c %s)", print(op.getExpression1()) ,op.getOperation(), print(op.getExpression2()));
	}

	private String printUnaryOperation(UnaryOperation op) {
		return (op.getOperation() == '-'?"-":"") + print(op.getExpression());
	}

	private String printNumberExpression(NumberExpression expression) {
		return ""+expression.getValue();
	}
	
}
