package entities;

import java.util.Date;

public class ConsumptionEntity {

    private static int idCounter = 1;
    private int id;
    private Date startDate;
    private Date endDate;
    private double value;

    public ConsumptionEntity() {
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConsumptionEntity{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", value=" + value +
                '}';
    }
}
