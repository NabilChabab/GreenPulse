package services;

import entities.ConsumptionEntity;
import entities.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConsumptionService {

    public List<Double> getDailyAverages(UserEntity user, Date startDate, Date endDate) {
        List<ConsumptionEntity> consumptions = user.getConsumptions();
        List<Double> dailyAverages = new ArrayList<>();

        Date current = startDate;
        while (!current.after(endDate)) {
            double totalConsumption = 0;
            int count = 0;

            for (ConsumptionEntity consumption : consumptions) {
                if (!consumption.getStartDate().after(current) && !consumption.getEndDate().before(current)) {
                    totalConsumption += consumption.getValue();
                    count++;
                }
            }

            dailyAverages.add(count > 0 ? totalConsumption / count : 0.0);

            current = new Date(current.getTime() + TimeUnit.DAYS.toMillis(1));
        }

        return dailyAverages;
    }

    public List<Double> getWeeklyAverages(UserEntity user, Date startDate, Date endDate) {
        List<Double> weeklyAverages = new ArrayList<>();
        Date current = startDate;

        while (!current.after(endDate)) {
            Date weekEnd = new Date(current.getTime() + TimeUnit.DAYS.toMillis(6));
            if (weekEnd.after(endDate)) {
                weekEnd = endDate;
            }

            double weeklyTotal = 0;
            int daysInWeek = 0;
            for (Date d = current; !d.after(weekEnd); d = new Date(d.getTime() + TimeUnit.DAYS.toMillis(1))) {
                double dayTotal = getDailyTotal(user, d);
                weeklyTotal += dayTotal;
                daysInWeek++;
            }

            weeklyAverages.add(weeklyTotal / daysInWeek);
            current = new Date(current.getTime() + TimeUnit.DAYS.toMillis(7));
        }

        return weeklyAverages;
    }

    public List<Double> getMonthlyAverages(UserEntity user, Date startDate, Date endDate) {
        List<Double> monthlyAverages = new ArrayList<>();
        Date current = startDate;

        while (!current.after(endDate)) {
            Date monthEnd = new Date(current.getTime());
            monthEnd.setMonth(current.getMonth() + 1);
            monthEnd.setDate(0);
            if (monthEnd.after(endDate)) {
                monthEnd = endDate;
            }

            double monthlyTotal = 0;
            int daysInMonth = 0;
            for (Date d = current; !d.after(monthEnd); d = new Date(d.getTime() + TimeUnit.DAYS.toMillis(1))) {
                double dayTotal = getDailyTotal(user, d);
                monthlyTotal += dayTotal;
                daysInMonth++;
            }

            monthlyAverages.add(monthlyTotal / daysInMonth);
            current.setMonth(current.getMonth() + 1);
            current.setDate(1);
        }

        return monthlyAverages;
    }

    private double getDailyTotal(UserEntity user, Date date) {
        List<ConsumptionEntity> consumptions = user.getConsumptions();
        return consumptions.stream()
                .filter(c -> !c.getStartDate().after(date) && !c.getEndDate().before(date))
                .mapToDouble(ConsumptionEntity::getValue)
                .sum();
    }

    private long getDaysBetween(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
    }
}
