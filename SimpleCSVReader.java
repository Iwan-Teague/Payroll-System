import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for reading data from CSV files and mapping it to specific structures.
 * <p>
 * This class provides methods to read general CSV data, employee details, pay slips, and pay scales,
 * transforming the data as required.
 * </p>
 */
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

    /**
     * Reads selected fields from the "Employees.csv" file and returns them as a list of string arrays.
     * <p>
     * Each string array contains the following fields: ID, name, and the 6th field (if present).
     * </p>
     *
     * @return a {@code List<String[]>} where each {@code String[]} contains selected employee details.
     */
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

    /**
     * Reads a CSV file containing pay scale details and returns rows where the number of fields exceeds the given length.
     *
     * @param length the minimum number of fields required in a row for it to be included.
     * @param name the name of the CSV file to read.
     * @return a {@code List<String[]>} where each {@code String[]} contains all the comma seperated values in the CSV that
     * corresponds to the name parameter.
     */
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
