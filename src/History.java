
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * PACKAGE_NAME
 * Created by langt_ybnheue
 * Date 11/22/2022 : 11:05 PM
 * Description
 */
public class History {
        private ArrayList<Record> history;

    public History() {
        history=new ArrayList<Record>();
        importHistoryFile("history.txt");
    }

    public void addHistory(Record re){
            history.add(re);
    }

    public ArrayList<Record> getHistory(){
        return this.history;
    }

    public void saveHistoryToFile(String date, String sslang,String file_name){
        File file=new File("./data/" +file_name);
        String text="";
        if (file.length()>0){
            text="\r\n";
        }
        text+=date+"  "+sslang;
        try {
            Files.write(Paths.get("./data/"+file_name), text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void importHistoryFile(String file_name) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("./data/" +file_name));
            String line = reader.readLine();
            while (line != null) {
                String[] splitLine = line.split("  ");
                String date = splitLine[0];
                String sslang = splitLine[1];

                Record re = new Record(date,sslang);
                this.addHistory(re);

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Record{
    private String date;
    private  String slang;

    Record(){
        date="";
        slang="";
    }

    Record(String date,String slang){
        this.date=date;
        this.slang=slang;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlang() {
        return slang;
    }

    public void setSlang(String slang) {
        this.slang = slang;
    }
}
