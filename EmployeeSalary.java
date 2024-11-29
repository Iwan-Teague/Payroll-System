import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class handles the salary calculations for employees, including tax calculations (USC, PRSI, PAYE),
 * loading pay scales and rates from a CSV file, and processing employee data.
 * <p>
 * It reads employee details from a CSV file, matches their pay scale and salary based on their job category
 * and role, calculates relevant taxes, and computes the after-tax salary.
 * </p>
 */
public class EmployeeSalary {

    private static Map<String, Integer> payScaleMap = new HashMap<>();
    private static Map<String, String> rateMap = new HashMap<>();

    /**
     * Retrieves a list of employee salaries along with their tax details and after-tax salary.
     * <p>
     * This method loads the pay scales and rates, processes the employees, calculates taxes, and stores the results.
     * </p>
     *
     * @return an {@code ArrayList<String[]>} where each {@code String[]} contains employee details, including name, PPS number, job category, role,
     * pay scale, salary, USC, PRSI, PAYE, and after-tax salary.
     */
    public static List<String[]> getSalaries() {
        List<String[]> salaries = new ArrayList<>();
        try {
            // Load pay scales and rates from the CSV file
            loadPayScalesAndRates();

            // Process employees and retrieve their data
            List<Employee> employees = processEmployees();

            // Put each employee's details into the ArrayList
            for (Employee employee : employees) {
                String[] person = {
                        employee.getName(),
                        employee.getPPSno(),
                        String.valueOf(employee.getJobCategory()),
                        String.valueOf(employee.getJobRole()),
                        String.valueOf(employee.getPayScale()),
                        employee.getSalary(),
                        employee.getUSC(),
                        employee.getPRSI(),
                        employee.getPAYE(),
                        String.valueOf(employee.getAfterTaxSalary())
                };

                // Check if any value in person[] is null
                boolean hasNullValue = false;
                for (String field : person) {
                    if (field == null) {
                        hasNullValue = true;
                        break;
                    }
                }

                // Add to salaries list only if no null values are present
                if (!hasNullValue) {
                    salaries.add(person);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return salaries;
    }

    /**
     * The main method that serves as the entry point for the application. It loads the pay scales and rates,
     * processes employee data, and prints out the employee details.
     * <p>
     * This method is intended to display employee salary details and tax calculations for each employee.
     * </p>
     *
     * @param args command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        try {
            // Load pay scales and rates from the CSV file
            loadPayScalesAndRates();

            // Process employees and retrieve their data
            List<Employee> employees = processEmployees();

            // Print employee details
            for (Employee employee : employees) {
                System.out.println("Employee: " + employee.getName() +
                        ", Role: " + employee.getJobRole() +
                        ", PayScale: " + employee.getPayScale() +
                        ", Rate: " + employee.getSalary() +
                        ", USC: " + employee.getUSC() +
                        ", PRSI: " + employee.getPRSI() +
                        ", PAYE: " + employee.getPAYE() +
                        ", After-Tax Salary: " + employee.getAfterTaxSalary());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes employee data by reading the CSV file, matching each employee's job category and role
     * to the correct pay scale and salary rate, then calculating taxes and after-tax salary.
     *
     * @return a list of {@link Employee} objects containing the processed data.
     * @throws IOException if an error occurs while reading the CSV file.
     */
    public static List<Employee> processEmployees() throws IOException {
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

            // set salary of employee object
            employee.setSalary(rate);

            if (payScale != null && rate != null) {
                int salary = Integer.parseInt(rate);

                // Set tax details and after-tax salary
                setTaxDetailsAndSalary(employee, salary);

                // Set additional employee data
                employee.setPayScale(payScale);
                employee.setSalary(rate);
            }
        }

        return employees;
    }

    // Method to set tax details and after-tax salary for an employee
    private static void setTaxDetailsAndSalary(Employee employee, int salary) {
        // Calculate taxes
        String usc = calculateUSC(salary);
        String prsi = calculatePRSI(salary);
        String paye = calculatePAYE(salary);

        // Calculate after-tax salary
        int afterTaxSalary = salary - Integer.parseInt(usc) - Integer.parseInt(prsi) - Integer.parseInt(paye);

        // Set tax details and after-tax salary on the employee object
        employee.setUSC(usc);
        employee.setPRSI(prsi);
        employee.setPAYE(paye);
        employee.setAfterTaxSalary(afterTaxSalary);
    }

    // Load pay scales and rates from the CSV file
    public static void loadPayScalesAndRates() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("ULPayScales.csv"));
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

                // Parse the scale point to make sure it's an int
                int scalePoint = Integer.parseInt(scalePointStr);

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

    /**
     * Calculates the USC (Universal Social Charge) tax based on the employee's salary.
     *
     * @param salary the employee's salary used to calculate the USC tax.
     * @return the USC tax as a string.
     */
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

    /**
     * Calculates the PRSI (Pay Related Social Insurance) tax based on the employee's salary.
     *
     * @param salary the employee's salary used to calculate the PRSI tax.
     * @return the PRSI tax as a string.
     */
    public static String calculatePRSI(double salary) {
        int prsi = (int) (salary * 0.041);
        return Integer.toString(prsi);
    }

    /**
     * Calculates the PAYE (Pay As You Earn) tax based on the employee's salary.
     *
     * @param salary the employee's salary used to calculate the PAYE tax.
     * @return the PAYE tax as a string.
     */
    public static String calculatePAYE(double salary) {
        int paye = 0;

        if (salary <= 42000) {
            paye = (int) (salary * 0.20);
        } else {
            paye = (int) (42000 * 0.20 + (salary - 42000) * 0.40);
        }

        return Integer.toString(paye);
    }

    /**
     * Retrieves the pay scale point for a given department and role.
     *
     * @param department the department name (e.g., "admin").
     * @param role the role within the department (e.g., "manager").
     * @return the pay scale point for the given department and role, or null if not found.
     */
    public static Integer getPayScale(String department, String role) {
        String key = department + "," + role;
        return payScaleMap.get(key);
    }

    /**
     * Retrieves the salary rate for a given department and role.
     *
     * @param department the department name (e.g., "admin").
     * @param role the role within the department (e.g., "manager").
     * @return the salary rate for the given department and role, or null if not found.
     */
    public static String getRate(String department, String role) {
        String key = department + "," + role;
        return rateMap.get(key);
    }
}
