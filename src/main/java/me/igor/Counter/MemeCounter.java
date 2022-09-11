package me.igor.Counter;

import java.time.LocalTime;

public class MemeCounter {
    private static int memeCounter = 0;

    public static void checkHourIfFineThenSetToZero() {
        if (LocalTime.now().getHour() == 5) {
            memeCounter = 0;
        }
    }

    public static void increment() {
        memeCounter++;
    }

    public static int get() {
        return memeCounter;
    }
}
