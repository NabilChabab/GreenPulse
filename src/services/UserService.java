package services;

import entities.ConsumptionEntity;
import entities.UserEntity;
import exeptions.ReportExeption;

import utils.DateUtils;

import java.util.HashMap;



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



    public void generateReportForUserById(int userId , String reportType) {
        UserEntity user = userMap.get(userId);
        if (user != null) {
            reportType = reportType.trim().toLowerCase();
            System.out.println("Generating " + reportType + " report for user ID: " + userId);

            double average;
            try {
                switch (reportType) {
                    case "daily":
                        average = consumptionService.dailyAverage(user);
                        System.out.println("Daily Average Carbon Consumption: " + average + " kg");
                        break;
                    case "weekly":
                        average = consumptionService.weeklyAverage(user);
                        System.out.println("Weekly Average Carbon Consumption: " + average + " kg");
                        break;
                    case "monthly":
                        average = consumptionService.monthlyAverage(user);
                        System.out.println("Monthly Average Carbon Consumption: " + average + " kg");
                        break;
                    default:
                        throw new ReportExeption("Invalid report type: " + reportType);
                }
            } catch (ReportExeption e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("User not found.");
        }

    }




}