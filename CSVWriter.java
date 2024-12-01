import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The CSVWriter class provides methods to write data to various CSV files including employees' pay scales, payslips, and part-time employee work hours.
 */
public class CSVWriter {
    /**
     * Writes the incremented pay scale data to Employees.csv.
     * This method fetches pay scale data from the PayscalePromoter and writes it to the CSV file.
     */
    public static void writeToCSVPayScale() {
        PayscalePromoter payScale = new PayscalePromoter();
        List<String[]> data = PayscalePromoter.incrementPayScale();
        try (FileWriter writer = new FileWriter("Employees.csv")) {
            for (int i = 0; i < data.size(); i++) {
                String[] row = data.get(i);
                writer.append(String.join(",", row));
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Writes payslip generation data to Payslips.csv.
     * This method fetches payslip data from the PayslipGenerator and appends it to the Payslips.csv file.
     */
    public void writeCsvPaySlipGen() {
        PayslipGenerator paySlipGen = new PayslipGenerator();

        List<String[]> data = paySlipGen.createPayslips();
        try (FileWriter writer = new FileWriter("Payslips.csv", true)) {
            for (int i = 0; i < data.size(); i++) {
                String[] row = data.get(i);
                writer.append(String.join(",", row));
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * Writes part-time employee data (PPS number, hours worked, date, earned amount) to PartTime.csv.
     * Appends data to the end of the CSV file.
     *
     * @param ppsNo The PPS number of the part-time employee.
     * @param hours The number of hours worked by the employee.
     * @param date The date when the hours were worked.
     * @param earned The amount earned by the employee based on hours worked.
     */
    public static void writeCSVPartTime(String ppsNo, double hours, String date, double earned) {
        //path to the PartTime.csv file
        String filePath = "PartTime.csv";

        //use try to write to file
        try (FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter writer = new BufferedWriter(fileWriter)) {


            //write data ppsNo, hours, date, earned
            writer.write(ppsNo + "," + hours + "," + date + "," + earned);
            writer.newLine(); //add a new line after

            System.out.println("Part-time data written to CSV: " + ppsNo + ", " + hours + ", " + date + ", " + earned);
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    /**
     * Writes a line of data to a specified CSV file.
     * The values are passed as varargs and are written as a single line in the CSV file.
     *
     * 
     * @param filePath The path of the CSV file where the data should be written.
     * @param values The values to be written to the CSV file.
     */
    public static void writeToCSV(String filePath, String... values) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            // Convert the array of values to a CSV line
            StringBuilder csvLine = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                csvLine.append(values[i]);

                // Add a comma unless it's the last value
                if (i < values.length - 1) {
                    csvLine.append(",");
                }
            }

            // Write the line and a newline to the CSV
            writer.append(csvLine.toString()).append("\n");
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    /**
     * Updates a specific cell in a CSV file.
     *
     * @param csvFilePath The path to the CSV file.
     * @param rowIndex    The zero-based index of the row to update.
     * @param columnIndex The zero-based index of the column to update.
     * @param newValue    The new value for the cell.
     */
    public static void updateCSVCell(String csvFilePath, int rowIndex, int columnIndex, String newValue) {
        List<String[]> csvData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;

            // Read all rows into memory
            while ((line = br.readLine()) != null) {
                csvData.add(line.split(","));
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            return;
        }

        // Check if the row and column indexes are valid
        if (rowIndex < 0 || rowIndex >= csvData.size() || columnIndex < 0 || columnIndex >= csvData.get(rowIndex).length) {
            System.err.println("Invalid row or column index.");
            return;
        }

        // Update the cell
        csvData.get(rowIndex)[columnIndex] = newValue;

        // Write the updated data back to the CSV file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath))) {
            for (String[] row : csvData) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to the CSV file: " + e.getMessage());
        }

        System.out.println("Cell updated successfully!");
    }

}
