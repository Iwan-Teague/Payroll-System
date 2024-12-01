package Checkers;

import csv.CSVWriter;
import csv.SimpleCSVReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class checks the date. If it's the 25th day of the month, payslips are generated.
 * If it's August, employees are moved up the payscale.
 * @author Samuel Luke
 */
public class DateCheck {
    private final String path = "csv/csv files/completedPayslipsOrPromotion.csv";
    private final int payslipsCol = 0;
    private final int promotionCol = 1;
    private List<String[]> completed = new SimpleCSVReader().readCsvPayScale(1, path);
    private boolean completedPayslips = Boolean.parseBoolean(completed.getFirst()[payslipsCol]);
    private boolean completedPromotion = Boolean.parseBoolean(completed.getFirst()[promotionCol]);

    /**
     * This constructor creates an instance of the Checkers.DateCheck class.
     */
    public DateCheck() {
        runPayslips();
        runPromotion();
    }

    private void changeValue(String value, int col) {
        CSVWriter.updateCSVCell(path, 0, col, value);
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
            //payroll.PayslipGenerator payslipGenerator = new payroll.PayslipGenerator();
            //payslipGenerator.writeCsv();
            CSVWriter csvwrite = new CSVWriter();
            csvwrite.writeCsvPaySlipGen();


            //completedPayslips = true;
            changeValue("true", payslipsCol);
        } else if (check("payslip") == false) {
            //completedPayslips = false;
            changeValue("false", payslipsCol);
        }
    }

    private void runPromotion() {
        if ((check("promotion") == true) && (completedPromotion == false)) {
            //employee.PayscalePromoter payscalePromoter = new employee.PayscalePromoter();
            //payscalePromoter.writeToCSVPayScale();
            CSVWriter csvWrite = new CSVWriter();
            csvWrite.writeToCSVPayScale();

            //completedPromotion = true;
            changeValue("true", promotionCol);
        } else if (check("promotion") == false) {
            //completedPromotion = false;
            changeValue("false", promotionCol);
        }
    }

}
