import java.util.List;
import java.io.IOException;

/**
 * A tester class to demonstrate reading and printing employee details
 * from a CSV file using the {@link SimpleCSVReader} class.
 * <p>
 * This program reads employee data from a file named "Employees.csv"
 * and prints each employee's details to the console. If an error occurs
 * during file reading, an appropriate error message is displayed.
 * </p>
 */
public class TesterClass {

    /**
     * The main method that serves as the entry point for the program.
     * <p>
     * It initializes a {@link SimpleCSVReader} to read employee data
     * from the "Employees.csv" file. The employee details are then
     * printed to the console. If an {@link IOException} occurs while
     * reading the file, an error message is logged to {@code System.err}.
     * </p>
     *
     * @param args command-line arguments (not used in this program)
     */
    public static void main(String[] args) {
        //CSVWriter.updateCSVCell("Employees.csv", 0, 0, "test");
        CSVWriter.updateCSVCell("Employees.csv", Checker.findRowByPPS("Employees.csv", "8901234H", 1) , 6, "test");
        DateCheckTester dateCheckTester = new DateCheckTester();
        DateCheckTester.testDateCheck();

    }
}
