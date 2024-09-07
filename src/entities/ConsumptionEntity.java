package entities;

import java.util.Date;

public abstract class ConsumptionEntity {

    private int id;
    private Date startDate;
    private Date endDate;
    private double value;
    private double consumptionImpact;
    private ConsumptionType consumptionType;

    public ConsumptionEntity(Date startDate, Date endDate, Float value) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.value = value;
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

    public double getConsumptionImpact() {
        return consumptionImpact;
    }

    public void setConsumptionImpact(double consumptionImpact) {
        this.consumptionImpact = consumptionImpact;
    }

    public ConsumptionType getConsumptionType() {
        return consumptionType;
    }

    public void setConsumptionType(ConsumptionType consumptionType) {
        this.consumptionType = consumptionType;
    }

    public abstract double calculateConsumptionImpact();

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
