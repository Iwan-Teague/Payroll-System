import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateCheck {
    private boolean completedPayslips = false;
    private boolean completedPromotion = false;

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

    public void runPayslips() {
        if ((check("payslip") == true) && (completedPayslips == false)) {
            PayslipGenerator payslipGenerator = new PayslipGenerator();
            payslipGenerator.writeCsv();
            completedPayslips = true;
        } else if (check("payslip") == false) {
            completedPayslips = false;
        }
    }

    public void runPromotion() {
        if ((check("promotion") == true) && (completedPromotion == false)) {
            PayscalePromoter payscalePromoter = new PayscalePromoter();
            payscalePromoter.writeToCSV();
            completedPromotion = true;
        } else if (check("promotion") == false) {
            completedPromotion = false;
        }
    }

}
