package entities;

import java.util.Date;

public class Food extends ConsumptionEntity {

    private int id;
    private String foodType;
    private double weight;

    public Food(Date startDate, Date endDate, Float value ,int id ,  String foodType, double weight) {
        super(startDate, endDate, value);
        this.id = id;
        this.foodType = foodType;
        this.weight = weight;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        System.out.println("Calculating consumption impact for food");
        return 0;
    }
}
