public class PartTimeHourCalculator {
    public String ppsNo;
    public String hours;
    public String date;
    public String earned;
    public String department;
    public String role;

    public String getDepartment(String ppsNo) {
        //read Employee.csv column 2 (ppsNo) and get column 3 (department)
        return department;
    }

    public String getRole(String ppsNo) {
        //read Employee.csv column 2 (ppsNo) and get column 4 (role)
        return role;
    }

    public PartTimeHourCalculator(String ppsNo, String hours, String date) {
        EmployeeSalary.getPayScale(department, role);

    }
}
