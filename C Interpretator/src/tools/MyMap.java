package tools;

import java.util.HashMap;

public class MyMap<K, V>extends HashMap<K, V>{
	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean containsKey(Object key) {
		
		for(K k: keySet()){
			if(k.equals(key))
				return true;
		}
		return false;
		
	}
	
	@Override
	public V get(Object key) {
		for(K k: keySet()){
			if(k.equals(key))
				return super.get(k);
		}
		return null;
	}
	
}
