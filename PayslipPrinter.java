import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class prints all the payslips for a chosen employee.
 */
public class PayslipPrinter {
    private List<String[]> payslips = new ArrayList<>();
    private List<String[]> employeePayslips = new ArrayList<>();
    private String employeeName;
    private String employeeNumber;

    /**
     * This constructor prints all payslips for a chosen employee.
     * @param employeeName The chosen employee's name.
     * @param employeeNumber The chosen employee's PPS number.
     */
    public PayslipPrinter(String employeeName, String employeeNumber) {
        this.employeeName = employeeName;
        this.employeeNumber = employeeNumber;
        getAllPayslips();
        getEmployeePayslips();
        printPayslips();
    }

    private void getEmployeePayslips() {
        for (String[] payslip : payslips) {
            if (payslip[1].equals(employeeName) && payslip[2].equals(employeeNumber)) {
                employeePayslips.add(payslip);
            }
        }
    }



    private void getAllPayslips() {
        try (BufferedReader br = new BufferedReader(new FileReader("Payslips.csv"))) {
            String line;

            // Skip the first line (header)
            br.readLine();

            // Read the remaining lines
            while ((line = br.readLine()) != null) {
                // Split the line by commas
                String[] row = line.split(",");
                // Add the resulting array to the list
                payslips.add(row);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void printPayslips() {
        for (String[] payslip : employeePayslips) {
            if (payslip.length < 10) {
                System.err.println("Skipping incomplete payslip: " + String.join(",", payslip));
                continue;
            }

            String date = "Date: " + payslip[0];
            String name = "Name: " + payslip[1];
            String ppsNo = "PPS Number: " + payslip[2];
            String jobCategory = "Department: " + payslip[3];
            String jobRole = "Role: " + payslip[4];
            String payScale = "Pay scale level: " + payslip[5];
            String grossPay = payslip[6];
            String USC = payslip[7];
            String PRSI = payslip[8];
            String netPay = payslip[9];
            String totalDeductions = String.valueOf(
                    Integer.valueOf(USC)
                    + Integer.valueOf(PRSI)
                    + Integer.valueOf(netPay));
            String border = "---------------------------------";

            System.out.printf("%s%s\n\n", border, border);
            System.out.printf("%-30s %30s \n\n%-30s %30s \n\n%-30s %30s \n\n",
                    date, name, ppsNo, jobCategory, jobRole, payScale);
            System.out.printf("%-30s\n",border);
            System.out.printf("|%-20s|%10s|\n", "Gross", grossPay);
            System.out.printf("|%-20s|%10s|\n", "USC", USC);
            System.out.printf("|%-20s|%10s|\n", "PRSI", PRSI);
            System.out.printf("|%-20s|%10s|\n", "Total Deductions", totalDeductions);
            System.out.printf("|%-20s|%10s|\n", "Net", netPay);
            System.out.printf("%-30s\n",border);
        }
    }
}
