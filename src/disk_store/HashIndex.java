package disk_store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A hash index.  
 * 
 */

public class HashIndex implements DBIndex {

	HashMap<Integer, ArrayList<Integer>> table;

	public HashIndex() {
		table = new HashMap<Integer, ArrayList<Integer>>();
	}
	
	@Override
	public List<Integer> lookup(int key) {
		return table.get(key);
	}
	
	@Override
	public void insert(int key, int blockNum) {
		if ( !table.containsKey(key)) {
			ArrayList<Integer> value = new ArrayList<Integer>();
			value.add(blockNum);
			table.put(key, value);
		} else {
			table.get(key).add(blockNum);
		}
	}

	@Override
	public void delete(int key, int blockNum) {
		table.get(key).remove((Integer) blockNum);
	}
	
	@Override
	public String toString() {
		String result = "";
	    for (Integer key : table.keySet()) {
	    	for (Integer blockNum : table.get(key)) {
		    	result += key + ", " + blockNum;
	    	}
	    }
	    return result;
	}
}
