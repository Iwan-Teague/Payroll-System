import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PayslipGenerator {
    public List<String[]> readCsv() {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Employees.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length > 5) {
                    String[] selectedFields = {fields[0], fields[1], fields[5]};
                    rows.add(selectedFields);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }

    public List<String[]> createPayslips() {
        List<String[]> payslips = new ArrayList<>();
        List<String[]> nameNumPay = new ArrayList<>();
        nameNumPay = readCsv();

        for (int i = 1; i < nameNumPay.size(); i++) {
            String[] row = nameNumPay.get(i);
            String[] result = new String[7];

            String name = row[0];
            String PPSnum = row[1];
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

    public void writeCsv() {
        List<String[]> data = createPayslips();
        try (FileWriter writer = new FileWriter("Payslips.csv", true)) {
            for (int i = 0; i < data.size(); i++) {
                String[] row = data.get(i);
                writer.append(String.join(",", row));
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


