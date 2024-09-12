package services;

import entities.Consumption;
import entities.User;

import repository.ConsumptionRepository;
import repository.UserRepository;
import utils.ConsoleUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public class UserService {

    private HashMap<Integer , User> userMap = new HashMap<>();

    private ConsumptionService consumptionService = new ConsumptionService();

    private UserRepository userRepository = new UserRepository();
    private ConsumptionRepository consumptionRepository = new ConsumptionRepository();

    public UserService() throws SQLException {
    }


    public Optional<User> createUser(String name, int age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);

        Optional<User> optionalUser = userRepository.save(user);
        optionalUser.ifPresent(value -> userMap.put(value.getId(), value));

        return optionalUser;

    }


    public Optional<User> showUser(int userId) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> System.out.println("User ID: " + value.getId() + ", Name: " + value.getName() + ", Age: " + value.getAge()));
        return user;
    }

    public Optional<User> updateUser(int userId, int choice, String name, int age) {
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(value -> {
            if (choice == 1 && name != null) {
                value.setName(name);
            } else if (choice == 2 && age != -1) {
                value.setAge(age);
            }
            userRepository.update(value);
        });
        return user;
    }

    public Optional<User> deleteUser(int userId) {
        Optional<User> user = userRepository.delete(userId);
        user.ifPresent(value -> userMap.remove(value.getId()));
        return user;

    }
    public Optional<Consumption> saveConsumptionForUser(Consumption consumption) {
        return consumptionRepository.save(consumption);
    }

    public List<User> findUsersWithConsumptions() {
        return userRepository.findAllUsersWithConsumptions();
    }


    public List<User> findUsers(){
        List<User> users = findUsersWithConsumptions();
        return users.stream().filter(e -> e.getConsumptions().stream().mapToDouble(Consumption::getConsumptionImpact).sum() > 3000).collect(Collectors.toList());
    }

    public void generateReportForUserById(int userId, String reportType) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            System.out.println(ConsoleUtils.RED + "User not found." + ConsoleUtils.RESET);
            return;
        }

        List<Consumption> consumptions = user.get().getConsumptions();
        if (consumptions.isEmpty()) {
            System.out.println(ConsoleUtils.RED + "No consumption data found for user." + ConsoleUtils.RESET);
            return;
        }

        LocalDate startDate = consumptions.get(0).getStartDate();
        LocalDate endDate = consumptions.get(consumptions.size() - 1).getEndDate();

        // Define a functional interface to get averages
        Function<User, List<Double>> averageFunction = getAveragesFunction(reportType.toLowerCase());

        if (averageFunction == null) {
            System.out.println(ConsoleUtils.RED + "Invalid report type." + ConsoleUtils.RESET);
            return;
        }

        List<Double> averages = averageFunction.apply(user.get());
        displayDetailedReport(userId, reportType, averages);
        displayAllConsumptions(consumptions);
    }

    // Helper method to return the correct function for the report type
    private Function<User, List<Double>> getAveragesFunction(String reportType) {
        switch (reportType) {
            case "daily":
                return (user) -> consumptionService.getDailyAverages(user, user.getConsumptions().get(0).getStartDate(), user.getConsumptions().get(user.getConsumptions().size() - 1).getEndDate());
            case "weekly":
                return (user) -> consumptionService.getWeeklyAverages(user, user.getConsumptions().get(0).getStartDate(), user.getConsumptions().get(user.getConsumptions().size() - 1).getEndDate());
            case "monthly":
                return (user) -> consumptionService.getMonthlyAverages(user, user.getConsumptions().get(0).getStartDate(), user.getConsumptions().get(user.getConsumptions().size() - 1).getEndDate());
            default:
                return null;
        }
    }


    private void displayAllConsumptions(List<Consumption> consumptions) {
        System.out.println(ConsoleUtils.CYAN + ConsoleUtils.BOLD + "\nAll Consumptions" + ConsoleUtils.RESET);
        ConsoleUtils.printLine("=" + ConsoleUtils.RED, 100);
        System.out.println(
                ConsoleUtils.formatCell("Consumption ID", 20) + "| " +
                        ConsoleUtils.formatCell("Start Date", 30) + "| " +
                        ConsoleUtils.formatCell("End Date", 30) + "| " +
                        ConsoleUtils.formatCell("Value (kg)", 20)
        );
        ConsoleUtils.printLine("=", 100);

        for (Consumption consumption : consumptions) {
            System.out.println(
                    ConsoleUtils.formatCell(String.valueOf(consumption.getId()), 20) + "| " +
                            ConsoleUtils.formatCell(consumption.getStartDate().toString(), 30) + "| " +
                            ConsoleUtils.formatCell(consumption.getEndDate().toString(), 30) + "| " +
                            ConsoleUtils.formatCell(String.format("%.2f", consumption.getValue()), 20)
            );
        }

        ConsoleUtils.printLine("=", 100);
    }

    private void displayDetailedReport(int userId, String reportType, List<Double> averages) {
        System.out.println(ConsoleUtils.CYAN + ConsoleUtils.BOLD + "\nDetailed Carbon Consumption Report" + ConsoleUtils.RESET);
        ConsoleUtils.printLine('=' + ConsoleUtils.BLUE, 100);
        System.out.println(
                ConsoleUtils.formatCell("User ID", 30) + "| " +
                        ConsoleUtils.formatCell("Report Type", 40) + "| " +
                        ConsoleUtils.formatCell("Average Consumption (kg)", 40)
        );
        ConsoleUtils.printLine("-", 100);

        for (int i = 0; i < averages.size(); i++) {
            System.out.println(
                    ConsoleUtils.formatCell(String.valueOf(userId), 30) + "| " +
                            ConsoleUtils.formatCell(reportType + " #" + (i + 1), 40) + "| " +
                            ConsoleUtils.formatCell(String.format("%.2f", averages.get(i)), 40)
            );
        }

        ConsoleUtils.printLine("=", 100);
    }





}
