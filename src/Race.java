import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Race extends JFrame {
    private final JTextField input;
    private boolean finished = false;
    private long startTime = 0;
    private double accuracy;
    private double speed;
    private double duration;
    private int errors;
    private String original;
    private final String textToType;
    private String textTyped;
    private ArrayList<String> wordsToType;
    private ArrayList<String> wordsTyped;
    private final ArrayList<JLabel> labelWords;
    private Color[] theme;
    private int currentWordIndex = 0;
    private int highlightWordIndex = 0;

    public Race(Color[] raceTheme) {
        super("Track Byte Race");
        StringBuilder textBuilder = new StringBuilder();
        finished = false;
        setResizable(false);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);

        theme = raceTheme;
        Font font = new Font("SansSerif", Font.PLAIN, 20);

        Texts texts = new Texts();
        textToType = texts.getTwoSentences();

        String endBuffer = " ".repeat(1000);
        String countDownBuffer = " ".repeat(50);
        String noteBuffer = " ".repeat(25);

        JLabel countdown = new JLabel(countDownBuffer + "Ready..." + endBuffer);
        countdown.setFont(font);
        countdown.setForeground(theme[2]);

        JLabel footnote = new JLabel(noteBuffer + "Note: the last word must be typed correctly to finish the race." );
        footnote.setFont(new Font("SansSerif", Font.PLAIN, 14));
        footnote.setForeground(theme[4]);

        JPanel raceWindow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        raceWindow.setBackground(theme[0]);

        raceWindow.add(countdown);

        wordsToType = new ArrayList<String>(Arrays.asList(textToType.trim().split(" ")));
        labelWords = new ArrayList<JLabel>();
        for (String word : wordsToType) {
            JLabel promptWord = new JLabel(word);
            promptWord.setFont(font);
            promptWord.setForeground(theme[1]);
            labelWords.add(promptWord);
        }

        labelWords.get(0).setForeground(theme[4]);

        for (JLabel jlabel : labelWords) {
            raceWindow.add(jlabel);
        }

        raceWindow.add(new JLabel(" ".repeat(1000)));
        raceWindow.add(footnote);

        add(raceWindow);

        setLocation(340, 240);
        setSize(600, 240);

        input = new JTextField();
        input.setEditable(false);

        add(input, BorderLayout.AFTER_LAST_LINE);
        setVisible(true);

        sleep(1);

        countdown.setText(countDownBuffer + " " + "Set..." + endBuffer);
        countdown.setForeground(theme[1]);

        sleep(1);

        input.setEditable(true);

        countdown.setText(countDownBuffer + " " + "Type!" + endBuffer);
        countdown.setForeground(theme[4]);

        input.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (startTime == 0 && e.getKeyCode() != KeyEvent.VK_SHIFT) {
                    startTime = System.currentTimeMillis();
                }
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    if (input.getText().length() == 1) {
                        e.consume();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE && highlightWordIndex != wordsToType.size() - 1) {
                    if (currentWordIndex > wordsToType.size() - 1) {
                        currentWordIndex = wordsToType.size() - 1;
                    }
                    String typedInput = input.getText();
                    if (typedInput.trim().isEmpty()) {
                        typedInput = " " + "|".repeat(wordsToType.get(currentWordIndex).length());
                    }
                    textBuilder.append(typedInput);
                    wordsTyped = new ArrayList<String>(Arrays.asList(textBuilder.toString().trim().split(" ")));
                    currentWordIndex++;
                    colorWords();
                    input.setText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String lastWordTyped = input.getText();
                String lastWordToType = getLastWord(textToType);
                if ((lastWordTyped.trim().equals(lastWordToType.replace("<br>", "").trim()) && (double) String.valueOf(textBuilder).length() / textToType.length() >= 3.0/4)) {
                    textBuilder.append(input.getText());
                    long finishTime = System.currentTimeMillis();
                    duration = (double) (finishTime - startTime);
                    textTyped = String.valueOf(textBuilder);
                    if (textTyped.length() < textToType.length()) {
                        duration *= (double) textToType.length() / textTyped.length();
                    }
                    dispose();
                    calculateAccuracy();
                    calculateSpeed();
                    finished = true;
                }
            }
        });
    }

    private void colorWords() {
        for (highlightWordIndex = 0; highlightWordIndex < labelWords.size(); highlightWordIndex++) {
            if (highlightWordIndex == currentWordIndex) {
                labelWords.get(highlightWordIndex).setForeground(theme[4]);
            }
            else if ((highlightWordIndex < currentWordIndex) &&
                    (!labelWords.get(highlightWordIndex).getText().equals(wordsTyped.get(highlightWordIndex))) &&
                    (highlightWordIndex != wordsToType.size() - 1)) {
                labelWords.get(highlightWordIndex).setForeground(theme[3]);
            }
            else if (highlightWordIndex < currentWordIndex && highlightWordIndex != wordsToType.size() - 1) {
                labelWords.get(highlightWordIndex).setForeground(theme[2]);
            }
            else if (highlightWordIndex != wordsToType.size() - 1) {
                labelWords.get(highlightWordIndex).setForeground(theme[1]);
            }
        }
    }

    public void setTheme(Color[] theme) {
        this.theme = theme;
    }

    private void calculateSpeed() {
        setVariables();
        fillEmptyWords();
        countErrors();
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        double calculatedSpeed = (Math.ceil((double) (textToType.length() - errors) / 5) / (duration / 1000)) * 60;
        if (calculatedSpeed <= 0) {
            calculatedSpeed = 0.0;
        }
        speed = Double.parseDouble(decimalFormat.format(calculatedSpeed));
    }

    private void calculateAccuracy() {
        setVariables();
        fillEmptyWords();
        countErrors();
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        double calculatedAccuracy = ((double) (textToType.length() - errors) / textToType.length()) * 100;
        if (calculatedAccuracy <= 0) {
            calculatedAccuracy = 0.0;
        }
        accuracy = Double.parseDouble(decimalFormat.format(calculatedAccuracy));
    }

    private String getLastWord(String sentence) {
        String[] words = sentence.split(" ");
        if (words.length > 0) {
            return words[words.length - 1];
        }
        else {
            return "";
        }
    }

    private void setVariables() {
        original = textTyped.trim().replaceAll(" +", " ");
        wordsToType = new ArrayList<String>(Arrays.asList(textToType.trim().split(" ")));
        wordsTyped = new ArrayList<String>(Arrays.asList(textTyped.trim().split(" ")));
    }

    private void countErrors() {
        errors = 0;
        for (int wordID = 0; wordID < wordsToType.size(); wordID++) {
            String wordToType = wordsToType.get(wordID).trim();
            String wordTyped = wordsTyped.get(wordID).trim();
            if (wordTyped.isEmpty()) {
                wordsTyped.remove(wordTyped);
            }
            if (wordToType.contains("<html>")) {
                wordToType = wordToType.replace("<html>", "").trim();
            }
            if (wordToType.contains("<br>")) {
                wordToType = wordToType.replace("<br>", "").trim();
            }
            if (wordTyped.isEmpty()) {
                errors++;
                wordsTyped.remove(wordID);
                wordTyped = wordsTyped.get(wordID);
            }
            if (wordTyped.length() < wordToType.length()) {
                wordsTyped.set(wordID, wordTyped + (String.valueOf("|".repeat(wordToType.length() - wordTyped.length()))));
                wordTyped = wordsTyped.get(wordID);
            }
            if (wordTyped.length() > wordToType.length()) {
                StringBuilder newWord = new StringBuilder();
                for (int i = 0; i < wordToType.length(); i++) {
                    newWord.append(wordTyped.charAt(i));
                }
                wordsTyped.set(wordID, String.valueOf(newWord));
                errors++;
            }
            for (int charID = 0; charID < wordToType.length(); charID++) {
                if (wordToType.charAt(charID) != wordTyped.charAt(charID)) {
                    errors++;
                }
            }
        }
        errors += countSpaces(textToType) - countSpaces(original);
    }

    private void fillEmptyWords() {
        if (wordsTyped.size() < wordsToType.size()) {
            for (int wordIndex = wordsTyped.size(); wordIndex < wordsToType.size(); wordIndex++) {
                wordsTyped.add(String.valueOf(("|".repeat(wordsToType.get(wordIndex).length()))));
            }
        }
    }

    private int countSpaces(String string) {
        int spaces = 0;
        for (char c : string.toCharArray()) {
            if (c == ' ') {
                spaces++;
            }
        }
        return spaces;
    }

    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        }
        catch (InterruptedException e) {
            System.out.print("");
        }
    }

    public double getSpeed() {
        return speed;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public boolean isFinished() {
        return finished;
    }
}

