package desuteam.webServer.utils.quickElements;

import java.util.List;

public class QuickUtils {
	public static String generateJson(List<QuickElement> elements){
		
		StringBuilder sb = new StringBuilder("[");
		
		for(int i = 0;i < elements.size();i++){
			sb.append(elements.get(i).printJson());
			if(i != elements.size() - 1)
				sb.append(',');
		}
		sb.append("]");
		return sb.toString();
	}
}
