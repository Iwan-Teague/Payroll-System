import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeSalary {

    private static Map<String, Integer> payScaleMap = new HashMap<>();
    private static Map<String, String> rateMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            // Load pay scales and rates from the CSV file
            loadPayScalesAndRates("ULPayScales.csv");

            // Read employee data from CSV
            csvReader reader = new csvReader();
            List<Employee> employees = reader.readCSV("Employees.csv");

            // Process each employee
            for (Employee employee : employees) {
                String department = employee.getJobCategory().toString().toLowerCase();
                String role = employee.getJobRole().toString().toLowerCase();

                // Retrieve pay scale and rate
                Integer payScale = getPayScale(department, role);
                String rate = getRate(department, role);

                if (payScale != null && rate != null) {
                    int salary = Integer.parseInt(rate);

                    // Calculate taxes and after-tax salary
                    String usc = calculateUSC(salary);
                    String prsi = calculatePRSI(salary);
                    String paye = calculatePAYE(salary);
                    int afterTaxSalary = salary - Integer.parseInt(usc) - Integer.parseInt(prsi) - Integer.parseInt(paye);

                    // Print employee details
                    System.out.println("Employee: " + employee.getName() +
                            ", Role: " + employee.getJobRole() +
                            ", PayScale: " + payScale +
                            ", Rate: " + rate +
                            ", USC: " + usc +
                            ", PRSI: " + prsi +
                            ", PAYE: " + paye +
                            ", After-Tax Salary: " + afterTaxSalary);
                } else {
                    System.out.println("No pay scale or rate found for: " + employee.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load pay scales and rates from the CSV file
    private static void loadPayScalesAndRates(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        // Skip the header line
        br.readLine();

        // Process each line in the CSV
        while ((line = br.readLine()) != null) {
            String[] values = parseCSVLine(line);
            if (values.length >= 4) {
                String category = values[0].trim().toLowerCase();
                String role = values[1].trim().toLowerCase();
                String scalePointStr = values[2].trim();
                String rateStr = values[3];

                // Parse the scale point, defaulting to 0 if it cannot be parsed
                int scalePoint = parseInteger(scalePointStr);

                // Clean the rate (removes non-numeric characters like "€")
                String cleanedRate = cleanRate(rateStr);

                // Create a key for the department-role pair
                String key = category + "," + role;
                payScaleMap.put(key, scalePoint);
                rateMap.put(key, cleanedRate);
            }
        }

        br.close();
    }

    // Parse a line of CSV data into an array of strings
    private static String[] parseCSVLine(String line) {
        StringBuilder value = new StringBuilder();
        boolean insideQuote = false;
        List<String> values = new ArrayList<>();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                insideQuote = !insideQuote;
            } else if (c == ',' && !insideQuote) {
                values.add(value.toString().trim());
                value.setLength(0);
            } else {
                value.append(c);
            }
        }
        values.add(value.toString().trim());
        return values.toArray(new String[0]);
    }

    // Clean the rate string by removing non-numeric characters
    private static String cleanRate(String rate) {
        return rate.replace("€", "").replace(",", "").trim();
    }

    // Parse integer and return default value 0 if not valid
    private static int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Calculate the USC tax based on salary
    public static String calculateUSC(double salary) {
        int usc = 0;

        if (salary <= 12012) {
            usc = (int) (salary * 0.005);
        } else if (salary <= 25760) {
            usc = (int) (12012 * 0.005 + (salary - 12012) * 0.02);
        } else if (salary <= 70044) {
            usc = (int) (12012 * 0.005 + (25760 - 12012) * 0.02 + (salary - 25760) * 0.04);
        } else {
            usc = (int) (12012 * 0.005 + (25760 - 12012) * 0.02 + (70044 - 25760) * 0.04 + (salary - 70044) * 0.08);
        }

        return Integer.toString(usc);
    }

    // Calculate the PRSI tax based on salary
    public static String calculatePRSI(double salary) {
        int prsi = (int) (salary * 0.041);
        return Integer.toString(prsi);
    }

    // Calculate the PAYE tax based on salary
    public static String calculatePAYE(double salary) {
        int paye = 0;

        if (salary <= 42000) {
            paye = (int) (salary * 0.20);
        } else {
            paye = (int) (42000 * 0.20 + (salary - 42000) * 0.40);
        }

        return Integer.toString(paye);
    }

    // Static getter for payScaleMap
    public static Integer getPayScale(String department, String role) {
        String key = department + "," + role;
        return payScaleMap.get(key);
    }

    // Static getter for rateMap
    public static String getRate(String department, String role) {
        String key = department + "," + role;
        return rateMap.get(key);
    }
}
