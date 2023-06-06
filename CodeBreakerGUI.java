import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class CodeBreakerGUI extends JFrame implements ActionListener {
    private static ArrayList<ArrayList<Character>> possibleCombinations = new ArrayList<>();
    private static ArrayList<Character> code = new ArrayList<>();
    private static ArrayList<Character> guess = new ArrayList<>();
    private boolean state = true;
    private String playerName = "";
    private int level = 0;
    private static int numOfGuesses = 0;
    HashMap<String, Color> buttonColours = new HashMap<>();
    JPanel ls = new JPanel();// leftside
    JPanel rs = new JPanel();// rightside

    JPanel topRow = new JPanel();
    JPanel resetAndCountPan = new JPanel();// for reset button and guess count

    static String lastCom;
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

    ArrayList<JButton> bw = new ArrayList<>();
    GridLayout mlay = new GridLayout(1, 2); // main layout
    GridLayout left = new GridLayout(2, 1);
    GridLayout topRowLayout = new GridLayout(1, 4);
    GridLayout right = new GridLayout(12, 1);
    GridLayout hlay = new GridLayout(2, 2); // hints lyaout
    GridLayout rcp = new GridLayout(1, 3);// gridlayout for reset and count panel
    GridLayout rightRowsLayout = new GridLayout(1, 6);
    String gameMode = "userGuess";

    public CodeBreakerGUI(String playerName, String selectedGameMode) {
        initUserGuess();
        this.playerName = playerName;
        this.gameMode = selectedGameMode;
        setSize(1200, 1200);
        setTitle("Codebreaker");
        colours.add(rPin);
        colours.add(gPin);
        colours.add(bPin);
        colours.add(yPin);
        colours.add(oPin);
        colours.add(pPin);
        bw.add(whitePin);
        bw.add(blackPin);
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        boxes.add(box4);
        ls.setBorder(new EmptyBorder(10, 10, 10, 30));
        rs.setBorder(new EmptyBorder(10, 30, 10, 30));
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

        desc.setFont(new Font("Arial", Font.PLAIN, 60));
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        fLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        box1.setFont(new Font("Arial", Font.PLAIN, 60));
        box2.setFont(new Font("Arial", Font.PLAIN, 60));
        box3.setFont(new Font("Arial", Font.PLAIN, 60));
        box4.setFont(new Font("Arial", Font.PLAIN, 60));
        blackLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        whiteLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        clear.setFont(new Font("Arial", Font.PLAIN, 40));
        submit.setFont(new Font("Arial", Font.PLAIN, 40));
        guessCount.setFont(new Font("Arial", Font.PLAIN, 60));
        reset.setFont(new Font("Arial", Font.PLAIN, 40));
        instructionsButton.setFont(new Font("Arial", Font.PLAIN, 60));
        crackCode.setFont(new Font("Arial", Font.PLAIN, 60));
        compFindCode.setFont(new Font("Arial", Font.PLAIN, 50));
        leaderboard.setFont(new Font("Arial", Font.PLAIN, 50));
        lbName.setFont(new Font("Arial", Font.PLAIN, 30));
        lbScore.setFont(new Font("Arial", Font.PLAIN, 30));
        compFindCode.addActionListener(this);
        crackCode.addActionListener(this);
        instructionsButton.addActionListener(this);
        reset.setBounds(600, 0, 390, 100);

        clear.addActionListener(this);
        submit.addActionListener(this);
        reset.addActionListener(this);

        setLayout(mlay);

        resetAndCountPan.setLayout(rcp);
        topRow.setLayout(topRowLayout);

        ls.setLayout(left);
        buttonList.setLayout(new GridLayout(4, 1));
        buttonList.add(instructionsButton);
        buttonList.add(crackCode);
        buttonList.add(compFindCode);
        buttonList.add(leaderboard);
        ls.add(buttonList);
        ls.add(leaderboardArea);

        rs.setBackground(new Color(147, 217, 255));
        ls.setBackground(new Color(126, 208, 253));
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
        rs.setLayout(right);
        rs.add(topRow);
        if (gameMode.equals("userGuess"))
            drawUserMode();
        else if (gameMode.equals("compGuess"))
            drawCompMode();
        resetAndCountPan.add(submit);
        resetAndCountPan.add(clear);
        resetAndCountPan.add(reset);
        rs.add(resetAndCountPan);
        add(ls);
        add(rs);
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

    Color oldColour;
    Color oldForeCOlour;

    public void initUserGuess() {
        genCode();
    }

    public void initComputer() {
        generateOutcomes();
        guess.add('G');
        guess.add('G');
        guess.add('R');
        guess.add('R');
        for (int i = 0; i < 4; i++) {
            guessBoxes.get(numOfGuesses).get(i).setBackground(buttonColours.get(String.valueOf(guess.get(i))));
            ;
        }
    }

    public void actionPerformed(ActionEvent event) {
        int[] hints = new int[2];
        String command = event.getActionCommand();
        if (command.equals("Computer find code")) {
            restart();
            rs.removeAll();
            remove(rs);
            revalidate();
            repaint();
            rs = new JPanel();
            rs.setLayout(right);
            drawCompMode();
            rs.add(resetAndCountPan);
            add(rs);
            revalidate();
            repaint();
            initComputer();
            gameMode = "compGuess";
            System.out.println(" compyure gues smdoe");
        }
        if (command.equals("Crack the code")) {
            initUserGuess();
            gameMode = "userGuess";
            rs.removeAll();
            remove(rs);
            rs = new JPanel();
            rs.setLayout(right);
            rs.add(topRow);
            drawUserMode();
            rs.add(resetAndCountPan);
            add(rs);
            revalidate();
            repaint();
            genCode();
            System.out.println("crack the code mode");
        }

        if (command.equals("Instructions")) {
            InstructionsManual instructionsManual = new InstructionsManual();
        }
        if (gameMode.equals("userGuess")) {
            if (colours.contains(event.getSource())) {
                state = !state;
                lastCom = colours.get(colours.indexOf(event.getSource())).getText();
                RoundButton temp = (RoundButton) event.getSource();
                if (temp.getBackground() != new Color(147, 217, 255)) {
                    oldForeCOlour = temp.getForeground();
                    oldColour = temp.getBackground();
                }
                for (JButton jb : colours) {
                    if (jb.getText().equals(command)) {
                        jb.setBackground(new Color(143, 192, 243));
                        jb.setForeground(oldColour);

                    } else {
                        jb.setBackground(buttonColours.get(jb.getText()));
                        jb.setForeground(Color.black);
                        if (jb.getText().equals("B"))
                            jb.setForeground(Color.white);
                    }
                }

            }
            if (!over) {
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
            if (bw.contains(event.getSource())) {
                System.out.println("colour select");
                state = true;
                lastCom = bw.get(bw.indexOf(event.getSource())).getText();
                JButton temp = (JButton) event.getSource();
                oldColour = temp.getBackground();
                oldForeCOlour = temp.getForeground();
            }
            if (!over) {
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
                        System.out.println("select ai mode");
                    } else {
                        hints = hintSelections(); // retrieve hints from user
                        numOfGuesses++;

                        if (level == 1) {
                            guess = beginnerComputerGuess(hints);
                        }
                        if (level == 2) {
                            guess = intermediateComputerGuess(hints);
                        }
                        if (level == 3) {
                            guess = expertComputerGuess(hints);
                        }

                        // update the colour
                        for (int i = 0; i < 4; i++) {
                            guessBoxes.get(numOfGuesses).get(i)
                                    .setBackground(buttonColours.get(String.valueOf(guess.get(i))));
                            ;
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
        genCode();
        generateOutcomes();// regenerate possible combinations
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
            renderFeedback(hints);
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
        for (int i = 0; i < 10; i++) {
            rightRows[i] = new JPanel();
            rightRows[i].setBackground(new Color(143, 192, 243));
            rightRows[i].setLayout(rightRowsLayout);
            hintPanels[i] = new JPanel();
            hintPanels[i].setLayout(hlay);
            guessBoxes.add(new ArrayList<>());
            hintBoxes.add(new ArrayList<>());
            if (i <= 5) {
                RoundButton rb = new RoundButton(colours.get(i).getText());
                rb.setFont(new Font("Arial", Font.PLAIN, 60));
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
                temp.setFont(new Font("Arial", Font.PLAIN, 40));
                temp.setBackground(Color.black);

                guessBoxes.get(i).add(temp);

                rightRows[i].add(guessBoxes.get(i).get(j));
                hintBoxes.get(i).add(new RoundButton(""));
                hintBoxes.get(i).get(j).setBackground(new Color(106, 157, 215));
                hintBoxes.get(i).get(j).setFont(new Font("Arial", Font.PLAIN, 30));
                hintPanels[i].add(hintBoxes.get(i).get(j));

            }
            rightRows[i].add(hintPanels[i]);
            rightRows[i].setBorder(new EmptyBorder(10, 0, 10, 0));
            rs.add(rightRows[i]);
            leaderboardArea.setVisible(true);
            leaderboard.setVisible(true);
        }
    }

    public void drawCompMode() {

        for (int i = 0; i < 10; i++) {
            rightRows[i] = new JPanel();
            rightRows[i].setBackground(new Color(52, 132, 175));
            rightRows[i].setLayout(rightRowsLayout);
            hintPanels[i] = new JPanel();
            hintPanels[i].setLayout(hlay);
            guessBoxes.add(new ArrayList<>());
            hintBoxes.add(new ArrayList<>());
            if (i <= 1) {
                RoundButton rb = new RoundButton(bw.get(i).getText());
                rb.setFont(new Font("Arial", Font.PLAIN, 50));
                rb.addActionListener(this);
                rb.setBackground(bw.get(i).getBackground());
                rb.setForeground(bw.get(i).getForeground());
                bw.set(i, rb);
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
                temp.setFont(new Font("Arial", Font.PLAIN, 40));
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
            rs.add(rightRows[i]);
        }
        String[] comDiffs = { "basic", "intermediate", "expert guesser", "Select AI intelligence level..." };
        JComboBox diffs = new JComboBox(comDiffs);
        diffs.setFont(new Font("Arial", Font.PLAIN, 40));
        diffs.setSelectedIndex(3);
        diffs.addActionListener(this);
        rs.add(diffs);

        leaderboardArea.setVisible(false);
        leaderboard.setVisible(false);
    }

    private void win() {
        JOptionPane.showMessageDialog(null, "Congratulations, You won!", "You WIN", JOptionPane.INFORMATION_MESSAGE);
        writeToLeaderBoard(playerName, numOfGuesses);
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
    public static ArrayList<Character> expertComputerGuess(int hints[]) {
        // cycle through all possible combinations
        for (int i = 0; i < possibleCombinations.size(); i++) {

            // get the hints between the guess and every possible combination.
            int results[] = getHint(guess, possibleCombinations.get(i));

            // compare if hints generated between the guess-code and the
            // guess-possibleCombination are the same
            // if the posisble combination does not generate the same hints, it cannot
            // possibly be
            // the answer so we remove it from the arraylist of possible combinations
            if ((results[0] != hints[0]) || (results[1] != hints[1])) {
                possibleCombinations.remove(i);
                i--; // when you remove a value from arraylist, all indexes shift back by one so you
                     // have to do the same with index
            }
        }
        // set guess as one of the possible combinations, in this case, the first
        return possibleCombinations.get(0);

    }

    public static ArrayList<Character> intermediateComputerGuess(int hints[]) {
        if (Math.random() > 0.9) { // 10% of not removing all non-possible combinations
            return possibleCombinations.get((int) Math.random());
        } else {
            return expertComputerGuess(hints);
        }
    }

    public static ArrayList<Character> beginnerComputerGuess(int hints[]) {
        if (Math.random() > 0.3) { // 70% of not removing all non-possible combinations
            return possibleCombinations.get((int) Math.random());
        } else {
            return expertComputerGuess(hints);
        }
    }
}
