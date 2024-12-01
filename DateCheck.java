import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class checks the date. If it's the 25th day of the month, payslips are generated.
 * If it's August, employees are moved up the payscale.
 * @author Samuel Luke
 */
public class DateCheck {
    private boolean completedPayslips = false;
    private boolean completedPromotion = false;


    /**
     * This constructor creates an instance of the DateCheck class.
     */
    public DateCheck() {
        runPayslips();
        runPromotion();
    }

    /**
     * this method reformates the date
     */
    private String getFormattedDate(String pattern) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return currentDate.format(formatter);
    }

    /**
     * This method gets formatted date  and checks if its the 25th of the month or august
     */
    private boolean check(String type) {
        String month = getFormattedDate("MM"); // checks
        String day = getFormattedDate("dd");

        return switch (type) {
            case "promotion" -> month.equals("08");
            case "payslip" -> day.equals("25");
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        };
    }
    /**
     * This method runs payslip generation method of wich is in CSVWriter
     */

    private void runPayslips() {
        if ((check("payslip") == true) && (completedPayslips == false)) {
            CSVWriter csvwrite = new CSVWriter();
            csvwrite.writeCsvPaySlipGen();


            completedPayslips = true;
        } else if (check("payslip") == false) {
            completedPayslips = false;
        }
    }
    /**
     * This method runs pay scale promotion method of wich is in CSVWriter
     */

    private void runPromotion() {

        if ((check("promotion") == true) && (completedPromotion == false)) {
            CSVWriter csvWrite = new CSVWriter();
            csvWrite.writeToCSVPayScale();

            completedPromotion = true;
        } else if (check("promotion") == false) {
            completedPromotion = false;
        }
    }

}
