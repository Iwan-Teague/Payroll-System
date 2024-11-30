import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * This class allows payslips to be generated for all employees.
 * @author Samuel Luke
 */
public class PayslipGenerator {

    // Gets all necessary information about each employee from the EmployeeSalary class
    private List<String[]> getDetails() {
        List<String[]> details = new EmployeeSalary().getSalaries();
        return details;
    }

    private String[] getPartTimeHours(String ppsNo) {
        double hours = 0;
        double total = 0;

        List<String[]> partTimeDetails = new SimpleCSVReader().readCsvPayScale(3, "PartTime.csv");

        for (String[] row : partTimeDetails) {
            // NEED TO ADD IN DATE CONDITION
            if (row[0].equals(ppsNo)) {
                hours = Double.parseDouble(row[1]) + hours;
                total = Double.parseDouble(row[3]) + total;
            }
        }

        return new String[]{String.valueOf(hours), String.valueOf(total)};
    }

    /**
     * Creates payslips for all employees.
     * @return an {@code ArrayList<String[]>} where each {@code String[]} contains Date, Name, PPSNo., JobCategory, JobRole, PayScale, GrossPay, USC, PRSI, PAYE and NetPay
     * for each employee.
     */
    public List<String[]> createPayslips() {
        List<String[]> details = getDetails();
        List<String[]> payslips = new ArrayList<>();
        String date = String.valueOf(LocalDate.now());


        for (String[] payslip : details) {
            String hours = "";
            if (!Objects.equals(payslip[2], "ulac")) {
                // divides pay details by 12 to calculate payslips pay & tax
                payslip[5] = String.valueOf(Integer.valueOf(payslip[5]) / 12);
                payslip[6] = String.valueOf(Integer.valueOf(payslip[6]) / 12);
                payslip[7] = String.valueOf(Integer.valueOf(payslip[7]) / 12);
                payslip[8] = String.valueOf(Integer.valueOf(payslip[8]) / 12);
                payslip[9] = String.valueOf(Integer.valueOf(payslip[9]) / 12);
                hours = "160";
            } else if (payslip[2].equals("ulac")) {
                String[] hoursAndPay = getPartTimeHours(payslip[1]);
                if (!Objects.equals(hoursAndPay[0], "0") && (!Objects.equals(hoursAndPay[1], "0"))) {
                    payslip[5] = hoursAndPay[1];
                    payslip[6] = String.valueOf((Integer.valueOf(payslip[6]) / 2080) * Double.parseDouble(hoursAndPay[0]) );
                    payslip[7] = String.valueOf((Integer.valueOf(payslip[7]) / 2080) * Double.parseDouble(hoursAndPay[0]) );
                    payslip[8] = String.valueOf((Integer.valueOf(payslip[8]) / 2080) * Double.parseDouble(hoursAndPay[0]) );
                    payslip[9] = String.valueOf((Integer.valueOf(payslip[9]) / 2080) * Double.parseDouble(hoursAndPay[0]) );
                    hours = hoursAndPay[0];
                }
            }

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
                    payslip[9],
                    hours};

            // Adding finalPayslip[] to ArrayList
            payslips.add(finalPayslip);
        }
        return payslips;
    }
}


