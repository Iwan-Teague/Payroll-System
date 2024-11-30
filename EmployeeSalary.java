import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Iwan Teague
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
     * @author Iwan Teague/Samuel Luke
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
            //load pay scales and rates from the CSV file
            loadPayScalesAndRates();

            //process employees and retrieve their data
            List<Employee> employees = processEmployees();

            //put each employee's details into the ArrayList
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

                //check if any value in person[] is null
                boolean hasNullValue = false;
                for (String field : person) {
                    if (field == null) {
                        hasNullValue = true;
                        break;
                    }
                }

                //add to salaries list if there are no null values
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
     * @author Iwan Teague
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
            //load pay scales and rates from the CSV file
            loadPayScalesAndRates();
            //get employee data and save to employees
            List<Employee> employees = processEmployees();
            //print employee details
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
     * @author Iwan Teague
     * Processes employee data by reading the CSV file, matching each employee's job category and role
     * to the correct pay scale and salary rate, then calculating taxes and after-tax salary.
     *
     * @return a list of {@link Employee} objects containing the processed data.
     * @throws IOException if an error occurs while reading the CSV file.
     */
    public static List<Employee> processEmployees() throws IOException
    {
        //read employee data from CSV
        csvReader reader = new csvReader();

        List<Employee> employees = reader.readCSV("Employees.csv");

        //process each employee
        for (Employee employee : employees) {
            String department = employee.getJobCategory().toString().toLowerCase();
            String role = employee.getJobRole().toString().toLowerCase();

            //get pay scale and rate
            Integer payScale = employee.getPayScale();
            String rate = getRate(department, role);

            // set salary of employee object
            employee.setSalary(rate);

            if (payScale != null && rate != null) {
                int salary = Integer.parseInt(rate);

                // set tax details (PAYE, PRSI, USC) and after-tax salary
                setTaxDetailsAndSalary(employee, salary);

                // set payscale and salary
                employee.setPayScale(payScale);
                employee.setSalary(rate);
            }
        }
        return employees;
    }

    /**
     * @author Iwan Teague
     * Sets all tax and salary details to an employee instance, including USC,
     * PRSI, PAYE and after tax salary.
     *
     * @param employee the instance of the employee.
     * @param salary the employee's salary used to calculate and set USC, PRSI, PAYE and after tax salary.
     */
    private static void setTaxDetailsAndSalary(Employee employee, int salary) {
        //calculate taxes
        String usc = calculateUSC(salary);
        String prsi = calculatePRSI(salary);
        String paye = calculatePAYE(salary);

        //calculate after-tax salary
        int afterTaxSalary = salary - Integer.parseInt(usc) - Integer.parseInt(prsi) - Integer.parseInt(paye);

        //set tax details and after-tax salary on the employee object
        employee.setUSC(usc);
        employee.setPRSI(prsi);
        employee.setPAYE(paye);
        employee.setAfterTaxSalary(afterTaxSalary);
    }

    /**
     * @author Iwan Teague
     * Reads data from ULPayScales.csv and stores the data in the values array.
     * Data in trimmed and converted to lower case to ensure consistency.
     * Stores the scale point of an employee and salary using maps.
     */
    public static void loadPayScalesAndRates() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("ULPayScales.csv"));
        String line;

        //skip first line
        br.readLine();

        // process each line in the CSV
        while ((line = br.readLine()) != null) {
            String[] values = parseCSVLine(line);
            if (values.length >= 4) {
                String category = values[0].trim().toLowerCase();
                String role = values[1].trim().toLowerCase();
                String scalePointStr = values[2].trim();
                String rateStr = values[3];

                // make sure scale point is an int
                int scalePoint = Integer.parseInt(scalePointStr);

                // remove € and ,
                String cleanedRate = cleanRate(rateStr);

                //create a key for the department-role pair
                String key = category + "," + role;
                payScaleMap.put(key, scalePoint);
                rateMap.put(key, cleanedRate);
            }
        }

        br.close();
    }

    /**
     * @author Iwan Teague
     * Checks String to see if it contains commas or quotes and deals with them appropriatly.
     * Saves all parsed values to the values list, which is converted to an array and returned.
     *
     * @return String[] an array of String characters that contain the formatted text
     */
    private static String[] parseCSVLine(String line) {
        StringBuilder value = new StringBuilder();

        //flag to see if current character is in quotes
        boolean insideQuote = false;
        List<String> values = new ArrayList<>();

        //iterate over every character and make into array of characters
        for (char c : line.toCharArray()) {
            //check for double quotes
            if (c == '"') {
                //toggle flag
                insideQuote = !insideQuote;
            //if character = comma and flag = false
            } else if (c == ',' && !insideQuote) {
                //trim character to remove space and add to values
                values.add(value.toString().trim());
                value.setLength(0);
            } else {
                //if character not , or '
                value.append(c);
            }
        }
        //convert values to array
        values.add(value.toString().trim());
        return values.toArray(new String[0]);
    }

    /**
     * @author Iwan Teague
     * Takes a String and removes € and , characters.
     *
     * @param rate String from csv file that needs to be formatted.
     * @return newly formatted String with € and , removed.
     */
    private static String cleanRate(String rate) {
        return rate.replace("€", "").replace(",", "").trim();
    }

    /**
     * @author Iwan Teague
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
     * @author Iwan Teague
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
     * @author Iwan Teague
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
     * @author Iwan Teague
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
     * @author Iwan Teague
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
