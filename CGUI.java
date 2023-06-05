import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class CGUI extends JFrame implements ActionListener{
    static ArrayList<Character> code = new ArrayList<>();
    static String codeStr = "";
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
    JButton blackPin = new JButton("B"); //black
    JButton whitePin = new JButton("W"); //white

    JButton clear = new JButton("clear");
    JButton submit = new JButton("submit");
    JButton reset = new JButton("restart");
    ArrayList<JButton> colours = new ArrayList<>();

    ArrayList<JButton> boxes = new ArrayList<>();
    ArrayList<ArrayList<JButton>> guessBoxes = new ArrayList<>();
    JPanel [] hintPanels = new JPanel[10];
    JPanel [] rightRows = new JPanel[10];
    ArrayList<ArrayList<RoundButton>> hintBoxes = new ArrayList<>();
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
    GridLayout hlay = new GridLayout(2, 2); //computer guess layout
    GridLayout rcp = new GridLayout(1, 3);//gridlayout for  reset and count panel
    GridLayout rightRowsLayout = new GridLayout(1, 6);

    public CGUI(){
        setSize(1400 ,1400);
        setTitle("Codebreaker");
        colours.add(blackPin);
        colours.add(whitePin);
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        boxes.add(box4);
        ls.setBorder(new EmptyBorder(10, 10, 10, 30));
        rs.setBorder(new EmptyBorder(10, 30, 10, 30));
        blackPin.setBackground(Color.black);
        whitePin.setBackground(Color.white);
        blackPin.setForeground(Color.white);


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
        cfc.setFont(new Font("Arial", Font.PLAIN, 60));
        lb.setFont(new Font("Arial", Font.PLAIN, 60));
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
        rs.setBackground(new Color(95, 194, 246));
        ls.setBackground(new Color(123, 198, 239));

        rs.setLayout(right);
        for (int i = 0; i < 10; i++) {
            rightRows[i] = new JPanel();
            rightRows[i].setBackground(new Color(52, 132, 175));
            rightRows[i].setLayout(rightRowsLayout);
            hintPanels[i] = new JPanel();
            hintPanels[i].setLayout(hlay);
            guessBoxes.add(new ArrayList<>());
            hintBoxes.add(new ArrayList<>());
            if(i<=1){
                RoundButton rb = new RoundButton(colours.get(i).getText());
                rb.setFont(new Font("Arial", Font.PLAIN, 50));
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
                temp.setFont(new Font("Arial", Font.PLAIN, 40));
                temp.setBackground(new Color(26, 47, 131));

                guessBoxes.get(i).add(temp);

                rightRows[i].add(guessBoxes.get(i).get(j));

                hintBoxes.get(i).add(new RoundButton(" "));
                hintBoxes.get(i).get(j).setBackground(new Color(26, 75, 131));
                hintBoxes.get(i).get(j).setFont(new Font("Arial", Font.PLAIN, 18));
                hintBoxes.get(i).get(j).addActionListener(this);
                hintPanels[i].add(hintBoxes.get(i).get(j));

            }
            rightRows[i].add(hintPanels[i]);
            rightRows[i].setBorder(new EmptyBorder(10, 0, 10, 0));
            rs.add(rightRows[i]);
        }
        resetAndCountPan.add(submit);
        resetAndCountPan.add(clear);
        resetAndCountPan.add(reset);
        rs.add(resetAndCountPan);
        String[] comDiffs = {"basic", "intermediate", "expert guesser", "Select AI intelligence level..."};
        JComboBox diffs = new JComboBox(comDiffs);
        diffs.setFont(new Font("Arial", Font.PLAIN, 40));
        diffs.setSelectedIndex(3);
        diffs.addActionListener(this);
        rs.add(diffs);
        add(ls);
        add(rs);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        CGUI frame = new CGUI();
        System.out.println(codeStr);
        over = false;
    }

    Color oldColour;
    Color oldForeCOlour;
    public void actionPerformed(ActionEvent event) {
        int[] hints = new int[2];
        String command = event.getActionCommand();
        if(command.equals("comboBoxChanged")){
            int level = 0;
            JComboBox cb = (JComboBox) event.getSource();
            switch ((String)cb.getSelectedItem()){
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
        if(colours.contains(event.getSource())){
            state = true;
            lastCom = colours.get(colours.indexOf(event.getSource())).getText();
            JButton temp = (JButton) event.getSource();
            oldColour = temp.getBackground();
            oldForeCOlour = temp.getForeground();
        }
        if(!over) {
            int index = hintBoxes.get(guesses).indexOf(event.getSource());

            if (state && index != -1) {
                JButton temp = hintBoxes.get(guesses).get(index);
                temp.setText(lastCom);
                temp.setBackground(oldColour);
                temp.setForeground(oldForeCOlour);
            }

            if (command.equals("clear")) {
                for (JButton j : hintBoxes.get(guesses)) {
                    j.setText("");
                    j.setBackground(new Color(26, 75, 131));
                    j.setForeground(Color.black);
                }
            }
            if (command.equals("submit")) {
                hints = getHint();
                guesses++;
                for(int i: hints)
                    System.out.println(i);
            }
        }
        if(command.equals("restart")){
            restart();
        }
    }

    private static final char[] COLORS = {'G', 'R', 'B', 'Y', 'O', 'P'};

    static boolean over = false;
    static HashMap<Character, Integer> colourMap = new HashMap<>();


    public void restart(){
        guesses=0;
        over=false;
        guessCount.setText("guesses: 0");
        System.out.println(codeStr);
        whiteLabel.setText("");
        blackLabel.setText("");
        for(JButton j : boxes){
            j.setText("?");
        }
        for(ArrayList<JButton> j : guessBoxes){
            for(JButton jb : j){
                jb.setText("");
                jb.setBackground(new Color(26, 47, 131));
            }
        }
        for(ArrayList<RoundButton> j:hintBoxes){
            for(JButton jl:j){
                jl.setText("");
                jl.setBackground(new Color(26, 75, 131));
            }
        }
        blackLabel.setFont(new Font("Arial", Font.PLAIN, 60));

    }

    public void gameOver(){
        for (int i = 0; i < 4; i++) {
            boxes.get(i).setText(code.get(i).toString());
            boxes.get(i).setEnabled(true);
        }
        over = true;
    }

    public int[] getHint(){
        int[] hints = new int[2];
        for(JButton j : hintBoxes.get(guesses)){
            if(j.getText().equals("B")){
                hints[0]++;
            } else if(j.getText().equals("W")){
                hints[1]++;
            }
        }
        return hints;
    }
}
