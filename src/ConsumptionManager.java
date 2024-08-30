import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ConsumptionManager {


    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public void addConsumption(User user) {

        if(user != null) {
            System.out.println("Please enter a start date consumption : ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine(), dateFormat);

            System.out.println("Please enter a end date consumption : ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine(), dateFormat);

            System.out.println("Please enter a total consumption : ");
            double value = scanner.nextDouble();
            scanner.nextLine();


            ConsumptionEntity consumptionEntity = new ConsumptionEntity(value , user , startDate , endDate);
            user.addCarbonConsumption(consumptionEntity);

            System.out.println("Consumption added for user with Id" + user.getId());
        }
        else {
            System.out.println("user not found");
        }
    }

    public void generateConsumptionReport(User user) {

        if (user != null) {
            List<ConsumptionEntity> consumptions = user.getConsumptions();
            System.out.println("Carbon Consumption Report for User: " + user.getName());
            for (ConsumptionEntity consumption : consumptions) {
                System.out.println(consumption);
            }
            System.out.println("Total Carbon Consumption: " + user.calculateTotalCarbon() + " kg");
        } else {
            System.out.println("User not found.");
        }
    }

    public void generateReportForAllUsers(HashMap<Integer, User> userMap) {
        System.out.println("Generating consumption report for all users...");
        for (User user : userMap.values()) {
            generateConsumptionReport(user);
        }
    }

    public void generateReportByUserId(HashMap<Integer, User> userMap , int userId) {
        System.out.println("Generating consumption report for user with Id: " + userId);
        User user = userMap.get(userId);

        if (user != null) {
            generateConsumptionReport(user);
        }
        else {
            System.out.println("user not found");
        }
    }

}
