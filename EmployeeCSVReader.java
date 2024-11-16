import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCSVReader {
    public static void main(String[] args) {
        String csvFile = "Employees.csv"; // Path to the CSV file
        String line;
        String csvSplitBy = ",";

        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read and ignore the header
            br.readLine();

            // Process each line
            while ((line = br.readLine()) != null) {
                // Split the line into an array
                String[] data = line.split(csvSplitBy);

                // Extract and map data
                String name = data[0];
                String ppsNo = data[1]; // Not used in Employee class, but available for extension
                String departmentStr = data[2];
                String roleStr = data[3];
                String payScaleStr = data[4]; // Not used in Employee class

                try {
                    // Convert department and role to enums
                    Employee.JobCategory jobCategory = Employee.JobCategory.valueOf(departmentStr.replace(" ", ""));
                    Employee.JobType jobType = Employee.JobType.valueOf(roleStr.replace(" ", ""));

                    // Create Employee instance
                    Employee employee = new Employee(ppsNo, name, jobCategory, jobType);
                    employees.add(employee);

                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid job category or role in line: " + line);
                } catch (Exception e) {
                    System.err.println("Error creating Employee from line: " + line);
                }
            }

            // Output all employees
            for (Employee emp : employees) {
                System.out.println(emp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
