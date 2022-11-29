import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Random;

/**
 * PACKAGE_NAME
 * Created by langt_ybnheue
 * Date 11/29/2022 : 10:16 AM
 * Description
 */
public class QuizUI extends JFrame{
    private JPanel TypeQuizPanel;
    private JButton Option1;
    private JButton Option3;
    private JButton Option2;
    private JButton Option4;
    private JLabel Question;
    private JLabel Logo;
    private JRadioButton SlangQuizOption;
    private JRadioButton DefinitionQuizOption;
    private JPanel quizPage;
    private JPanel answerPanel;
    private JPanel appIntro;
    private JLabel IntroName;
    private JButton nextButton;
    private ButtonGroup radioGroup;
    private SlangWord dict;
    private String CorrectAnswerQuiz="";
    private String typeQuiz="Slang";

    public QuizUI(){
        dict = new SlangWord();
        try {
            dict.init("slang.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        radioGroup=new ButtonGroup();
        radioGroup.add(SlangQuizOption);
        radioGroup.add(DefinitionQuizOption);
        SlangQuizOption.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Option1.setBackground(Color.white);
                Option2.setBackground(Color.white);
                Option3.setBackground(Color.white);
                Option4.setBackground(Color.white);
                typeQuiz="Slang";
                loadSlangQuiz();
            }
        });
        DefinitionQuizOption.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Option1.setBackground(Color.white);
                Option2.setBackground(Color.white);
                Option3.setBackground(Color.white);
                Option4.setBackground(Color.white);
                typeQuiz="Definition";
                loadDefinitionQuiz();
            }
        });
        Option1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CorrectAnswerQuiz==""){
                    JOptionPane.showMessageDialog(null,"Please choose Quiz type to start!");
                }
                else if (CorrectAnswerQuiz==Option1.getText()){
                    Option1.setBackground(Color.GREEN);
                }
                else if (CorrectAnswerQuiz!=Option1.getText()){
                    Option1.setBackground(Color.RED);

                    if (CorrectAnswerQuiz==Option2.getText()){Option2.setBackground(Color.GREEN);}
                    else if (CorrectAnswerQuiz==Option3.getText()){Option3.setBackground(Color.GREEN);}
                    else {Option4.setBackground(Color.GREEN);}
                }
            }
        });
        Option2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CorrectAnswerQuiz==""){
                    JOptionPane.showMessageDialog(null,"Please choose Quiz type to start!");
                }
                else if (CorrectAnswerQuiz==Option2.getText()){
                    Option2.setBackground(Color.GREEN);
                }
                else if (CorrectAnswerQuiz!=Option2.getText()){
                    Option2.setBackground(Color.RED);

                    if (CorrectAnswerQuiz==Option1.getText()){Option1.setBackground(Color.GREEN);}
                    else if (CorrectAnswerQuiz==Option3.getText()){Option3.setBackground(Color.GREEN);}
                    else {Option4.setBackground(Color.GREEN);}
                }
            }
        });
        Option3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CorrectAnswerQuiz==""){
                    JOptionPane.showMessageDialog(null,"Please choose Quiz type to start!");
                }
                else if (CorrectAnswerQuiz==Option3.getText()){
                    Option3.setBackground(Color.GREEN);
                }
                else if (CorrectAnswerQuiz!=Option3.getText()) {
                    Option3.setBackground(Color.RED);

                    if (CorrectAnswerQuiz == Option1.getText()) {
                        Option1.setBackground(Color.GREEN);
                    } else if (CorrectAnswerQuiz == Option2.getText()) {
                        Option2.setBackground(Color.GREEN);
                    } else {
                        Option4.setBackground(Color.GREEN);
                    }
                }
            }
        });
        Option4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CorrectAnswerQuiz==""){
                    JOptionPane.showMessageDialog(null,"Please choose Quiz type to start!");
                }
                else if (CorrectAnswerQuiz==Option4.getText()){
                    Option4.setBackground(Color.GREEN);
                }
                else if (CorrectAnswerQuiz!=Option4.getText()){
                    Option4.setBackground(Color.RED);

                    if (CorrectAnswerQuiz==Option1.getText()){Option1.setBackground(Color.GREEN);}
                    else if (CorrectAnswerQuiz==Option2.getText()){Option2.setBackground(Color.GREEN);}
                    else {Option3.setBackground(Color.GREEN);}
                }
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Option1.setBackground(Color.white);
                Option2.setBackground(Color.white);
                Option3.setBackground(Color.white);
                Option4.setBackground(Color.white);
                if (typeQuiz=="Slang")
                {
                    loadSlangQuiz();
                }
                else{
                    loadDefinitionQuiz();
                }
            }
        });
    }

    public void setOption(int orderCorrectOption,int orderOption, String correctAnswer,JButton btn){
        if (orderOption==orderCorrectOption){
            btn.setText(correctAnswer);
        }
        else{
            String answer;
            do{
                answer=(typeQuiz=="Slang")? dict.randomDefinition(dict.randomSlang()) : dict.randomSlang();
            }while (answer.compareTo(correctAnswer)==0);
            btn.setText(answer);
        }
    }
    private void loadSlangQuiz(){
        String key= dict.randomSlang();

        int randomCorrectOption=new Random().nextInt(3)+1;
        System.out.println(randomCorrectOption);
        String correctAnswer=dict.randomDefinition(key);

        Question.setText(key);
        CorrectAnswerQuiz=correctAnswer;
        setOption(randomCorrectOption,1,correctAnswer,Option1);
        setOption(randomCorrectOption,2,correctAnswer,Option2);
        setOption(randomCorrectOption,3,correctAnswer,Option3);
        setOption(randomCorrectOption,4,correctAnswer,Option4);
    }
    private void loadDefinitionQuiz(){
        String correctAnswer= dict.randomSlang();

        int randomCorrectOption=new Random().nextInt(4)+1;
        String value=dict.randomDefinition(correctAnswer);

        Question.setText(value);
        CorrectAnswerQuiz=correctAnswer;
        setOption(randomCorrectOption,1,correctAnswer,Option1);
        setOption(randomCorrectOption,2,correctAnswer,Option2);
        setOption(randomCorrectOption,3,correctAnswer,Option3);
        setOption(randomCorrectOption,4,correctAnswer,Option4);
    }

    public void openQuizUI(){
        //dict=dictionary;
        JFrame frame = new JFrame("Slang Word Quiz");
        frame.setContentPane(new QuizUI().quizPage);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
