package br.com.shapeup.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class DayOfWeekUtils {

    public static Map<String, Integer> abbreviations() {
        Map<String, Integer> dayOfWeekAbbreviations = new LinkedHashMap<>();
        dayOfWeekAbbreviations.put("SUN", CustomDayOfWeek.SUNDAY.value);
        dayOfWeekAbbreviations.put("MON", CustomDayOfWeek.MONDAY.value);
        dayOfWeekAbbreviations.put("TUE", CustomDayOfWeek.TUESDAY.value);
        dayOfWeekAbbreviations.put("WED", CustomDayOfWeek.WEDNESDAY.value);
        dayOfWeekAbbreviations.put("THU", CustomDayOfWeek.THURSDAY.value);
        dayOfWeekAbbreviations.put("FRI", CustomDayOfWeek.FRIDAY.value);
        dayOfWeekAbbreviations.put("SAT", CustomDayOfWeek.SATURDAY.value);
        return dayOfWeekAbbreviations;
    }

    public enum CustomDayOfWeek {
        SUNDAY(1),
        MONDAY(2),
        TUESDAY(3),
        WEDNESDAY(4),
        THURSDAY(5),
        FRIDAY(6),
        SATURDAY(7);

        private int value;

        CustomDayOfWeek(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
