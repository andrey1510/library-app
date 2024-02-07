package com.libraryapp.utilities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LendingTermGeneratorTests {

    @Test
    void testGenerateLendingTerm(){
        LocalDateTime expectedTime = LocalDateTime.now().plusDays(30).withSecond(0).withNano(0);
        assertEquals(expectedTime, LendingTermGenerator.generateLendingTerm(30).withSecond(0).withNano(0));
    }

    @Test
    void testGenerateLendingTermPlusZeroDays(){
        LocalDateTime expectedTime = LocalDateTime.now().withSecond(0).withNano(0);
        assertEquals(expectedTime, LendingTermGenerator.generateLendingTerm(0).withSecond(0).withNano(0));
    }

}
