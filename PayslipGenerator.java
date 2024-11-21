import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayslipGenerator {
    SimpleCSVReader csvReader = new SimpleCSVReader();


    public List<String[]> createPayslips() {
        List<String[]> payslips = new ArrayList<>();
        List<String[]> nameNumPay = new ArrayList<>();
        nameNumPay = csvReader.readCsvPaySlip();
        

        for (int i = 1; i < nameNumPay.size(); i++) {
            String[] row = nameNumPay.get(i);
            String[] result = new String[7];

            String name = row[0];
            String PPSnum = row[1];
            //use methods from EmployeeSalary  suggestion
            double pay = (Double.parseDouble(row[2])) / 52;
            double PRSI = pay * 0.04;
            double USC = pay * 0.02;
            double PAYE = pay * 0.2;
            double net = pay - (PRSI + USC + PAYE);

            result[0] = name;
            result[1] = PPSnum;
            result[2] = String.valueOf(pay);
            result[3] = String.valueOf(PRSI);
            result[4] = String.valueOf(USC);
            result[5] = String.valueOf(PAYE);
            result[6] = String.valueOf(net);

            payslips.add(result);
        }
        return payslips;
    }

    // moved csv method to CSVWriter
}
