import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UserGUI extends JFrame implements ActionListener{
    static ArrayList<Character> codeList = new ArrayList<>();
    static String code = "";
    static String guess;
    static int[] freqArr = new int[6];
    boolean state = false;
    static int guesses = 0;;


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
    JLabel pinLabel = new JLabel("Colours");
    JLabel fLabel = new JLabel("feedback");
    JLabel desc = new JLabel("CODEBREAKER");

    GridLayout boxLay = new GridLayout(1, 5);
    GridLayout mlay = new GridLayout(7, 1); //main layout
    GridLayout blay = new GridLayout(1, 7); //button layout
    GridLayout hlay = new GridLayout(1, 2); //hints lyaout
    GridLayout rcp = new GridLayout(1, 2);//gridlayout for  reset and count panel

    public UserGUI(){
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
        rPin.setFont(new Font("Arial", Font.PLAIN, 60));
        gPin.setFont(new Font("Arial", Font.PLAIN, 60));
        bPin.setFont(new Font("Arial", Font.PLAIN, 60));
        yPin.setFont(new Font("Arial", Font.PLAIN, 60));
        oPin.setFont(new Font("Arial", Font.PLAIN, 60));
        pPin.setFont(new Font("Arial", Font.PLAIN, 60));
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
        reset.setBounds(600, 0, 390, 100);

        rPin.addActionListener(this);
        gPin.addActionListener(this);
        bPin.addActionListener(this);
        yPin.addActionListener(this);
        oPin.addActionListener(this);
        pPin.addActionListener(this);
        box1.addActionListener(this);
        box2.addActionListener(this);
        box3.addActionListener(this);
        box4.addActionListener(this);
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

        guessButtonPan.add(rPin);
        guessButtonPan.add(gPin);
        guessButtonPan.add(bPin);
        guessButtonPan.add(yPin);
        guessButtonPan.add(oPin);
        guessButtonPan.add(pPin);
        guessButtonPan.add(clear);

        userGuessPan.add(box1);
        userGuessPan.add(box2);
        userGuessPan.add(box3);
        userGuessPan.add(box4);
        userGuessPan.add(submit);

        compHintPan.add(blackLabel);
        compHintPan.add(whiteLabel);

        add(desc);
        add(pinLabel);
        add(resetAndCountPan);
        add(guessButtonPan);
        add(userGuessPan);
        add(fLabel);
        add(compHintPan);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        UserGUI frame = new UserGUI();
        genCode();
        System.out.println(code);
        over = false;

        colourMap.put('G', 0);
        colourMap.put('R', 1);
        colourMap.put('B', 2);
        colourMap.put('Y', 3);
        colourMap.put('O', 4);
        colourMap.put('P', 5);
    }

    public void actionPerformed(ActionEvent event) {
        int[] hints = new int[2];

        String command = event.getActionCommand();
        if(colours.contains(command)){
            state = true;
            lastCom = command;
        }
        if(state && boxes.contains(event.getSource())){
            boxes.get(boxes.indexOf(event.getSource())).setText(lastCom);
        }

        if(command.equals("clear") && !over){
            for(JButton j : boxes){
                j.setText("");
            }
        }
        if(command.equals("submit")&&!over){
            guesses++;
            guessCount.setText("guesses: " + guesses);

            ArrayList<Character> guessList = getGuess();

            hints = getHint(guessList);
            renderFeedback(hints);

            if(guesses==10){
                gameOver();
            }
        }

        if(command.equals("restart")){
            restart();
        }
    }

    private static final char[] COLORS = {'G', 'R', 'B', 'Y', 'O', 'P'};

    public static String genCode(){
        freqArr = new int[6];
        code = "";

        for (int i = 0; i < 4; i++) { //randomly generate the code
            int tempNum = ((int) (Math.random() * 6));
            char tempChar = COLORS[tempNum];
            code += tempChar; //add to code string
            codeList.add(tempChar); //add to arraylist of chars
            freqArr[tempNum]++;
        }

        return code;
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
            if (guess.get(j)== codeList.get(j)) {
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
            if (codeList.contains(c) && guess.get(j)!=codeList.get(j)) {
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
        code = genCode();
        System.out.println(code);
        whiteLabel.setText("");
        blackLabel.setText("");
        for(JButton j : boxes){
            j.setText("");
        }
    }

    public void gameOver(){
        guessCount.setText("guesses: 10");
        blackLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        blackLabel.setText("Game over 10 guesses reached");
        whiteLabel.setText("Code: "+code);
        over = true;
    }

    public ArrayList<Character> getGuess(){
        guess="";
        ArrayList<Character> guessList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            guess+=boxes.get(i).getText();
            guessList.add(boxes.get(i).getText().charAt(0));
        }
        return guessList;
    }

    public void renderFeedback(int[] hints){
        if(guess.equals(code)){
            whiteLabel.setText("You won!");
            blackLabel.setText("");
            over=true;
        }
        else {
            whiteLabel.setText(hints[1] + " white pins");
            blackLabel.setText(hints[0] + " black pins");
        }
    }

}
