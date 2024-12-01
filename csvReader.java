import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * The csvReader class provides functionality for reading employee data from a CSV file.
 * It reads the data line by line and maps each line into an Employee object.
 * @author Iwan Teague
 */
public class csvReader {

    /**
     * Constructor for the csvReader class. Initializes a new instance of the csvReader.
     */
    public csvReader() {
    }

    /**
     * Reads employee data from a CSV file and returns a list of Employee objects.
     * The method processes the file by reading each line, skipping the header,
     * and mapping each line to an Employee object using an EmployeeMapper instance.
     *
     * @param var1 The path to the CSV file to read.
     * @return A list of Employee objects created from the CSV data.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public List<Employee> readCSV(String var1) throws IOException {
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
