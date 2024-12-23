import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class QuizApplication extends JFrame {

    private int currentQuestionIndex = 0;
    private int score = 0;
    private javax.swing.Timer timer;
    private int timeLeft = 20;
    private JLabel timerLabel, questionLabel;
    private JRadioButton option1, option2, option3, option4;
    private ButtonGroup optionsGroup;
    private JButton submitButton;
    private JLabel scoreLabel;
    private JPanel questionPanel, resultPanel;
    private final List<Question> questions = new ArrayList<>();

    public QuizApplication() {
        setTitle("Quiz Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createQuizQuestions();

        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

        questionLabel = new JLabel("Question will appear here.");
        questionPanel.add(questionLabel);

        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();

        optionsGroup = new ButtonGroup();
        optionsGroup.add(option1);
        optionsGroup.add(option2);
        optionsGroup.add(option3);
        optionsGroup.add(option4);

        questionPanel.add(option1);
        questionPanel.add(option2);
        questionPanel.add(option3);
        questionPanel.add(option4);

        submitButton = new JButton("Submit Answer");
        questionPanel.add(submitButton);
        submitButton.setEnabled(false);

        add(questionPanel, BorderLayout.CENTER);

        timerLabel = new JLabel("Time left: 20s");
        timerLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        add(timerLabel, BorderLayout.NORTH);

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        add(scoreLabel, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        startNewQuestion();
    }

    private void createQuizQuestions() {
        questions.add(new Question("What is the capital of France?", 
            "Berlin", "Madrid", "Paris", "Rome", 3));
        questions.add(new Question("Which planet is known as the Red Planet?", 
            "Earth", "Mars", "Jupiter", "Saturn", 2));
        questions.add(new Question("What is 5 + 3?", 
            "5", "6", "7", "8", 4));
        questions.add(new Question("Which programming language is used for Android development?", 
            "Java", "Python", "C++", "Ruby", 1));
        questions.add(new Question("Who developed the theory of relativity?", 
            "Isaac Newton", "Galileo Galilei", "Albert Einstein", "Niels Bohr", 3));
    }

    private void startNewQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionLabel.setText(question.getQuestionText());
            option1.setText(question.getOption1());
            option2.setText(question.getOption2());
            option3.setText(question.getOption3());
            option4.setText(question.getOption4());

            timeLeft = 20;
            timerLabel.setText("Time left: " + timeLeft + "s");

            submitButton.setEnabled(true);
            optionsGroup.clearSelection();

            startTimer();
        } else {
            showResultScreen();
        }
    }

    private void startTimer() {
        timer = new javax.swing.Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft + "s");
                if (timeLeft <= 0) {
                    timer.stop();
                    checkAnswer();
                }
            }
        });
        timer.start();
    }

    private void checkAnswer() {
        timer.stop();
        submitButton.setEnabled(false);

        Question currentQuestion = questions.get(currentQuestionIndex);
        int correctAnswerIndex = currentQuestion.getCorrectAnswerIndex();

        int selectedAnswerIndex = -1;
        if (option1.isSelected()) selectedAnswerIndex = 1;
        else if (option2.isSelected()) selectedAnswerIndex = 2;
        else if (option3.isSelected()) selectedAnswerIndex = 3;
        else if (option4.isSelected()) selectedAnswerIndex = 4;

        if (selectedAnswerIndex == correctAnswerIndex) {
            score++;
        }

        currentQuestionIndex++;
        scoreLabel.setText("Score: " + score);

        Timer nextQuestionTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewQuestion();
            }
        });
        nextQuestionTimer.setRepeats(false);
        nextQuestionTimer.start();
    }

    private void showResultScreen() {
        questionPanel.setVisible(false);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.add(new JLabel("Quiz Over!"));
        resultPanel.add(new JLabel("Your Final Score: " + score));

        JButton restartButton = new JButton("Restart Quiz");
        resultPanel.add(restartButton);

        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentQuestionIndex = 0;
                score = 0;
                scoreLabel.setText("Score: " + score);
                questionPanel.setVisible(true);
                showResultScreen();
            }
        });

        add(resultPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new QuizApplication().setVisible(true);
            }
        });
    }
}

class Question {
    private String questionText;
    private String option1, option2, option3, option4;
    private int correctAnswerIndex;

    public Question(String questionText, String option1, String option2, String option3, String option4, int correctAnswerIndex) {
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}
