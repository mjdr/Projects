package lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
	
	private List<Rule>ruls;
	private Pattern pattern;
	private boolean done;
	
	public Lexer() {
		ruls = new ArrayList<Lexer.Rule>();
	}
	
	public void addRule(Rule rule){
		if(done)
			throw new IllegalStateException("Lexer is done");
		ruls.add(rule);
		
	}
	
	public void done(){
		if(done)
			throw new IllegalStateException("Lexer is done");
		String pattern = "";
		for(Rule r : ruls)
			pattern += "(?:"+r.regExp+")|";
		pattern = pattern.substring(0, pattern.length()-1);
		this.pattern = Pattern.compile(pattern);
		done = true;
	}
	
	public List<Token> parse(String source){

		if(!done)
			throw new IllegalStateException("Lexer isn't done call done()");
		Matcher matcher = pattern.matcher(source);
		List<Token> result = new ArrayList<>();
		
		while(matcher.find()){
			String find = matcher.group();
			for(Rule r : ruls)
				if(find.matches(r.regExp)){
					result.add(r.listener.parse(find));
					break;
				}
		}
		
		return result;
		
	}
	public static class Rule{
		
		private String regExp;
		private LexerListener listener;
		
		public Rule(String regExp, LexerListener listener) {
			this.regExp = regExp;
			this.listener = listener;
		}
		
		
		
	}
	
}
