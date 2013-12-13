package pt.ulisboa.tecnico.bank.security;

import java.util.ArrayList;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SecurityMatrixUtil {

	public static TreeMap<String, ArrayList<ArrayList<Integer>>> generateMatrix(
			int seed) {
		char[] alphabet = "ABCDEFG".toCharArray();

		TreeMap<String, ArrayList<ArrayList<Integer>>> matrix = new TreeMap<String, ArrayList<ArrayList<Integer>>>();
		for (char c : alphabet) {
			ArrayList<ArrayList<Integer>> array = new ArrayList<ArrayList<Integer>>(
					9);
			for (int i = 0; i < 9; i++) {
				ArrayList<Integer> number = new ArrayList<Integer>(3);
				int random1 = (int) (Math.random() * (seed + 1)) % 10;
				int random2 = (int) (Math.random() * (seed + 1)) % 10;
				int random3 = (int) (Math.random() * (seed + 1)) % 10;
				number.add(0, random1);
				number.add(1, random2);
				number.add(2, random3);
				array.add(i, number);
			}
			String value = c + "";
			matrix.put(value, array);
		}
		return matrix;
	}

	public static String generateJSON(
			TreeMap<String, ArrayList<ArrayList<String>>> matrix) {
		Gson gson = new Gson();
		String output = gson.toJson(matrix);
		return output;
	}

	public static TreeMap<String, ArrayList<ArrayList<String>>> generateFromJSON(
			String jsonArray) {
		Gson gson = new Gson();
		return gson.fromJson(jsonArray,
						new TypeToken<TreeMap<String, ArrayList<ArrayList<String>>>>() {
						}.getType());
	}

	public static String getMatrix(
			TreeMap<String, ArrayList<ArrayList<Integer>>> userMatrix) {
		String matrix = "  ";
		for (int i = 0; i < 9; i++) {
			matrix += " " + i + "  ";
		}
		matrix += "\n";
		for (String s : userMatrix.keySet()) {
			matrix += s + " ";
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 3; j++) {
					matrix += userMatrix.get(s).get(i).get(j);
				}
				matrix += " ";
			}
			matrix += "\n";
		}
		return matrix;
	}
	
	public static String hashMatrix(TreeMap<String, ArrayList<ArrayList<Integer>>> matrix, String salt, Integer iterations){
		TreeMap<String, ArrayList<ArrayList<String>>> matrixHashed = new TreeMap<String, ArrayList<ArrayList<String>>>();
		
		for(String s : matrix.keySet()){
			ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>(9);
			for(int i = 0 ; i < 9 ; i++ ) {
				ArrayList<String> numberHashed = new ArrayList<String>(3);
				for(int j = 0 ; j < 3 ; j++) {
					numberHashed.add(j, HashUtil.hashPassword(matrix.get(s).get(i).get(j) + "", salt, iterations));
				}
				array.add(i, numberHashed);
			}
			matrixHashed.put(s, array);
		}
		
		return generateJSON(matrixHashed);
	}
	
	public static void main(String[] args) {
		System.out.println(hashMatrix(generateMatrix(100), "salty", 1024).length());
	}
}
