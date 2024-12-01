package Checkers;

import csv.CSVWriter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class checks the date. If it's the 25th day of the month, payslips are generated.
 * If it's August, employees are moved up the payscale.
 * this code is a copy of DateCheck wich is made by Samuel luke
 * DateCheckFake was made by Saif for testing purposes to allow flexibility with dates inputted
 * diffrence is this class will take a parameter of date
 * @author Samuel Luke
 */
public class DateCheckFake {
    public boolean completedPayslips = false;
    public boolean completedPromotion = false;
    private LocalDate TestDate;

    // changed  completedPayslips and completedPromotion from private to public temporarly for tester class
    //

    /**
     * This constructor creates an instance of the Checkers.DateCheck class.
     */
    public DateCheckFake(LocalDate TestDate) {
        this.TestDate = TestDate;
        runPayslips();
        runPromotion();
    }

    private String getFormattedDate(String pattern) {
        LocalDate currentDate = TestDate; // mistake was here (mistake is forgot to change local date now to test date)
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


            completedPayslips = true;
        } else if (check("payslip") == false) {
            completedPayslips = false;
        }
    }

    private void runPromotion() {

        if ((check("promotion") == true) && (completedPromotion == false)) {
            //employee.PayscalePromoter payscalePromoter = new employee.PayscalePromoter();
            //payscalePromoter.writeToCSVPayScale();
            CSVWriter csvWrite = new CSVWriter();
            csvWrite.writeToCSVPayScale();

            completedPromotion = true;
        } else if (check("promotion") == false) {
            completedPromotion = false;
        }
    }

}