/**
 *  Developed by Neel Patel
 */

package Inverted_Index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class Ranking {
	public HashMap<String, HashMap<Integer,Integer>> frequency;
	
	public Ranking() {
		frequency = new HashMap<String, HashMap<Integer,Integer>>();
	}	
	public void sortAll() {
		System.out.println(frequency.values());
		for (String key : frequency.keySet()) {
			List<Entry<Integer,Integer>>entries = this.sortWord(key);
		}
	}
	public List<Entry<Integer,Integer>> sortWord(String word){
		List<Entry<Integer,Integer>> entries = this.sortHashMap(this.frequency.get(word));
		HashMap<Integer,Integer> sortedHashMap = new HashMap<Integer,Integer>();
		for (Entry<Integer, Integer> entry: entries) {
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		this.frequency.replace(word, sortedHashMap);
		return entries;
	}
	
	public List<Entry<Integer,Integer>> sortMultipleWords(String[] words){
		HashMap<Integer,Integer> ranking = new HashMap<Integer,Integer>();
		for (String word : words) {
			List<Entry<Integer,Integer>>list = this.sortWord(word.toLowerCase());
			for (Entry<Integer, Integer> entry : list) {
				Integer key = entry.getKey();
				Integer value = entry.getValue();
				if (!ranking.containsKey(key)) {
					ranking.put(key, value);
				} else {
					ranking.replace(key, value + ranking.get(key));
				}
			}
		}
		return this.sortHashMap(ranking);
	}
	
	private List<Entry<Integer,Integer>> sortHashMap(HashMap<Integer,Integer> targetHashMap) {
		List<Entry<Integer, Integer>> list = new ArrayList<>(targetHashMap.entrySet());
		Collections.sort(list, new Comparator<Entry<Integer, Integer>>(){
			public int compare(Entry<Integer, Integer> pair1, Entry<Integer, Integer> pair2) {
				return pair2.getValue().compareTo(pair1.getValue());
			}
		});
		return list;
	}
	
}
