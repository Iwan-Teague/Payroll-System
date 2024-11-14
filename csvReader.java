import java.util.Scanner;
public class csvReader {

    String filePath = "default file path";
    //
    public void FilePath () {

        //ask for file path
        Scanner path = new Scanner(System.in);
        System.out.println("Please enter file path to csv file");
        filePath = path.nextLine();

        //read back file path to user
        System.out.println("Your file path is: " + filePath);

        path.close();
    }

    public String getPAth() {
        return filePath;
    }


}
