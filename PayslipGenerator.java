import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayslipGenerator {

    // Gets all necessary information about each employee from the EmployeeSalary class
    public List<String[]> getDetails() {
        List<String[]> details = new EmployeeSalary().getSalaries();
        return details;
    }

    // Converts salary details into suitable payslip details
    public List<String[]> createPayslips() {
        List<String[]> details = getDetails();
        List<String[]> payslips = new ArrayList<>();
        String date = String.valueOf(LocalDate.now());


        for (String[] payslip : details) {
            // divides pay details by 12 to calculate payslips pay & tax
            payslip[5] = String.valueOf(Integer.valueOf(payslip[5]) / 12);
            payslip[6] = String.valueOf(Integer.valueOf(payslip[6]) / 12);
            payslip[7] = String.valueOf(Integer.valueOf(payslip[7]) / 12);
            payslip[8] = String.valueOf(Integer.valueOf(payslip[8]) / 12);
            payslip[9] = String.valueOf(Integer.valueOf(payslip[9]) / 12);

            // Adding all necessary values to payslip String[]
            String[] finalPayslip = {
                    date,
                    payslip[0],
                    payslip[1],
                    payslip[2],
                    payslip[3],
                    payslip[4],
                    payslip[5],
                    payslip[6],
                    payslip[7],
                    payslip[8],
                    payslip[9]};

            // Adding finalPayslip[] to ArrayList
            payslips.add(finalPayslip);
        }
        return payslips;
    }

    // Writes a new payslip to
    public void writeCsv() {
        List<String[]> data = createPayslips();
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
}


