package com.chaan32.FindangBackend.service;

import com.chaan32.FindangBackend.domain.InspectionRecord;
import com.chaan32.FindangBackend.domain.Reservation;
import com.chaan32.FindangBackend.dto.ReservationDTO;
import com.chaan32.FindangBackend.exception.NotFoundReservationException;
import com.chaan32.FindangBackend.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    //예약 정보 입력하기
    public Reservation addReservationInfo(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    //예약 정보 수정하기
    public Reservation editReservation(ReservationDTO reservationDTO) throws NotFoundReservationException {
        log.info("[ [ReservationService] / [editReservation] ] reservationDTO : {}", reservationDTO);
        Reservation targetRSV = reservationRepository.findById(reservationDTO.getReservationId())
                .orElseThrow(() -> new NotFoundReservationException(reservationDTO.getReservationId() + "에 해당하는 예약 정보가 없습니다."));

        // 수정되지 않은 아이템들을 null로 설정하지 않도록 복붙 해두는 것
        reflectItems(reservationDTO, targetRSV);

        return targetRSV.updateReservation(reservationDTO);
    }
    private void reflectItems(ReservationDTO reservationDTO, Reservation reservation){
        // DTO 속의 item이 null인 경우에 그대로 값을 받아야 함
        if (reservationDTO.getInspectionItem01() == null) {
            reservationDTO.setInspectionItem01(reservation.getInspectionItem01());
        }
        if (reservationDTO.getInspectionItem02() == null) {
            reservationDTO.setInspectionItem02(reservation.getInspectionItem02());
        }
        if (reservationDTO.getInspectionItem03() == null) {
            reservationDTO.setInspectionItem03(reservation.getInspectionItem03());
        }
    }

    //예약 정보 삭제하기
    public Boolean deleteReservationInfo(Long reservationId) {
        Optional<Reservation> deleted = reservationRepository.findById(reservationId);
        if (deleted.isPresent()) {
            reservationRepository.deleteById(reservationId);
            return true;
        }
        return false;
    }

    //모든 예약 정보 반환
    public List<Reservation> getAllReservationInfo() {
        return reservationRepository.findAll();
    }

    //특정 유저의 예약 정보 반환
    public List<Reservation> getReservationInfoByUser(Long userId){
        return reservationRepository.findByUser_UserId(userId);
    }

    public Optional<Reservation> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public Reservation updateInspectionRecord(Reservation reservation, InspectionRecord inspectionRecord){
        reservation.setInspectionRecord(inspectionRecord);
        return reservation;
    }
}