
import java.util.*;
public class Main{
    private static final char[] COLORS = {'G', 'R', 'B', 'Y', 'O', 'P'};
    private static ArrayList<ArrayList<Character>> possibleCombinations = new ArrayList<>();
    private static ArrayList<Character>code = new ArrayList<Character>();
    public static void main(String[]args){
    	ArrayList<Character> guess = new ArrayList<Character>();
        generateOutcomes();
        computerGuess();
        code.add('G');
        code.add('G');
        code.add('Y');
        code.add('Y');
        guess.add('G');
        guess.add('G');
        guess.add('G');
        guess.add('Y');
        int results [] = check(guess);
        for(int i = 0; i < results.length; i++){
        	System.out.println(results[i]);
        }
    }

    public static void generateOutcomes(){

        
        for (int a = 0; a < COLORS.length; a++) {
            for (int b = 0; b < COLORS.length; b++) {
                for (int c = 0; c < COLORS.length; c++) {
                    for (int d = 0; d < COLORS.length; d++) {
                        ArrayList<Character> combination = new ArrayList<>();
                        combination.add(COLORS[a]);
                        combination.add(COLORS[b]);
                        combination.add(COLORS[c]);
                        combination.add(COLORS[d]);
                        possibleCombinations.add(combination);
                    }
                }
            }
        }
    }

	public static void userGuess() throws IOException{
        b = 0;
        w = 0;
        HashMap<Character, Integer> cmap = new HashMap<>();
        cmap.put('G', 0);
        cmap.put('R', 1);
        cmap.put('B', 2);
        cmap.put('Y', 3);
        cmap.put('O', 4);
        cmap.put('P', 5);

        int[] gFreq = new int[6];
        ArrayList<Character> guessList = new ArrayList<>();
        int[] limit = new int[6];
        for (int i = 0; i < 6; i++) {
            limit[i] = freqArr[i];
        }

        System.out.println("Enter guess");
        String guess = readLine();
        if(guess.equals(code)) over = true;
        else {
            int i = 0;
            for (char c : guess.toCharArray()) {
                guessList.add(c);
                int temp = cmap.get(c);
                gFreq[temp]++;
                i++;
            }

            for (int j = 0; j < 4; j++) {
                if (guessList.get(j)== codeList.get(j)) {
                    char c = guessList.get(j);
                    if(limit[cmap.get(c)]>0 && gFreq[cmap.get(c)]>0) {
                        limit[cmap.get(c)]--;
                        gFreq[cmap.get(c)]--;
                        b++;
                    }
                }
            }
	
    public static int[] check(ArrayList<Character>guess){
    	int[]results = new int[2];
		for(int i = 0; i < guess.size(); i++){
			if(code.get(i) == guess.get(i)){
				results[0]++;
			}
			else if(code.contains(guess.get(i))){
				results[1]++;
			}
		}
		return results;
    }

    public static void computerGuess(){
    	char bond[] = new char[2];
    }

    
}
