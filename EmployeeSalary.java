import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeSalary {

    // A map to store the pay scale data (department and role mapped to their corresponding pay scale)
    private static Map<String, Integer> payScaleMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            // Read pay scales from ULPayScales.csv and load them into the map
            loadPayScales("ULPayScales.csv");

            // Read employee data from Employees.csv and calculate their salary
            csvReader reader = new csvReader();
            List<Employee> employees = reader.readCSV("Employees.csv");

            // For each employee, retrieve the pay scale and calculate the salary
            for (Employee employee : employees) {
                String department = employee.getJobCategory().toString().toLowerCase(); // Convert job category to string
                String role = employee.getJobRole().toString().toLowerCase(); // Convert job role to string

                String key = department + "," + role;
                Integer payScale = payScaleMap.get(key);

                if (payScale != null) {
                    System.out.println("Employee: " + employee.getName() +
                            ", Role: " + employee.getJobRole() +
                            ", PayScale: " + payScale);
                } else {
                    System.out.println("No pay scale found for: " + employee.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load pay scales from a CSV file
    private static void loadPayScales(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        // Skip the header line
        br.readLine();

        // Read each line and populate the pay scale map
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (values.length >= 3) {
                String category = values[0].trim().toLowerCase(); // Trim and convert to lower case
                String role = values[1].trim().toLowerCase(); // Trim and convert to lower case
                String scalePointStr = values[2].trim(); // Trim to remove extra spaces
                int scalePoint = 0;

                try {
                    // Attempt to parse the scale point as an integer
                    scalePoint = Integer.parseInt(scalePointStr);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing scale point for: " + category + " - " + role);
                }

                String key = category + "," + role;
                payScaleMap.put(key, scalePoint);
            }
        }

        br.close();
    }
}
