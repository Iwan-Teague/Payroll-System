package tests;
import csv.SimpleCSVReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateCheckTester {

    private static final String path = "csv/csv files/completedPayslipsOrPromotion.csv";
    private static final int payslipsCol = 0;
    private static final int promotionCol = 1;

    private static String getValue(int col) {
        return  new SimpleCSVReader().readCsvPayScale(1, path).getFirst()[col];
    }

    // purpose of this class is to test DateCheck
    /**
     * This class checks the date. If it's the 25th day of the month, payslips are generated.
     * If it's August, employees are moved up the payscale.
     * @author Saif Khawaldeh
     * WARNING  USES tests.DateCheckFake  the reason for this to allow flexibility for testing without changing the DateCheck class
     */
    public static void testDateCheck() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        // test case 1 random date not august or 25th
        LocalDate testVal1 = LocalDate.of(2022,2,21);  //test val1 has date of 2022/2/21
        DateCheckFake dateCheck1 = new DateCheckFake(testVal1);
        System.out.println("testing date with " + testVal1);
        System.out.println("completed pay Slips : " +  getValue(payslipsCol));// should be false
        System.out.println("completed pay scale promotions : " + getValue(promotionCol));// should be false
        LocalDate testVal2 = LocalDate.of(2022,2,25);  // val2 has date 2022/2/25  a payslip should be generated
        DateCheckFake dateCheck2 = new DateCheckFake(testVal2);
        System.out.println("testing date with " + testVal2);
        System.out.println("completed pay Slips : " + getValue(payslipsCol));// should be True
        System.out.println("completed pay scale promotions : " + getValue(promotionCol));// should be false
        // unknown error here keeps giving me error saying 8 is "integer number too large"   shouldnt be the case tho
        LocalDate testVal3 = LocalDate.of(2022,8,16); // val3 has date 2022/08/12 promotions should be run
        DateCheckFake dateCheck3 = new DateCheckFake(testVal3);
        System.out.println("testing date with " + testVal3);
        System.out.println("completed pay Slips : " + getValue(payslipsCol));// should be false
        System.out.println("completed pay scale promotions : " + getValue(promotionCol));// should be True
    }
}