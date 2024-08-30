import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

}
