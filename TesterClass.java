import java.util.List;
import java.io.IOException;

public class TesterClass {

    public static void main(String[] args) {
        SimpleCSVReader reader = new SimpleCSVReader();
        try{
            List<String> employees = reader.readCSV("Employees.csv");

            for (String line : employees) {

                EmployeeMapper employeeMapper = new EmployeeMapper();
                Employee employee = employeeMapper.fromCSV(line);
                System.out.println(employee);
            }
        }  catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

    }
}