import java.util.Scanner;

public class Menu {

    private UserManger userManger;
    private Scanner scanner;


    public Menu() {
        userManger = new UserManger();
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\nWelcome Again :");
            System.out.println("1. Create a User");
            System.out.println("2. View a User Details");
            System.out.println("3. Update a User Details");
            System.out.println("4. Delete a User");
            System.out.println("5. Add Carbon Consumption");
            System.out.println("6. Generate Consumption Report for All Users");
            System.out.println("7. Generate Consumption Report for Specific User");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1 :
                    userManger.createUser();
                    break;
                case 2 :
                    userManger.showUser();
                    break;
                case 3 :
                    userManger.updateUser();
                    break;
                case 4 :
                    userManger.deleteUser();
                    break;
                case 5 :
                    userManger.calculateUserConsumption();
                    break;
                case 6:
                    userManger.generateConsumptionReport();
                    break;
                case 7 :
                    userManger.generateReportForUserById();
                    break;
            }
        }
    }
}
