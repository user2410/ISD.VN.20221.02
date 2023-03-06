package edu.hust.vn.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    public static String getToday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Timestamp stringToTimestamp(String dateString, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDate date = LocalDate.parse(dateString, formatter);
        LocalDateTime dateTime = date.atStartOfDay();
        return localDateTimeToTimeStamp(dateTime);
    }

    public static Timestamp localDateTimeToTimeStamp(LocalDateTime time){
        ZonedDateTime zonedDateTime = time.atZone(ZoneOffset.UTC);
        return Timestamp.from(zonedDateTime.toInstant());
    }

    /**
     * EAN-13 barcode validator
     *
     * @param barcode
     * @return boolean value indicating barcode validation
     */
    public static boolean validateBarcode(String barcode) {
        if(barcode == null){
            return false;
        }

        if (barcode.length() != 13 || !barcode.matches("^\\d{13}$")) {
            return false;
        }

        // Calculate check digit
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(barcode.charAt(i));
            if ((i & 1) == 0) {
                sum += digit;
            } else {
                sum += digit * 3;
            }
        }
        int checkDigit = (10 - (sum % 10)) % 10;

        // Compare check digit to last digit in barcode
        int lastDigit = Character.getNumericValue(barcode.charAt(12));
        return checkDigit == lastDigit;
    }
}
