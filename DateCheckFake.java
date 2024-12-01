package tests;

import csv.CSVWriter;
import csv.SimpleCSVReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * This class checks the date. If it's the 25th day of the month, payslips are generated.
 * If it's August, employees are moved up the payscale.
 * this code is a copy of DateCheck wich is made by Samuel luke
 * DateCheckFake was made by Saif for testing purposes to allow flexibility with dates inputted
 * diffrence is this class will take a parameter of date
 * @author Samuel Luke
 */
public class DateCheckFake {
    private final String path = "csv/csv files/completedPayslipsOrPromotion.csv";
    private final int payslipsCol = 0;
    private final int promotionCol = 1;
    private List<String[]> completed = new SimpleCSVReader().readCsvPayScale(1, path);
    public boolean completedPayslips = Boolean.parseBoolean(completed.getFirst()[payslipsCol]);
    public boolean completedPromotion = Boolean.parseBoolean(completed.getFirst()[promotionCol]);
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
        //System.out.println(completedPayslips);
        //System.out.println(completedPromotion);

    }

    private void changeValue(String value, int col) {
        CSVWriter.updateCSVCell(path, 0, col, value);
    }

    private String getFormattedDate(String pattern) {
        LocalDate currentDate = TestDate;
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
        // Check for payslips generation: should be on the 25th day of the month
        if (check("payslip") && !completedPayslips) {
            // Generate payslips logic
            CSVWriter csvwrite = new CSVWriter();
            csvwrite.writeCsvPaySlipGen();
            changeValue("true", payslipsCol);
        } else if (!check("payslip")) {
            // If it's not the 25th, update to false
            changeValue("false", payslipsCol);
        }
    }

    private void runPromotion() {
        // Check for promotion: should be in the month of August
        if (check("promotion") && !completedPromotion) {
            // Promotion logic
            CSVWriter csvWrite = new CSVWriter();
            csvWrite.writeToCSVPayScale();
            changeValue("true", promotionCol);
        } else if (!check("promotion")) {
            // If it's not August, update to false
            changeValue("false", promotionCol);
        }
    }


}