package entities;

import java.util.ArrayList;
import java.util.List;

public class UserEntity {


    private int id;
    private String name;
    private int age;
    private List<ConsumptionEntity> consumptions;


    private static int idCounter = 1;

    public UserEntity(int id , String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        consumptions = new ArrayList<>();
    }

    public UserEntity() {
    }

    public int getId() {
        return id;
    }

    public List<ConsumptionEntity> getConsumptions() {
        return consumptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addConsumption(ConsumptionEntity consumption) {
        this.consumptions.add(consumption);
    }

    public double calculateTotalCarbon() {
        return consumptions.stream()
                .mapToDouble(ConsumptionEntity::getValue)
                .sum();
    }


    public String toString() {
        return "entities.UserEntity [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}
