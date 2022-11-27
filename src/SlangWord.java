import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.io.*;

/**
 * PACKAGE_NAME
 * Created by langt_ybnheue
 * Date 11/22/2022 : 10:44 PM
 * Description
 */
public class SlangWord {
    private TreeMap<String, HashSet<String>> slang;
    private HashMap<String, HashSet<String>> definition;
    private History history;
    private String dailySlang="";
    private LocalDate lastRandom=LocalDate.MIN;

    SlangWord() {
        slang = new TreeMap<>();
        definition = new HashMap<>();
        history = new History();
    }



    private void addSeparateKeyWord(String key, String value){
        String[] keyword = value.split(" ");
        for (String word:keyword){
            if (definition.containsKey(word)){
                if (!definition.containsValue(key)){
                    definition.get(word).add(key);
                }
            }
            else{
                HashSet<String> slangtemp=new HashSet<String>();
                slangtemp.add(key);
                definition.put(word,slangtemp);
            }
        }
    }

    private void deleteElementDefinition(String key, String value){
        String[] keyword = value.split(" ");
        for (String word:keyword){
            if (definition.get(word).contains(key)){
                definition.get(word).remove(key);
                if (definition.get(word).isEmpty())
                    definition.remove(word);
            }
        }
    }

    private void init(String file_name) throws IOException {

        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./data/" + file_name), "utf8"));
        String line;
        line = br.readLine();
        while (line != null) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            String[] splitLine = line.split("`");
            HashSet<String> deftemp = new HashSet<String>();
            if (!splitLine[1].contains("|")) {
                deftemp.add(splitLine[1]);
                addSeparateKeyWord(splitLine[0],splitLine[1]);
                slang.put(splitLine[0], deftemp);
            }
            else {
                String[] temp = splitLine[1].split("\\| ");
                for (String str: temp) {
                    addSeparateKeyWord(splitLine[0],str );
                    deftemp.add(str);
                }
                slang.put(splitLine[0], deftemp);
            }
        }
        br.close();

    }

    public HashSet<String> getDefinitionFromKey(String key){
        return slang.get(key);
    }

    public void addSlang(String newSlang,String newdef){
        HashSet<String> temp=new HashSet<>();
        temp.add(newdef);
        slang.put(newSlang,temp);
        addSeparateKeyWord(newSlang,newdef);
    }

    public void overwriteSlang(String sSlang,String newdef){
        for (String temp: slang.get(sSlang)){
            deleteElementDefinition(sSlang,temp);
        }
        slang.get(sSlang).clear();

        slang.get(sSlang).add(newdef);
        addSeparateKeyWord(sSlang,newdef);

    }

    public void duplicateSlang(String key,String newdef){
        slang.get(key).add(newdef);
        addSeparateKeyWord(key,newdef);
    }

    public void editSlang(String oldSlang,String newSlang){
        HashSet<String> defi=slang.remove(oldSlang);
        for (String temp: defi){
            deleteElementDefinition(oldSlang,temp);
            addSeparateKeyWord(newSlang,temp);
        }
        slang.put(newSlang,defi);
    }

    public void deleteSlang(String sslang){
        HashSet<String> defi=slang.remove(sslang);
        for (String temp: defi){
            deleteElementDefinition(sslang,temp);
        }
    }

    public String randomSlang() {
        Object[] slangList = slang.keySet().toArray();
        Object key = slangList[new Random().nextInt(slangList.length)];

        String[] defList = slang.get(key).toArray(new String[slang.get(key).size()]);
        Object value = defList[new Random().nextInt(defList.length)];

        return key.toString() +": "+ value.toString();
    }

    public static void main(String[] args) {
        SlangWord dict = new SlangWord();
        try {
            dict.init("slang.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner input=new Scanner(System.in);
        System.out.println("Enter the slang: ");
        String key=input.nextLine();

        for (String x: dict.getDefinition().get(key)){
            System.out.println(x);
        }
        System.out.println("The daily slang: "+dict.randomSlang());

//        Date date = new Date();
//        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        String stringDate= DateFor.format(date);
//        System.out.println(stringDate);



    }

    public TreeMap<String, HashSet<String>> getSlang() {
        return slang;
    }

    public HashMap<String, HashSet<String>> getDefinition() {
        return definition;
    }

    public History getHistory() {
        return history;
    }
}
