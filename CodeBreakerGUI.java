import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class CodeBreakerGUI extends JFrame implements ActionListener {
    Color lightBlue = new Color(143, 192, 243);
    private static ArrayList<ArrayList<Character>> possibleCombinations = new ArrayList<>();
    private static ArrayList<Character> code = new ArrayList<>();
    private static ArrayList<Character> guess = new ArrayList<>();
    private boolean state = false;
    private String playerName = "";
    private int level = 0;
    private static int numOfGuesses = 0;
    HashMap<String, Color> buttonColours = new HashMap<>();
    JPanel leftSide = new JPanel();// leftside
    JPanel rightSide = new JPanel();// rightside

    JPanel topRow = new JPanel();
    JPanel resetAndCountPan = new JPanel();// for reset button and guess count

    static String lastCom;
    
    String[]computerPhrases = {"This is a hard code...", "Oh this is easy!", "I can do it!, it has to have Red right?", "One step closer to cracking this code!"};
    JButton crackCode = new JButton("Crack the code");// user guessing mode
    JButton compFindCode = new JButton("Computer find code");
    JButton instructionsButton = new JButton("Instructions");
    JLabel leaderboard = new JLabel("Leaderboard");
    JLabel lbName = new JLabel("Name");// leaderboard name
    JLabel lbScore = new JLabel("Score");
    RoundButton blackPin = new RoundButton("B"); // black
    RoundButton whitePin = new RoundButton("W"); // white
    JButton rPin = new JButton("R"); // red
    JButton gPin = new JButton("G"); // green
    JButton bPin = new JButton("B"); // blue
    JButton yPin = new JButton("Y"); // yellow
    JButton oPin = new JButton("O"); // orange
    JButton pPin = new JButton("P"); // pink
    JButton clear = new JButton("clear");
    JButton submit = new JButton("submit");
    JButton reset = new JButton("restart");
    ArrayList<JButton> colours = new ArrayList<>();

    ArrayList<JButton> boxes = new ArrayList<>();

    ArrayList<ArrayList<JButton>> guessBoxes = new ArrayList<>();
    JPanel[] hintPanels = new JPanel[10];
    JPanel[] rightRows = new JPanel[10];
    ArrayList<ArrayList<RoundButton>> hintBoxes = new ArrayList<>();
    JButton box1 = new JButton();
    JButton box2 = new JButton();
    JButton box3 = new JButton();
    JButton box4 = new JButton();

    JLabel blackLabel = new JLabel();
    JLabel whiteLabel = new JLabel();
    JLabel guessCount = new JLabel("guesses: " + numOfGuesses);
    JLabel pinLabel = new JLabel("Colours");
    JLabel fLabel = new JLabel("feedback");
    JLabel desc = new JLabel("CODEBREAKER");

    JTextArea leaderboardArea = new JTextArea();
    JPanel buttonList = new JPanel();

    ArrayList<JButton> hintPegs = new ArrayList<>();
    GridLayout mainLayout = new GridLayout(1, 2); // main layout
    GridLayout leftLayout = new GridLayout(2, 1);
    GridLayout topRowLayout = new GridLayout(1, 4);
    GridLayout right = new GridLayout(12, 1);
    GridLayout hintLayout = new GridLayout(2, 2); // hints lyaout
    GridLayout controlLayout = new GridLayout(1, 3);// gridlayout for reset and count panel
    GridLayout rightRowsLayout = new GridLayout(1, 6);
    String gameMode = "userGuess";

    public CodeBreakerGUI(String playerName, String selectedGameMode) {
        initUserGuess();
        this.playerName = playerName;
        this.gameMode = selectedGameMode;
        setSize(800, 950);
        setTitle("Codebreaker");
        colours.add(rPin);
        colours.add(gPin);
        colours.add(bPin);
        colours.add(yPin);
        colours.add(oPin);
        colours.add(pPin);
        hintPegs.add(whitePin);
        hintPegs.add(blackPin);
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        boxes.add(box4);
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

        leaderboardArea.setEditable(false);
        leaderboardArea.setFont(new Font("Courier", Font.BOLD, 20));
        leaderboardArea.setWrapStyleWord(rootPaneCheckingEnabled);
        leaderboardArea.setLineWrap(true);
        leaderboardArea.setWrapStyleWord(true);
        leaderboard.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        leaderboard.setAlignmentY(JTextArea.CENTER_ALIGNMENT);

        desc.setFont(new Font("Arial", Font.PLAIN, 30));
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        fLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        box1.setFont(new Font("Arial", Font.PLAIN, 30));
        box2.setFont(new Font("Arial", Font.PLAIN, 30));
        box3.setFont(new Font("Arial", Font.PLAIN, 30));
        box4.setFont(new Font("Arial", Font.PLAIN, 30));
        blackLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        whiteLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        clear.setFont(new Font("Arial", Font.PLAIN, 20));
        submit.setFont(new Font("Arial", Font.PLAIN, 20));
        guessCount.setFont(new Font("Arial", Font.PLAIN, 30));
        reset.setFont(new Font("Arial", Font.PLAIN, 20));
        instructionsButton.setFont(new Font("Arial", Font.PLAIN, 30));
        crackCode.setFont(new Font("Arial", Font.PLAIN, 30));
        compFindCode.setFont(new Font("Arial", Font.PLAIN, 25));
        leaderboard.setFont(new Font("Arial", Font.PLAIN, 25));
        lbName.setFont(new Font("Arial", Font.PLAIN, 30));
        lbScore.setFont(new Font("Arial", Font.PLAIN, 30));
        compFindCode.addActionListener(this);
        crackCode.addActionListener(this);
        instructionsButton.addActionListener(this);
        reset.setBounds(600, 0, 390, 100);

        clear.addActionListener(this);
        submit.addActionListener(this);
        reset.addActionListener(this);

        setLayout(mainLayout);

        resetAndCountPan.setLayout(controlLayout);
        topRow.setLayout(topRowLayout);

        leftSide.setLayout(leftLayout);
        buttonList.setLayout(new GridLayout(4, 1));
        buttonList.add(instructionsButton);
        buttonList.add(crackCode);
        buttonList.add(compFindCode);
        buttonList.add(leaderboard);
        leftSide.add(buttonList);
        leftSide.add(leaderboardArea);

        rightSide.setBackground(lightBlue);
        leftSide.setBackground(new Color(126, 208, 253));
        UIManager.put("OptionPane.minimumSize", new Dimension(500, 500));
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(
                "Arial", Font.BOLD, 50)));
        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font(
                "Arial", Font.PLAIN, 50)));
        for (JButton j : boxes) {
            j.setText("?");
            j.setFocusPainted(false);
            j.setEnabled(false);
            topRow.add(j);
        }
        rightSide.setLayout(right);
        rightSide.add(topRow);
        if (gameMode.equals("userGuess")) {

            drawUserMode();
        }
        else if (gameMode.equals("compGuess")) {

            drawCompMode();
        }
        resetAndCountPan.add(submit);
        resetAndCountPan.add(clear);
        resetAndCountPan.add(reset);
        rightSide.add(resetAndCountPan);
        add(leftSide);
        add(rightSide);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        showCurrentRow();
        buttonColours.put("R", rPin.getBackground());
        buttonColours.put("G", gPin.getBackground());
        buttonColours.put("B", bPin.getBackground());
        buttonColours.put("Y", yPin.getBackground());
        buttonColours.put("O", oPin.getBackground());
        buttonColours.put("P", pPin.getBackground());

        displayLeaderBoard();

    }

    Color oldColour = Color.WHITE;
    Color oldForeCOlour = Color.BLACK;

    public void initUserGuess() {
        guess.clear();

        genCode();

        for(char c: guess){
            System.out.println(c);
        }

        leaderboardArea.setText("");
        displayLeaderBoard();

    }

    public void initComputer() {
        generateOutcomes();
        guess.add('G');
        guess.add('G');
        guess.add('G');
        guess.add('G');
        for (int i = 0; i < 4; i++) {
            guessBoxes.get(numOfGuesses).get(i).setBackground(buttonColours.get(String.valueOf(guess.get(i))));
            ;
        }
        leaderboardArea.setText("I am Mr. B, a codebreaker AI! Start the game by writing down your code on a piece of paper and providing me the hints by clicking and placing the black and white hints to the left! I already have my first guess for you to start it off!");
    }

    public void actionPerformed(ActionEvent event) {
        int[] hints = new int[2];
        String command = event.getActionCommand();
        if (command.equals("Computer find code")) {
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
            initComputer();
            gameMode = "compGuess";
            System.out.println(" compyure gues smdoe");
        }
        if (command.equals("Crack the code")) {
            initUserGuess();
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
            for (JButton jb : guessBoxes.get(0)) {
                jb.setBackground(Color.white);

            }
            genCode();
            System.out.println("crack the code mode");
        }

        if (command.equals("Instructions")) {
            InstructionsManual instructionsManual = new InstructionsManual();
        }
        if (gameMode.equals("userGuess")) {
            if (colours.contains(event.getSource())) {
                state = true;
                lastCom = colours.get(colours.indexOf(event.getSource())).getText();
                RoundButton temp = (RoundButton) event.getSource();
                if (temp.getBackground() !=lightBlue) {
                    oldForeCOlour = temp.getForeground();
                    oldColour = temp.getBackground();
                }
                for (JButton jb : colours) {
                    if (jb.getText().equals(command)) {
                        jb.setBackground(lightBlue);
                        jb.setForeground(oldColour);

                    } else {
                        jb.setBackground(buttonColours.get(jb.getText()));
                        jb.setForeground(Color.black);
                        if (jb.getText().equals("B"))
                            jb.setForeground(Color.white);
                    }
                }

            }
            if (!over && state) {
                int index = guessBoxes.get(numOfGuesses).indexOf(event.getSource());

                if (index != -1) {
                    JButton temp = guessBoxes.get(numOfGuesses).get(index);

                    temp.setText(lastCom);
                    temp.setBackground(oldColour);
                    temp.setForeground(oldForeCOlour);
                }

                if (command.equals("clear")) {
                    for (JButton j : guessBoxes.get(numOfGuesses)) {
                        j.setText("");
                        j.setBackground(Color.white);
                        j.setForeground(Color.black);
                    }
                }
                if (command.equals("submit")) {
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
                        valid = true;
                    } catch (StringIndexOutOfBoundsException error) {
                        whiteLabel.setText("Please enter valid guess");
                        valid = false;
                    }
                    if (valid) {

                        guessCount.setText("guesses: " + numOfGuesses);

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
            if (command.equals("restart")) {
                restart();
            }
        } else if (gameMode.equals("compGuess")) {

            if (command.equals("comboBoxChanged")) {
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
                System.out.println("Ai level: " + level);
            }
            if (hintPegs.contains(event.getSource())) {
                System.out.println("colour select");
                state = true;
                lastCom = hintPegs.get(hintPegs.indexOf(event.getSource())).getText();
                JButton temp = (JButton) event.getSource();
                oldColour = temp.getBackground();
                oldForeCOlour = temp.getForeground();
            }
            if (!over && state) {
                int index = hintBoxes.get(numOfGuesses).indexOf(event.getSource());

                if (state && index != -1) {
                    JButton temp = hintBoxes.get(numOfGuesses).get(index);
                    temp.setText(lastCom);
                    temp.setBackground(oldColour);
                    temp.setForeground(oldForeCOlour);
                }

                if (command.equals("clear")) {
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
                                guessBoxes.get(numOfGuesses).get(i)
                                        .setBackground(buttonColours.get(String.valueOf(guess.get(i))));
                                System.out.println("update");
                                ;
                            }
                            if(hints[0]==4){
                                over = true;
                                compWin();

                            }
                        }

                    }

                }
            }
            if (command.equals("restart")) {
                restart();
            }
        }

    }

    public static void displayColours(ArrayList<Character> compsGuess) {

    }

    private static final char[] COLORS = { 'G', 'R', 'B', 'Y', 'O', 'P' };

    public static void genCode() {
        code = new ArrayList<>();
        for (int i = 0; i < 4; i++) { // randomly generate the codeStr
            int tempNum = ((int) (Math.random() * 6));
            char selectedColour = COLORS[tempNum];
            code.add(selectedColour); // add to arraylist of chars
        }
    }

    static boolean over = false;

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

    public void restart() {
        for (JButton jb : colours) {

            jb.setBackground(buttonColours.get(jb.getText()));
            jb.setForeground(Color.black);
            if (jb.getText().equals("B")) jb.setForeground(Color.white);

        }
        for(JButton jb : boxes){
            jb.setBackground(null);
            jb.setEnabled(false);
        }
        genCode();
        generateOutcomes();// regenerate possible combinations
        leaderboardArea.removeAll(); //clears the leaderboard
        numOfGuesses = 0;
        over = false;
        guessCount.setText("guesses: 0");
        whiteLabel.setText("");
        blackLabel.setText("");
        for (JButton j : boxes) {
            j.setText("?");
        }
        for (ArrayList<JButton> j : guessBoxes) {
            for (JButton jb : j) {
                jb.setText("");
                jb.setBackground(Color.black);
            }
        }
        for (ArrayList<RoundButton> j : hintBoxes) {
            for (JButton jl : j) {
                jl.setText("");
                jl.setBackground(new Color(106, 157, 215));
            }
        }
        blackLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        showCurrentRow();
    }

    public void gameOver() {
        for (int i = 0; i < 4; i++) {
            boxes.get(i).setText(code.get(i).toString());
            boxes.get(i).setEnabled(true);
            switch (code.get(i).toString()) {
                case "R":
                    boxes.get(i).setBackground(new Color(253, 104, 104));
                    break;
                case "G":
                    boxes.get(i).setBackground(new Color(154, 246, 142, 240));
                    break;
                case "B":
                    boxes.get(i).setBackground(new Color(85, 171, 255));
                    boxes.get(i).setForeground(Color.white);
                    break;
                case "Y":
                    boxes.get(i).setBackground(new Color(248, 233, 159));
                    break;
                case "O":
                    boxes.get(i).setBackground(new Color(243, 162, 102));
                    break;
                case "P":
                    boxes.get(i).setBackground(new Color(255, 174, 228));
                    break;
            }
        }
        over = true;
    }

    public ArrayList<Character> getGuess() {
        guess = new ArrayList<>();
        ArrayList<Character> guessList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            guessList.add(guessBoxes.get(numOfGuesses).get(i).getText().charAt(0));
        }
        return guessList;
    }

    public void renderFeedback(int[] hints) {
        if (hints[0] == 4) {
            gameOver();
            win();
            over = true;
        } else {
            System.out.println("black: " + hints[0] + " whites: " + hints[1]);
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

    public void showCurrentRow() {
        for (int i = 0; i < 4; i++) {
            if (numOfGuesses < 10) {
                guessBoxes.get(numOfGuesses).get(i).setBackground(Color.white);
            }
        }
    }

    public void drawUserMode() {
        right = new GridLayout(12, 1);
        rightSide.setLayout(right);
        for (int i = 0; i < 10; i++) {
            rightRows[i] = new JPanel();
            rightRows[i].setBackground(new Color(143, 192, 243));
            rightRows[i].setLayout(rightRowsLayout);
            hintPanels[i] = new JPanel();
            hintPanels[i].setLayout(hintLayout);
            guessBoxes.add(new ArrayList<>());
            hintBoxes.add(new ArrayList<>());
            if (i <= 5) {
                RoundButton rb = new RoundButton(colours.get(i).getText());
                rb.setFont(new Font("Arial", Font.PLAIN, 20));
                rb.addActionListener(this);
                rb.setBackground(colours.get(i).getBackground());
                rb.setForeground(colours.get(i).getForeground());
                colours.set(i, rb);
                rightRows[i].add(rb);
            } else {
                JButton tempButton = new JButton();
                rightRows[i].add(tempButton);
                tempButton.setVisible(false);
            }
            hintPanels[i].setBackground(new Color(169, 255, 248));

            for (int j = 0; j < 4; j++) {
                RoundButton temp = new RoundButton("");
                temp.setFocusPainted(false);
                temp.addActionListener(this);
                temp.setFont(new Font("Arial", Font.PLAIN, 20));
                temp.setBackground(Color.black);

                guessBoxes.get(i).add(temp);

                rightRows[i].add(guessBoxes.get(i).get(j));
                hintBoxes.get(i).add(new RoundButton(""));
                hintBoxes.get(i).get(j).setBackground(new Color(106, 157, 215));
                hintBoxes.get(i).get(j).setFont(new Font("Arial", Font.PLAIN, 15));
                hintPanels[i].add(hintBoxes.get(i).get(j));

            }
            rightRows[i].add(hintPanels[i]);
            rightRows[i].setBorder(new EmptyBorder(10, 0, 10, 0));

            rightSide.add(rightRows[i]);
            leaderboardArea.setVisible(true);
            leaderboard.setVisible(true);
            setSize(new Dimension(800, 950));

            leaderboard.setText("Leaderboard");
            leaderboardArea.removeAll();

        }
    }

    public void drawCompMode() {
        right = new GridLayout(14, 1);
        rightSide.setLayout(right);
        setSize(new Dimension(800, 1000));
        for (int i = 0; i < 10; i++) {
            rightRows[i] = new JPanel();
            rightRows[i].setBackground(new Color(52, 132, 175));
            rightRows[i].setLayout(rightRowsLayout);
            hintPanels[i] = new JPanel();
            hintPanels[i].setLayout(hintLayout);
            guessBoxes.add(new ArrayList<>());
            hintBoxes.add(new ArrayList<>());
            if (i <= 1) {
                RoundButton rb = new RoundButton(hintPegs.get(i).getText());
                rb.setFont(new Font("Arial", Font.PLAIN, 15));
                rb.addActionListener(this);
                rb.setBackground(hintPegs.get(i).getBackground());
                rb.setForeground(hintPegs.get(i).getForeground());
                hintPegs.set(i, rb);
                rightRows[i].add(rb);
            } else {
                JButton tempButton = new JButton();
                rightRows[i].add(tempButton);
                tempButton.setVisible(false);
            }
            hintPanels[i].setBackground(new Color(169, 255, 248));

            for (int j = 0; j < 4; j++) {
                RoundButton temp = new RoundButton("");
                temp.setFocusPainted(false);
                temp.setFont(new Font("Arial", Font.PLAIN, 20));
                temp.setBackground(new Color(26, 47, 131));

                guessBoxes.get(i).add(temp);

                rightRows[i].add(guessBoxes.get(i).get(j));

                hintBoxes.get(i).add(new RoundButton(" "));
                hintBoxes.get(i).get(j).setBackground(new Color(26, 75, 131));
                hintBoxes.get(i).get(j).setFont(new Font("Arial", Font.PLAIN, 15));
                hintBoxes.get(i).get(j).addActionListener(this);
                hintPanels[i].add(hintBoxes.get(i).get(j));

            }
            rightRows[i].add(hintPanels[i]);
            rightRows[i].setBorder(new EmptyBorder(10, 0, 10, 0));
            rightSide.add(rightRows[i]);

        }
        String[] comDiffs = { "basic", "intermediate", "expert guesser", "Select AI intelligence level..." };
        JComboBox diffs = new JComboBox(comDiffs);
        diffs.setFont(new Font("Arial", Font.PLAIN, 20));
        diffs.setSelectedIndex(3);
        diffs.addActionListener(this);

        rightSide.add(diffs);
        JLabel availColours = new JLabel("Possible colours:");
        availColours.setFont(new Font("Arial", Font.PLAIN, 30));
        JPanel acp = new JPanel();
        GridLayout clay = new GridLayout(1, 6);
        acp.setLayout(clay);
        for(JButton j : colours){
            acp.add(j);
        }
        rightSide.add(availColours);
        rightSide.add(acp);
        
        leaderboard.setText("Computer Chat");
        leaderboardArea.removeAll();
        leaderboardArea.append("I am guessing");

    }

    private void win() {
        JOptionPane.showMessageDialog(null, "Congratulations, You won!", "You WIN", JOptionPane.INFORMATION_MESSAGE);
        writeToLeaderBoard(playerName, numOfGuesses);
    }

    private void compWin(){
        String s = "";
        for(char c : guess){
            s+=c;
        }
        JOptionPane.showMessageDialog(null, "The code is: " + s, "Your Code is:", JOptionPane.INFORMATION_MESSAGE);

    }

    private void loss() {
        JOptionPane.showMessageDialog(null, "Unfortunately, you lost.", "Defear", JOptionPane.INFORMATION_MESSAGE);
    }

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

    public void displayLeaderBoard() {
        try {
            File myObj = new File("leaderboard.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                leaderboardArea.append(data + "\n");
            }
            myReader.close();
        } catch (Exception e) {
        }
    }

    public void writeToLeaderBoard(String playerName, int numOfGuesses) {

        try {
            ArrayList<String[]> tempLeaderBoard = new ArrayList<>();
            File myFile = new File("leaderboard.txt");
            Scanner myReader = new Scanner(myFile);
            //

            playerName = playerName.replaceAll(" ", "");

            int insertIndex = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] playerData = data.split(" ");
                if (Integer.parseInt(playerData[3]) < numOfGuesses) {
                    insertIndex++;
                }
                tempLeaderBoard.add(playerData);
            }
            myReader.close();
            FileWriter myWriter = new FileWriter(myFile);

            String[] playerDataAsArr = new String[5];
            playerDataAsArr[1] = playerName;
            playerDataAsArr[3] = String.valueOf(numOfGuesses);
            System.out.println(insertIndex);
            tempLeaderBoard.add(insertIndex, playerDataAsArr);

            for (int i = 0; i < tempLeaderBoard.size(); i++) {
                myWriter.write(
                        (i + 1) + " " + tempLeaderBoard.get(i)[1] + " - " + tempLeaderBoard.get(i)[3] + " guesses");
                myWriter.write(System.lineSeparator());
            }

            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * computerGuess method()
     * 
     * This method is used to generate the next guest and eventually reach the
     * answer.
     * The method cycles through all possible combinations and then calls the
     * getHint
     * method between the guess and the possible combination. Like a detective, it
     * will
     * see if the possibleCombination will produce the exact same hint as the
     * answer.
     * If it doesn't, the combination is no longer possible at all and is removed
     * from
     * the list. The next guess should be any value still remaining in the possible
     * combinations.
     * 
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
        if(possibleCombinations.size()==0){
        	System.out.println("That is impossible!");
        	leaderboardArea.setText("That is impossible! Try redoing your hints");
        	numOfGuesses--;
        	possibleCombinations.addAll(SavePointPossibleCombinations);
        	 return possibleCombinations.get(0);
        }else{
        	//possibleCombinations.addAll(SavePointPossibleCombinations);
        	 return possibleCombinations.get(0);
        }
        // set guess as one of the possible combinations, in this case, the first
       

    }

    public ArrayList<Character> intermediateComputerGuess(int hints[]) {
        if (Math.random() > 0.9) { // 10% of not removing all non-possible combinations
            return possibleCombinations.get((int) (Math.random()*possibleCombinations.size()));
        } else {
            return expertComputerGuess(hints);
        }
    }

    public ArrayList<Character> beginnerComputerGuess(int hints[]) {
        if (Math.random() > 0.3) { // 70% of not removing all non-possible combinations
            return possibleCombinations.get((int) (Math.random() * possibleCombinations.size()));
        } else {
            return expertComputerGuess(hints);
        }
    }
}
