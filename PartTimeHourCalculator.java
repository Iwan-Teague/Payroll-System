import java.io.*;
import java.util.*;

public class PartTimeHourCalculator {

    private List<Employee> employeeList; // Store all employees

    //example of how to use the methods.
    public static void main(String[] args) {

        //test writePartTime with sample data. this data will be passed to functions
        String targetPPSNo = "1234567A"; // example ppsNo
        double hoursWorked = 20.0; //example hours
        String date = "2024-11-29"; // example date

        //create a PartTimeHourCalculator and call writePartTime with given data
        PartTimeHourCalculator calculator = new PartTimeHourCalculator();
        calculator.writePartTime(targetPPSNo, hoursWorked, date);
    }

    //calculate earned amount for part-time employees based on department, role, and hours worked
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

        // Try to parse the salary rate string into a double
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

    public void writePartTime(String ppsNo, double hoursWorked, String date) {
        //create a PartTimeHourCalculator and load employees via EmployeeSalary
        PartTimeHourCalculator calculator = new PartTimeHourCalculator();

        //find emplouyee pps
        Employee employee = calculator.findEmployeeByPPSNo(ppsNo);
        if (employee != null) {
            System.out.println("Employee Found: " + employee);

            //get department and role for that employee
            String department = employee.getJobCategory().toString();
            String role = employee.getJobRole().toString();
            System.out.println("Department: " + department);
            System.out.println("Role: " + role);

            //calculate earned
            double earned = calculateEarned(department, role, hoursWorked);
            System.out.println("Total Earned for " + hoursWorked + " hours on " + date + ": " + earned);

            // Call writeCSVPartTime in CSVWriter to write data to PartTime.csv
            CSVWriter.writeCSVPartTime(ppsNo, hoursWorked, date, earned);

        } else {
            System.out.println("Employee with PPS No. " + ppsNo + " not found.");
        }
    }

    // constructor for employees using EmployeeSalary
    public PartTimeHourCalculator() {
        employeeList = new ArrayList<>();
        loadEmployeesFromEmployeeSalary(); // load employees via EmployeeSalary
    }

    // load employees using processEmployees from EmployeeSalary
    private void loadEmployeesFromEmployeeSalary() {
        try {
            // this method uses processEmployees() from EmployeeSalary to get employee data
            List<Employee> employees = EmployeeSalary.processEmployees();
            employeeList.addAll(employees); //adds all employees to the list
        } catch (IOException e) {
            System.err.println("Error loading employees from EmployeeSalary: " + e.getMessage());
        }
    }

    //get employee ppsNo
    public Employee findEmployeeByPPSNo(String ppsNo) {
        for (Employee employee : employeeList) {
            if (employee.getPPSno().equals(ppsNo)) {
                return employee;
            }
        }
        return null; // null if not found
    }
}
