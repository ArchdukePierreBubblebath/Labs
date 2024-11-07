import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CountdownTimer extends JFrame {
    private JButton startButton;
    private JButton cancelButton;
    private JTextField textField;
    private JLabel aminoAcidLabel;
    private JLabel timerLabel;
    private JLabel score;
    private int count = 30;
    private int randomIndex;
    private int numRight = 0;
    private int numWrong = 0;

    String[] Short_names = 
        { "A","R", "N", "D", "C", "Q", "E", 
        "G",  "H", "I", "L", "K", "M", "F", 
        "P", "S", "T", "W", "Y", "V" };
    
    String[] FULL_NAMES = 
        {
        "alanine","arginine", "asparagine", 
        "aspartic acid", "cysteine",
        "glutamine",  "glutamic acid",
        "glycine" ,"histidine","isoleucine",
        "leucine",  "lysine", "methionine", 
        "phenylalanine", "proline", 
        "serine","threonine","tryptophan", 
        "tyrosine", "valine"};

    public CountdownTimer() {
        startButton = new JButton("Start Quiz");
        cancelButton = new JButton("Cancel");
        textField = new JTextField();
        aminoAcidLabel = new JLabel("", JLabel.CENTER);
        timerLabel = new JLabel(count + " seconds remaining", JLabel.CENTER);
        score = new JLabel("Correct: " + numRight + " Incorrect: " + numWrong);

        setTitle("Amino Acid Quiz");
        setSize(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        //make invisible by default
        aminoAcidLabel.setVisible(false);
        textField.setVisible(false);
        timerLabel.setVisible(false);
        score.setVisible(false);
        
        JPanel text = new JPanel();
        text.setLayout(new GridLayout(2, 1));
        text.add(aminoAcidLabel);
        text.add(textField);
        
        add(text, BorderLayout.NORTH); 
        JPanel timerScore = new JPanel();
        timerScore.setLayout(new GridLayout(2, 1));
        timerScore.add(timerLabel);
        timerScore.add(score);
        add(timerScore, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count > 0) {
                    count--;
                    timerLabel.setText(count + " seconds remaining");
                } else {
                    ((Timer) e.getSource()).stop();
                    timerLabel.setText("Time's up!");
                    //score.setVisible(true);
                    aminoAcidLabel.setVisible(false);
                    textField.setVisible(false);
                    checkAnswer();
                    startButton.setEnabled(true);
                    cancelButton.setEnabled(false);
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	count = 30;
            	numRight = 0;
            	numWrong = 0;
            	score.setVisible(true);
            	score.setText("Correct: " + numRight + " Incorrect: " + numWrong);
                randomIndex = (int)(Math.random() * FULL_NAMES.length);
                aminoAcidLabel.setText(FULL_NAMES[randomIndex]);
                aminoAcidLabel.setVisible(true);
                textField.setVisible(true);
                timerLabel.setVisible(true);
                textField.setText("");
                startButton.setEnabled(false);
                cancelButton.setEnabled(true);
                timer.start();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //stop timer and reset the count
                timer.stop();
                count = 30; //Not really needed but I think it looks nicer with this here
                timerLabel.setText(count + " seconds remaining");
                startButton.setEnabled(true);
                aminoAcidLabel.setVisible(false);
                textField.setVisible(false);
                timerLabel.setVisible(false);
                score.setText("Correct: " + numRight + " Incorrect: " + numWrong);
            }
        });
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                }
                    
        });
    }

    private void checkAnswer() {
        String userInput = textField.getText();
        String correctAnswer = Short_names[randomIndex];
        
        if (userInput.toUpperCase().equals((correctAnswer))) {
            numRight++;
        } else {
            numWrong++;
        }
        score.setText("Correct: " + numRight + " Incorrect: " + numWrong);
        resetQuiz();
    }

    private void resetQuiz() {
        randomIndex = (int)(Math.random() * FULL_NAMES.length);
        aminoAcidLabel.setText(FULL_NAMES[randomIndex]);
        textField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CountdownTimer();
            }
        });
    }
}
