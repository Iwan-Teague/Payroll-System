public class EmployeeMapper {
    /**
     * Parses a CSV line and creates an Employee object.
     *
     * @param csvLine The CSV line containing employee data.
     * @return An Employee object created from the CSV line.
     * @throws IllegalArgumentException If the line contains invalid data.
     */
    public Employee fromCSV(String csvLine) {
        String csvSplitBy = ",";
        String[] data = csvLine.split(csvSplitBy);

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

            // Map to enums
            Employee.JobCategory jobCategory = Employee.JobCategory.valueOf(departmentStr.replace(" ", ""));
            Employee.JobType jobType = Employee.JobType.valueOf(roleStr.replace(" ", ""));

            // Create and return the Employee object
            return new Employee(name, ppsNo, jobCategory, jobType, payScale);

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error parsing CSV line: " + csvLine + ". " + e.getMessage(), e);
        }
    }
}

