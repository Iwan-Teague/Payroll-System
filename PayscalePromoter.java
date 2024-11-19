import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayscalePromoter {
    public List<String[]> readCsv(int length, String name) {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(name))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length > length) {
                    rows.add(fields);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public List<String[]> incrementPayScale() {
        List<String[]> EmployeesList = readCsv(5, "Employees.csv");
        List<String[]> JobsList = readCsv(3, "ULPayScales.csv");

        for (int i = 1; i < EmployeesList.size(); i++) {
            String EmployeeJobTitle = EmployeesList.get(i)[3];
            String EmployeePayScale = EmployeesList.get(i)[4];
            for (int j = 1; j < JobsList.size(); j++) {
                String ListJobTitle = JobsList.get(j)[1];
                String ListPayScale = JobsList.get(j)[2];
                
                String IncrementedJobTitle = "";
                String IncrementedPayScale = "";
                String IncrementedPay = "";
                
                if (j + 1 < JobsList.size()) {
                    IncrementedJobTitle = JobsList.get(j + 1)[1];
                    IncrementedPayScale = JobsList.get(j + 1)[2];
                    IncrementedPay = JobsList.get(j + 1)[3];
                }
                
                if (
                        (EmployeeJobTitle.equals(ListJobTitle))
                        && (EmployeePayScale.equals(ListPayScale))
                        && (EmployeeJobTitle.equals(IncrementedJobTitle))
                ) {
                    EmployeesList.get(i)[4] = IncrementedPayScale;
                    EmployeesList.get(i)[5] = IncrementedPay;
                }
            }
        }

        return EmployeesList;
    }


    public void writeToCSV() {
            List<String[]> data = incrementPayScale();
            try (FileWriter writer = new FileWriter("Employees.csv")) {
                for (int i = 0; i < data.size(); i++) {
                    String[] row = data.get(i);
                    writer.append(String.join(",", row));
                    writer.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}


