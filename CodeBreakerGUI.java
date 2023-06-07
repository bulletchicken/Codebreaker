import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class CodeBreakerGUI extends JFrame implements ActionListener {
    //Creating the arraylists
    private static ArrayList<ArrayList<Character>> possibleCombinations = new ArrayList<>();
    private static ArrayList<Character> code = new ArrayList<>();
    private static ArrayList<Character> guess = new ArrayList<>();

    //The characters of the colours used in our version of masterind
    private static final char[] COLORS = { 'G', 'R', 'B', 'Y', 'O', 'P' };
    private static boolean over = false; //to check if the game is over
    private static String lastCom; //store the selected colour's leter
    private boolean state = false; //active state to make sure a no colour selection was made
    private String playerName = ""; //stores player name
    private String gameMode = "userGuess"; //the current game mode
    private int level = 0; //level of AI difficulty
    private int numOfGuesses = 0;

    private HashMap<String, Color> buttonColours = new HashMap<>();
    private JPanel leftSide = new JPanel();// leftside
    private JPanel rightSide = new JPanel();// rightside

    private JPanel topRow = new JPanel();
    private JPanel resetAndCountPan = new JPanel();// for reset button and guess count


    private String[]computerPhrases = {"This is a hard code...", "Oh this is easy!", "I can do it!, it has to have Red right?", "One step closer to cracking this code!"};
    private JButton modeButton = new JButton("go To AI Guessing");// user guessing mode
    private JButton menuButton = new JButton("Go to Menu");
    private JButton instructionsButton = new JButton("Instructions");
    private JLabel leaderboard = new JLabel("Leaderboard");
    private JLabel lbName = new JLabel("Name");// leaderboard name
    private JLabel lbScore = new JLabel("Score");
    private RoundButton blackPin = new RoundButton("B"); // black
    private RoundButton whitePin = new RoundButton("W"); // white
    private JButton rPin = new JButton("R"); // red
    private JButton gPin = new JButton("G"); // green
    private JButton bPin = new JButton("B"); // blue
    private JButton yPin = new JButton("Y"); // yellow
    private JButton oPin = new JButton("O"); // orange
    private JButton pPin = new JButton("P"); // pink
    private JButton clear = new JButton("clear");
    private JButton submit = new JButton("submit");
    private JButton reset = new JButton("restart");
    private ArrayList<JButton> colours = new ArrayList<>();
    private ArrayList<JButton> boxes = new ArrayList<>();
    private Color lightBlue = new Color(143, 192, 243);//saving the colour

    private ArrayList<ArrayList<JButton>> guessBoxes = new ArrayList<>(); //storing the 4x10 list of buttons
    private JPanel[] hintPanels = new JPanel[10];
    private JPanel[] rightRows = new JPanel[10];

    //hint boxes
    private ArrayList<JButton> hintPegs = new ArrayList<>();
    private ArrayList<ArrayList<RoundButton>> hintBoxes = new ArrayList<>();
    private JButton box1 = new JButton();
    private JButton box2 = new JButton();
    private JButton box3 = new JButton();
    private JButton box4 = new JButton();

    //display the number of guesses the player is on
    private JLabel pinLabel = new JLabel("Colours");
    private JLabel fLabel = new JLabel("feedback");
    private JLabel desc = new JLabel("CODEBREAKER");

    //to store the text for the leaderboard and also the computer AI chat
    private JTextArea leaderboardArea = new JTextArea();
    private JPanel buttonList = new JPanel();

    //formating layouts
    private GridLayout mainLayout = new GridLayout(1, 2); // main layout
    private GridLayout leftLayout = new GridLayout(2, 1);
    private GridLayout topRowLayout = new GridLayout(1, 4);
    private GridLayout right = new GridLayout(12, 1);
    private GridLayout hintLayout = new GridLayout(2, 2); // hints lyaout
    private GridLayout controlLayout = new GridLayout(1, 3);// gridlayout for reset and count panel
    private GridLayout rightRowsLayout = new GridLayout(1, 6);

    private Color oldColour = Color.WHITE;
    private Color oldForeCOlour = Color.BLACK;


    public CodeBreakerGUI(String playerName) {
        initUserGuess(); //The game starts off as user guessing so run the initialization for it
        this.playerName = playerName; //set the player name

        //set the main frame prop.
        setSize(800, 950);
        setTitle("Codebreaker");

        //add all the colours to the colours panel
        colours.add(rPin);
        colours.add(gPin);
        colours.add(bPin);
        colours.add(yPin);
        colours.add(oPin);
        colours.add(pPin);

        //adding all the hints to the hints panel
        hintPegs.add(whitePin);
        hintPegs.add(blackPin);

        //adding boxes for the code
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        boxes.add(box4);

        //setting formating for the panels and buttons
        leftSide.setBorder(new EmptyBorder(10, 10, 10, 30));
        rightSide.setBorder(new EmptyBorder(10, 30, 10, 30));
        rPin.setBackground(new Color(253, 104, 104));
        gPin.setBackground(new Color(154, 246, 142, 240));
        bPin.setBackground(new Color(85, 171, 255));
        bPin.setForeground(Color.white);
        yPin.setBackground(new Color(248, 233, 159));
        oPin.setBackground(new Color(243, 162, 102));
        pPin.setBackground(new Color(255, 174, 228));
        blackPin.setBackground(Color.black);
        whitePin.setBackground(Color.white);
        blackPin.setForeground(Color.white);

        //leaderboard area formating
        leaderboardArea.setEditable(false); //make it unwritable
        leaderboardArea.setFont(new Font("Courier", Font.BOLD, 20)); //font change
        leaderboardArea.setWrapStyleWord(rootPaneCheckingEnabled);
        leaderboardArea.setLineWrap(true);
        leaderboardArea.setWrapStyleWord(true);
        //centre align
        leaderboard.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        leaderboard.setAlignmentY(JTextArea.CENTER_ALIGNMENT);

        //setting the fonts
        Font font30 = new Font("Arial", Font.PLAIN, 30);
        Font font20 = new Font("Arial", Font.PLAIN, 20);
        Font font25 = new Font("Arial", Font.PLAIN, 25);
        desc.setFont(font30);
        pinLabel.setFont(font30);
        fLabel.setFont(font30);
        box1.setFont(font30);
        box2.setFont(font30);
        box3.setFont(font30);
        box4.setFont(font30);
        clear.setFont(font20);
        submit.setFont(font20);
        reset.setFont(font20);
        instructionsButton.setFont(font30);
        modeButton.setFont(font30);
        menuButton.setFont(font25);
        leaderboard.setFont(font25);
        lbName.setFont(font30);
        lbScore.setFont(font30);

        //adding action listener so that it will run the actionperformed method on button press bellow
        menuButton.addActionListener(this);
        modeButton.addActionListener(this);
        instructionsButton.addActionListener(this);
        reset.setBounds(600, 0, 390, 100);
        clear.addActionListener(this);
        submit.addActionListener(this);
        reset.addActionListener(this);

        //setting the formating for the whole frame
        setLayout(mainLayout);

        //setting the formating for all the panels
        resetAndCountPan.setLayout(controlLayout);
        topRow.setLayout(topRowLayout);
        leftSide.setLayout(leftLayout);
        buttonList.setLayout(new GridLayout(4, 1));
        buttonList.add(instructionsButton);
        buttonList.add(menuButton);
        buttonList.add(modeButton);
        buttonList.add(leaderboard);
        leftSide.add(buttonList);
        leftSide.add(leaderboardArea);
        rightSide.setBackground(lightBlue);
        leftSide.setBackground(new Color(126, 208, 253));

        //setting the dimensions and fonts for the frame
        UIManager.put("OptionPane.minimumSize", new Dimension(500, 500));
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(
                "Arial", Font.BOLD, 50)));
        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font(
                "Arial", Font.PLAIN, 50)));

        //setting the code buttons so that they show ?
        for (JButton j : boxes) {
            j.setText("?"); //change the text on the button
            j.setFocusPainted(false);
            j.setEnabled(false);
            topRow.add(j);
        }

        //setting formating for the right side panel
        rightSide.setLayout(right);
        rightSide.add(topRow);

        //draws the gamemode it is currently on (initializing the GUI)
        if (gameMode.equals("userGuess")) {
            drawUserMode();
        }
        else if (gameMode.equals("compGuess")) {
            drawCompMode();
        }

        //adding all the buttons to the panel
        resetAndCountPan.add(submit);
        resetAndCountPan.add(clear);
        resetAndCountPan.add(reset);
        rightSide.add(resetAndCountPan);
        add(leftSide);
        add(rightSide);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        showCurrentRow();

        //Adding the char and colour mappings to buttonColours to resuse colours
        buttonColours.put("R", rPin.getBackground());
        buttonColours.put("G", gPin.getBackground());
        buttonColours.put("B", bPin.getBackground());
        buttonColours.put("Y", yPin.getBackground());
        buttonColours.put("O", oPin.getBackground());
        buttonColours.put("P", pPin.getBackground());

    }
    /** initUserGuess()
     *  This method is used to run methods to initialize and start the user guessing game mode
     *
     * @parameters event the event that occured once an object with an actionlistener is interacted with
     * @returns void
     */
    public void initUserGuess() {

        //changes the title to user guessing mode
        setTitle("Code Breaker - User Guessing Mode");
        guess.clear(); //clears the current guess
        genCode(); //generate the random code
        leaderboardArea.setText(""); //empty the leaderboard
        displayLeaderBoard(); //display the leaderboard from the txt file

    }
    /** initComputer()
     *  This method is used to run methods to initialize and start the computer guessing game mode
     *
     * @parameters n/a
     * @returns void
     */
    public void initComputer() {
        setTitle("Code Breaker - Computer Guessing Mode"); //changes the title
        generateOutcomes(); //generate all possible outcomes and fill in the arraylist
        guess.add('G'); //starting guess is G G G G
        guess.add('G');
        guess.add('G');
        guess.add('G');

        //places the start guesses colour into the buttons
        for (int i = 0; i < 4; i++) {
            //retrieves the button  -->  set the background colour to the values in the guess
            guessBoxes.get(numOfGuesses).get(i).setBackground(buttonColours.get(String.valueOf(guess.get(i))));
        }

        //set the AI chat
        leaderboardArea.setText("I am Mr. B, a codebreaker AI! Start the game by writing down your code on a piece of paper and providing me the hints by clicking and placing the black and white hints to the left! I already have my first guess for you to start it off!");
    }

    /** ActionPerformed method
     *  Called automactically by the program when anything with an action listener is interacted with.
     *  Depending on the object that was pressed, different code will be executed
     *  if the command used was a game mode, that game mode will be run
     *  once the game mode is set, whenever a colour peg is clicked, the appropiate boxes can be clicked on to set its colour
     *  once boxes colour is set, in the user guessing mode, you can use the submit, clear, or restart buttons to do their repsective functions
     *
     * @parameters event that occured once an object with an actionlistener is interacted with
     * @return void
     */
    public void actionPerformed(ActionEvent event) {
        int[] hints = new int[2];
        String command = event.getActionCommand();
        if(command.equals("Go to Menu")) {
            MainMenu reopenMenu = new MainMenu();
            dispose();
        }

        if (command.equals("go To AI Guessing")) {
            /*
            if computer guessing code mode, clear the board and add in the computer guessing layout and components
            and re-render the frame
             */
            restart();
            rightSide.removeAll();
            remove(rightSide);
            revalidate();
            repaint();
            rightSide = new JPanel();
            rightSide.setLayout(right);
            drawCompMode();
            rightSide.add(resetAndCountPan);
            add(rightSide);
            revalidate();
            repaint();
            initComputer(); //initialize the computer guess mode
            gameMode = "compGuess";
            modeButton.setText("go To User Guessing");
        }
        if (command.equals("go To User Guessing")) {
            /*
            if game mode is user guessing the code,
            clear the board and add its repsectice layout and buttons
            then re-render the frame and reset all the colours
             */
            initUserGuess(); //initialize the userguess mode
            gameMode = "userGuess";
            rightSide.removeAll();
            remove(rightSide);
            rightSide = new JPanel();
            rightSide.setLayout(right);
            rightSide.add(topRow);
            drawUserMode();
            rightSide.add(resetAndCountPan);
            add(rightSide);
            revalidate();
            repaint();

            //reset all the guess buttons to white
            for (JButton jb : guessBoxes.get(0)) {
                jb.setBackground(Color.white);

            }
            genCode();
            modeButton.setText("go To AI Guessing");
        }

        if (command.equals("Instructions")) {
            InstructionsManual instructionsManual = new InstructionsManual();
        }
        if (gameMode.equals("userGuess")) {
            /*
            if gamemode is set to user guessing, allow user to select colour by pressing the according button
            once a button is pressed, its colour is saved and any guess box can be set to that colour to represent
            a part of the guess.
            */
            if (colours.contains(event.getSource())) {
                state = true; //active state meaning that a colour has been selected to avoid a no colour error
                lastCom = colours.get(colours.indexOf(event.getSource())).getText();
                RoundButton temp = (RoundButton) event.getSource();
                if (temp.getBackground() !=lightBlue) {
                    oldForeCOlour = temp.getForeground();
                    oldColour = temp.getBackground();
                    //grabs the colours of the pin if it is not already sleected
                }
                for (JButton jb : colours) {
                    if (jb.getText().equals(command)) {
                        jb.setBackground(lightBlue);
                        jb.setForeground(oldColour);
                        //chgne colour of current button selected

                    } else {
                        jb.setBackground(buttonColours.get(jb.getText()));
                        jb.setForeground(Color.black);
                        if (jb.getText().equals("B"))
                            jb.setForeground(Color.white);
                        //reset colours of other pegs
                    }
                }

            }
            if (!over) {
                if(state){
                    int index = guessBoxes.get(numOfGuesses).indexOf(event.getSource());

                    if (index != -1) {
                        JButton temp = guessBoxes.get(numOfGuesses).get(index);

                        temp.setText(lastCom);
                        temp.setBackground(oldColour);
                        temp.setForeground(oldForeCOlour);
                        //if the guessbox is pressed, change its colour to last selected colour peg
                    }

                    if (command.equals("clear")) {
                        /*
                        if the clear button is pressed, reset all the colours at the current guess row
                         */
                        for (JButton j : guessBoxes.get(numOfGuesses)) {
                            j.setText("");
                            j.setBackground(Color.white);
                            j.setForeground(Color.black);
                        }
                    }
                    if (command.equals("submit")) {
                        /*
                        if user submits their guess, store the guess in an arraylist
                        after, pass the list to a checking method to get hints
                        increment guess count at every guess until 10 at which, the game ends and losing message dialog will pop up
                         */
                        ArrayList<Character> guessList = new ArrayList<>();
                        for (JButton jb : colours) {

                            jb.setBackground(buttonColours.get(jb.getText()));
                            jb.setForeground(Color.black);
                            if (jb.getText().equals("B"))
                                jb.setForeground(Color.white);

                        }
                        boolean valid = false;
                        try {
                            guessList = getGuess();
                            //stores guess in arraylist
                            valid = true;
                        } catch (StringIndexOutOfBoundsException error) {
                            //if imvalid guess d=show popup for UFP
                            JOptionPane.showMessageDialog(null, "Your guess is not complete! Make sure all the white circles are colored!", "Error :/", JOptionPane.INFORMATION_MESSAGE);
                            valid = false;
                        }
                        if (valid) {
                            
                            

                            hints = getHint(guessList, code);
                            renderFeedback(hints);
                            numOfGuesses++;
                            if (!over) {
                                showCurrentRow();
                            }
                            if (numOfGuesses == 10) {
                                gameOver();
                                loss();
                            }
                        }
                    }
                }
            }

        } else if (gameMode.equals("compGuess")) {

            if (command.equals("comboBoxChanged")) {
                //checks which level is selected for AI
                JComboBox cb = (JComboBox) event.getSource();
                switch ((String) cb.getSelectedItem()) {
                    case "basic":
                        level = 1;
                        break;
                    case "intermediate":
                        level = 2;
                        break;
                    case "expert guesser":
                        level = 3;
                        break;
                }
            }
            if (hintPegs.contains(event.getSource())) {
                //if a colour peg is pressed
                state = true;
                lastCom = hintPegs.get(hintPegs.indexOf(event.getSource())).getText();
                JButton temp = (JButton) event.getSource();
                oldColour = temp.getBackground();
                oldForeCOlour = temp.getForeground();
                //grab its coolour
            }
            if (!over && state) {
                int index = hintBoxes.get(numOfGuesses).indexOf(event.getSource());

                if (state && index != -1) {
                    //if guessbox is pressed, change its colour to last pressed colour peg
                    JButton temp = hintBoxes.get(numOfGuesses).get(index);
                    temp.setText(lastCom);
                    temp.setBackground(oldColour);
                    temp.setForeground(oldForeCOlour);
                }

                if (command.equals("clear")) {
                    //if clear, reset the colours for current row's hints
                    for (JButton j : hintBoxes.get(numOfGuesses)) {
                        j.setText("");
                        j.setBackground(new Color(26, 75, 131));
                        j.setForeground(Color.black);
                    }
                }
                if (command.equals("submit")) {

                    if (level == 0) { // no mode selected
                        // popup error "please select ai difficulty"
                        leaderboardArea.setText("Please select AI mode");
                    } else {

                        leaderboardArea.setText(computerPhrases[(int)Math.random()*computerPhrases.length]);
                        hints = hintSelections(); // retrieve hints from user
                        numOfGuesses++;
                        if(!over) {
                            if(hints[0]==4){
                                over = true;
                                compWin();
                            }else {
                                if (level == 1) {
                                    guess = beginnerComputerGuess(hints);
                                }
                                if (level == 2) {
                                    guess = intermediateComputerGuess(hints);
                                }
                                if (level == 3) {
                                    guess = expertComputerGuess(hints);
                                }
                                for (int i = 0; i < 4; i++) {
                                    guessBoxes.get(numOfGuesses).get(i).setBackground(buttonColours.get(String.valueOf(guess.get(i))));
                                }
                            }

                        }

                    }

                }
            }
        }
        if (command.equals("restart")) {
            //if restart is pressed, cal lrestart method
            restart();
        }

    }

    public static void displayColours(ArrayList<Character> compsGuess) {

    }



    public static void genCode() {
        //using an array of chars, we can randomly generate a number to grab that char from the array to add it to the code list
        // this will be the randomly generated code that the user must guess
        code = new ArrayList<>();
        for (int i = 0; i < 4; i++) { // randomly generate the codeStr
            int tempNum = ((int) (Math.random() * 6));
            char selectedColour = COLORS[tempNum];
            code.add(selectedColour); // add to arraylist of chars
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
    public static int[] getHint(ArrayList<Character> guess, ArrayList<Character> compareCode) {
        int hints[] = new int[2]; // # of pins are stored in the array. [0] represents black, [1] represents white

        // creating a copy of the guess combination and the answer combination
        ArrayList<Character> guessCopy = new ArrayList<Character>();
        ArrayList<Character> codeCopy = new ArrayList<Character>();
        for (int i = 0; i < guess.size(); i++) {
            guessCopy.add(guess.get(i));
            codeCopy.add(compareCode.get(i));
        }

        // checks for same colour between the two lists in the same index
        for (int i = 0; i < guessCopy.size(); i++) {
            if (guessCopy.get(i) == codeCopy.get(i)) {
                hints[0]++; // adds 1 black pin
                guessCopy.remove(i); // removes the colour from both arrays so when we count white, it does not
                // double count
                codeCopy.remove(i);
                i--; // when we remove a colour, all values shift in index by 1 back, so we have to
                // follow by decrementing too
            }
        }

        // checks for if the colour from the guess is atleast contained in the code
        for (int i = 0; i < guessCopy.size(); i++) {
            if (codeCopy.contains(guessCopy.get(i))) {
                hints[1]++; // adds 1 white pin
                codeCopy.remove(guessCopy.get(i));
                guessCopy.remove(i);
                i--;
            }
        }

        // returns the pins
        return hints;
    }

    /**restart()
     * Restarts the whole gui including variables for another round
     *
     * @param:
     * String playerName, int numOfGuesses
     *
     * @return void
     */
    public void restart() {

        for (JButton button : colours) {
            //loops for all the buttons for the colours and resets them to their defaults
            button.setBackground(buttonColours.get(button.getText()));
            button.setForeground(Color.black);
            if (button.getText().equals("B")) button.setForeground(Color.white);

        }
        for(JButton button : boxes){
            //resetting the buttons for the actual code itself being displayed when game ends
            button.setBackground(null);
            button.setEnabled(false);
        }

        numOfGuesses = 0;
        over = false;
        
        for (JButton button : boxes) {
            //resets the code display boxes to "?"
            button.setText("?");
        }
        for (ArrayList<JButton> button : guessBoxes) {
            //resets all the guessing boxes to default colour and clearing their text
            for (JButton button2 : button) {
                button2.setText("");
                button2.setBackground(Color.black);
            }
        }
        for (ArrayList<RoundButton> button : hintBoxes) {
            //resetting color and text for the hints
            for (JButton button2 : button) {
                button2.setText("");
                button2.setBackground(new Color(106, 157, 215));
            }
        }


        guess=new ArrayList<>(); //clearing guess arraylist for next game

        if(gameMode.equals("userGuess")){
            genCode(); //generates a new code
            leaderboardArea.setText(""); //clears the leaderboard
            initUserGuess(); //reinitializing user guess
            showCurrentRow();// shows the first row to indicate the first guess
        }
        else if(gameMode.equals("compGuess")){
            initComputer(); //reinitializing computer guessing
            possibleCombinations = new ArrayList<>(); //emptying possible combinations
            generateOutcomes();// regenerate possible combinations
            for(int i = 0; i < 4; i++){
                guessBoxes.get(numOfGuesses).get(0).setBackground(buttonColours.get(String.valueOf(guess.get(i))));
            }

        }
    }
    /**
     * gameOver()
     * display the answer code on the top
     *
     * @param none
     * @return void
     */
    public void gameOver() {
        for (int i = 0; i < 4; i++) {
            boxes.get(i).setText(code.get(i).toString());
            boxes.get(i).setEnabled(true);
            boxes.get(i).setBackground(buttonColours.get(code.get(i).toString())); //setting the background colour of each button by matching the code to the colour map
        }
        over = true; //set over true
    }

    /**
     * getGuess()
     * method to get the list of chars in the guess boxes that the user submitted
     *
     * @param none
     * @return an arraylist of Character type
     */
    public ArrayList<Character> getGuess() {

        guess = new ArrayList<>();
        ArrayList<Character> guessList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            //gets the char of the text at the current row's box
            guessList.add(guessBoxes.get(numOfGuesses).get(i).getText().charAt(0));
        }
        return guessList;
    }

    /** renderFeedback void method
     *  this method changes the colour of the hints for each guess
     *  loops through the array of hitns and changes the colours of the hint boxes accordiningly
     * @param hints the hints from the user's guess
     */
    public void renderFeedback(int[] hints) {
        if (hints[0] == 4) {
            //if there are 4 blacks, the guess is the same as the code so the user wins
            gameOver(); //run game over
            win(); //run win
            over = true;

        } else {
            for (int i = 0; i < hints[0]; i++) {
                hintBoxes.get(numOfGuesses).get(i).setBackground(Color.black);
            }
            for (int i = hints[0]; i < hints[1] + hints[0]; i++) {
                if (hints[0] < 4) {
                    hintBoxes.get(numOfGuesses).get(i).setBackground(Color.white);
                }
            }
        }
    }


    /**showCurrentRow()
     * void method to highlight the current row the user is guessing on to indicate the guess
     *
     * @param none
     * @return none
     */
    public void showCurrentRow() {
        for (int i = 0; i < 4; i++) {
            if (numOfGuesses < 10) {
                guessBoxes.get(numOfGuesses).get(i).setBackground(Color.white);
            }
        }
    }

    /**
     * void method drawUserMode()
     * changes the layout of the board to allow for the adding of components neededd for user guessing mdoe
     * called when user selects user guessing mode
     * @param none
     * @return none
     */
    public void drawUserMode() {
        right = new GridLayout(12, 1);
        rightSide.setLayout(right); //setting the layout
        for (int i = 0; i < 10; i++) {
            //loops through for each row, add the required components
            rightRows[i] = new JPanel(); //initallize the row's panel
            rightRows[i].setBackground(new Color(143, 192, 243)); //setting bg colour
            rightRows[i].setLayout(rightRowsLayout);
            hintPanels[i] = new JPanel(); //initialize
            hintPanels[i].setLayout(hintLayout);
            guessBoxes.add(new ArrayList<>()); //initalize new arraylists
            hintBoxes.add(new ArrayList<>());
            if (i <= 5) {
                RoundButton rb = new RoundButton(colours.get(i).getText());
                rb.setFont(new Font("Arial", Font.PLAIN, 20));
                rb.addActionListener(this);
                rb.setBackground(colours.get(i).getBackground());
                rb.setForeground(colours.get(i).getForeground());
                //changing style of the rounbed button
                colours.set(i, rb);
                rightRows[i].add(rb); //add the button to the row
            } else {
                //if no more colours, add empty button as placeholder and set visible to false so it doesnt appear
                JButton tempButton = new JButton();
                rightRows[i].add(tempButton);
                tempButton.setVisible(false);
            }
            hintPanels[i].setBackground(new Color(169, 255, 248)); //colour change of hint panel

            for (int j = 0; j < 4; j++) {
                //4 guess boxes and hint boxes
                RoundButton temp = new RoundButton("");
                temp.setFocusPainted(false);
                temp.addActionListener(this);
                temp.setFont(new Font("Arial", Font.PLAIN, 20));
                temp.setBackground(Color.black);
                //stylisation of button
                guessBoxes.get(i).add(temp);
                rightRows[i].add(guessBoxes.get(i).get(j));
                hintBoxes.get(i).add(new RoundButton(""));
                hintBoxes.get(i).get(j).setBackground(new Color(106, 157, 215));
                hintBoxes.get(i).get(j).setFont(new Font("Arial", Font.PLAIN, 15));
                hintPanels[i].add(hintBoxes.get(i).get(j));
                // add to the hint panel
            }
            rightRows[i].add(hintPanels[i]); //add the current row's components to the row panel
            rightRows[i].setBorder(new EmptyBorder(10, 0, 10, 0)); //formatting border

            rightSide.add(rightRows[i]); //add panel to main right panel
            leaderboardArea.setVisible(true); //leaderbaord bisible
            leaderboard.setVisible(true);
            setSize(new Dimension(800, 950));

            leaderboard.setText("Leaderboard");
            leaderboardArea.removeAll();

        }
    }
    /**
     * void method drawCompMode()
     * called user selects AI guessing mode
     * changes the layout and adds the componnets necessary for the AI guessing mode
     * @param none
     * @return none
     */
    public void drawCompMode() {
        right = new GridLayout(14, 1);
        rightSide.setLayout(right); //layout change
        setSize(new Dimension(800, 1000)); //apporaipte dimensions
        for (int i = 0; i < 10; i++) {
            //for each of the 10 rows,
            rightRows[i] = new JPanel(); //initilaze jpanel for current row
            rightRows[i].setBackground(new Color(52, 132, 175));
            rightRows[i].setLayout(rightRowsLayout);
            hintPanels[i] = new JPanel();
            hintPanels[i].setLayout(hintLayout);
            guessBoxes.add(new ArrayList<>()); //intialize arraylists
            hintBoxes.add(new ArrayList<>());
            if (i <= 1) {
                //for frist 2 rows, add the black and white buttons for the user to provide hints
                RoundButton rb = new RoundButton(hintPegs.get(i).getText());
                rb.setFont(new Font("Arial", Font.PLAIN, 15));
                rb.addActionListener(this);
                rb.setBackground(hintPegs.get(i).getBackground());
                rb.setForeground(hintPegs.get(i).getForeground());
                //style
                hintPegs.set(i, rb);
                rightRows[i].add(rb); //add button to row panel
            } else {
                //after first two, add inivislbe placeholder buttons
                JButton tempButton = new JButton();
                rightRows[i].add(tempButton);
                tempButton.setVisible(false);
            }
            hintPanels[i].setBackground(new Color(169, 255, 248));

            for (int j = 0; j < 4; j++) {
                //4 guess boxes and 4 hint boxes
                RoundButton temp = new RoundButton("");
                temp.setFocusPainted(false);
                temp.setFont(new Font("Arial", Font.PLAIN, 20));
                temp.setBackground(new Color(26, 47, 131));
                //stylize
                guessBoxes.get(i).add(temp); //add the guess box to the list of guess boxes
                rightRows[i].add(guessBoxes.get(i).get(j));
                hintBoxes.get(i).add(new RoundButton(" "));
                hintBoxes.get(i).get(j).setBackground(new Color(26, 75, 131));
                hintBoxes.get(i).get(j).setFont(new Font("Arial", Font.PLAIN, 15));
                hintBoxes.get(i).get(j).addActionListener(this); //add actionlistner
                //style the hint boxes 
                hintPanels[i].add(hintBoxes.get(i).get(j));//add hintbox to hint panel

            }
            rightRows[i].add(hintPanels[i]);//add hint panel to the row
            rightRows[i].setBorder(new EmptyBorder(10, 0, 10, 0));//formatting
            rightSide.add(rightRows[i]);//add to main right paenl

        }
        String[] comDiffs = { "basic", "intermediate", "expert guesser", "Select AI intelligence level..." };
        //array of different labels for AI level
        JComboBox diffs = new JComboBox(comDiffs); //dropdown menu
        diffs.setFont(new Font("Arial", Font.PLAIN, 20));
        diffs.setSelectedIndex(3);
        //formatting
        diffs.addActionListener(this); //action listner for the dropdown menu

        rightSide.add(diffs);//add dropdown menu to main right panel
        JLabel availColours = new JLabel("Possible colours:"); //label for colours
        availColours.setFont(new Font("Arial", Font.PLAIN, 30)); 
        JPanel availableColoursPanel = new JPanel();
        GridLayout colourLayout = new GridLayout(1, 6);
        availableColoursPanel.setLayout(colourLayout);
        //formattig and stlying
        for(JButton j : colours){
            availableColoursPanel.add(j);
            //add the colours to the panel 
        }
        rightSide.add(availColours); //add the colour label to the main right layout
        rightSide.add(availableColoursPanel); //add the panel

        //text for AI guessing
        leaderboard.setText("Computer Chat");
        leaderboardArea.removeAll();
        leaderboardArea.append("I am guessing");

    }

    /**
     * void method win()
     * called when user successfully guesses the code that was generated randomly
     * displays a popup with a winning message and writes score and playername to leaderboard
     * @param none
     * @return none
     */
    private void win() {
        JOptionPane.showMessageDialog(null, "Congratulations, You won!", "You WIN", JOptionPane.INFORMATION_MESSAGE);
        writeToLeaderBoard(playerName, numOfGuesses);
        leaderboardArea.setText("");
        displayLeaderBoard();
    }

    /**compWin()
     * This method is used to display the Winning popup for the computer after it successfully gets your code
     *
     * @param:N/A
     * @return void
     */
    private void compWin(){
        String s = "";
        for(char c : guess){
            s+=c;
        }
        JOptionPane.showMessageDialog(null, "The code is: " + s, "Your Code is:", JOptionPane.INFORMATION_MESSAGE);
    }

    /**loss()
     * This method is used to display the losing popup
     *
     * @param:N/A
     * @return void
     */
    private void loss() {
        JOptionPane.showMessageDialog(null, "Unfortunately, you lost.", "Defear", JOptionPane.INFORMATION_MESSAGE);
    }

    /**hintSelections()
     * This method is used to retrieve the user input on the hints
     * they provided for the AI
     *
     * @param:N/A
     *
     * @return int[]
     */
    public int[] hintSelections() {
        int[] hints = new int[2];
        for (JButton j : hintBoxes.get(numOfGuesses)) {
            if (j.getText().equals("B")) {
                hints[0]++;
            } else if (j.getText().equals("W")) {
                hints[1]++;
            }
        }
        return hints;
    }
    /**displayLeaderBoard()
     * Writes the contents of the txt file onto the JTextarea to display to the user
     *
     * @param: n/a
     *
     * @return void
     */
    public void displayLeaderBoard() {
        try {
            File file = new File("leaderboard.txt"); //Save the file as an object
            Scanner myReader = new Scanner(file); //initialize scanner
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine(); //get each line of txt and save into a string
                leaderboardArea.append(data + "\n"); //add each of those lines onto the leaderboard
            }
            myReader.close();
        } catch (Exception e) {

        }
    }
    /**writeToLeaderBoard()
     * Edits the leaderboard.txt files with the players new score. It first
     * reads through the current leaderboard txt file and parses it into
     * An ArrayList containing string arrays. While it does this, it marks
     * the index of where the player's score should be. Then it rewrites the
     * txt file with the updated leaderboard using FileWriter.
     *
     * @param:
     * String playerName, int numOfGuesses
     *
     * @return void
     */
    public void writeToLeaderBoard(String playerName, int numOfGuesses) {
        //try catch for errors of missing files or anything else
        try {
            //templeaderboard to store the scores and names from the txt file
            ArrayList<String[]> tempLeaderBoard = new ArrayList<>();
            File myFile = new File("leaderboard.txt"); //txt file
            Scanner myReader = new Scanner(myFile); //to read the files
            playerName = playerName.replaceAll(" ", ""); //removes any spaces from the username

            int insertIndex = 0; //the starting index for the new user score is at the top
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine(); //gets the txt line by line into a String
                String[] playerData = data.split(" "); //converting the string into an array and spliting every space
                if (Integer.parseInt(playerData[3]) < numOfGuesses) { //playerData[3] is where the score number is stored
                    insertIndex++; //incremenet the line number it should be placed
                }
                tempLeaderBoard.add(playerData); //add the player data
            }
            myReader.close();//close the reader here since file writer can only read the stream if no others are reading too
            FileWriter myWriter = new FileWriter(myFile);

            String[] playerDataAsArr = new String[5]; //to store the current player's data as an arr like all the others
            playerDataAsArr[1] = playerName; //stores the name
            playerDataAsArr[3] = String.valueOf(numOfGuesses); //stores the number of guesses
            tempLeaderBoard.add(insertIndex, playerDataAsArr); //readds the player info at the spot it is supposed to be in

            for (int i = 0; i < tempLeaderBoard.size(); i++) { //write the arraylist onto the txt file properly formated
                myWriter.write((i + 1) + " " + tempLeaderBoard.get(i)[1] + " - " + tempLeaderBoard.get(i)[3] + " guesses");
                myWriter.write(System.lineSeparator());
            }

            myWriter.close(); //close the writer
        } catch (Exception e) {
            System.out.println("missing files!");
        }
    }

    /**
     * generateOutcomes method()
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

    public static void generateOutcomes() {
        //loop for all the 1st slot colours
        for (int a = 0; a < COLORS.length; a++) {
            //loop for all the 2nd slot colours
            for (int b = 0; b < COLORS.length; b++) {
                //loop for all the 3rd slot colours
                for (int c = 0; c < COLORS.length; c++) {
                    //loop for all the 4th slot colours
                    for (int d = 0; d < COLORS.length; d++) {
                        //create a temporary combination arraylist to store the combination to add to the total possible combinations
                        ArrayList<Character> combination = new ArrayList<>();
                        combination.add(COLORS[a]); //add from the available colours array
                        combination.add(COLORS[b]);
                        combination.add(COLORS[c]);
                        combination.add(COLORS[d]);
                        possibleCombinations.add(combination); //add the final combination into the possible combinations
                    }
                }
            }
        }
    }

    /**
     * expertComputerGuess()
     *
     * This method is used to generate the next guest and eventually reach the
     * answer. The method cycles through all possible combinations and then calls the
     * getHint method between the guess and the possible combination. Like a detective, it
     * will see if the possibleCombination will produce the exact same hint as the
     * answer. If it doesn't, the combination is no longer possible at all and is removed
     * from the list. The next guess should be any value still remaining in the possible
     * combinations. It also has a save point arraylist that is used to save the possible
     * combinations before trying another guess. This is to be able to backup after a
     * faulty hint was given.
     *
     * @param:
     * int         hints[]
     *
     * @return ArrayList<Character>
     */
    public ArrayList<Character> expertComputerGuess(int hints[]) {

        ArrayList<ArrayList<Character>> SavePointPossibleCombinations = new ArrayList<>();
        SavePointPossibleCombinations.addAll(possibleCombinations);
        // cycle through all possible combinations
        for (int i = 0; i < possibleCombinations.size(); i++) {

            // get the hints between the guess and every possible combination.
            int results[] = getHint(guess, possibleCombinations.get(i));

            // compare if hints generated between the guess-code and the
            // guess-possibleCombination are the same
            // if the posisble combination does not generate the same hints, it cannot
            // possibly be the answer so we remove it from the arraylist of possible combinations
            if ((results[0] != hints[0]) || (results[1] != hints[1])) {
                possibleCombinations.remove(i);
                i--; // when you remove a value from arraylist, all indexes shift back by one so you
                // have to do the same with index
            }
        }
        //if all combinations are reached, the user hints were faulty
        if(possibleCombinations.size()==0){
            //display in the chat log
            leaderboardArea.setText("That is impossible! Try redoing your hints");
            numOfGuesses--; //remove this guess as it does not count
            possibleCombinations.addAll(SavePointPossibleCombinations); //revert back to the savepoint
            return guess; //does not change the original guess
        }else{
            //possibleCombinations.addAll(SavePointPossibleCombinations);
            return possibleCombinations.get(0);
        }
        // set guess as one of the possible combinations, in this case, the first


    }

    /**
     * intermediateComputerGuess()
     *
     * This method is used to generate the next guest and eventually reach the
     * answer. There is a 20% chance of not removing all non-possible combinations
     * like the expert computer guess does. This results in an extra or more wasteful
     * move, taking longer to reach the answer.
     *
     * @param:
     * int hints[]
     *
     * @return ArrayList<Character>
     */
    public ArrayList<Character> intermediateComputerGuess(int hints[]) {
        if (Math.random() > 0.8) { // 20% of not removing all non-possible combinations
            return possibleCombinations.get((int) (Math.random()*possibleCombinations.size()));
        } else {
            return expertComputerGuess(hints);//run the expertComputerGuess and return whatever that returns
        }
    }

    /**
     * beginnerComputerGuess()
     *
     * This method is used to generate the next guest and eventually reach the
     * answer. There is a 70% chance of not removing all non-possible combinations
     * like the expert computer guess does. This results in an extra or more wasteful
     * move, taking longer to reach the answer.
     *
     * @param:
     * int hints[]
     *
     * @return ArrayList<Character>
     */
    public ArrayList<Character> beginnerComputerGuess(int hints[]) {
        if (Math.random() > 0.3) { // 70% of not removing all non-possible combinations
            return possibleCombinations.get((int) (Math.random() * possibleCombinations.size()));
        } else {
            return expertComputerGuess(hints); //run the expertComputerGuess and return whatever that returns
        }
    }
}
