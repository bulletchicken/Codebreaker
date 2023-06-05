import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main{

    //given possible colours for the game
    private static final char[] COLORS = {'G', 'R', 'B', 'Y', 'O', 'P'};
    //stores all possible combinations given the colours and slots
    private static ArrayList<ArrayList<Character>> possibleCombinations = new ArrayList<>();
    //the guess to be compared against the code
    private static ArrayList<Character>guess = new ArrayList<>();
    //the combination answer
    private static ArrayList<Character>code = new ArrayList<Character>();
    

    public static void main(String[]args) throws IOException{
        
        //initialize computer mode variables;
        initComputer();
        
        int results[] = new int[2];
        int numOfGuesses = 0;

        //demo code
        //guesses until computer gets it right
        do{
            numOfGuesses++;
            
            //first run uses the already set guess
            if(numOfGuesses==1){
            	results = getHint(guess, code);
            } else{
                //get the new guess based off the hints of the current guess
            	guess = computerGuess(results);
            }


            //get the hints based off the new guess and the answer
        	results = getHint(guess, code);

            //print the tried combination
            System.out.println("Guess num: " + numOfGuesses);
            System.out.println(guess);
            System.out.println("Black pins: " + results[0]);
            System.out.println("White pins: " + results[1]);
            System.out.println("------------");
            System.out.println();
        }while(results[0]!=4); //4 black pins mean the answer was reached
        
        System.out.println("Total # of guesses: " + numOfGuesses);
        System.out.println("Answer is " + guess);
        
    }

    /**initComputer method()
	 * 
	 * initComputer stands for initialize computer. This method resets all the
     * variables in preperation for a new game run. It sets the code with user input
	 *
	 * LOCAL VARIABLES
	 * combination - ArrayList<Character>
	 * 
	 * @param n/a
	 * @return n/a
     * @throws IOException
	 */
    public static void initComputer()throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //empties the arraylists
        possibleCombinations.clear();
        guess.clear();
        code.clear();

        //generates all possible outcomes to fill up possibleCombinations arraylist
        generateOutcomes();

        //starting initial guess (can be anything)
        guess.add('G');
        guess.add('G');
        guess.add('R');
        guess.add('R');

        //input for setting the code
        System.out.println("Setting the Code: Options - G, R, B, Y, O, P");
        for(int i = 0; i < 4; i++){
            System.out.println("Enter Letter #" + (i+1));
            code.add(br.readLine().charAt(0));
        }
    }

    
	/**generateOutcomes method()
	 * 
	 * This method generates every possible combination of the 6 colours within
     * the COLORS array in 4 slots. It adds each of these combinations into
     * a combination arraylist, and that arraylist gets added into a 2d array 
     * called possibleCombinations. This arraylist is every possible combination 
     * for the game.
	 *
	 * LOCAL VARIABLES
	 * combination - ArrayList<Character>
	 * 
	 * @param n/a
	 * @return n/a
	 */
    
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

    /**getHint method()
	 * 
	 * This method is used to check two combinations and to return the hints between them.
     * The method makes a copy of both since we will be removing values for counting. After
     * If colours are the same in the same index between the two combinations, increment 
     * hints[0] representing black pins. Then it removes it from the array so when we count
     * whites it doesn't double count. For white, we loop through the remaining combinations
     * and use .contains() to check if the same colour is atleast somewhere in the combination
	 * 
     * LOCAL VARIABLES
	 * guessCopy - ArrayList<Character>
	 * codeCopy - ArrayList<Character>
     * 
	 * @param:
     * ArrayList<Character>guess
     * ArrayList<Character>compareCode
     * 
	 * @return int[]
	 */
    public static int[] getHint(ArrayList<Character> guess, ArrayList<Character>compareCode) {
        int hints [] = new int[2]; //# of pins are stored in the array. [0] represents black, [1] represents white
        
        //creating a copy of the guess combination and the answer combination
        ArrayList<Character>guessCopy = new ArrayList<Character>();
        ArrayList<Character>codeCopy = new ArrayList<Character>();
        for(int i = 0; i < guess.size(); i++){
            guessCopy.add(guess.get(i));
            codeCopy.add(compareCode.get(i));
        }   

        //checks for same colour between the two lists in the same index
        for(int i = 0; i < guessCopy.size(); i++){
            if(guessCopy.get(i)==codeCopy.get(i)){
                hints[0]++; //adds 1 black pin
                guessCopy.remove(i); //removes the colour from both arrays so when we count white, it does not double count
                codeCopy.remove(i);
                i--; //when we remove a colour, all values shift in index by 1 back, so we have to follow by decrementing too
            }
        }

        //checks for if the colour from the guess is atleast contained in the code
        for(int i = 0; i < guessCopy.size(); i++){
            if(codeCopy.contains(guessCopy.get(i))){
                hints[1]++; //adds 1 white pin
                codeCopy.remove(guessCopy.get(i));
                guessCopy.remove(i);
                i--; 
            }
        }

        //returns the pins
        return hints;
    }


    /**computerGuess method()
	 * 
	 * This method is used to generate the next guest and eventually reach the answer.
     * The method cycles through all possible combinations and then calls the getHint
     * method between the guess and the possible combination. Like a detective, it will
     * see if the possibleCombination will produce the exact same hint as the answer.
     * If it doesn't, the combination is no longer possible at all and is removed from
     * the list. The next guess should be any value still remaining in the possible
     * combinations. 
     *  
     * 
	 * @param:
     * int hints[]
     * 
	 * @return ArrayList<Character>
	 */
    public static ArrayList<Character> computerGuess(int hints[]){
        //cycle through all possible combinations
        for(int i = 0; i < possibleCombinations.size(); i++){
            
            //get the hints between the guess and every possible combination.
            int results[] = getHint(guess, possibleCombinations.get(i));

            //compare if hints generated between the guess-code and the guess-possibleCombination are the same
            //if the posisble combination does not generate the same hints, it cannot possibly be
            //the answer so we remove it from the arraylist of possible combinations 
            if( (results[0] != hints[0]) || (results[1] != hints[1]) ){
                possibleCombinations.remove(i);
                i--; //when you remove a value from arraylist, all indexes shift back by one so you have to do the same with index
            }
        }
        //set guess as one of the possible combinations, in this case, the first
        return possibleCombinations.get(0);
        
    }
    
    public static ArrayList<Character> intermediateComputerGuess(int hints[]) {
    	if(Math.random()>0.9){ //10% of not removing all non-possible combinations
    		return possibleCombinations.get((int) Math.random());
    	} else{
    		return computerGuess(hints);
    	}
    }
    
    public static ArrayList<Character> beginnerComputerGuess(int hints[]) {
    	if(Math.random()>0.9){ //10% of not removing all non-possible combinations
    		return possibleCombinations.get((int) Math.random());
    	} else{
    		return computerGuess(hints);
    	}
    }

}
