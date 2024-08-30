import java.util.List;

public class User {


    private int id;
    private String name;
    private int age;
    private List<ConsumptionEntity> consumptions;


    private int idCounter = 1;

    public User(String name, int age) {
        this.id = idCounter++;
        this.name = name;
        this.age = age;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addCarbonConsumption(ConsumptionEntity consumption) {
        this.consumptions.add(consumption);
    }


    public String toString() {
        return "User [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}
