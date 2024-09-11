package entities;

import java.util.ArrayList;
import java.util.List;

public class User {


    private int id;
    private String name;
    private int age;
    private List<Consumption> consumptions;



    public User(int id , String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        consumptions = new ArrayList<>();
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Consumption> getConsumptions() {
        return consumptions;
    }

    public List<Consumption> setConsumptions(List<Consumption> consumptions) {
        return this.consumptions = consumptions;
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


    public String toString() {
        return "entities.User [id=" + id + ", name=" + name + ", age=" + age + "]";
    }


}
