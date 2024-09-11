package entities;

import java.time.LocalDate;
import java.util.Date;

public class Food extends Consumption {

    private String foodType;
    private double weight;

    public Food(LocalDate startDate, LocalDate endDate, double value , User user, String foodType, double weight) {
        super(startDate, endDate, value , user);
        this.foodType = foodType;
        this.weight = weight;
    }


    public Food(){

    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public double calculateConsumptionImpact() {
        double impact = 0.0;
        if ("meet".equalsIgnoreCase(foodType)) {
            impact = 5.0;
        } else if ("vegetable".equalsIgnoreCase(foodType)) {
            impact = 0.5;
        }
        return impact * weight;
    }
}
