package entities;

import java.time.LocalDate;
import java.util.Date;

public class Housing extends Consumption {

    private String energyType;
    private double energyConsumption;

    public Housing(LocalDate startDate, LocalDate endDate, double value,User user, String energyType, double energyConsumption) {
        super(startDate, endDate, value , user);
        this.energyType = energyType;
        this.energyConsumption = energyConsumption;
    }

    public Housing() {
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }

    public double getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(double energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    @Override
    public double calculateConsumptionImpact() {
        double impact = 0.0;

        if ("electricity".equalsIgnoreCase(energyType)) {
            impact = 1.5;
        } else if ("gaz".equalsIgnoreCase(energyType)) {
            impact = 2.0;
        }

        return impact * energyConsumption;
    }
}
