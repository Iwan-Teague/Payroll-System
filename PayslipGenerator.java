import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayslipGenerator {

    // Read CSV and return a list of String[] where each array represents a line of the CSV
    public List<String[]> readCsv(String filePath) {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split each line by commas and add to the list
                String[] fields = line.split(",");
                rows.add(fields);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    // Get employees' PPS No. and Pay as a HashMap
    public HashMap<String, String> getEmployees() {
        HashMap<String, String> pairs = new HashMap<>();
        List<String[]> employeeData = readCsv("Employees.csv");

        // Loop through each employee data (skipping the header row)
        for (String[] employee : employeeData) {
            if (employee.length >= 6) {
                String ppsNo = employee[1]; // Assuming PPS No. is in the second column
                String pay = employee[5]; // Assuming Pay is in the sixth column
                pairs.put(ppsNo, pay); // Add PPS No. and Pay to the HashMap
            }
        }
        return pairs;
    }


}


