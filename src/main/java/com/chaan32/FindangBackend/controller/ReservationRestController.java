package com.chaan32.FindangBackend.controller;

import com.chaan32.FindangBackend.domain.InspectionRecord;
import com.chaan32.FindangBackend.domain.Reservation;
import com.chaan32.FindangBackend.domain.User;
import com.chaan32.FindangBackend.domain.type.ISType;
import com.chaan32.FindangBackend.dto.ReservationDTO;
import com.chaan32.FindangBackend.dto.ReservationResponseDTO;
import com.chaan32.FindangBackend.exception.NotFoundReservationException;
import com.chaan32.FindangBackend.exception.NotFoundUserException;
import com.chaan32.FindangBackend.service.InspectionRecordService;
import com.chaan32.FindangBackend.service.ReservationService;
import com.chaan32.FindangBackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("findang/reservation")
@Transactional
public class ReservationRestController {
    private final ReservationService reservationService;
    private final UserService userService;
    private final InspectionRecordService inspectionRecordService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    //예약 정보 입력하기
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addReservationInfo(@RequestBody ReservationDTO reservationDTO) throws NotFoundUserException {
        log.info("[ [ReservationRestController] / [AddReservationInfo] ] reservationDTO : {}", reservationDTO);
        Optional<User> user = Optional.ofNullable(userService.getUser(reservationDTO.getUserId())
                .orElseThrow(() -> new NotFoundUserException(reservationDTO.getUserId() + "에 해당하는 유저가 없습니다.")));

        Reservation reservation = reservationService
                .addReservationInfo(ReservationDTO.toEntity(reservationDTO, user.get()));

        InspectionRecord inspectionRecord = inspectionRecordService
                .addInspectionRecord(new InspectionRecord(user.get(), reservation));

        reservationService.updateInspectionRecord(reservation, inspectionRecord);

        Map<String, Object> response = getResponseMap(reservation);

        return ResponseEntity.status(201).body(response);
    }
    //예약 정보 수정하기
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateReservationInfo(@RequestBody ReservationDTO reservationDTO) throws NotFoundReservationException {
        printReservationLog("UpdateReservationInfo", reservationDTO.getReservationId());
        Map<String, Object> response = getResponseMap(reservationService.editReservation(reservationDTO));
        return ResponseEntity.status(201).body(response);
    }
    //예약 정보 삭제하기
    @DeleteMapping("/delete")
    public String deleteReservationInfo(@RequestBody ReservationDTO reservationDTO) {
        printReservationLog("DeleteReservationInfo", reservationDTO.getReservationId());
        Boolean deleted = reservationService.deleteReservationInfo(reservationDTO.getReservationId());
        return deleted ? "예약 정보 삭제 성공" : "예약 정보 삭제 실패";
    }
    //예약 정보 조회하기 (모든 예약)
    @GetMapping("/find/all")
    public ResponseEntity<Map<String, Object>> getAllReservationInfo() {
        log.info("[ [ReservationRestController] / [GetAllReservationInfo] ]");
        List<Reservation> allReservations = reservationService.getAllReservationInfo();
        printSizeLog("GetAllReservationInfo", allReservations.size());
        return getResponseEntity(allReservations);
    }
    //예약 정보 조회하기 (특정 예약)
    @GetMapping("/find/user")
    public ResponseEntity<Map<String, Object>> getReservationInfoByUser(@RequestBody ReservationDTO reservationDTO) {
        log.info("[ [ReservationRestController] / [GetReservationInfoByUser] ] ");
        List<Reservation> reservations = reservationService.getReservationInfoByUser(reservationDTO.getUserId());
        printSizeLog("GetReservationInfoByUser", reservations.size());
        return getResponseEntity(reservations);
    }

    private Map<String, Object> getResponseMap(Reservation reservation){
        Map<String, Object> response = new HashMap<>();

        response.put("user_id", reservation.getUser().getUserId());
        response.put("reservation_date", dateFormat.format(reservation.getReservationDate()));
        response.put("status", reservation.getStatus());
        response.put("inspection_record_id", reservation.getInspectionRecord().getInspectionRecordId());
        if (reservation.getInspectionItem01() != null){
            response.put("inspection_item01", ISType.getDescriptionByNumber(reservation.getInspectionItem01()));
        }
        if (reservation.getInspectionItem02() != null){
            response.put("inspection_item02", ISType.getDescriptionByNumber(reservation.getInspectionItem02()));
        }
        if (reservation.getInspectionItem03() != null){
            response.put("inspection_item03", ISType.getDescriptionByNumber(reservation.getInspectionItem03()));
        }
        return response;
    }
    private void printReservationLog(String methodName, Long reservationId){
        log.info("[ [ReservationRestController] / [{}] ] reservationId : {}", methodName, reservationId);
    }
    private void printSizeLog(String methodName, int size){
        log.info("[ [ReservationRestController] / [{}] ] size : {}", methodName, size);
    }
    private List<ReservationResponseDTO> getRSVResponseDTOList(List<Reservation> reservationList){
        List<ReservationResponseDTO> DTOList = new ArrayList<>();
        for (Reservation reservation : reservationList){
            DTOList.add(new ReservationResponseDTO(reservation));
        }
        return DTOList;
    }
    private ResponseEntity<Map<String, Object>> getResponseEntity(List<Reservation> reservations){
        if (reservations.isEmpty()){
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "예약 정보가 없습니다."));
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("size", reservations.size());
            response.put("reservations", getRSVResponseDTOList(reservations));
            return ResponseEntity.status(200).body(response);
        }
    }
}
