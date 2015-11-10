package lexer;

import lexer.Lexer.Rule;
import lexer.Token.Type;

public class Rules {
	public static void setRules(Lexer lexer){

		lexer.addRule(new Rule("0x[a-fA-F]+",        			 (String str) -> new Token(Type.HexNumber,str)));
		lexer.addRule(new Rule("0b[01]+",        			     (String str) -> new Token(Type.BinaryNumber,str)));
		lexer.addRule(new Rule("\\d*\\.\\d+", 					 (String str) -> new Token(Type.Number,str)));
		lexer.addRule(new Rule("\\d+",        					 (String str) -> new Token(Type.Number,str)));
		lexer.addRule(new Rule("\\+",         					 (String str) -> new Token(Type.Plus)));
		lexer.addRule(new Rule("\\-",         					 (String str) -> new Token(Type.Minus)));
		lexer.addRule(new Rule("\\*",         					 (String str) -> new Token(Type.Star)));
		lexer.addRule(new Rule("/",           					 (String str) -> new Token(Type.Slash)));
		lexer.addRule(new Rule("\\|\\|",           				 (String str) -> new Token(Type.VbarVbar)));
		lexer.addRule(new Rule("\\|",           				 (String str) -> new Token(Type.Vbar)));
		lexer.addRule(new Rule("&&",           					 (String str) -> new Token(Type.AMPAMP)));
		lexer.addRule(new Rule("&",           					 (String str) -> new Token(Type.AMP)));
		lexer.addRule(new Rule("<",         					 (String str) -> new Token(Type.LT)));
		lexer.addRule(new Rule(">",           					 (String str) -> new Token(Type.GT)));
		lexer.addRule(new Rule("%",           					 (String str) -> new Token(Type.Percent)));
		lexer.addRule(new Rule("\\(",         					 (String str) -> new Token(Type.LParent)));
		lexer.addRule(new Rule("\\)",         					 (String str) -> new Token(Type.RParent)));
		lexer.addRule(new Rule("\\{",         					 (String str) -> new Token(Type.LCParent)));
		lexer.addRule(new Rule("\\}",         					 (String str) -> new Token(Type.RCParent)));
		lexer.addRule(new Rule("=",         					 (String str) -> new Token(Type.Equal)));
		lexer.addRule(new Rule(",",         					 (String str) -> new Token(Type.Comma)));
		lexer.addRule(new Rule(";",         					 (String str) -> new Token(Type.Semicolon)));
		lexer.addRule(new Rule("!",         					 (String str) -> new Token(Type.ExclamationMark)));
		lexer.addRule(new Rule("def",         					 (String str) -> new Token(Type.DEF)));
		lexer.addRule(new Rule("null",         					 (String str) -> new Token(Type.NULL)));
		lexer.addRule(new Rule("use",         					 (String str) -> new Token(Type.USE)));
		lexer.addRule(new Rule("as",         					 (String str) -> new Token(Type.AS)));
		lexer.addRule(new Rule("echo",         					 (String str) -> new Token(Type.ECHO)));
		lexer.addRule(new Rule("if",         					 (String str) -> new Token(Type.IF)));
		lexer.addRule(new Rule("else",         					 (String str) -> new Token(Type.ELSE)));
		lexer.addRule(new Rule("while",         				 (String str) -> new Token(Type.WHILE)));
		lexer.addRule(new Rule("true",         					 (String str) -> new Token(Type.TRUE)));
		lexer.addRule(new Rule("false",         				 (String str) -> new Token(Type.FALSE)));
		lexer.addRule(new Rule("[a-zA-Z_][a-zA-Z0-9_]*",         (String str) -> new Token(Type.ID,str)));
		lexer.addRule(new Rule("\"((\\.)|[^\"])*\"",             (String str) -> new Token(Type.String,str)));
		
		
		
		lexer.done();
		
	}
}
