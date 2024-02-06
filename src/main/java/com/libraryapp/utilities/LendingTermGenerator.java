package com.libraryapp.utilities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Calendar;

public class LendingTermGenerator {

    public static Timestamp generateLendingTerm(Integer days){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return new Timestamp(calendar.getTime().getTime());
    }
}
