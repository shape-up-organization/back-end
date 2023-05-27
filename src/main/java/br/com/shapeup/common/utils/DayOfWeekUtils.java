package br.com.shapeup.common.utils;

import java.time.DayOfWeek;
import java.util.Map;

public class DayOfWeekUtils {

    public static Map<String, DayOfWeek> abbreviations() {

        return Map.of(
                "SUN", DayOfWeek.SUNDAY,
                "MON", DayOfWeek.MONDAY,
                "TUE", DayOfWeek.TUESDAY,
                "WED", DayOfWeek.WEDNESDAY,
                "THU", DayOfWeek.THURSDAY,
                "FRI", DayOfWeek.FRIDAY,
                "SAT", DayOfWeek.SATURDAY
        );
    }
}
