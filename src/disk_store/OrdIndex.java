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
	
	private ArrayList keys;
	private ArrayList blockNums;
	public OrdIndex() {
		keys = new ArrayList<int>();
		blockNums = new ArrayList<int>();
	}
	
	@Override
	public List<Integer> lookup(int key) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int pos : lookupPosOfAll(key)){
			result.add(blockNums.get(pos));
		}
		return result;
	}

	private List<integer> lookupPosOfAll(int key){
		int pos = binarySearch(key);
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = pos; i >= 0; i--){
			if (keys.get(i) == key){
				result.add(i);
			}else{
				break;
			}
		}
		for (int i = pos; i < size(); i++){
			if (keys.get(i) == key){
				result.add(i);
			}else{
				break;
			}
		}
		return result;
	}
	private Integer binarySearch(int key){
		int begin = 0;
		int end = size();
		int val;
		int pos;
		while (begin != end){
			pos = (begin + end)/2;
			val = keys.get(pos);
			if (val < key){
				end = pos;
			}
			else if (val > key){
				begin = pos;
			}
			else {
				break;
			}
		}
		return pos;
	}
	
	@Override
	public void insert(int key, int blockNum) {
		insert(binarySearch(key), key, blockNum);
	}

	@Override
	public void delete(int key, int blockNum) {
		for (int pos : lookupPosOfAll(key)){
			if (blockNums.get(pos) == blockNum){
				remove(pos);
				return;
			}
		}
	}
	
	/**
	 * Return the number of entries in the index
	 * @return
	 */
	public int size() {
		return keys.size()
	}
	private void delete(int index){
		key.remove(index);
		blockNums.remove(index);
	}
	private void put(int index, int key, int blockNum){
		keys.insert(index, key);
		blockNums.insert(index, blockNum);
	}

	@Override
	public String toString() {
		throw new UnsupportedOperationException();
	}
}
