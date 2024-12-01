import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * This class allows payslips to be generated for all employees.
 */
public class PayslipGenerator {

    private DecimalFormat df = new DecimalFormat("#.00");

    private LocalDate getSecondFridayOfCurrentMonth() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get the first day of the current month
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);

        // Find the first Friday of the month
        LocalDate firstFriday = firstDayOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

        // Add 7 days to the first Friday to get the second Friday
        LocalDate secondFriday = firstFriday.plusDays(7);

        return secondFriday;
    }

    private LocalDate getSecondFridayOfPreviousMonth() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get the first day of the previous month
        LocalDate firstDayOfPreviousMonth = currentDate.minusMonths(1).withDayOfMonth(1);

        // Find the first Friday of the previous month
        LocalDate firstFriday = firstDayOfPreviousMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

        // Add 7 days to the first Friday to get the second Friday
        LocalDate secondFriday = firstFriday.plusDays(7);

        return secondFriday;
    }

    private boolean CheckDate(String dateString) {
        LocalDate currentFriday = getSecondFridayOfCurrentMonth();
        LocalDate previousFriday = getSecondFridayOfPreviousMonth();
        LocalDate date = LocalDate.parse(dateString);
        return date.isAfter(previousFriday) && (date.isBefore(currentFriday) || date.isEqual(currentFriday));
    }

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
            if (row[0].equals(ppsNo) && CheckDate(row[2])) {
                hours = Double.parseDouble(row[1]) + hours;
                total = Double.parseDouble(row[3]) + total;
            }
        }
        return new String[]{df.format(hours), df.format(total)};
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
                payslip[5] = df.format(Double.parseDouble(payslip[5]) / 12);
                payslip[6] = df.format(Double.parseDouble(payslip[6]) / 12);
                payslip[7] = df.format(Double.parseDouble(payslip[7]) / 12);
                payslip[8] = df.format(Double.parseDouble(payslip[8]) / 12);
                payslip[9] = df.format(Double.parseDouble(payslip[9]) / 12);
                hours = "160";
            } else if (payslip[2].equals("ulac")) {
                String[] hoursAndPay = getPartTimeHours(payslip[1]);
                if (!Objects.equals(hoursAndPay[0], "0") && (!Objects.equals(hoursAndPay[1], "0"))) {
                    payslip[5] = hoursAndPay[1];
                    payslip[6] = df.format((Double.parseDouble(payslip[6]) / 2080) * Double.parseDouble(hoursAndPay[0]) );
                    payslip[7] = df.format((Double.parseDouble(payslip[7]) / 2080) * Double.parseDouble(hoursAndPay[0]) );
                    payslip[8] = df.format((Double.parseDouble(payslip[8]) / 2080) * Double.parseDouble(hoursAndPay[0]) );
                    payslip[9] = df.format((Double.parseDouble(payslip[9]) / 2080) * Double.parseDouble(hoursAndPay[0]) );
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


