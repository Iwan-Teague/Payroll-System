import java.util.List;
import java.io.IOException;

public class TesterClass {

    public static void main(String[] args) {
        SimpleCSVReader reader = new SimpleCSVReader();
        try{
            List<Employee> employees = reader.readCSV("Employees.csv");

            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }  catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

    }
}