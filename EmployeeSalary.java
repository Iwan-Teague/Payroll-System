import java.io.*;
import java.util.*;


class PayScale {
    String category;
    String role;
    int payScale;
    double rate;

    public PayScale(String category, String role, int payScale, double rate) {
        this.category = category;
        this.role = role;
        this.payScale = payScale;
        this.rate = rate;
    }
}

// store employee data and payscale dar=ta in array lists
public class EmployeeSalary {
    private List<Employee> employees = new ArrayList<>();
    private List<PayScale> payScales = new ArrayList<>();

    // put employee data from Employees.csv in arraylist
    public void loadEmployees(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                String ppsNo = parts[1].trim();
                Employee.JobCategory category = Employee.JobCategory.valueOf(parts[2].trim());
                Employee.JobType role = Employee.JobType.valueOf(parts[3].trim());
                int payScale = Integer.parseInt(parts[4].trim());
                employees.add(new Employee(name, ppsNo, category, role, payScale));
            }
        }
    }

    // put salary data from ULPayScales.csv in payScale arraylist
    public void loadPayScales(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String category = parts[0].trim();
                String role = parts[1].trim();
                int payScale = Integer.parseInt(parts[2].trim());
                double rate = Double.parseDouble(parts[3].replace("\u20ac", "").replace(",", "").trim());
                payScales.add(new PayScale(category, role, payScale, rate));
            }
        }
    }

    // Calculate the salary for an employee
    public double getSalary(Employee employee) {
        for (PayScale ps : payScales) {
            if (ps.category.equals(employee.getJobCategory().name()) &&
                    ps.role.equals(employee.getJobRole().name()) &&
                    ps.payScale == employee.getPayScale()) {
                return ps.rate;
            }
        }
        throw new IllegalArgumentException("PayScale not found for: " + employee.getName());
    }

    // Print salaries for all employees
    public void printSalaries() {
        for (Employee emp : employees) {
            try {
                double salary = getSalary(emp);
                System.out.println(emp.getName() + "'s Salary: €" + salary);
            } catch (IllegalArgumentException e) {
                System.out.println("Error calculating salary for " + emp.getName() + ": " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        EmployeeSalary manager = new EmployeeSalary();
        try {
            // Load data from CSV files
            manager.loadEmployees("Employees.csv");
            manager.loadPayScales("ULPayScales.csv");

            // Print salaries for all employees
            manager.printSalaries();
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}