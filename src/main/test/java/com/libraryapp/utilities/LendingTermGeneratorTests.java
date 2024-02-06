package com.libraryapp.utilities;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LendingTermGeneratorTests {

    @Test
    void testGenerateLendingTerm(){
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        assertEquals(currentTime, LendingTermGenerator.generateLendingTerm(30));
    }


}
