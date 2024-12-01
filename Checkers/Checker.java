package Checkers;

import employee.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Collection of methods for checking if formatting
 */

public class Checker {

    /**
     * Private constructor to prevent instantiation.
     */
    private Checker() {
        // Prevent instantiation
    }
    
    /**
     * Validates the format of a PPS number.
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

    /**
     *Checks whether a given string matches the name a department.
     *
     * @param value the string to be compared
     * @param enumClass the employee department value
     * @return True or False
     */
    public static boolean isStringInDepartment(String value, Class<Employee.JobCategory> enumClass) {
        for (Employee.JobCategory department : enumClass.getEnumConstants()) {
            if (department.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a given string matches the name of any constant in a specified enum class.
     *
     * @param value the string to be compared
     * @param enumClass the employee job type emun
     * @return True or False
     *
     */
    public static boolean isStringInRole(String value, Class<Employee.JobType> enumClass) {
        for (Employee.JobType role : enumClass.getEnumConstants()) {
            if (role.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a string is present in a column of a CSV file.
     *
     * @param csvFilePath the file path for the csv
     * @param columnIndex the column to check
     * @param targetString the string to search for in the specified column
     * @return True or False
     *
     */
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

    /**
     * Finds the highest scale point for a specified role in a CSV file.
     *
     * @param csvFilePath the file path for the csv
     * @param targetRole the role for which the highest scale point is to be found
     * @return the highest scale point for the specified role, or if the role is not found
     *         or if the scale point data is unavailable
     */
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

    /**
     * Checks if a given string can be parsed as an integer.
     *
     * @param str the string to be checked
     * @return True or False
     *
     */
    public static boolean canBeInteger(String str) {
        try {
            Integer.parseInt(str);
            return true; // Parsing successful
        } catch (NumberFormatException e) {
            return false; // Parsing failed
        }
    }

    /**
     * Finds the row number of a given PPS number in employee.csv
     *
     * @param csvFilePath the file path for the csv
     * @param ppsNumber the PPS number to search for
     * @param ppsColumnIndex the index of the column containing PPS numbers
     * @return the row number of the PPS number, or if the PPS number is not found
     *
     */
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
     * @param csvFilePath the file path for the csv.
     * @param rowIndex The row to retrieve.
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
