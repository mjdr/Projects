package osl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import osl.lexser.Lexer;
import osl.lexser.Token;
import osl.parser.Parser;
import osl.parser.TokenList;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		
		String source = "";
		Lexer lexer = new Lexer();
		BufferedReader reader = new BufferedReader(
				new FileReader("source/sb.src"));
		
		String line;
		while ((line = reader.readLine()) != null)
			source += line+"\n";
		reader.close();
		
		Vector<Token> tokens = lexer.parse(source);

		System.out.println(source);
		TokenList list = new TokenList(tokens);
		Parser parser = new Parser(list);
		System.out.println("------------------------------------");
		
		
	}
}
