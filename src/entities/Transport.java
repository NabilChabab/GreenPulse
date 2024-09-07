package entities;

import java.util.Date;

public class Transport extends ConsumptionEntity {

    private int id;
    private String transportType;
    private double distance;

    public Transport(Date startDate, Date endDate, Float value, int id , String transportType, double distance) {
        super(startDate, endDate, value);
        this.id = id;
        this.transportType = transportType;
        this.distance = distance;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public double calculateConsumptionImpact() {
        System.out.println("Calculating consumption impact for transport");
        return 0;
    }
}
