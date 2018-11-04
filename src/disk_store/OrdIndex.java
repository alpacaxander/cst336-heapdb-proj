package disk_store;

import java.util.ArrayList;
import java.util.List;

/**
 * An ordered index.  Duplicate search key values are allowed,
 * but not duplicate index table entries.  In DB terminology, a
 * search key is not a superkey.
 * 
 * A limitation of this class is that only single integer search
 * keys are supported.
 *
 */

public class OrdIndex implements DBIndex {
	
	private ArrayList<Integer> keys;
	private ArrayList<Integer> blockNums;
	public OrdIndex() {
		keys = new ArrayList<Integer>();
		blockNums = new ArrayList<Integer>();
	}
	
	@Override
	public List<Integer> lookup(int key) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int pointer = binarySearch(key); pointer < size(); pointer++) {
			int blockNum = blockNums.get(pointer);
			if ((int)keys.get(pointer) == key && !result.contains(blockNum)) {
				result.add(blockNum);
			}
		}
		return result;
	}
	private Integer binarySearch(int key){
		int low = 0;
		int high = size() - 1;
		int pointer;
		int mid = 0;
		while (low <= high){
			mid = (low + high)/2;
			pointer = (int)keys.get(mid);
			if (pointer >= key){
				high = mid - 1;
			}
			else if (pointer < key){
				low = mid + 1;
			}
		}
		return low;
	}
	
	@Override
	public void insert(int key, int blockNum) {
		int index = binarySearch(key);
		keys.add(index, key);
		blockNums.add(index, blockNum);
	}

	@Override
	public void delete(int key, int blockNum) {
		for (int index = binarySearch(key); index < size() && keys.get(index) == key; index++){
			if (blockNums.get(index) == blockNum){
				keys.remove(index);
				blockNums.remove(index);
				return;
			}
		}
	}
	
	/**
	 * Return the number of entries in the index
	 * @return
	 */
	public int size() {
		return keys.size();
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < size(); i++) {
			result += keys.get(i) + ", " + blockNums.get(i) + "\n";
		}
		return result;
	}
}
