//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class csvReader {
    public csvReader() {
    }

    public List<Employee> readCSV(String var1) throws IOException {
        ArrayList var2 = new ArrayList();
        EmployeeMapper var3 = new EmployeeMapper();
        BufferedReader var4 = new BufferedReader(new FileReader(var1));

        try {
            var4.readLine();

            String var5;
            while((var5 = var4.readLine()) != null) {
                var2.add(var3.fromCSV(var5));
            }
        } catch (Throwable var8) {
            try {
                var4.close();
            } catch (Throwable var7) {
                var8.addSuppressed(var7);
            }

            throw var8;
        }

        var4.close();
        return var2;
    }
}
