import java.util.*;

public class Main{
    public static void main(String[]args){
        generateOutcomes();
        computerGuess();
    }
    private static final char[] COLORS = {'G', 'R', 'B', 'Y', 'O', 'P'};
    private static ArrayList<ArrayList<Character>> possibleCombinations = new ArrayList<>();
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

    public static int[] check(ArrayList<Character>guess){
        
    }

    public static void computerGuess(){

    }

    
}