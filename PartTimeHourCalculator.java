public class PartTimeHourCalculator {
    public String ppsNo;
    public String hours;
    public String date;
    public String earned;
    public static String department;
    public String role;

    // Correctly call getPPSno() on the passed Employee instance
    public String getDepartment(String ppsNo, Employee employee) {
        if (employee.getPPSno().equals(ppsNo)) { // Call on instance, not class
            return employee.getJobCategory().toString();
        } else {
            throw new IllegalArgumentException("PPS number does not match the employee record.");
        }
    }

    public String getRole(String ppsNo) {
        // Placeholder for implementation to read from a CSV file
        return role;
    }

    public PartTimeHourCalculator(String ppsNo, String hours, String date) {
        // This is dependent on EmployeeSalary and its implementation
        EmployeeSalary.getRate(department, role);
    }
}
