
import exception.MyException;

import javax.print.Doc;
import java.util.*;

public class Database {
    public static   Database database = new Database(new ArrayList<>());

    private List<Hospital>hospitals = new ArrayList<>();

    public List<Hospital> getHospitals() {
        return hospitals;
    }
    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }


    public Database(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }
    public Database() {
    }

}
