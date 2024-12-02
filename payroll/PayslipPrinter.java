package payroll;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class prints all the payslips for a chosen employee.
 * @author Samuel Luke
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


    // filters payslips for given employee from all payslips
    private void getEmployeePayslips() {
        for (String[] payslip : payslips) {
            if (payslip.length > 1 && payslip[1].equals(employeeName) && payslip[2].equals(employeeNumber)) {
                employeePayslips.add(payslip);
            }
        }
    }



    // gets payslips for all employees
    private void getAllPayslips() {
        try (BufferedReader br = new BufferedReader(new FileReader("csv/csv files/Payslips.csv"))) {
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


    // prints all of employees payslips
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
            String PAYE = payslip[9];
            String netPay = payslip[10];
            String hours = "Hours: " + payslip[11];
            String totalDeductions = String.valueOf(
                    Double.parseDouble(USC)
                    + Double.parseDouble(PRSI)
                    + Double.parseDouble(PAYE));
            String border = "---------------------------------";

            System.out.printf("%s%s\n\n", border, border);
            System.out.printf("%-30s %30s \n\n%-30s %30s \n\n%-30s %30s \n\n%s \n\n",
                    date, name, ppsNo, jobCategory, jobRole, payScale, hours);
            System.out.printf("%-30s\n",border);
            System.out.printf("|%-20s|%10s|\n", "Gross", grossPay);
            System.out.printf("|%-20s|%10s|\n", "USC", USC);
            System.out.printf("|%-20s|%10s|\n", "PRSI", PRSI);
            System.out.printf("|%-20s|%10s|\n", "PAYE", PAYE);
            System.out.printf("|%-20s|%10s|\n", "Total Deductions", totalDeductions);
            System.out.printf("|%-20s|%10s|\n", "Net", netPay);
            System.out.printf("%-30s\n",border);
        }
    }
}
