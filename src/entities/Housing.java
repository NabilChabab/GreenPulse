package entities;

import java.util.Date;

public class Housing extends ConsumptionEntity {

    private int id;
    private String energyType;
    private double energyConsumption;

    public Housing(Date startDate, Date endDate, Float value, int id, String energyType, double energyConsumption) {
        super(startDate, endDate, value);
        this.id = id;
        this.energyType = energyType;
        this.energyConsumption = energyConsumption;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        System.out.println("Calculating consumption impact for housing");
        return 0;
    }
}
