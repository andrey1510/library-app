package com.libraryapp.notifications;

import com.libraryapp.repositories.LendingRecordRepository;
import com.libraryapp.testdata.TestData;
import com.libraryapp.utilities.LendingTermGenerator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static com.libraryapp.utilities.LendingTermGenerator.generateLendingTerm;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTests extends TestData {

    @InjectMocks
    SchedulerService schedulerService;

    @Mock
    NotificationStrategy notificationStrategy;

    @Mock
    LendingTermGenerator lendingTermGenerator;

    @Mock
    LendingRecordRepository lendingRecordRepository;

    @Test
    @SneakyThrows
    void getLendingRecordsByLendingTermBeforeTest() {

        when(lendingRecordRepository.findLendingRecordsByLendingTermBefore(any()))
                .thenReturn(createLendingRecordlist());
        when(generateLendingTerm(any()))
                .thenReturn(LocalDateTime.now());

        schedulerService.getLendingRecordsByLendingTermBefore();

        verify(lendingRecordRepository, times(1))
                .findLendingRecordsByLendingTermBefore(generateLendingTerm(any()));
        verify(notificationStrategy)
                .sendNotification(any());
    }

}
