package entities;

import java.time.LocalDate;
import java.util.Date;

public class Transport extends Consumption {

    private String transportType;
    private double distance;

    public Transport(LocalDate startDate, LocalDate endDate, double value, User user , String transportType, double distance) {
        super(startDate, endDate, value , user);
        this.transportType = transportType;
        this.distance = distance;
    }

    public Transport() {
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
        double impact = 0.0;
        if ("car".equalsIgnoreCase(transportType)) {
            impact = 0.5;
        } else if ("train".equalsIgnoreCase(transportType)) {
            impact = 0.1;
        }
        return impact * distance;
    }
}
