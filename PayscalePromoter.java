import java.util.ArrayList;
import java.util.List;

public class PayscalePromoter {

    public List<String[]> incrementPayScale() {
        SimpleCSVReader csvReader = new SimpleCSVReader();
        List<String[]> EmployeesList = csvReader.readCsvPayScale(5, "Employees.csv");
        List<String[]> JobsList = csvReader.readCsvPayScale(3, "ULPayScales.csv");

        List<String[]> JobsListModified = new ArrayList<>();

        for (String[] list : JobsList) {
            String[] newarr = new String[3];
            for (int i = 0; i < 3; i++) {
                newarr[i] = list[i];
            }
            JobsListModified.add(newarr);
        }

        for (int i = 1; i < EmployeesList.size(); i++) {
            String EmployeeJobTitle = EmployeesList.get(i)[3];
            String EmployeePayScale = EmployeesList.get(i)[4];
            for (int j = 1; j < JobsListModified.size(); j++) {
                String ListJobTitle = JobsListModified.get(j)[1];
                String ListPayScale = JobsListModified.get(j)[2];

                String IncrementedJobTitle = "";
                String IncrementedPayScale = "";
                String IncrementedPay = "";

                if (j + 1 < JobsList.size()) {
                    IncrementedJobTitle = JobsList.get(j + 1)[1];
                    IncrementedPayScale = JobsList.get(j + 1)[2];
                    IncrementedPay = JobsList.get(j + 1)[3];
                }
                if (
                        (EmployeeJobTitle.equals(ListJobTitle))
                                && (EmployeePayScale.equals(ListPayScale))
                                && (EmployeeJobTitle.equals(IncrementedJobTitle))
                ) {
                    EmployeesList.get(i)[4] = IncrementedPayScale;
                    EmployeesList.get(i)[5] = IncrementedPay;
                }
            }
        }
        return EmployeesList;
    }
    // write csv method moved to CSVWriter
    // read csv method moved to SimpleCSVReader



}
