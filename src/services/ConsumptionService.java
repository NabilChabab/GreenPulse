package services;

import entities.ConsumptionEntity;
import entities.UserEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConsumptionService {

    public double dailyAverage(UserEntity user) {
        List<ConsumptionEntity> consumptions = user.getConsumptions();
        if (consumptions.isEmpty()) {
            return 0;
        }

        long totalDays = 0;
        double totalConsumption = 0;

        for (ConsumptionEntity consumption : consumptions) {
            long daysBetween = getDaysBetween(consumption.getStartDate(), consumption.getEndDate());
            totalDays += daysBetween;
            totalConsumption += consumption.getValue();
        }

        return totalDays > 0 ? totalConsumption / totalDays : 0;
    }

    public double weeklyAverage(UserEntity user) {
        double dailyAvg = dailyAverage(user);
        return dailyAvg * 7;
    }

    public double monthlyAverage(UserEntity user) {
        double dailyAvg = dailyAverage(user);
        return dailyAvg * 30;
    }

    private long getDaysBetween(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
}
