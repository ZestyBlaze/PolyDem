package dev.zestyblaze.demeter.util;

import dev.zestyblaze.demeter.world.season.Season;

import java.time.DayOfWeek;

public class TimeUtil {
    public static final long TICKS_PER_DAY = 24000L;
    public static final double SCALE = TICKS_PER_DAY / 24000D;
    public static final long SIX_AM = (long) (SCALE * 6000D);
    public static final int MONTH_DAYS = 28;
    public static final int YEAR_DAYS = MONTH_DAYS * 4;
    public static final DayOfWeek[] DAYS = DayOfWeek.values();

    public static int getElapsedDays(long time) {
        return (int) (time / TICKS_PER_DAY);
    }

    public static int getElapsedYears(long time) {
        return Math.floorDiv(getElapsedDays(time), YEAR_DAYS);
    }

    public static int getCurrentSeason(long time) {
        return Math.floorMod(getElapsedDays(time) / 28, 4) + 1;
    }

    public static Season getSeason(long time) {
        int intSeason = getCurrentSeason(time);

        if (intSeason == 1) {
            return Season.SPRING;
        } else if (intSeason == 2) {
            return Season.SUMMER;
        } else if (intSeason == 3) {
            return Season.AUTUMN;
        } else if (intSeason == 4) {
            return Season.WINTER;
        }
        return null;
    }

    public static long getTimeOfDay(long time) {
        return (time + SIX_AM) % TICKS_PER_DAY;
    }

    public static DayOfWeek getWeekday(long time) {
        int days = getElapsedDays(time);
        int modulus = days % 7;
        if (modulus < 0) modulus = 0;
        return DAYS[modulus];
    }

    public static String shortName(DayOfWeek day) {
        return day.name().substring(0, 3);
    }

    public static String formatTime(int time) {
        int hour = time / 1000;
        int minute = (int) ((double) (time % 1000) / 20 * 1.2);
        //if (DemeterConfig.clockType.get() == ClockType.TWENTY_FOUR_HOUR) {
            //return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
        //} else {
            boolean pm = false;
            if (hour > 12) {
                hour = hour - 12;
                pm = true;
            }
            if (hour == 12)
                pm = true;
            if (hour == 0)
                hour = 12;

            return (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + (pm ? "PM" : "AM");
        //}
    }
}
