import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
public class CSVWriter {
    public static void writeToCSVPayScale() {
        PayscalePromoter payScale = new PayscalePromoter();
        List<String[]> data = PayscalePromoter.incrementPayScale();
        try (FileWriter writer = new FileWriter("Employees.csv")) {
            for (int i = 0; i < data.size(); i++) {
                String[] row = data.get(i);
                writer.append(String.join(",", row));
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeCsvPaySlipGen() {
        PayslipGenerator paySlipGen = new PayslipGenerator();

        List<String[]> data = paySlipGen.createPayslips();
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
