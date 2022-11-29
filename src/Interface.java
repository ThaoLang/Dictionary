import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;

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
    private JList slangList;
    private JList defList;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JList keyList;
    private LocalDate lastRandom=LocalDate.MIN;
    private String typeSearching="slang";
    private String[] defs;
    private SlangWord dict;


    private void loadInSlangScrollPane(String[] arr){
        slangList.setListData(arr);
    }

    public Interface() {
        super("Home Page");
//        searchButton.setSize(10, 10);
        dict = new SlangWord();
        try {
            dict.init("slang.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] keys = dict.getSlang().keySet().toArray(new String[0]);
        loadInSlangScrollPane(keys);

        dailySlangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastRandom.compareTo(LocalDate.now()) == 0) {
                    JOptionPane.showMessageDialog(null, "The Slang for today's been released. Come back tomorrow to learn slang!");
                } else {
                    String randomText = dict.randomSlang();
                    DailySlang.setText(randomText);
                    lastRandom = LocalDate.now();
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
                typeSearching = typeSearch.getSelectedIndex() == 0 ? "slang" : "definition";
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dict.getHistory().addHistory(new Record(dict.getDate(),textSearch.getText()));
                defList.setListData(new String[0]);
                if (typeSearching == "slang") {
                    if (dict.getSlang().containsKey(textSearch.getText())) {
                        String[] keys = new String[]{textSearch.getText()};
                        loadInSlangScrollPane(keys);
                    } else {
                        slangList.setListData(new String[0]);
                    }

                } else {
                    String[] keyWords = textSearch.getText().split("`");
                    if (dict.getDefinition().containsKey(keyWords[0])) {
                        HashSet<String> keys = dict.getDefinition().get(keyWords[0]);

                        for (int i = 1; i < keyWords.length; i++) {
                            for (String kw : dict.getDefinition().get(keyWords[i]))
                                if (!keys.contains(kw)) {
                                    keys.add(kw);
                                }
                        }
                        loadInSlangScrollPane(keys.toArray(new String[0]));
                    } else {
                        slangList.setListData(new String[0]);
                    }


                }
            }
        });
        slangList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (slangList.getSelectedValue()!=null){
                    dict.getHistory().addHistory(new Record(dict.getDate(),slangList.getSelectedValue().toString()));
                    defs = dict.getSlang().get(slangList.getSelectedValue()).toArray(new String[0]);
                    defList.setListData(defs);
                }
                else {
                    defList.setListData(new String[0]);
                }
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defList.setListData(new String[0]);
                String[] keys = dict.getSlang().keySet().toArray(new String[0]);
                loadInSlangScrollPane(keys);
            }
        });
//        resetButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                SlangWord dict = new SlangWord();
//                try {
//                    dict.init("original.txt");
//                } catch (IOException er) {
//                    throw new RuntimeException(er);
//                }
//                String[] keys = dict.getSlang().keySet().toArray(new String[0]);
//                loadInSlangScrollPane(keys);
//
//            }
//        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField newSlang = new JTextField();
                JTextField newDef = new JTextField();
                Object[] add = {
                        "Slang", newSlang,
                        "Definition", newDef
                };
                int result = JOptionPane.showConfirmDialog(null, add, "Add new slang", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String key = newSlang.getText();
                    String value = newDef.getText();

                    if (dict.getSlang().containsKey(key)) {
                        String[] options = {"Overwrite", "Add new definition", "Cancel"};
                        int opt = JOptionPane.showOptionDialog(null, "The slang already exists. Overwrite the old " +
                                "slang or add new definition to this slang?", "Exist Slang", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
                        if (opt == JOptionPane.YES_OPTION) {
                            dict.overwriteSlang(key, value);
                        } else if (opt == JOptionPane.NO_OPTION) {
                            dict.duplicateSlang(key, value);
                        }
                    } else {
                        dict.addSlang(key, value);
                    }
                }

            }

        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField oldSlang = new JTextField();
                JTextField newslang = new JTextField();
                Object[] add = {
                        "Old slang", oldSlang,
                        "New slang", newslang
                };
                int result = JOptionPane.showConfirmDialog(null, add, "Edit a slang", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String oldkey = oldSlang.getText();
                    String newkey = newslang.getText();

                    if (!dict.getSlang().containsKey(oldkey)) {
                        JOptionPane.showMessageDialog(null, "The slang doesn't exist!");
                    } else {
                        dict.editSlang(oldkey, newkey);
                    }
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word=slangList.getSelectedValue().toString();
                if (word==null){
                    JOptionPane.showMessageDialog(null,"Please choose a slang to delete");
                }
                else{
                    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Delete slang", JOptionPane.OK_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        dict.deleteSlang(word);
                    }
                }
            }

        });
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defList.setListData(new String[0]);
                HashSet<String> historyList=new HashSet<String>();
                for (Record re:dict.getHistory().getHistory()){
                    historyList.add(re.getDate()+"  "+re.getSlang());
                }
                loadInSlangScrollPane(historyList.toArray(new String[0]));
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dict = new SlangWord();
                try {
                    dict.init("original.txt");
                } catch (IOException er) {
                    throw new RuntimeException(er);
                }
                String[] keys = dict.getSlang().keySet().toArray(new String[0]);
                loadInSlangScrollPane(keys);
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
