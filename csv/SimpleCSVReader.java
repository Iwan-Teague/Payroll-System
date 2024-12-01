package csv;

import employee.Employee;
import employee.EmployeeMapper;

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
        List<Employee> employees = new ArrayList<>(); // list of employees
        EmployeeMapper employeeMapper = new EmployeeMapper();  // instance of employee mapper

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) { // read every line until EOF
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
        List<String[]> rows = new ArrayList<>(); // rows is list<String>
        try (BufferedReader br = new BufferedReader(new FileReader("csv/csv files/Employees.csv"))) { // buffer reader reads file
            String line;
            while ((line = br.readLine()) != null) { // reads every line until EOF end of file
                String[] fields = line.split(",");

                if (fields.length > 5) {
                    String[] selectedFields = {fields[0], fields[1], fields[5]};
                    rows.add(selectedFields); // array of fields added to array list rows
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
        List<String[]> rows = new ArrayList<>(); // create empty list
        try (BufferedReader br = new BufferedReader(new FileReader(name))) { // create buffer reader to read line by line
            String line;
            while ((line = br.readLine()) != null) { // loop  reads every line until EOF end of file
                String[] fields = line.split(","); // split lines into fields
                if (fields.length > length) {
                    rows.add(fields); // line is added to rows list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows; // return list<String>
    }

    /**
     * Reads a CSV file containing
     *
     * @param var1 the file path for Payslips.csv
     *
     * @return var2 array list of employee objects
     * corresponds to the name parameter.
     */
    public List<Employee> readCSVSalary(String var1) throws IOException {
        ArrayList var2 = new ArrayList(); // array list to store empolyee objects
        EmployeeMapper var3 = new EmployeeMapper(); // var3 instance of employee.EmployeeMapper
        BufferedReader var4 = new BufferedReader(new FileReader(var1)); // var 4 to read CSV file (var1)

        try {
            var4.readLine();//read and ingore first line of CSV

            String var5;// declare var5 as string
            while((var5 = var4.readLine()) != null) { // loop reads file line by line until EOF end of file
                var2.add(var3.fromCSV(var5));// each line wich is (var5) with fromCSV converts to employee object wich is (var3) wich is then stored in (var2) array list
            }
        } catch (Throwable var8) { // exception
            try {
                var4.close(); // close file
            } catch (Throwable var7) {
                var8.addSuppressed(var7);  // suppressed exception
            }

            throw var8; // exception thrown
        }

        var4.close(); // close file
        return var2; // return list of employee objects
    }
}
