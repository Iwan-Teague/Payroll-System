import java.io.BufferedWriter;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
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

    //write part-time employee data to PartTime.csv
    public static void writeCSVPartTime(String ppsNo, double hours, String date, double earned) {
        //path to the PartTime.csv file
        String filePath = "PartTime.csv";

        // Use try to handle file writing
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

}
