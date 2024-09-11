package services;

import entities.Consumption;
import entities.User;
import utils.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ConsumptionService {

    public List<Double> getDailyAverages(User user, LocalDate startDate, LocalDate endDate) {
        List<Consumption> consumptions = user.getConsumptions();
        List<Double> dailyAverages = new ArrayList<>();

        for (LocalDate current = startDate; !current.isAfter(endDate); current = current.plusDays(1)) {
            LocalDate finalCurrent = current;
            double dailyTotal = consumptions
                    .stream()
                    .filter(e -> !DateUtils.isDateAvailable(e.getStartDate(), e.getEndDate(), List.of(finalCurrent)))
                    .mapToDouble(Consumption::getConsumptionImpact)
                    .sum();
            dailyAverages.add(dailyTotal);
        }

        return dailyAverages;
    }

    public List<Double> getWeeklyAverages(User user, LocalDate startDate, LocalDate endDate) {
        List<Consumption> consumptions = user.getConsumptions();
        List<Double> weeklyAverages = new ArrayList<>();

        for (LocalDate current = startDate; !current.isAfter(endDate); current = current.plusWeeks(1)) {
            LocalDate finalCurrent = current;
            double weeklyTotal = consumptions
                    .stream()
                    .filter(e -> !DateUtils.isDateAvailable(e.getStartDate(), e.getEndDate(), List.of(finalCurrent)))
                    .mapToDouble(Consumption::getConsumptionImpact)
                    .sum();
            weeklyAverages.add(weeklyTotal);
        }

        return weeklyAverages;
    }

    public List<Double> getMonthlyAverages(User user, LocalDate startDate, LocalDate endDate) {
        List<Consumption> consumptions = user.getConsumptions();
        List<Double> monthlyAverages = new ArrayList<>();

        for (LocalDate current = startDate; !current.isAfter(endDate); current = current.plusMonths(1)) {
            LocalDate finalCurrent = current;
            double monthlyTotal = consumptions
                    .stream()
                    .filter(e -> !DateUtils.isDateAvailable(e.getStartDate(), e.getEndDate(), List.of(finalCurrent)))
                    .mapToDouble(Consumption::getConsumptionImpact)
                    .sum();
            monthlyAverages.add(monthlyTotal);
        }

        return monthlyAverages;
    }

    private double getTotalForPeriod(List<Consumption> consumptions, LocalDate startDate, int days) {
        LocalDate endDate = startDate.plusDays(days);
        return consumptions
                .stream()
                .filter(e -> !DateUtils.isDateAvailable(e.getStartDate(), e.getEndDate(), DateUtils.dateListRange(startDate, endDate)))
                .mapToDouble(Consumption::getConsumptionImpact)
                .sum();
    }

    public Double averageByPeriod(User user, LocalDate start , LocalDate endDate){

        if (!start.isAfter(endDate)){
            List<Consumption> consumptions = user.getConsumptions();
            List<LocalDate> dates = DateUtils.dateListRange(start , endDate);

            double average =  (consumptions
                    .stream()
                    .filter(e -> !DateUtils.isDateAvailable(e.getStartDate() , e.getEndDate() ,dates ))
                    .mapToDouble(Consumption::getConsumptionImpact).sum()) / dates.size();
        }
        return 0.0;
    }

}
