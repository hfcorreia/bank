package pt.ulisboa.tecnico.bank.security;

import java.util.ArrayList;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SecurityMatrixUtil {
	
	private int seed;
	
	public SecurityMatrixUtil(int seed) {
		this.seed = seed;
	}
	
	public TreeMap<String, ArrayList<Integer>> generateMatrix(){
		char[] alphabet = "ABCDEFG".toCharArray();
		
		TreeMap<String, ArrayList<Integer>> matrix = new TreeMap<String, ArrayList<Integer>>();
		for(char c:  alphabet) {
			ArrayList<Integer> array = new ArrayList<Integer>(9);
			for(int i = 0; i < 9 ; i++ ){
				int random = (int) (Math.random() * ( seed + 1 )) % 1000;
				random = ( random < 100) ? random + 100 : random;
				array.add(i, random);
			}
			String value = c + "";
			matrix.put(value, array);
		}
		return matrix;
	}
	
	public String generateJSON(TreeMap<String, ArrayList<Integer>> matrix){
		Gson gson = new Gson();
		String output = gson.toJson(matrix);
		return output;	
	}
	
	public TreeMap<String, ArrayList<Integer>> generateFromJSON(String jsonArray){
		Gson gson = new Gson();
		return gson.fromJson(jsonArray, new TypeToken<TreeMap<String, ArrayList<Integer>>>(){}.getType());
	}

	public String generateJSON() {
		return generateJSON(generateMatrix());
	}

	public String print(TreeMap<String, ArrayList<Integer>> userMatrix) {
		String matrix = "  ";
		for(int i = 0; i < 9 ; i++){
			matrix += " " + i + "  ";
		}
		matrix += "\n";
		for(String s : userMatrix.keySet()) {
			matrix += s + " ";
			for(int i = 0; i < 9 ; i++)
				matrix += userMatrix.get(s).get(i) + " ";
			matrix += "\n";
		}
		return matrix;
	}
}
