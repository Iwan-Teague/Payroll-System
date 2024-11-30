import java.io.*;
import java.util.*;

/**
 * @author Iwan Teague
 * The PartTimeHourCalculator class is responsible for calculating the earned amount for part-time employees
 * based on their department, role, and hours worked. It also manages employee data and writes the results
 * to a CSV file.
 */
public class PartTimeHourCalculator {

    private List<Employee> employeeList; //store all employees

    /**
     * @author Iwan Teague
     * Main method to test the writePartTime functionality with sample data.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {

        //test writePartTime with sample data. this data will be passed to functions
        String targetPPSNo = "1212121K"; // example ppsNo
        String hoursWorkedStr = "8.0"; // example hours (as String)
        String date = "2024-11-29"; // example date

        //create a PartTimeHourCalculator and call writePartTime with given data
        PartTimeHourCalculator calculator = new PartTimeHourCalculator();
        calculator.writePartTime(targetPPSNo, hoursWorkedStr, date);
    }

    /**
     * @author Iwan Teague
     * Calculates the earned amount for a part-time employee based on their department, role, and hours worked.
     *
     * @param department The department of the employee.
     * @param role The role of the employee.
     * @param hoursWorked The number of hours worked by the employee.
     * @return The earned amount for the employee, rounded to two decimal places.
     */
    public static double calculateEarned(String department, String role, double hoursWorked) {
        //get list of salaries from EmployeeSalary
        List<String[]> salaries = EmployeeSalary.getSalaries();

        //get the salary scale based on department and role
        String salaryRateStr = null;
        for (String[] salary : salaries) {
            String empDepartment = salary[2]; // job category
            String empRole = salary[3]; //jobRole

            if (empDepartment.equalsIgnoreCase(department) && empRole.equalsIgnoreCase(role)) {
                salaryRateStr = salary[5]; //salary
                break;
            }
        }

        if (salaryRateStr == null) {
            System.out.println("Salary rate not found for the department: " + department + " and role: " + role);
            return 0.0;
        }

        // change salary rate to double for calculations.
        double salaryRate = 0.0;
        try {
            salaryRate = Double.parseDouble(salaryRateStr);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing salary rate: " + salaryRateStr);
            return 0.0;
        }

        //calculate earned based on hours worked
        double earnedAmount = Math.round(((salaryRate / 40 / 52) // divide by 40-hour work week and 52 weeks
                * hoursWorked) * 100.0) / 100.0; // round to two decimal places
        return earnedAmount;
    }

    /**
     * @author Iwan Teague
     * Writes the part-time employee's worked hours and earnings to a CSV file.
     *
     * @param ppsNo The PPS number of the employee.
     * @param hoursWorkedStr The number of hours worked (as a String).
     * @param date The date when the hours were worked.
     */
    public static void writePartTime(String ppsNo, String hoursWorkedStr, String date) {
        // change hoursWorkedStr to double
        double hoursWorked = 0.0;
        try {
            hoursWorked = Double.parseDouble(hoursWorkedStr);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid hours worked value.");
            return;
        }

        //create a PartTimeHourCalculator and load employees via EmployeeSalary
        PartTimeHourCalculator calculator = new PartTimeHourCalculator();

        //find employee by pps
        Employee employee = calculator.findEmployeeByPPSNo(ppsNo);
        if (employee != null) {
            System.out.println("Employee Found: " + employee);

            //get department and role for that employee
            String department = employee.getJobCategory().toString();
            String role = employee.getJobRole().toString();
            System.out.println("Department: " + department);
            System.out.println("Role: " + role);

            // check department = "ulac"
            if (!department.equalsIgnoreCase("ulac")) {
                System.out.println("Employee is not part-time worker.");
                return;
            }

            //calculate earned
            double earned = calculateEarned(department, role, hoursWorked);
            System.out.println("Total Earned for " + hoursWorked + " hours on " + date + ": " + earned);

            // call writeCSVPartTime in CSVWriter to write data to PartTime.csv
            CSVWriter.writeCSVPartTime(ppsNo, hoursWorked, date, earned);

        } else {
            System.out.println("Employee with PPS No. " + ppsNo + " not found.");
        }
    }

    /**
     * @author Iwan Teague
     * Constructor to initialize the PartTimeHourCalculator and load employees from EmployeeSalary.
     */
    public PartTimeHourCalculator() {
        employeeList = new ArrayList<>();
        loadEmployeesFromEmployeeSalary(); // load employees via EmployeeSalary
    }

    /**
     * @author Iwan Teague
     * loads employee data from EmployeeSalary and adds it to employeeList
     *
     *  @throws IOException if an error occurs while retrieving employee data from EmployeeSalary
     */
    private void loadEmployeesFromEmployeeSalary() {
        try {
            // uses processEmployees() from EmployeeSalary to get employee data
            List<Employee> employees = EmployeeSalary.processEmployees();
            employeeList.addAll(employees); //adds all employees to the list
        } catch (IOException e) {
            System.err.println("Error loading employees from EmployeeSalary: " + e.getMessage());
        }
    }

    /**
     * @author Iwan Teague
     * Finds an employee by their PPS number.
     *
     * @param ppsNo The PPS number of the employee.
     * @return The employee object if found, null if not found.
     */
    public Employee findEmployeeByPPSNo(String ppsNo) {
        for (Employee employee : employeeList) {
            if (employee.getPPSno().equals(ppsNo)) {
                return employee;
            }
        }
        return null; // null if not found
    }
}
