import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeSalary {

    private static Map<String, Integer> payScaleMap = new HashMap<>();
    private static Map<String, String> rateMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            loadPayScalesAndRates("ULPayScales.csv");

            csvReader reader = new csvReader();
            List<Employee> employees = reader.readCSV("Employees.csv");

            for (Employee employee : employees) {
                String department = employee.getJobCategory().toString().toLowerCase();
                String role = employee.getJobRole().toString().toLowerCase();

                String key = department + "," + role;
                Integer payScale = payScaleMap.get(key);
                String rate = rateMap.get(key);

                if (payScale != null && rate != null) {
                    int salary = Integer.parseInt(rate);

                    String usc = calculateUSC(salary);
                    String prsi = calculatePRSI(salary);
                    String paye = calculatePAYE(salary);

                    // Calculate after-tax salary
                    int afterTax = salary - Integer.parseInt(usc) - Integer.parseInt(prsi) - Integer.parseInt(paye);

                    System.out.println("Employee: " + employee.getName() +
                            ", Role: " + employee.getJobRole() +
                            ", PayScale: " + payScale +
                            ", Rate: " + rate +
                            ", USC: " + usc +
                            ", PRSI: " + prsi +
                            ", PAYE: " + paye +
                            ", After-Tax Salary: " + afterTax);
                } else {
                    System.out.println("No pay scale or rate found for: " + employee.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadPayScalesAndRates(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] values = parseCSVLine(line);
            if (values.length >= 4) {
                String category = values[0].trim().toLowerCase();
                String role = values[1].trim().toLowerCase();
                String scalePointStr = values[2].trim();
                String rateStr = values[3];

                int scalePoint = 0;
                try {
                    scalePoint = Integer.parseInt(scalePointStr);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing scale point for: " + category + " - " + role);
                }

                String cleanedRate = cleanRate(rateStr);

                String key = category + "," + role;
                payScaleMap.put(key, scalePoint);
                rateMap.put(key, cleanedRate);
            }
        }

        br.close();
    }

    private static String[] parseCSVLine(String line) {
        StringBuilder value = new StringBuilder();
        boolean insideQuote = false;
        List<String> values = new ArrayList<>();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                insideQuote = !insideQuote;
            } else if (c == ',' && !insideQuote) {
                values.add(value.toString().trim());
                value.setLength(0);
            } else {
                value.append(c);
            }
        }
        values.add(value.toString().trim());
        return values.toArray(new String[0]);
    }

    private static String cleanRate(String rate) {
        return rate.replace("€", "").replace(",", "").trim();
    }

    private static String calculateUSC(double salary) {
        int usc = 0;

        if (salary <= 12012) {
            usc = (int) (salary * 0.005);
        } else if (salary <= 25760) {
            usc = (int) (12012 * 0.005 + (salary - 12012) * 0.02);
        } else if (salary <= 70044) {
            usc = (int) (12012 * 0.005 + (25760 - 12012) * 0.02 + (salary - 25760) * 0.04);
        } else {
            usc = (int) (12012 * 0.005 + (25760 - 12012) * 0.02 + (70044 - 25760) * 0.04 + (salary - 70044) * 0.08);
        }

        return Integer.toString(usc);
    }

    private static String calculatePRSI(double salary) {
        int prsi = (int) (salary * 0.041);
        return Integer.toString(prsi);
    }

    private static String calculatePAYE(double salary) {
        int paye = 0;

        if (salary <= 42000) {
            paye = (int) (salary * 0.20);
        } else {
            paye = (int) (42000 * 0.20 + (salary - 42000) * 0.40);
        }

        return Integer.toString(paye);
    }
}
