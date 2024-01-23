package pdf;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int seconds = 0;
        int seconds2 = 4935;
        int seconds3 = 60;
        System.out.println(makeReadable(seconds));
        System.out.println(makeReadable(seconds2));
        System.out.println(makeReadable(seconds3));
    }

    public static String makeReadable(int seconds) {
        int hours = (seconds - seconds % 3600) / 3600;
        int minutes = (seconds - hours * 3600 -  seconds % 60) / 60;
        int endSeconds = seconds - hours * 3600 - minutes * 60;
        String readableTime = "";
        if (hours < 10) {
            readableTime += "0";
        }
        readableTime += hours;
        readableTime += (":");
        if (minutes < 10) {
            readableTime += "0";
        }
        readableTime += minutes;
        readableTime += (":");
        if (endSeconds < 10) {
            readableTime += "0";
        }
        readableTime += endSeconds;
        return readableTime;
    }
}
