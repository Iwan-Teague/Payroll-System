import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleCSVReader {
    /**
     * Reads the contents of a CSV file and returns the lines as a list of strings.
     *
     * @param filePath Path to the CSV file.
     * @return List of lines from the CSV file.
     * @throws IOException If an error occurs while reading the file.
     */
    public List<String> readCSV(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }
}

