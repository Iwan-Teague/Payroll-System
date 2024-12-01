package CLI;

import employee.Employee;
import employee.EmployeeMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Login {
    private String csvFile;
    private EmployeeMapper employeeMapper = new EmployeeMapper();

    public Login(String csvFile) {
        this.csvFile = csvFile;
    }

    public Employee authenticateEmployee(String name, String ppsNo) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(name) && data[1].equals(ppsNo)) {
                    System.out.println(line);
                    Employee employee = EmployeeMapper.fromCSV(line);
                    System.out.println(employee.toString());
                    return employee;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean authenticate(String name, String ppsNo, String userType) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
             
                String[] data = line.split(",");
                
                if (data[0].equals(name) && data[1].equals(ppsNo) && data[5].equals(userType)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
