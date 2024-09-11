package entities;

import entities.enums.ConsumptionType;

import java.time.LocalDate;

public abstract class Consumption {

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private double value;
    private double consumptionImpact;
    private ConsumptionType consumptionType;
    private User user;

    public Consumption(LocalDate startDate, LocalDate endDate, Double value , User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.value = value;
        this.user = user;
    }

    public Consumption() {
    }

    public User getUser() {
        return user;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Consumption{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", value=" + value +
                '}';
    }
}
