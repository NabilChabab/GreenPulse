package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static LocalDate parseDate(String dateStr) {
        try {
            Date date = DATE_FORMAT.parse(dateStr);
            return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd");
        }
    }

    public static boolean isDateAvailable(LocalDate startDate, LocalDate endDate, List<LocalDate> dates) {
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if (!dates.contains(date)) {
                return false;
            }
        }
        return true;
    }

    public static List<LocalDate> dateListRange(LocalDate startDate , LocalDate endDate){
        List<LocalDate> dateListRange = new ArrayList<>();
            for(LocalDate dateTest = startDate; !dateTest.isAfter(endDate); dateTest=dateTest.plusDays(1)){
                dateListRange.add(dateTest);

        }
        return dateListRange;
    }


}
