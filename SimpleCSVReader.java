import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleCSVReader {
    /**
     * Reads the contents of a CSV file and returns the lines as a list of strings.
     *
     * @param filePath Path to the CSV file.
     * @return List of lines from the CSV file.
     * @throws IOException If an error occurs while reading the file.
     */
    public List<Employee> readCSV(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        EmployeeMapper employeeMapper = new EmployeeMapper();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                employees.add(employeeMapper.fromCSV(line));
            }
        }

        return employees;
    }

    public List<String[]> readCsvPaySlip() {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Employees.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length > 5) {
                    String[] selectedFields = {fields[0], fields[1], fields[5]};
                    rows.add(selectedFields);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }
    public List<String[]> readCsvPayScale(int length, String name) {
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
    public List<Employee> readCSVSalary(String var1) throws IOException {
        ArrayList var2 = new ArrayList();
        EmployeeMapper var3 = new EmployeeMapper();
        BufferedReader var4 = new BufferedReader(new FileReader(var1));

        try {
            var4.readLine();

            String var5;
            while((var5 = var4.readLine()) != null) {
                var2.add(var3.fromCSV(var5));
            }
        } catch (Throwable var8) {
            try {
                var4.close();
            } catch (Throwable var7) {
                var8.addSuppressed(var7);
            }

            throw var8;
        }

        var4.close();
        return var2;
    }
}
