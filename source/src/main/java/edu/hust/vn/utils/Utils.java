package edu.hust.vn.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String convertSecondsToTimeFormat(long seconds) {
        int hour = (int) (seconds / 3600);
        int minute = (int) ((seconds % 3600) / 60);
        int second = (int) (seconds % 60);
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
