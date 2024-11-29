import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Checker {
    
    /**
     * Validates the format of a PPS number.
     * @param ppsNumber The PPS number to check.
     * @return true if the format is correct, false otherwise.
     */
    public static boolean isValidPPS(String ppsNumber) {
        if (ppsNumber == null || ppsNumber.isEmpty()) {
            return false; // Null or empty strings are invalid
        }

        // Regular expression to match PPS format: 7 digits followed by 1-2 uppercase letters
        String ppsPattern = "^[0-9]{7}[A-Z]{1,2}$";

        // Check if the PPS number matches the pattern
        return ppsNumber.matches(ppsPattern);
    }

    public static boolean isStringInDepartment(String value, Class<Employee.JobCategory> enumClass) {
        for (Employee.JobCategory department : enumClass.getEnumConstants()) {
            if (department.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStringInRole(String value, Class<Employee.JobType> enumClass) {
        for (Employee.JobType role : enumClass.getEnumConstants()) {
            if (role.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStringInCSVColumn(String csvFilePath, int columnIndex, String targetString) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into columns
                String[] columns = line.split(",");

                // Check if the column index is valid
                if (columnIndex < columns.length) {
                    // Compare the target string with the column value
                    if (columns[columnIndex].trim().equals(targetString)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }
        return false;
    }

    public static int findHighestScalePoint(String csvFilePath, String targetRole) {
        int highestScalePoint = -1; // Default value if role is not found

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;

            // Skip the header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Split the line into columns
                String[] columns = line.split(",");

                if (columns.length < 3) {
                    continue;
                }

                // Extract relevant fields (ROLE at index 1, SCALE POINT at index 2)
                String role = columns[1].trim();
                int scalePoint = Integer.parseInt(columns[2].trim());

                // Check if the role matches the target role
                if (role.equals(targetRole)) {
                    // Update the highest scale point
                    highestScalePoint = Math.max(highestScalePoint, scalePoint);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing scale point: " + e.getMessage());
        }

        return highestScalePoint;
    }

    public static boolean canBeInteger(String str) {
        try {
            Integer.parseInt(str);
            return true; // Parsing successful
        } catch (NumberFormatException e) {
            return false; // Parsing failed
        }
    }
}
