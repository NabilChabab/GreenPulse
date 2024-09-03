package services;

import entities.ConsumptionEntity;
import entities.UserEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConsumptionService {

    public List<Double> getDailyAverages(UserEntity user, Date startDate, Date endDate) {
        List<ConsumptionEntity> consumptions = user.getConsumptions();
        List<Double> dailyAverages = new ArrayList<>();

        Calendar current = Calendar.getInstance();
        current.setTime(startDate);

        while (!current.getTime().after(endDate)) {
            double dailyTotal = getTotalForPeriod(consumptions, current.getTime(), 1);
            dailyAverages.add(dailyTotal);
            current.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dailyAverages;
    }

    public List<Double> getWeeklyAverages(UserEntity user, Date startDate, Date endDate) {
        List<ConsumptionEntity> consumptions = user.getConsumptions();
        List<Double> weeklyAverages = new ArrayList<>();

        Calendar current = Calendar.getInstance();
        current.setTime(startDate);

        while (!current.getTime().after(endDate)) {
            double weeklyTotal = getTotalForPeriod(consumptions, current.getTime(), 7);
            weeklyAverages.add(weeklyTotal / 7);
            current.add(Calendar.WEEK_OF_YEAR, 1);
        }

        return weeklyAverages;
    }

    public List<Double> getMonthlyAverages(UserEntity user, Date startDate, Date endDate) {
        List<ConsumptionEntity> consumptions = user.getConsumptions();
        List<Double> monthlyAverages = new ArrayList<>();

        Calendar current = Calendar.getInstance();
        current.setTime(startDate);

        while (!current.getTime().after(endDate)) {
            int daysInMonth = current.getActualMaximum(Calendar.DAY_OF_MONTH);
            double monthlyTotal = getTotalForPeriod(consumptions, current.getTime(), daysInMonth);
            monthlyAverages.add(monthlyTotal / daysInMonth);
            current.add(Calendar.MONTH, 1);
        }

        return monthlyAverages;
    }

    private double getTotalForPeriod(List<ConsumptionEntity> consumptions, Date startDate, int days) {
        Calendar end = Calendar.getInstance();
        end.setTime(startDate);
        end.add(Calendar.DAY_OF_MONTH, days);
        double total = 0;

        for (ConsumptionEntity consumption : consumptions) {
            if (!consumption.getStartDate().after(end.getTime()) && !consumption.getEndDate().before(startDate)) {
                total += consumption.getValue();
            }
        }

        return total;
    }

    private long getDaysBetween(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
}
