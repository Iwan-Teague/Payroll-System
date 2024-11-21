import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateCheck {
    private boolean completedPayslips = false;
    private boolean completedPromotion = false;

    public DateCheck() {
        runPayslips();
        runPromotion();
    }

    private String getFormattedDate(String pattern) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return currentDate.format(formatter);
    }

    private boolean check(String type) {
        String month = getFormattedDate("MM");
        String day = getFormattedDate("dd");

        return switch (type) {
            case "promotion" -> month.equals("08");
            case "payslip" -> day.equals("25");
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        };
    }

    private void runPayslips() {
        if ((check("payslip") == true) && (completedPayslips == false)) {
            //PayslipGenerator payslipGenerator = new PayslipGenerator();
            //payslipGenerator.writeCsv();
            CSVWriter csvwrite = new CSVWriter();
            csvwrite.writeCsvPaySlipGen();


            completedPayslips = true;
        } else if (check("payslip") == false) {
            completedPayslips = false;
        }
    }

    private void runPromotion() {

        if ((check("promotion") == true) && (completedPromotion == false)) {
            //PayscalePromoter payscalePromoter = new PayscalePromoter();
            //payscalePromoter.writeToCSVPayScale();
            CSVWriter csvWrite = new CSVWriter();
            csvWrite.writeToCSVPayScale();

            completedPromotion = true;
        } else if (check("promotion") == false) {
            completedPromotion = false;
        }
    }

}
