package ast;

import java.util.HashMap;
import java.util.Map;

public class Functions {
	
	private static Map<String, Integer>numbersOfArgs = new HashMap<String, Integer>();
	
	
	static {
		numbersOfArgs.put("sin", 1);
		numbersOfArgs.put("cos", 1);
		numbersOfArgs.put("tg", 1);
		numbersOfArgs.put("ctg", 1);
		numbersOfArgs.put("sqrt", 1);
		numbersOfArgs.put("ln", 1);
		numbersOfArgs.put("exp", 1);
	}
	
	public static int getNumberOfArgs(String name){
		Integer n = numbersOfArgs.get(name);
		if(n == null)
			return -1;
		return n;
	}
	
}
