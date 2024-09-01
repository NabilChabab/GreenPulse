package ui;

import services.UserService;

import java.util.Scanner;

public class Menu {

    private UserService userService;
    private Scanner scanner;

    public Menu(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in); // Scanner initialized here
    }

    public void start() {
        while (true) {
            System.out.println("\nWelcome Again :");
            System.out.println("1. Create a User");
            System.out.println("2. View User Details");
            System.out.println("3. Update User Details");
            System.out.println("4. Delete a User");
            System.out.println("5. Generate Consumption Report for Specific User");
            System.out.println("6. Add Carbon Consumption for User");
            System.out.println("7. Exit");

            int choice = getUserInputAsInt("Choose an option: ");

            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    showUser();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    generateReportForUserById();
                    break;
                case 6:
                    addConsumptionForUser();
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

    private void createUser() {
        String name = getUserInput("Enter name: ");
        int age = getUserInputAsInt("Enter age: ");
        userService.createUser(name, age);
    }

    private void showUser() {
        int id = getUserInputAsInt("Enter user ID to view details: ");
        userService.showUser(id);
    }

    private void updateUser() {
        int id = getUserInputAsInt("Enter user ID to update: ");
        System.out.println("1. Update Name");
        System.out.println("2. Update Age");
        int choice = getUserInputAsInt("Choose what to update: ");

        if (choice == 1) {
            String newName = getUserInput("Enter new name: ");
            userService.updateUser(id, choice, newName, -1);
        } else if (choice == 2) {
            int newAge = getUserInputAsInt("Enter new age: ");
            userService.updateUser(id, choice, null, newAge);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void deleteUser() {
        int id = getUserInputAsInt("Enter user ID to delete: ");
        userService.deleteUser(id);
    }

    private void generateReportForUserById() {
        int userId = getUserInputAsInt("Enter user ID to generate report: ");
        System.out.println("Choose report type:");
        System.out.println("1. Daily");
        System.out.println("2. Weekly");
        System.out.println("3. Monthly");

        int reportChoice = getUserInputAsInt("Enter your choice: ");
        String reportType = "";

        switch (reportChoice) {
            case 1:
                reportType = "daily";
                break;
            case 2:
                reportType = "weekly";
                break;
            case 3:
                reportType = "monthly";
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        userService.generateReportForUserById(userId, reportType);
    }

    private void addConsumptionForUser() {
        int userId = getUserInputAsInt("Enter user ID to add consumption: ");
        String startDate = getUserInput("Enter start date (yyyy-mm-dd): ");
        String endDate = getUserInput("Enter end date (yyyy-mm-dd): ");
        double value = getUserInputAsDouble("Enter carbon consumption value: ");

        userService.addConsumptionForUser(userId, startDate, endDate, value);
    }

    private String getUserInput(String str) {
        System.out.print(str);
        return scanner.nextLine().trim(); // Fixes name input issue
    }

    private int getUserInputAsInt(String str) {
        System.out.print(str);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.print(str);
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume newline to fix input handling
        return value;
    }

    private double getUserInputAsDouble(String str) {
        System.out.print(str);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.print(str);
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // Consume newline to fix input handling
        return value;
    }
}