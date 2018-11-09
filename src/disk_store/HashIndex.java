package disk_store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
	int size;

	public HashIndex() {
		table = new HashMap<Integer, ArrayList<Integer>>(1024);
		size = 0;
	}
	
	@Override
	public List<Integer> lookup(int key) {
		if (!table.containsKey(key)) {
			return new ArrayList<Integer>();
		}
		List<Integer> al = (ArrayList)table.get(key).clone();//got this idea from https://stackoverflow.com/questions/203984/how-do-i-remove-repeated-elements-from-arraylist
											// otherwise I would loop through and add each item to result if not contained in result
		Set<Integer> hs = new HashSet<>();
		hs.addAll(al);
		al.clear();
		al.addAll(hs);
		return al;
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
		size++;
	}

	@Override
	public void delete(int key, int blockNum) {
		if (table.get(key).remove((Integer) blockNum)) {
			size--;
		}
	}
	
	@Override
	public String toString() {
		String result = "";
	    for (Integer key : table.keySet()) {
	    	for (Integer blockNum : table.get(key)) {
		    	result += key + ", " + blockNum + "\n";
	    	}
	    }
	    return result;
	}
	
	public int size() {
		return size;
	}
}
