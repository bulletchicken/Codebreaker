import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main{

    private static final char[] COLORS = {'G', 'R', 'B', 'Y', 'O', 'P'};
    private static ArrayList<ArrayList<Character>> possibleCombinations = new ArrayList<>();
    private static ArrayList<Character>guess = new ArrayList<>();
    private static int[] freqArr = new int[6];
    private static ArrayList<Character>code = new ArrayList<Character>();
    public static void main(String[]args){
        
        //when the code is inputed, run initComputer
        initComputer();

        initComputer();
        
        //Guess is GGRR

        int results[] = getHint(guess);
        System.out.println(results[0] + " " + results[1]);
        
        while(results[0]!=2 && results[1]!=2){
        	computerGuess(results);
        	results = getHint(guess);
            System.out.println(results[0] + " " + results[1]);
            
        }
        
    }

    public static void initComputer(){
        possibleCombinations.clear();
        generateOutcomes();

        //starting initial guess
        
        //following are just for the test cast
        guess.add('G');
        guess.add('G');
        guess.add('Y');
        guess.add('R');
        
        code.add('G');
        code.add('G');
        code.add('G');
        code.add('R');
        
        colourMap.put('G', 0);
        colourMap.put('R', 1);
        colourMap.put('B', 2);
        colourMap.put('Y', 3);
        colourMap.put('O', 4);
        colourMap.put('P', 5);
        
        //freqArray
        
        freqArr[0]++;
        freqArr[0]++;
        freqArr[0]++;
        freqArr[1]++;
        //update GUI
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


    static boolean over = false;
    static HashMap<Character, Integer> colourMap = new HashMap<>();
    public static int[] getHint(ArrayList<Character> guess) {
        int[] hints = new int[2];

        int[] guessFreq = new int[6];
        int[] limit = new int[6];
        for (int i = 0; i < 6; i++) {
            limit[i] = freqArr[i];
        }

        for (char c : guess) {
            int temp = colourMap.get(c);
            guessFreq[temp]++;
        }

        for (int j = 0; j < 4; j++) {
            if (guess.get(j)== code.get(j)) {
                char c = guess.get(j);
                if(limit[colourMap.get(c)]>0 && guessFreq[colourMap.get(c)]>0) {
                    limit[colourMap.get(c)]--;
                    guessFreq[colourMap.get(c)]--;
                    hints[0]++;
                }
            }
        }

        for (int j = 0; j < 4; j++) {
            char c = guess.get(j);
            if (code.contains(c) && guess.get(j)!=code.get(j)) {
                if(limit[colourMap.get(c)]>0 && guessFreq[colourMap.get(c)]>0) {
                    limit[colourMap.get(c)]--;
                    guessFreq[colourMap.get(c)]--;
                    hints[1]++;
                }
            }
        }

        return hints;
    }

    public static ArrayList<Character> computerGuess(int hints[]){
    	possibleCombinations.remove(0);
        //remove the current guess from possibleCombinations so .get(0)
        for(int i = 0; i < possibleCombinations.size(); i++){
            
            //the following code to check if the combination will create the same hints as the answer given the guess
            int results[] = getHint(possibleCombinations.get(i));
            for(int j = 0; j < 2; j++){

                //results are from the possible combinations
                //hints are from guess being compared to the answer
                if(results[j] != hints[j]){
                    possibleCombinations.remove(i);

                    
                    j=2; //to end the for loop early
                }
            }
        }
        //one of the possible combinations
        System.out.println(possibleCombinations);
        return possibleCombinations.get(0);
        
    }

}
