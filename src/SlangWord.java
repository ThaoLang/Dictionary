import java.io.IOException;
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

    SlangWord() {
        slang = new TreeMap<>();
        definition = new HashMap<>();
        history = new History();
    }

    private void separateKeyWord(String key, String value){
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
                separateKeyWord(splitLine[0],splitLine[1]);
                slang.put(splitLine[0], deftemp);
            }
            else {
                String[] temp = splitLine[1].split("\\| ");
                for (String str: temp) {
                    separateKeyWord(splitLine[0],str );
                    deftemp.add(str);
                }
                slang.put(splitLine[0], deftemp);
            }
        }
        br.close();
        System.out.println(definition);

    }

    public HashSet<String> getDefinitionFromKey(String key){
        return slang.get(key);
    }

    public static void main(String[] args) {
        SlangWord dict = new SlangWord();
        try {
            dict.init("slang.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
