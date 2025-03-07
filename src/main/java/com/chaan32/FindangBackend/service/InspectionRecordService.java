package com.chaan32.FindangBackend.service;

import com.chaan32.FindangBackend.domain.InspectionRecord;
import com.chaan32.FindangBackend.domain.Reservation;
import com.chaan32.FindangBackend.domain.User;
import com.chaan32.FindangBackend.dto.InspectionRecordDTO;
import com.chaan32.FindangBackend.dto.InspectionRecordResponseDTO;
import com.chaan32.FindangBackend.exception.NotFoundInspectionRecordException;
import com.chaan32.FindangBackend.exception.NotFoundReservationException;
import com.chaan32.FindangBackend.exception.NotFoundUserException;
import com.chaan32.FindangBackend.repository.InspectionRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InspectionRecordService {
    private final InspectionRecordRepository inspectionRecordRepository;
    private final ReservationService reservationService;
    // Reservation 추가하고 InspectionRecord 생성해서 넣어주기
    public InspectionRecord addInspectionRecord(InspectionRecord inspectionRecord) {
        log.info("[ [InspectionRecordService] / [addInspectionRecord] ] inspectionRecord : {}", inspectionRecord);
        return inspectionRecordRepository.save(inspectionRecord);
    }

    // InspectionRecord에 대한 정보를 수정하는 메소드
    public InspectionRecord updateInspectionRecord(InspectionRecord inspectionRecord, InspectionRecordDTO dto, Reservation reservation) throws NotFoundUserException {
        if (inspectionRecord == null) {
            throw new NotFoundUserException("해당하는 검사 기록이 없습니다. updateInspectionRecord");
        }
        InspectionRecord update = inspectionRecord.update(dto, reservation);
        return update;
    }
    // 유저에 따라서 정보를 가져오는 메소드
    public List<InspectionRecord> getInspectionRecordByUser(User user) {
        return inspectionRecordRepository.findByUser(user);
    }

    // 특정 검사 정보를 가져오는 메소드
    public Optional<InspectionRecord> getInspectionRecordById(Long inspectionRecordId) {
        return inspectionRecordRepository.findById(inspectionRecordId);
    }

    // 특정 검사를 업데이트하는 메소드
    public Optional<InspectionRecord> updateInspectionRecord(InspectionRecordDTO dto) throws NotFoundInspectionRecordException {
        Optional<InspectionRecord> record = inspectionRecordRepository.findById(dto.getInspectionRecordId());

        record.ifPresent(inspectionRecord -> {
                    inspectionRecord.update(dto);
                    inspectionRecordRepository.save(inspectionRecord);
                });

        return record;
    }
}
