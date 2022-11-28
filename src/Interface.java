import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * PACKAGE_NAME
 * Created by langt_ybnheue
 * Date 11/22/2022 : 11:40 PM
 * Description
 */
public class Interface  extends JFrame{
    private JPanel homePage;
    private JPanel searchPanel;
    private JPanel displayPanel;
    private JLabel searchLabel;
    private JComboBox typeSearch;
    private JTextField textSearch;
    private JButton searchButton;
    private JPanel optionPanel;
    private JButton historyButton;
    private JButton resetButton;
    private JButton quizButton;
    private JScrollPane slangScrollPane;
    private JScrollPane definitionScrollPane;
    private JButton button1;
    private JButton dailySlangButton;
    private JLabel Logo;
    private JLabel Intro;
    private JLabel DailySlang;
    private JList keyList;
    private LocalDate lastRandom=LocalDate.MIN;
    private String typeSearching="slang";

    public Interface(){
        super("Home Page");
        SlangWord dict=new SlangWord();
        try {
            dict.init("slang.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] keys=dict.getSlang().keySet().toArray(new String[0]);
        keyList=new JList<String>(keys);

        dailySlangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastRandom.compareTo(LocalDate.now())==0){
                    JOptionPane.showMessageDialog(null,"The Slang for today's been released. Come back tomorrow to learn slang!");
                }
                else{
                    String randomText= dict.randomSlang();
                    DailySlang.setText(randomText);
                    lastRandom=LocalDate.now();
                }

            }
        });

        textSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        textSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }
        });

        typeSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                typeSearching=typeSearch.getSelectedIndex()==0?"slang":"definition";
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (typeSearching=="slang"){

                }
                else{

                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Slang Word App");
        frame.setContentPane(new Interface().homePage);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
