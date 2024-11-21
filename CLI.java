import java.util.Scanner;

public class CLI {

    static Scanner scanner = new Scanner(System.in);
    static Login login = new Login("Employees.csv");
    




    //ascci art titles
    private static void printStartScreen(){
        System.out.println("  ____ ____  _____ _  _     _   _ _       ____                       _ _ \r\n" + //
                        " / ___|  _ \\|___ /| || |   | | | | |     |  _ \\ __ _ _   _ _ __ ___ | | |\r\n" + //
                        "| |  _| |_) | |_ \\| || |_  | | | | |     | |_) / _` | | | | '__/ _ \\| | |\r\n" + //
                        "| |_| |  _ < ___) |__   _| | |_| | |___  |  __/ (_| | |_| | | | (_) | | |\r\n" + //
                        " \\____|_| \\_\\____/   |_|    \\___/|_____| |_|   \\__,_|\\__, |_|  \\___/|_|_|\r\n" + //
                        "/ ___| _   _ ___| |_ ___ _ __ ___                    |___/               \r\n" + //
                        "\\___ \\| | | / __| __/ _ \\ '_ ` _ \\                                       \r\n" + //
                        " ___) | |_| \\__ \\ ||  __/ | | | | |                                      \r\n" + //
                        "|____/ \\__, |___/\\__\\___|_| |_| |_|                                      \r\n" + //
                        "       |___/                       ");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Select user type: (E)mployee, (A)dmin, (H)R, (Q)uit");
    }

    private static void printEmployeeScreen(){
        System.out.println(" _____                 _                         __  __                  \r\n" + //
                        "| ____|_ __ ___  _ __ | | ___  _   _  ___  ___  |  \\/  | ___ _ __  _   _ \r\n" + //
                        "|  _| | '_ ` _ \\| '_ \\| |/ _ \\| | | |/ _ \\/ _ \\ | |\\/| |/ _ \\ '_ \\| | | |\r\n" + //
                        "| |___| | | | | | |_) | | (_) | |_| |  __/  __/ | |  | |  __/ | | | |_| |\r\n" + //
                        "|_____|_| |_| |_| .__/|_|\\___/ \\__, |\\___|\\___| |_|  |_|\\___|_| |_|\\__,_|\r\n" + //
                        "                |_|            |___/                                     ");
        System.out.println("-------------------------------------------------------------------------------");
    }

    private static void printAdminScreen(){
        System.out.println("    _       _           _         __  __                  \r\n" + //
                        "   / \\   __| |_ __ ___ (_)_ __   |  \\/  | ___ _ __  _   _ \r\n" + //
                        "  / _ \\ / _` | '_ ` _ \\| | '_ \\  | |\\/| |/ _ \\ '_ \\| | | |\r\n" + //
                        " / ___ \\ (_| | | | | | | | | | | | |  | |  __/ | | | |_| |\r\n" + //
                        "/_/   \\_\\__,_|_| |_| |_|_|_| |_| |_|  |_|\\___|_| |_|\\__,_|");
        System.out.println("-------------------------------------------------------------------------------");
    }

    private static void printHRScreen(){
        System.out.println(" _   _ ____    __  __                  \r\n" + //
                        "| | | |  _ \\  |  \\/  | ___ _ __  _   _ \r\n" + //
                        "| |_| | |_) | | |\\/| |/ _ \\ '_ \\| | | |\r\n" + //
                        "|  _  |  _ <  | |  | |  __/ | | | |_| |\r\n" + //
                        "|_| |_|_| \\_\\ |_|  |_|\\___|_| |_|\\__,_|");
        System.out.println("-------------------------------------------------------------------------------");
    }





    // code for the selection menus
    public static String startScreen(){
        printStartScreen();
        System.out.print("Enter your selection: ");
        String choise = scanner.nextLine(); 
        choise = choise.toUpperCase();
        
        while (true) {
            if ((choise.equals("E")) || (choise.equals("A")) ||(choise.equals("H"))||(choise.equals("Q"))){
                break;
            } else{
              
                printStartScreen();
                System.out.println("Not one of the options ");
                System.out.print("Enter your selection: ");
                choise = scanner.nextLine();
                choise = choise.toUpperCase();
                
            }
        }
        return choise;
    }
    
    public static void employeeMenu(){
        printEmployeeScreen();
        System.out.print("Login\nName: ");
        String name = scanner.nextLine();
        System.out.print("PPS number: ");
        String PPSno = scanner.nextLine();
        Employee employee = login.authenticateEmployee(name, PPSno);

        while (true){

            
           
            if (employee == (null)){
                printEmployeeScreen();
                System.out.println("Incorrect name or PPS number");
                System.out.print("Login\nName: ");
                name = scanner.nextLine();
                System.out.print("PPS number: ");
                PPSno = scanner.nextLine();
                employee = login.authenticateEmployee(name, PPSno);
            }else{
                break;
            }
        }

        printEmployeeScreen();
        System.out.println("What do you wish to view: your (D)etails, (R)ecent payslips, (H)istorical payslips, (B)ack");
        System.out.print("Enter your selection: ");
        String choise = scanner.nextLine();
        choise = choise.toUpperCase();

        while (true) {
            if ((choise.equals("D")) || (choise.equals("R")) ||(choise.equals("H")) || (choise.equals("B"))){
                break;
            } else{
              
                printEmployeeScreen();
                System.out.println("Select what you wish to view: your (D)etails, (R)ecent payslips, (H)istorical payslips, (B)ack");
                System.out.println("Not one of the options ");
                System.out.print("Enter your selection: ");
                choise = scanner.nextLine();
                choise = choise.toUpperCase();
                
            }
        }

        

    }

    public static void adminMenu(){
        printAdminScreen();
        System.out.print("Login\nName: ");
        String name = scanner.nextLine();
        System.out.print("PPS number: ");
        String PPSno = scanner.nextLine();

        while (true){


            // need a system to check if name and pps number match with anyone in the employees.csv
            if (login.authenticate(name, PPSno, "Admin")){
                break;
            }else{
                printAdminScreen();
                System.out.println("Incorrect name or PPS number");
                System.out.print("Login\nName: ");
                name = scanner.nextLine();
                System.out.print("PPS number: ");
                PPSno = scanner.nextLine();
            }
        }

        printAdminScreen();
        System.out.println("Select what you want to do: (A)dd employee, (B)ack");
        System.out.print("Enter your selection: ");
        String choise = scanner.nextLine();
        choise = choise.toUpperCase();

        while (true) {
            if ((choise.equals("D")) || (choise.equals("A")) || (choise.equals("B"))){
                break;
            } else{
              
                printAdminScreen();
                System.out.println("Select what you want to do: (A)dd employee, (B)ack");
                System.out.println("Not one of the options ");
                System.out.print("Enter your selection: ");
                choise = scanner.nextLine();
                choise = choise.toUpperCase();
                
            }
        }

    }

    public static void hrMenu(){
        printHRScreen();
        System.out.print("Login\nName: ");
        String name = scanner.nextLine();
        System.out.print("PPS number: ");
        String PPSno = scanner.nextLine();

        while (true){


            // need a system to check if name and pps number match with anyone in the employees.csv
            if (login.authenticate(name, PPSno, "HR")){
                break;
            }else{
                printHRScreen();
                System.out.println("Incorrect name or PPS number");
                System.out.print("Login\nName: ");
                name = scanner.nextLine();
                System.out.print("PPS number: ");
                PPSno = scanner.nextLine();
            }
        }

        printHRScreen();
        System.out.println("Select what you want to do: (P)romote employee, (B)ack");
        System.out.print("Enter your selection: ");
        String choise = scanner.nextLine();
        choise = choise.toUpperCase();

        while (true) {
            if ((choise.equals("P")) || (choise.equals("B"))){
                break;
            } else{
              
                printHRScreen();
                System.out.println("Select what you want to do: (A)dd employee, (B)ack");
                System.out.println("Not one of the options ");
                System.out.print("Enter your selection: ");
                choise = scanner.nextLine();
                choise = choise.toUpperCase();
                
            }
        }

    }
    
    public static void main(String[] args) {

        while (true){
            String choise = startScreen();
            if (choise.equals("E")){
                employeeMenu();
            }else if (choise.equals("A")){
                adminMenu();
            }else if (choise.equals("H")){
                hrMenu();
            }else if (choise.equals("Q")){
                break;
            }
        }




    
    
    }
}
