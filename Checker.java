import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
     * Collection of methods for checking if formatting
     *
     */

public class Checker {
    
    /**
     * Validates the format of a PPS number.
     * 
     * 
     * 
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

    public static int findRowByPPS(String csvFilePath, String ppsNumber, int ppsColumnIndex) {
        

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            int rowNumber = 0; // Row counter (0-based initially)

            // Read each row in the CSV
            while ((line = br.readLine()) != null) {
                

                // Split the row into columns
                String[] columns = line.split(",");

                // Check if the PPS Number column matches the target PPS Number
                if (columns.length > ppsColumnIndex && columns[ppsColumnIndex].trim().equals(ppsNumber)) {
                    return rowNumber; // Return the 1-based row number
                }
                rowNumber++; // Increment the row counter (1-based)
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

        return -1; // Return -1 if the PPS Number is not found
    }

    /**
     * Retrieves a row from a CSV file by its index.
     *
     * @param csvFilePath The path to the CSV file.
     * @param rowIndex    The 1-based index of the row to retrieve.
     * @return A string array representing the row, or null if the index is out of bounds or an error occurs.
     */
    public static String getRowByIndex(String csvFilePath, int rowIndex) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            int currentRow = 0;

            while ((line = br.readLine()) != null) {
                

                if (currentRow == rowIndex) {
                    // Return the String
                    return line;
                }
                currentRow++;
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

        return null; // Return null if the row index is out of bounds or an error occurs
    }
}
