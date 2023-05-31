import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class Merge extends JFrame implements ActionListener{

    //given possible colours for the game
    private static final char[] COLORS = {'G', 'R', 'B', 'Y', 'O', 'P'};
    //stores all possible combinations given the colours and slots
    private static ArrayList<ArrayList<Character>> possibleCombinations = new ArrayList<>();
    //the guess to be compared against the code
    private static ArrayList<Character>guess = new ArrayList<>();
    //the combination answer
    private static ArrayList<Character>code = new ArrayList<Character>();
    
    static String codeStr = "";
    static int[] freqArr = new int[6];
    static boolean state = false;
    static int guesses = 0;;
    static boolean over = false;


    JPanel userGuessPan = new JPanel(); //user inputs colours to guess
    JPanel compHintPan = new JPanel(); //computer sets the hints for the user
    JPanel guessButtonPan = new JPanel(); //different modes of each colour
    JPanel resetAndCountPan = new JPanel();//for reset button and guess count

    static String lastCom;
    ArrayList<JButton> btn = new ArrayList<>();
    JButton rPin = new JButton("R"); //red
    JButton gPin = new JButton("G"); //green
    JButton bPin = new JButton("B"); //blue
    JButton yPin = new JButton("Y"); //yellow
    JButton oPin = new JButton("O"); //orange
    JButton pPin = new JButton("P"); //pink
    JButton clear = new JButton("clear");
    JButton submit = new JButton("submit");
    JButton reset = new JButton("restart");
    ArrayList<String> colours = new ArrayList<>();

    ArrayList<JButton> boxes = new ArrayList<>();

    JButton box1 = new JButton();
    JButton box2 = new JButton();
    JButton box3 = new JButton();
    JButton box4 = new JButton();

    JLabel blackLabel = new JLabel();
    JLabel whiteLabel = new JLabel();
    JLabel guessCount = new JLabel("guesses: " + guesses);
    JLabel pinLabel = new JLabel("Pegs:");
    JLabel fLabel = new JLabel("feedback");
    JLabel desc = new JLabel("CODEBREAKER");

    GridLayout boxLay = new GridLayout(1, 5);
    GridLayout mlay = new GridLayout(7, 1); //main layout
    GridLayout blay = new GridLayout(1, 7); //button layout
    GridLayout hlay = new GridLayout(1, 2); //hints lyaout
    GridLayout rcp = new GridLayout(1, 2);//gridlayout for  reset and count panel

    public Merge(){
        setSize(1400 ,1000);
        setTitle("Codebreaker");
        btn.add(rPin);
        btn.add(gPin);
        btn.add(bPin);
        btn.add(yPin);
        btn.add(oPin);
        btn.add(pPin);
        colours.add("R");
        colours.add("G");
        colours.add("B");
        colours.add("Y");
        colours.add("O");
        colours.add("P");
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        boxes.add(box4);


        rPin.setBackground(Color.red);
        gPin.setBackground(Color.green);
        bPin.setBackground(Color.blue);
        bPin.setForeground(Color.white);
        yPin.setBackground(Color.yellow);
        oPin.setBackground(Color.orange);
        pPin.setBackground(Color.pink);

        desc.setFont(new Font("Arial", Font.PLAIN, 60));
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        fLabel.setFont(new Font("Arial", Font.PLAIN, 60));

        blackLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        whiteLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        clear.setFont(new Font("Arial", Font.PLAIN, 40));
        submit.setFont(new Font("Arial", Font.PLAIN, 40));
        guessCount.setFont(new Font("Arial", Font.PLAIN, 60));
        reset.setFont(new Font("Arial", Font.PLAIN, 40));
        reset.setBounds(600, 0, 390, 100);


        clear.addActionListener(this);
        submit.addActionListener(this);
        reset.addActionListener(this);

        setLayout(mlay);

        guessButtonPan.setLayout(blay);
        compHintPan.setLayout(hlay);
        userGuessPan.setLayout(boxLay);
        resetAndCountPan.setLayout(rcp);

        resetAndCountPan.add(guessCount);
        resetAndCountPan.add(reset);


        for(JButton j:btn){
            guessButtonPan.add(j);
            j.addActionListener(this);
            j.setFont(new Font("Arial", Font.PLAIN, 60));
        }
        guessButtonPan.add(clear);


        for(JButton box:boxes){
            userGuessPan.add(box);
            box.addActionListener(this);
            box.setFont(new Font("Arial", Font.PLAIN, 60));
        }
        userGuessPan.add(submit);

        compHintPan.add(blackLabel);
        compHintPan.add(whiteLabel);

        add(desc);
        add(resetAndCountPan);
        add(pinLabel);

        add(guessButtonPan);
        add(userGuessPan);
        add(fLabel);
        add(compHintPan);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[]args) throws IOException{

        Merge frame = new Merge();
        genCode();
        System.out.println(codeStr);
        over = false;
        
        //initialize computer mode variables;
        //initComputer();
    }

    Color oldColour;
    Color oldForeCOlour;
    public void actionPerformed(ActionEvent event) {
        int[] hints = new int[2];

        String command = event.getActionCommand();
        if(colours.contains(command)){
            state = true;
            lastCom = command;
            JButton temp = (JButton) event.getSource();
            oldColour = temp.getBackground();
            oldForeCOlour = temp.getForeground();
        }
        if(state && boxes.contains(event.getSource())){
            boxes.get(boxes.indexOf(event.getSource())).setText(lastCom);
            boxes.get(boxes.indexOf(event.getSource())).setBackground(oldColour);
            boxes.get(boxes.indexOf(event.getSource())).setForeground(oldForeCOlour);
        }

        if(command.equals("clear") && !over){
            for(JButton j : boxes){
                j.setText("");
                j.setBackground(null);
                j.setForeground(Color.black);
            }
        }
        if(command.equals("submit")&&!over){
            guess = new ArrayList<>();
            boolean valid = false;
            try {
                getGuess();
                System.out.println(guess);
                valid = true;
            }catch (StringIndexOutOfBoundsException error){
                whiteLabel.setText("Please enter valid guess");
                valid = false;
            }
            if(valid) {
                guesses++;
                guessCount.setText("guesses: " + guesses);


                hints = getHint(guess, code);
                System.out.println(hints[0]);
                renderFeedback(hints);

                if (guesses == 10) {
                    gameOver();
                }
            }
        }

        if(command.equals("restart")){
            restart();
        }
    }

    public static String genCode(){
        freqArr = new int[6];
        codeStr = "";
        code = new ArrayList<>();
        for (int i = 0; i < 4; i++) { //randomly generate the codeStr
            int tempNum = ((int) (Math.random() * 6));
            char tempChar = COLORS[tempNum];
            codeStr += tempChar; //add to codeStr string
            code.add(tempChar); //add to arraylist of chars
            freqArr[tempNum]++;
        }

        return codeStr;
    }

        public void restart(){
        guesses=0;
        over=false;
        guessCount.setText("guesses: 0");
        codeStr = genCode();
        System.out.println(codeStr);
        whiteLabel.setText("");
        blackLabel.setText("");
        for(JButton j : boxes){
            j.setText("");
            j.setBackground(null);
            j.setForeground(Color.black);
        }
        blackLabel.setFont(new Font("Arial", Font.PLAIN, 60));
    }

    public void gameOver(){
        guessCount.setText("guesses: 10");
        blackLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        blackLabel.setText("Game over 10 guesses reached");
        whiteLabel.setText("Code: "+codeStr);
        over = true;
    }

    public void getGuess(){
        for (int i = 0; i < 4; i++) {
            guess.add(boxes.get(i).getText().charAt(0));
        }
    }

    public void renderFeedback(int[] hints){
        if(hints[0]==4){
            System.out.println("hint");
            whiteLabel.setText("You won!");
            blackLabel.setText("");
            over=true;
        }
        else {
            whiteLabel.setText(hints[1] + " white pins");
            blackLabel.setText(hints[0] + " black pins");
        }
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

}
