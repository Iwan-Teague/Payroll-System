import java.util.ArrayList;
import java.util.List;

public class PayscalePromoter {

    public static List<String[]> incrementPayScale() {
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

                if (j + 1 < JobsListModified.size()) {
                    IncrementedJobTitle = JobsListModified.get(j + 1)[1];
                    IncrementedPayScale = JobsListModified.get(j + 1)[2];

                }
                if (
                        (EmployeeJobTitle.equals(ListJobTitle))
                                && (EmployeePayScale.equals(ListPayScale))
                                && (EmployeeJobTitle.equals(IncrementedJobTitle))
                ) {
                    EmployeesList.get(i)[4] = IncrementedPayScale;
                }
            }
        }
        return EmployeesList;
    }
}
