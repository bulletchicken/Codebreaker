import javax.swing.*;
import java.awt.*;

//<html>1. A 4 long code is made chosen from 6 colours (Red, Green, Blue, Yellow, Pink, Orange). <br> 2. The other player has to guess the code by also creating a 4 long code from the 6 colours within 10 moves <br> 3. Every guess the player takes, they receive hints in forms of black and white pins. For every colour the guess matches in the correct slot to the answer, they recieve a black pin on the side. For every colour that matches but is in the wrong slot recieves a white pin on the side. </html>
public class InstructionsManual extends JFrame{
    JPanel mainUI = new JPanel();

    JPanel howToPlayPanel = new JPanel();
    JPanel userGuessingPanel = new JPanel();
    JPanel computerGuessingPanel = new JPanel();

    JLabel howToPlay = new JLabel("<html><br>How to Play</html>");
    JLabel howToUserGuessing = new JLabel("<html> <br>Instructions for Player Guessing </html>");
    JLabel howToComputerGuessing = new JLabel("<html> <br> Instructions for Computer Guessing</html>");

    JTextArea howToPlayText = new JTextArea("\n\n1. A 4 long code is made chosen from 6 colours (Red, Green, Blue, Yellow, Pink, Orange). \n\n2. The other player has to guess the code by also creating a 4 long code from the 6 colours within 10 moves \n\n3. Every guess the player takes, they receive hints in forms of black and white pins. For every colour the guess matches in the correct slot to the answer, they recieve a black pin on the side. For every colour that matches but is in the wrong slot recieves a white pin on the side. ");
    JTextArea howToUserGuessingText = new JTextArea("\nWhen you load up the mode, a randomized code is set and you can begin guessing right away. \n\nHow do I guess?\nTo create your guess, you first click the colour you want to place from the selection of colours column. Then click one of the slots highlighted in white to the left to colour that section of the guess. Repeat until you have a full guess of 4 colours. Afterwards, you can press submit to finalize your answer and recieve hints. \n\nHow can I change my guess? \nIf you want to change your guess before you submit, you can reclick your slot with your chosen colour, or erasing your current guess by clicking the clear button.\n\nHow can I restart the game?\nYou can restart the game simply by clicking the restart button!");
    JTextArea howToComputerGuessingText = new JTextArea("\nWhen you load up the mode, you will need to think of and remember a secret code for the computer to guess(we recommend typing out the colours on notepad to help memorize).\n\nRequirements for the code?\nThe code should be four colours long using the set of 6 colours on the bottom.\n\nHow do I give hints?\nAfter the computer has guessed, you have to give it hints in forms of black and white pegs (look at How to Play Codebreaker instructions on how black and white hints work). Beside the guesses, there will be a 2x2 grid. These are where the hints can go. You can select either black or white from the left side of the guesses, then click the hints to place them. If you want to erase a hint, you can click clear in the bottom.");

    public InstructionsManual() {
        //adjusting the main window's settings
        setTitle("Code Breaker - Instruction Manual");
        setSize(1200, 900);
        setResizable(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        

    
        //setting the main grid layout
        mainUI.setLayout(new GridLayout(1, 3, 20, 0));
    
        //creating custom fonts for the titles
        Font titleFont = new Font("Trebuchet MS", Font.BOLD, 30);
        Font textFont = new Font("Trebuchet MS", Font.PLAIN, 20);
    
        //applying the custom fonts to the labels
        howToPlay.setFont(titleFont);
        howToUserGuessing.setFont(titleFont);
        howToComputerGuessing.setFont(titleFont);
    
        howToPlayText.setFont(textFont);
        howToUserGuessingText.setFont(textFont);
        howToComputerGuessingText.setFont(textFont);
    
        howToPlay.setForeground(Color.WHITE);
        howToUserGuessing.setForeground(Color.WHITE);
        howToComputerGuessing.setForeground(Color.WHITE);

        howToComputerGuessingText.setForeground(Color.WHITE);
        howToComputerGuessingText.setWrapStyleWord(rootPaneCheckingEnabled);
    
        // Setting background colors for visualization
        howToPlayPanel.setBackground(Color.GRAY);
        userGuessingPanel.setBackground(Color.DARK_GRAY);
        computerGuessingPanel.setBackground(Color.BLACK);
    
        // Setting layouts for the panels
        howToPlayPanel.setLayout(new BorderLayout());
        userGuessingPanel.setLayout(new BorderLayout());
        computerGuessingPanel.setLayout(new BorderLayout());
    
        // Adding the labels to the panels
        howToPlayPanel.add(howToPlay, BorderLayout.NORTH);
        userGuessingPanel.add(howToUserGuessing, BorderLayout.NORTH);
        computerGuessingPanel.add(howToComputerGuessing, BorderLayout.NORTH);
    
        // Setting font and wrap style for the text areas
        howToPlayText.setMargin(new Insets(0, 20, 0, 20));
        howToPlayText.setFont(textFont);
        howToPlayText.setLineWrap(true);
        howToPlayText.setWrapStyleWord(true);
    
        howToUserGuessingText.setMargin(new Insets(0, 20, 0, 20));
        howToUserGuessingText.setFont(textFont);
        howToUserGuessingText.setLineWrap(true);
        howToUserGuessingText.setWrapStyleWord(true);

        howToComputerGuessingText.setMargin(new Insets(0, 20, 0, 20));
        howToComputerGuessingText.setFont(textFont);
        howToComputerGuessingText.setLineWrap(true);
        howToComputerGuessingText.setWrapStyleWord(true);
    
        howToPlayText.setEditable(false);
        howToUserGuessingText.setEditable(false);
        howToComputerGuessingText.setEditable(false);

        howToPlayText.setBackground(Color.LIGHT_GRAY);
        howToUserGuessingText.setBackground(Color.GRAY);
        howToComputerGuessingText.setBackground(Color.DARK_GRAY);
    
        // Center-align the text within the JTextArea components
        howToPlayText.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        howToPlayText.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        howToUserGuessingText.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        howToUserGuessingText.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        howToComputerGuessingText.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        howToComputerGuessingText.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
    
        // Adding body text to the panels
        howToPlayPanel.add(howToPlayText, BorderLayout.CENTER);
        userGuessingPanel.add(howToUserGuessingText, BorderLayout.CENTER);
        computerGuessingPanel.add(howToComputerGuessingText, BorderLayout.CENTER);
    
        // Adding the panels to the mainUI panel
        mainUI.add(howToPlayPanel);
        mainUI.add(userGuessingPanel);
        mainUI.add(computerGuessingPanel);
    
        add(mainUI);
        setVisible(true);
    }
}
