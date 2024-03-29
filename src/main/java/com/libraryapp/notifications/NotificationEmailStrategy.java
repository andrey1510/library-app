package com.libraryapp.notifications;

import com.libraryapp.dto.LendingRecordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class NotificationEmailStrategy implements NotificationStrategy {

    // stub for sending email notification
    @Override
    public void sendNotification(List<LendingRecordDTO> recordsForNotification){
        recordsForNotification.forEach(lendingRecord -> log.info(
                "Клиенту с читательским билетом № {} было послано электронное письмо о возврате книги с ISBN {} до {}.",
                lendingRecord.getLibraryCard(), lendingRecord.getIsbn(), lendingRecord.getLendingTerm()));
    }

}
