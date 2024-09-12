package ui;

import entities.*;
import entities.enums.ConsumptionType;
import services.UserService;
import utils.ConsoleUtils;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private UserService userService;
    private Scanner scanner;

    public Menu(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);

    }

    public void start() {
        while (true) {
            System.out.println(ConsoleUtils.YELLOW + "\nWelcome Again :" + ConsoleUtils.RESET);
            System.out.println("1. Create a User");
            System.out.println("2. View User Details");
            System.out.println("3. Update User Details");
            System.out.println("4. Delete a User");
            System.out.println("5. Add Carbon Consumption for User");
            System.out.println("6. Generate Consumption Report for Specific User");
            System.out.println("7. Get All Users With There Consumptions");
            System.out.println("8. Get All Users With Total Consumptions greater than 3000 KgCO2eq");
            System.out.println("9. Exit");


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
                    addConsumptionForUser();
                    break;
                case 6:
                    generateReportForUserById();
                    break;
                case 7:
                    findUsersWithConsumptions();
                    break;
                case 8:
                    findUsersWithSupTotalConsumptions();
                    break;
                case 9:
                    System.out.println(ConsoleUtils.GREEN + "Thank you for using the application. Goodbye!" + ConsoleUtils.RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(ConsoleUtils.RED + "Invalid choice." + ConsoleUtils.RESET);
                    break;
            }
        }
    }

    private void createUser() {
        String name = getUserInput("Enter name: ");
        int age = getUserInputAsInt("Enter age: ");
        userService.createUser(name, age);
        System.out.println(ConsoleUtils.GREEN + "User created successfully." + ConsoleUtils.RESET);
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
            System.out.println(ConsoleUtils.GREEN + "User updated successfully." + ConsoleUtils.RESET);
        } else if (choice == 2) {
            int newAge = getUserInputAsInt("Enter new age: ");
            userService.updateUser(id, choice, null, newAge);
            System.out.println(ConsoleUtils.GREEN + "User updated successfully." + ConsoleUtils.RESET);
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
                System.out.println(ConsoleUtils.RED + "Invalid choice." + ConsoleUtils.RESET);
                return;
        }

        userService.generateReportForUserById(userId , reportType);
    }

    private void addConsumptionForUser() {
        int userId = getUserInputAsInt("Enter user ID to add consumption: ");
        Optional<User> userOptional = userService.showUser(userId);
        if (!userOptional.isPresent()) {
            System.out.println(ConsoleUtils.RED + "User not found." + ConsoleUtils.RESET);
            return;
        }
        User user = userOptional.get();

        LocalDate startDate = DateUtils.parseDate(getUserInput("Enter start date (yyyy-mm-dd): "));
        LocalDate endDate = DateUtils.parseDate(getUserInput("Enter end date (yyyy-mm-dd): "));
        double value = getUserInputAsDouble("Enter carbon consumption value: ");

        System.out.println("Choose consumption type:");
        System.out.println("1. Housing");
        System.out.println("2. Transport");
        System.out.println("3. Food");
        int typeChoice = getUserInputAsInt("Enter your choice: ");

        Consumption consumption;

        switch (typeChoice) {
            case 1:
                String energyType = getUserInput("Enter energy type (electricity , gaz) : ");
                double energyConsumption = getUserInputAsDouble("Enter energy consumption: ");
                consumption = new Housing(startDate, endDate, value, user , energyType, energyConsumption);
                consumption.setConsumptionType(ConsumptionType.HOUSING);
                break;
            case 2:
                double distance = getUserInputAsDouble("Enter distance: ");
                String transportType = getUserInput("Enter transport type (train , car)  : ");
                consumption = new Transport(startDate, endDate, value, user,  transportType , distance);
                consumption.setConsumptionType(ConsumptionType.TRANSPORT);
                break;
            case 3:
                String foodType = getUserInput("Enter food type (meet , vegetables) : ");
                double quantity = getUserInputAsDouble("Enter quantity: ");
                consumption = new Food(startDate, endDate, value, user, foodType, quantity);
                consumption.setConsumptionType(ConsumptionType.FOOD);
                break;
            default:
                System.out.println(ConsoleUtils.RED + "Invalid choice." + ConsoleUtils.RESET);
                return;
        }

        System.out.println("User: " + consumption.getUser());
        System.out.println("Consumption Type: " + consumption.getConsumptionType());

        userService.saveConsumptionForUser(consumption);
        System.out.println(ConsoleUtils.GREEN + "Consumption added successfully." + ConsoleUtils.RESET);
    }

    private void findUsersWithConsumptions() {
        List<User> users = userService.findUsersWithConsumptions();
        if (users.isEmpty()) {
            System.out.println(ConsoleUtils.RED + "No users found with consumptions." + ConsoleUtils.RESET);
            return;
        }

        // Header
        System.out.println(ConsoleUtils.GREEN + "========================================");
        System.out.println("  Users with Consumptions");
        System.out.println("========================================" + ConsoleUtils.RESET);

        // Iterate through each user and display their data
        for (User user : users) {
            System.out.println(ConsoleUtils.CYAN + "User ID: " + user.getId() + " | Name: " + user.getName() + " | Age: " + user.getAge() + ConsoleUtils.RESET);

            List<Consumption> consumptions = user.getConsumptions();
            if (consumptions.isEmpty()) {
                System.out.println(ConsoleUtils.RED + "  No consumptions found for this user." + ConsoleUtils.RESET);
            } else {
                // Table header for consumptions
                System.out.println(ConsoleUtils.YELLOW + "  --------------------------------------------------------------------------");
                System.out.println("  | Consumption ID | Type       | Value   | Impact  | Additional Info        |");
                System.out.println("  --------------------------------------------------------------------------" + ConsoleUtils.RESET);

                for (Consumption consumption : consumptions) {
                    String additionalInfo = "";

                    // Specific details based on consumption type
                    if (consumption instanceof Transport) {
                        Transport transport = (Transport) consumption;
                        additionalInfo = "Distance: " + transport.getDistance() + "km, Vehicle: " + transport.getTransportType();
                    } else if (consumption instanceof Housing) {
                        Housing housing = (Housing) consumption;
                        additionalInfo = "Energy: " + housing.getEnergyConsumption() + ", Type: " + housing.getEnergyType();
                    } else if (consumption instanceof Food) {
                        Food food = (Food) consumption;
                        additionalInfo = "Food: " + food.getFoodType() + ", Weight: " + food.getWeight() + "kg";
                    }

                    // Table row for each consumption
                    System.out.printf(ConsoleUtils.WHITE + "  | %-14d | %-10s | %-7.2f | %-7.2f | %-25s |%n",
                            consumption.getId(),
                            consumption.getConsumptionType(),
                            consumption.getValue(),
                            consumption.getConsumptionImpact(),
                            additionalInfo);
                }

                // End table line
                System.out.println(ConsoleUtils.YELLOW + "  --------------------------------------------------------------------------" + ConsoleUtils.RESET);
            }
        }
    }

    private void findUsersWithSupTotalConsumptions(){
        List<User> users = userService.findUsers();
        if (users.isEmpty()) {
            System.out.println(ConsoleUtils.RED + "No users found with total consumptions greater than 3000 KgCO2eq." + ConsoleUtils.RESET);
            return;
        }

        // Header
        System.out.println(ConsoleUtils.GREEN + "========================================");
        System.out.println("  Users with Total Consumptions > 3000 KgCO2eq");
        System.out.println("========================================" + ConsoleUtils.RESET);

        // Iterate through each user and display their data
        for (User user : users) {
            System.out.println(ConsoleUtils.CYAN + "User ID: " + user.getId() + " | Name: " + user.getName() + " | Age: " + user.getAge() + ConsoleUtils.RESET);

            List<Consumption> consumptions = user.getConsumptions();
            if (consumptions.isEmpty()) {
                System.out.println(ConsoleUtils.RED + "  No consumptions found for this user." + ConsoleUtils.RESET);
            } else {
                // Table header for consumptions
                System.out.println(ConsoleUtils.BLUE + "  --------------------------------------------------------------------------");
                System.out.println("  | Consumption ID | Type       | Value   | Impact  | Additional Info        |");
                System.out.println("  --------------------------------------------------------------------------" + ConsoleUtils.RESET);


                for (Consumption consumption : consumptions) {
                    String additionalInfo = "";

                    // Specific details based on consumption type
                    if (consumption instanceof Transport) {
                        Transport transport = (Transport) consumption;
                        additionalInfo = "Distance: " + transport.getDistance() + "km, Vehicle: " + transport.getTransportType();
                    } else if (consumption instanceof Housing) {
                        Housing housing = (Housing) consumption;
                        additionalInfo = "Energy: " + housing.getEnergyConsumption() + ", Type: " + housing.getEnergyType();
                    } else if (consumption instanceof Food) {
                        Food food = (Food) consumption;
                        additionalInfo = "Food: " + food.getFoodType() + ", Weight: " + food.getWeight() + "kg";
                    }

                    // Table row for each consumption
                    System.out.printf(ConsoleUtils.BLUE + "  | %-14d | %-10s | %-7.2f | %-7.2f | %-25s |%n",
                            consumption.getId(),
                            consumption.getConsumptionType(),
                            consumption.getValue(),
                            consumption.getConsumptionImpact(),
                            additionalInfo);
                }
            }}}




    private String getUserInput(String str) {
        System.out.print(str);
        return scanner.nextLine().trim();
    }

    private int getUserInputAsInt(String str) {
        System.out.print(str);
        while (!scanner.hasNextInt()) {
            System.out.println(ConsoleUtils.RED + "Invalid input. Please enter a number." + ConsoleUtils.RESET);
            System.out.print(str);
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    private double getUserInputAsDouble(String str) {
        System.out.print(str);
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.print(str);
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
}
