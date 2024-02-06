package com.libraryapp.notifications;

import com.libraryapp.dto.LendingRecordDTO;
import com.libraryapp.mappers.LendingRecordMapper;
import com.libraryapp.repositories.LendingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.libraryapp.utilities.LendingTermGenerator.generateLendingTerm;

@Service
public class NotificationServiceImpl {

    @Autowired
    private LendingRecordRepository lendingRecordRepository;
    @Autowired
    private LendingRecordMapper lendingRecordMapper;

    @Value("${scheduler.lendingTerm.daysBeforeLendingTermExpiry}")
    private Integer daysBeforeLendingTermExpiry;

    @Scheduled(cron = "${scheduler.lendingTerm.cron}")
    public List<LendingRecordDTO> getLendingRecordsToReturn() {
       return lendingRecordRepository.findLendingRecordsByLendingTermBefore(
               generateLendingTerm(daysBeforeLendingTermExpiry)).stream()
                .map(lendingRecordMapper::lendingRecordToLendingRecordDTO)
                .collect(Collectors.toList());
    }
}