import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UserGUI extends JFrame implements ActionListener{
    static ArrayList<Character> code = new ArrayList<>();
    static String codeStr = "";
    static String guess;
    static int[] freqArr = new int[6];
    boolean state = false;
    static int guesses = 0;

    JPanel ls = new JPanel();//leftside
    JPanel rs = new JPanel();//rightside

    JPanel topRow = new JPanel();

    JPanel resetAndCountPan = new JPanel();//for reset button and guess count

    static String lastCom;
    JButton umb = new JButton("Crack the code");//user guessing mode
    JButton cfc = new JButton("Computer find code");
    JLabel gs = new JLabel("Game Settings");
    JLabel lb = new JLabel("Leaderboard");
    JLabel lbName = new JLabel("Name");//leaderboard name
    JLabel lbScore = new JLabel("Score");
    JButton rPin = new JButton("R"); //red
    JButton gPin = new JButton("G"); //green
    JButton bPin = new JButton("B"); //blue
    JButton yPin = new JButton("Y"); //yellow
    JButton oPin = new JButton("O"); //orange
    JButton pPin = new JButton("P"); //pink
    JButton clear = new JButton("clear");
    JButton submit = new JButton("submit");
    JButton reset = new JButton("restart");
    ArrayList<JButton> colours = new ArrayList<>();

    ArrayList<JButton> boxes = new ArrayList<>();
    ArrayList<ArrayList<JButton>> guessBoxes = new ArrayList<>();
    JPanel [] hintPanels = new JPanel[10];
    JPanel [] rightRows = new JPanel[10];
    RoundButton [][] hintBoxes = new RoundButton[10][4];
    JButton box1 = new JButton();
    JButton box2 = new JButton();
    JButton box3 = new JButton();
    JButton box4 = new JButton();

    JLabel blackLabel = new JLabel();
    JLabel whiteLabel = new JLabel();
    JLabel guessCount = new JLabel("guesses: " + guesses);
    JLabel pinLabel = new JLabel("Colours");
    JLabel fLabel = new JLabel("feedback");
    JLabel desc = new JLabel("CODEBREAKER");


    GridLayout mlay = new GridLayout(1, 2); //main layout
    GridLayout left = new GridLayout(6, 1);
    GridLayout topRowLayout = new GridLayout(1, 4);
    GridLayout right = new GridLayout(12, 1);
    GridLayout hlay = new GridLayout(2, 2); //hints lyaout
    GridLayout rcp = new GridLayout(1, 3);//gridlayout for  reset and count panel
    GridLayout rightRowsLayout = new GridLayout(1, 6);

    public UserGUI(){
        setSize(1200 ,1200);
        setTitle("Codebreaker");
        colours.add(rPin);
        colours.add(gPin);
        colours.add(bPin);
        colours.add(yPin);
        colours.add(oPin);
        colours.add(pPin);
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
        gs.setFont(new Font("Arial", Font.PLAIN, 60));
        umb.setFont(new Font("Arial", Font.PLAIN, 60));
        cfc.setFont(new Font("Arial", Font.PLAIN, 50));
        lb.setFont(new Font("Arial", Font.PLAIN, 50));
        lbName.setFont(new Font("Arial", Font.PLAIN, 30));
        lbScore.setFont(new Font("Arial", Font.PLAIN, 30));

        reset.setBounds(600, 0, 390, 100);

        clear.addActionListener(this);
        submit.addActionListener(this);
        reset.addActionListener(this);


        setLayout(mlay);

        resetAndCountPan.setLayout(rcp);
        topRow.setLayout(topRowLayout);

        ls.setLayout(left);
        ls.add(gs);
        ls.add(umb);
        ls.add(cfc);
        ls.add(lb);
        rs.setBackground(new Color(147, 217, 255));
        ls.setBackground(new Color(126, 208, 253));
        UIManager.put("OptionPane.minimumSize", new Dimension(500, 500));
        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font(
                "Arial", Font.BOLD, 50)) );
        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font(
                "Arial", Font.PLAIN, 50)));
        win();
        for(JButton j:boxes){
            j.setText("?");
            j.setFocusPainted(false);
            j.setEnabled(false);
            topRow.add(j);
        }
        rs.setLayout(right);
        rs.add(topRow);
        for (int i = 0; i < 10; i++) {
            rightRows[i] = new JPanel();
            rightRows[i].setBackground(new Color(143, 192, 243));
            rightRows[i].setLayout(rightRowsLayout);
            hintPanels[i] = new JPanel();
            hintPanels[i].setLayout(hlay);
            guessBoxes.add(new ArrayList<>());
            if(i<=5){
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
                hintBoxes[i][j] = new RoundButton("");
                hintBoxes[i][j].setBackground(new Color(106, 157, 215));
                hintBoxes[i][j].setFont(new Font("Arial", Font.PLAIN, 30));
                hintPanels[i].add(hintBoxes[i][j]);

            }
            rightRows[i].add(hintPanels[i]);
            rightRows[i].setBorder(new EmptyBorder(10, 0, 10, 0));
            rs.add(rightRows[i]);
        }
        resetAndCountPan.add(submit);
        resetAndCountPan.add(clear);
        resetAndCountPan.add(reset);
        rs.add(resetAndCountPan);
        add(ls);
        add(rs);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        showCurrentRow();
    }

    public static void main(String[] args) {
        UserGUI frame = new UserGUI();
        genCode();
        System.out.println(codeStr);
        over = false;
        colourMap.put('G', 0);
        colourMap.put('R', 1);
        colourMap.put('B', 2);
        colourMap.put('Y', 3);
        colourMap.put('O', 4);
        colourMap.put('P', 5);
    }

    Color oldColour;
    Color oldForeCOlour;
    public void actionPerformed(ActionEvent event) {
        int[] hints = new int[2];
        String command = event.getActionCommand();
        if(colours.contains(event.getSource())){
            state = true;
            lastCom = colours.get(colours.indexOf(event.getSource())).getText();
            JButton temp = (JButton) event.getSource();
            oldColour = temp.getBackground();
            oldForeCOlour = temp.getForeground();
        }
        if(!over) {
            int index = guessBoxes.get(guesses).indexOf(event.getSource());

            if (state && index != -1) {
                JButton temp = guessBoxes.get(guesses).get(index);
                temp.setText(lastCom);
                temp.setBackground(oldColour);
                temp.setForeground(oldForeCOlour);
            }

            if (command.equals("clear")) {
                for (JButton j : guessBoxes.get(guesses)) {
                    j.setText("");
                    j.setBackground(Color.white);
                    j.setForeground(Color.black);
                }
            }
            if (command.equals("submit")) {
                ArrayList<Character> guessList = new ArrayList<>();
                boolean valid = false;
                try {
                    guessList = getGuess();
                    valid = true;
                } catch (StringIndexOutOfBoundsException error) {
                    whiteLabel.setText("Please enter valid guess");
                    valid = false;
                }
                if (valid) {

                    guessCount.setText("guesses: " + guesses);


                    hints = getHint(guessList);
                    renderFeedback(hints);
                    guesses++;
                    if (!over) {
                        showCurrentRow();
                    }
                    if (guesses == 10) {
                        gameOver();
                        loss();
                    }
                }
            }
        }
        if(command.equals("restart")){
            restart();
        }
    }

    private static final char[] COLORS = {'G', 'R', 'B', 'Y', 'O', 'P'};

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

    public void restart(){
        guesses=0;
        over=false;
        guessCount.setText("guesses: 0");
        codeStr = genCode();
        System.out.println(codeStr);
        whiteLabel.setText("");
        blackLabel.setText("");
        for(JButton j : boxes){
            j.setText("?");
        }
        for(ArrayList<JButton> j : guessBoxes){
            for(JButton jb : j){
                jb.setText("");
                jb.setBackground(Color.black);
            }
        }
        for(JButton[] j:hintBoxes){
            for(JButton jl:j){
                jl.setText("");
            }
        }
        blackLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        showCurrentRow();
    }

    public void gameOver(){
        for (int i = 0; i < 4; i++) {
            boxes.get(i).setText(code.get(i).toString());
            boxes.get(i).setEnabled(true);
            switch (code.get(i).toString()){
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

    public ArrayList<Character> getGuess(){
        guess="";
        ArrayList<Character> guessList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            guess+=guessBoxes.get(guesses).get(i).getText();
            guessList.add(guessBoxes.get(guesses).get(i).getText().charAt(0));
        }
        return guessList;
    }
    public void renderFeedback(int[] hints){
        if(guess.equals(codeStr)){
            gameOver();
            win();
            over=true;
        }
        else {
            System.out.println("black: " + hints[0] + " whites: " + hints[1]);
            for (int i = 0; i < hints[0]; i++) {
                hintBoxes[guesses][i].setBackground(Color.black);
            }
            for (int i = hints[0]; i < hints[1]+hints[0]; i++) {
                if(hints[0]<4){
                    hintBoxes[guesses][i].setBackground(Color.white);
                }
            }
        }
    }
    public void showCurrentRow(){
        for (int i = 0; i < 4; i++) {
            if(guesses<10) {
                guessBoxes.get(guesses).get(i).setBackground(Color.white);
            }
        }
    }


    private void win(){
        JOptionPane.showMessageDialog(null, "Congratulations, You won!", "You WIN", JOptionPane.INFORMATION_MESSAGE);

    }
    private void loss(){
        JOptionPane.showMessageDialog(null, "Unfortunately, you lost.","Defear", JOptionPane.INFORMATION_MESSAGE);
    }

}
