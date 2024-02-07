package com.libraryapp.utilities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LendingTermGenerator {

    public static LocalDateTime generateLendingTerm(Integer days){
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.plus(days, ChronoUnit.DAYS);
    }
}
