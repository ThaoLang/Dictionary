
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
    }

    public void addHistory(Record re){
            history.add(re);
    }

    public ArrayList<Record> getHistory(){
        return this.history;
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
