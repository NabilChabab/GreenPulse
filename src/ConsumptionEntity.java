import java.time.LocalDate;

public class ConsumptionEntity {

    private  int id;
    private static int idCounter = 1;
    private double value;
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;


    public ConsumptionEntity(double value, User user, LocalDate startDate, LocalDate endDate) {
        this.id = idCounter++;
        this.value = value;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ConsumptionEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ConsumptionEntity{" +
                "id=" + id +
                ", value=" + value +
                ", user=" + user +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
