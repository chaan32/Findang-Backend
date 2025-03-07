package com.chaan32.FindangBackend.controller;


import com.chaan32.FindangBackend.domain.InspectionRecord;
import com.chaan32.FindangBackend.domain.Reservation;
import com.chaan32.FindangBackend.domain.User;
import com.chaan32.FindangBackend.dto.InspectionRecordDTO;
import com.chaan32.FindangBackend.dto.InspectionRecordResponseDTO;
import com.chaan32.FindangBackend.dto.UserDTO;
import com.chaan32.FindangBackend.exception.NotFoundInspectionRecordException;
import com.chaan32.FindangBackend.exception.NotFoundReservationException;
import com.chaan32.FindangBackend.exception.NotFoundUserException;
import com.chaan32.FindangBackend.service.InspectionRecordService;
import com.chaan32.FindangBackend.service.ReservationService;
import com.chaan32.FindangBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("findang/inspection")
@RequiredArgsConstructor
@Slf4j
public class InspectionRecordRestController {
    private final InspectionRecordService inspectionRecordService;
    private final ReservationService reservationService;
    private final UserService userService;

    // 검사 정보 입력하기
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addInspectionRecord(@RequestBody InspectionRecordDTO inspectionRecordDTO) throws NotFoundReservationException, NotFoundUserException {
        Optional<Reservation> reservation = Optional.of(reservationService.getReservationById(inspectionRecordDTO.getReservationId())
                .orElseThrow(()-> new NotFoundReservationException(inspectionRecordDTO.getReservationId() + "에 해당하는 예약 정보가 없습니다.")));
        User user = reservation.get().getUser();

        // DTO에서 reservation_id와 user_id를 받아옴
        log.info("[ [InspectionRecordRestController] / [AddInspectionRecord] reservation_id : {} / user_id : {} ]", inspectionRecordDTO.getReservationId(), user.getUserId());

        // 날짜 생성 추가해주기 ..
        inspectionRecordDTO.setInspectionDate(reservation.get().getReservationDate());

        // generatedRecord : Reservation 생성할 때 생성이 되어 있었음
        InspectionRecord generatedRecord = reservation.get().getInspectionRecord();

        InspectionRecord record = inspectionRecordService.updateInspectionRecord(generatedRecord, inspectionRecordDTO, reservation.get());

        return ResponseEntity.status(201).body(getResponseMap(record));
    }
    // 검사 정보 가져오기 (특정 유저의 검사 정보 모두)
    @GetMapping("/find/user")
    public List<InspectionRecordResponseDTO> getInspectionRecordByUser(@RequestBody UserDTO userDTO) throws NotFoundUserException {
        Optional<User> user = Optional.of(userService.getUser(userDTO.getUserId()))
                .orElseThrow(()->new NotFoundUserException(userDTO.getUserId() + "에 해당하는 유저 정보가 없습니다."));
        List<InspectionRecord> inspectionRecordByUserList = inspectionRecordService.getInspectionRecordByUser(user.get());

        return getInspectionRecordResponseDTOList(inspectionRecordByUserList);
    }
    // 검사 정보 가져오기 (특정 검사 정보)
    @GetMapping("/find")
    public InspectionRecordResponseDTO  getInspectionRecordById(@RequestBody InspectionRecordDTO inspectionRecordDTO) throws NotFoundInspectionRecordException {
        Optional<InspectionRecord> recordById = Optional.of(inspectionRecordService.getInspectionRecordById(inspectionRecordDTO.getInspectionRecordId())
                .orElseThrow(
                        () -> new NotFoundInspectionRecordException(inspectionRecordDTO.getInspectionRecordId() + "에 해당하는 검사 정보가 없습니다."))
        );
        return new InspectionRecordResponseDTO(recordById.get());
    }
    // 검사 정보 수정하기
    @PutMapping("/update")
    public InspectionRecordResponseDTO updateInspectionRecord(@RequestBody InspectionRecordDTO inspectionRecordDTO) throws NotFoundInspectionRecordException {
        Optional<InspectionRecord> inspectionRecord = Optional.of(inspectionRecordService.updateInspectionRecord(inspectionRecordDTO)
                .orElseThrow(()-> new NotFoundInspectionRecordException(inspectionRecordDTO.getInspectionRecordId() + "에 해당하는 검사 정보가 없습니다.")));
        return new InspectionRecordResponseDTO(inspectionRecord.get());
    }

    private List<InspectionRecordResponseDTO> getInspectionRecordResponseDTO(List<InspectionRecord> list){
        List<InspectionRecordResponseDTO> responseDTOList = new ArrayList<>();
        for (InspectionRecord record : list){
            responseDTOList.add(new InspectionRecordResponseDTO(record));
        }
        return responseDTOList;
    }
    private Map<String, Object> getResponseMap(InspectionRecord record){
        Map<String, Object> response = new HashMap<>();
        response.put("inspectionRecord Id",  record.getInspectionRecordId());
        response.put("user Id", record.getUser().getUserId());
        response.put("reservation Id", record.getReservation().getReservationId());
        response.put("inspection Date", record.getInspectionDate());
        response.put("hba1c", record.getHba1c());
        response.put("systolic Blood Pressure", record.getSystolicBP());
        response.put("diastolic Blood Pressure", record.getDiastolicBP());
        return response;
    }

    private List<InspectionRecordResponseDTO> getInspectionRecordResponseDTOList(List<InspectionRecord> list){
        List<InspectionRecordResponseDTO> responseDTOList = new ArrayList<>();
        for (InspectionRecord record : list){
            responseDTOList.add(new InspectionRecordResponseDTO(record));
        }
        return responseDTOList;
    }
}
