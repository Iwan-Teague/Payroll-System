import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeSalary {

    // A map to store the pay scale data (department and role mapped to their corresponding pay scale)
    private static Map<String, Integer> payScaleMap = new HashMap<>();

    // A map to store the RATE data (department and role mapped to their corresponding rate)
    private static Map<String, String> rateMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            // Read pay scales and rates from ULPayScales.csv and load them into the maps
            loadPayScalesAndRates("ULPayScales.csv");

            // Read employee data from Employees.csv
            csvReader reader = new csvReader();
            List<Employee> employees = reader.readCSV("Employees.csv");

            // For each employee, retrieve the pay scale and rate
            for (Employee employee : employees) {
                String department = employee.getJobCategory().toString().toLowerCase(); // Convert job category to string
                String role = employee.getJobRole().toString().toLowerCase(); // Convert job role to string

                String key = department + "," + role;
                Integer payScale = payScaleMap.get(key);
                String rate = rateMap.get(key);

                if (payScale != null && rate != null) {
                    // Convert rate from string to integer for calculation
                    int salary = Integer.parseInt(rate);

                    // Calculate USC and PRSI as strings
                    String usc = calculateUSC(salary);
                    String prsi = calculatePRSI(salary);

                    System.out.println("Employee: " + employee.getName() +
                            ", Role: " + employee.getJobRole() +
                            ", PayScale: " + payScale +
                            ", Rate: " + rate +
                            ", USC: " + usc +
                            ", PRSI: " + prsi);
                } else {
                    System.out.println("No pay scale or rate found for: " + employee.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load pay scales and rates from a CSV file manually handling quoted fields
    private static void loadPayScalesAndRates(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        // Skip the header line
        br.readLine();

        // Read each line and populate the pay scale and rate maps
        while ((line = br.readLine()) != null) {
            String[] values = parseCSVLine(line); // Parse the line using custom method
            if (values.length >= 4) {
                String category = values[0].trim().toLowerCase(); // Trim and convert to lower case
                String role = values[1].trim().toLowerCase(); // Trim and convert to lower case
                String scalePointStr = values[2].trim(); // Scale point column
                String rateStr = values[3]; // Rate column

                int scalePoint = 0;
                try {
                    scalePoint = Integer.parseInt(scalePointStr);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing scale point for: " + category + " - " + role);
                }

                // Clean the rate string (remove commas and currency symbols)
                String cleanedRate = cleanRate(rateStr);

                // Store the scale point in the payScaleMap
                String key = category + "," + role;
                payScaleMap.put(key, scalePoint);

                // Store the cleaned rate in the rateMap
                rateMap.put(key, cleanedRate);
            }
        }

        br.close();
    }

    // Method to manually parse a CSV line, handling quoted values
    private static String[] parseCSVLine(String line) {
        // Split the line by commas, but handle quoted fields
        StringBuilder value = new StringBuilder();
        boolean insideQuote = false;
        List<String> values = new ArrayList<>();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                insideQuote = !insideQuote; // Toggle quote status
            } else if (c == ',' && !insideQuote) {
                values.add(value.toString().trim());
                value.setLength(0); // Reset value for the next field
            } else {
                value.append(c);
            }
        }
        // Add the last field (after the last comma or at the end of the line)
        values.add(value.toString().trim());
        return values.toArray(new String[0]);
    }

    // Helper method to clean the rate string (remove currency symbols and commas)
    private static String cleanRate(String rate) {
        return rate.replace("€", "").replace(",", "").trim(); // Remove € symbol, commas, and trim spaces
    }


    // Method to calculate the USC based on the salary and return it as a string
    private static String calculateUSC(double salary) {
        int usc = 0;

        if (salary <= 12012) {
            usc = (int) (salary * 0.005); // 0.5% for income up to €12,012
        } else if (salary <= 25760) {
            usc = (int) (12012 * 0.005 + (salary - 12012) * 0.02); // 2% for income from €12,012.01 to €25,760
        } else if (salary <= 70044) {
            usc = (int) (12012 * 0.005 + (25760 - 12012) * 0.02 + (salary - 25760) * 0.04); // 4% for income from €25,760.01 to €70,044
        } else {
            usc = (int) (12012 * 0.005 + (25760 - 12012) * 0.02 + (70044 - 25760) * 0.04 + (salary - 70044) * 0.08); // 8% for income over €70,044.01
        }

        return Integer.toString(usc); // Return USC as a string
    }

    // Method to calculate the PRSI (4.1% of salary) and return it as a string
    private static String calculatePRSI(double salary) {
        int prsi = (int) (salary * 0.041); // PRSI is 4.1% of salary
        return Integer.toString(prsi); // Return PRSI as a string
    }
}
