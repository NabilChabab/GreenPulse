package services;

import entities.ConsumptionEntity;
import entities.UserEntity;

import utils.ConsoleUtils;
import utils.DateUtils;

import java.util.HashMap;
import java.util.List;


public class UserService {

    private HashMap<Integer , UserEntity> userMap = new HashMap<>();

    private ConsumptionService consumptionService = new ConsumptionService();


    public void createUser(String name, int age){

        UserEntity user = new UserEntity(name, age);
        userMap.put(user.getId(), user);
        System.out.println("User created: " + user);
    }


    public void showUser(int userId){
        UserEntity user = userMap.get(userId);
        if (user != null) {
            System.out.println("User Details: " + user);
            System.out.println("Total Carbon Consumption: " + user.calculateTotalCarbon() + " kg");
        } else {
            System.out.println("User not found.");
        }
    }

    public void updateUser(int userId, int choice, String naame, int age){
        UserEntity user = userMap.get(userId);
        if (user != null) {
            switch (choice) {
                case 1:
                    user.setName(naame);
                    System.out.println("Name updated successfully.");
                    break;
                case 2:
                    user.setAge(age);
                    System.out.println("Age updated successfully.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    public void deleteUser(int userId){
        if (userMap.remove(userId) != null) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void addConsumptionForUser(int userId, String startDate, String endDate, double value) {
        UserEntity user = userMap.get(userId);

        if (user != null) {
            try {
                ConsumptionEntity consumption = new ConsumptionEntity();
                consumption.setStartDate(DateUtils.parseDate(startDate));
                consumption.setEndDate(DateUtils.parseDate(endDate));
                consumption.setValue(value);

                user.addConsumption(consumption);
                System.out.println("Carbon consumption added successfully for user ID: " + userId);
            } catch (Exception e) {
                System.out.println("Error adding consumption: " + e.getMessage());
            }
        } else {
            System.out.println("User not found with ID: " + userId);
        }
    }



    public void generateReportForUserById(int userId, String reportType) {
        UserEntity user = userMap.get(userId);

        if (user != null) {
            List<ConsumptionEntity> consumptions = user.getConsumptions();
            if (consumptions.isEmpty()) {
                System.out.println(ConsoleUtils.RED + "No consumption data found for user." + ConsoleUtils.RESET);
                return;
            }

            // Get averages based on report type
            switch (reportType.toLowerCase()) {
                case "daily":
                    List<Double> dailyAverages = consumptionService.getDailyAverages(user, consumptions.get(0).getStartDate(), consumptions.get(consumptions.size() - 1).getEndDate());
                    displayDetailedReport(userId, reportType, dailyAverages);
                    break;
                case "weekly":
                    List<Double> weeklyAverages = consumptionService.getWeeklyAverages(user, consumptions.get(0).getStartDate(), consumptions.get(consumptions.size() - 1).getEndDate());
                    displayDetailedReport(userId, reportType, weeklyAverages);
                    break;
                case "monthly":
                    List<Double> monthlyAverages = consumptionService.getMonthlyAverages(user, consumptions.get(0).getStartDate(), consumptions.get(consumptions.size() - 1).getEndDate());
                    displayDetailedReport(userId, reportType, monthlyAverages);
                    break;
                default:
                    System.out.println(ConsoleUtils.RED + "Invalid report type." + ConsoleUtils.RESET);
                    return;
            }

            displayAllConsumptions(consumptions);

        } else {
            System.out.println(ConsoleUtils.RED + "User not found." + ConsoleUtils.RESET);
        }
    }

    private void displayAllConsumptions(List<ConsumptionEntity> consumptions) {
        System.out.println(ConsoleUtils.CYAN + ConsoleUtils.BOLD + "\nAll Consumptions" + ConsoleUtils.RESET);
        ConsoleUtils.printLine('=', 100);
        System.out.println(
                ConsoleUtils.formatCell("Consumption ID", 20) + "| " +
                        ConsoleUtils.formatCell("Start Date", 30) + "| " +
                        ConsoleUtils.formatCell("End Date", 30) + "| " +
                        ConsoleUtils.formatCell("Value (kg)", 20)
        );
        ConsoleUtils.printLine('-', 100);

        for (ConsumptionEntity consumption : consumptions) {
            System.out.println(
                    ConsoleUtils.formatCell(String.valueOf(consumption.getId()), 20) + "| " +
                            ConsoleUtils.formatCell(consumption.getStartDate().toString(), 30) + "| " +
                            ConsoleUtils.formatCell(consumption.getEndDate().toString(), 30) + "| " +
                            ConsoleUtils.formatCell(String.format("%.2f", consumption.getValue()), 20)
            );
        }

        ConsoleUtils.printLine('=', 100);
    }

    private void displayDetailedReport(int userId, String reportType, List<Double> averages) {
        System.out.println(ConsoleUtils.CYAN + ConsoleUtils.BOLD + "\nDetailed Carbon Consumption Report" + ConsoleUtils.RESET);
        ConsoleUtils.printLine('=', 100);
        System.out.println(
                ConsoleUtils.formatCell("User ID", 30) + "| " +
                        ConsoleUtils.formatCell("Report Type", 40) + "| " +
                        ConsoleUtils.formatCell("Average Consumption (kg)", 40)
        );
        ConsoleUtils.printLine('-', 100);

        for (int i = 0; i < averages.size(); i++) {
            System.out.println(
                    ConsoleUtils.formatCell(String.valueOf(userId), 30) + "| " +
                            ConsoleUtils.formatCell(reportType + " #" + (i + 1), 40) + "| " +
                            ConsoleUtils.formatCell(String.format("%.2f", averages.get(i)), 40)
            );
        }

        ConsoleUtils.printLine('=', 100);
    }

    private void displayReport(int userId, String reportType, double average) {
        System.out.println(ConsoleUtils.CYAN + ConsoleUtils.BOLD + "\nCarbon Consumption Report" + ConsoleUtils.RESET);
        ConsoleUtils.printLine('=', 100);
        System.out.println(
                ConsoleUtils.formatCell("User ID", 30) + "| " +
                        ConsoleUtils.formatCell("Report Type", 40) + "| " +
                        ConsoleUtils.formatCell("Average Consumption (kg)", 40)
        );
        ConsoleUtils.printLine('-', 100);
        System.out.println(
                ConsoleUtils.formatCell(String.valueOf(userId), 30) + "| " +
                        ConsoleUtils.formatCell(reportType, 40) + "| " +
                        ConsoleUtils.formatCell(String.format("%.2f", average), 40)
        );
        ConsoleUtils.printLine('=', 100);
    }




}
