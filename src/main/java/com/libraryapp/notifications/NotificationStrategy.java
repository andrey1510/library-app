package com.libraryapp.notifications;

import com.libraryapp.dto.LendingRecordDTO;

import java.util.List;

public interface NotificationStrategy {

    void sendNotification(List<LendingRecordDTO> recordsForNotification);
}
