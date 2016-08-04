package hk.edu.cityu.financialwatchdog.helpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Helper class enabling convenient access to calendars for given time periods
 */
public class CalendarHelper {

    public static String[] getStringArray(List<Calendar> calendars) {
        return new String[]{
                Long.toString(calendars.get(0).getTime().getTime()),
                Long.toString(calendars.get(1).getTime().getTime())
        };
    }

    private static List<Calendar> getCalendars(int days1, int days2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.add(Calendar.DAY_OF_YEAR, days1);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        cal2.add(Calendar.DAY_OF_YEAR, days2);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 59);

        List<Calendar> calendars = new ArrayList<>(2);
        calendars.add(cal1);
        calendars.add(cal2);
        return calendars;
    }

    public static List<Calendar> getCalendarsForToday() {
        return getCalendars(0, 0);
    }

    public static List<Calendar> getCalendarsForYesterday() {
        return getCalendars(-1, -1);
    }

    public static List<Calendar> getCalendarsForWeek() {
        return getCalendars(-6, 0);
    }

    public static List<Calendar> getCalendarsForMonth() {
        return getCalendars(-29, 0);
    }

    public static List<Calendar> getCalendarsForTotal() {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.set(Calendar.YEAR, 1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.SECOND, 59);

        List<Calendar> calendars = new ArrayList<>(2);
        calendars.add(cal1);
        calendars.add(cal2);
        return calendars;
    }
}
