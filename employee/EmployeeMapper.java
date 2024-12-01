package employee;

import Checkers.Checker;

public class EmployeeMapper {
    /**
     * Parses a CSV line and creates an employee.Employee object.
     *
     * @param csvLine The CSV line containing employee data.
     * @return An employee.Employee object created from the CSV line.
     * @throws IllegalArgumentException If the line contains invalid data.
     */
    public static Employee fromCSV(String csvLine) {
        String csvSplitBy = ",";
        String[] data = csvLine.split(csvSplitBy);

        /*if (data.length < 6) {
            throw new IllegalArgumentException("Invalid CSV format. Expected 6 fields, got " + data.length);
        }*/

        try {
            // Extract data
            String name = data[0];
            String ppsNo = data[1]; // Not currently used, but available for extension
            String departmentStr = data[2];
            String roleStr = data[3];
            int payScale = Integer.parseInt(data[4].trim()); // Convert PayScale to integer
            String promotion = null;
            System.out.println(data.length + "test");
            if (data.length > 6){
                promotion = data[6];
            }
            System.out.println(promotion);

            // Map to enums
            Employee.JobCategory jobCategory = Employee.JobCategory.valueOf(departmentStr.replace(" ", ""));
            Employee.JobType jobType = Employee.JobType.valueOf(roleStr.replace(" ", ""));

            // Create and return the employee.Employee object
            if (promotion == null){
                return new Employee(name, ppsNo, jobCategory, jobType, payScale);
            } else {
                return new Employee(name, ppsNo, jobCategory, jobType, payScale,data[5], promotion);
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error parsing CSV line: " + csvLine + ". " + e.getMessage(), e);
        }
    }

    public static Employee fromCSV(String... data) {
       
        

        if (data.length < 6) {
            throw new IllegalArgumentException("Invalid CSV format. Expected 6 fields, got " + data.length);
        }

        try {
            // Extract data
            String name = data[0];
            String ppsNo = data[1]; // Not currently used, but available for extension
            String departmentStr = data[2];
            String roleStr = data[3];
            int payScale = Integer.parseInt(data[4].trim()); // Convert PayScale to integer
            String userType = data[5];

            // Map to enums
            Employee.JobCategory jobCategory = Employee.JobCategory.valueOf(departmentStr.replace(" ", ""));
            Employee.JobType jobType = Employee.JobType.valueOf(roleStr.replace(" ", ""));

            // Create and return the employee.Employee object
            return new Employee(name, ppsNo, jobCategory, jobType, payScale, userType);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error parsing CSV line: "  + ". " + e.getMessage(), e);
        }
    }

    public static Employee fromPPSno(String PPSno){
        System.out.println(PPSno);
        int row = Checker.findRowByPPS("csv/csv files/Employees.csv", PPSno, 1);
        System.out.println(row);
        String lineFromCSV = Checker.getRowByIndex("csv/csv files/Employees.csv", row);
        System.out.println(lineFromCSV);
        return fromCSV(lineFromCSV);

    }
}

