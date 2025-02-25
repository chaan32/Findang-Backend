package com.chaan32.FindangBackend.service;

import com.chaan32.FindangBackend.domain.BloodSugar;
import com.chaan32.FindangBackend.domain.type.BSType;
import com.chaan32.FindangBackend.dto.BloodSugarDTO;
import com.chaan32.FindangBackend.exception.NotFoundBloodSugarException;
import com.chaan32.FindangBackend.repository.BloodSugarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BloodSugarService {
    private final BloodSugarRepository bloodSugarRepository;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 혈당 정보를 입력하기
    public BloodSugar saveBloodSugar(BloodSugar bloodSugar){
        log.info("[ [BloodSugarService] / [saveBloodSugar] ]");
        bloodSugar.setStatus(setBloodSugarStatus(bloodSugar.getBloodSugarValue()));
        BloodSugar savedBS = bloodSugarRepository.save(bloodSugar);
        return savedBS;
    }
    // 혈당 status 설정하기
    private Integer setBloodSugarStatus(Integer bloodSugarValue){
        if ( bloodSugarValue > 200) { //매우 고혈당
            return BSType.VERY_HIGH_BLOOD_SUGAR.getNumber();
        }
        else if ( 160 <= bloodSugarValue && bloodSugarValue <= 200) { //고혈당
            return BSType.HIGH_BLOOD_SUGAR.getNumber();
        }
        else if ( 120 <= bloodSugarValue && bloodSugarValue <= 160) { //과도
            return BSType.EXCESSIVE.getNumber();
        }
        else if ( 80 <= bloodSugarValue && bloodSugarValue <= 120){ //정상
            return BSType.NORMAL.getNumber();
        }
        else { // 저혈당
            return BSType.LOW_BLOOD_SUGAR.getNumber();
        }
    }
    // 혈당 정보를 수정하기
    public BloodSugar editBloodSugar(BloodSugarDTO bloodSugarDTO) throws NotFoundBloodSugarException {
        log.info("[ [BloodSugarService] / [editBloodSugar] ]");
        BloodSugar targetBS = bloodSugarRepository.findById(bloodSugarDTO.getBloodSugarId())
                .orElseThrow(() -> new NotFoundBloodSugarException(bloodSugarDTO.getBloodSugarId() + "에 해당하는 혈당 정보가 없습니다."));

        targetBS.updateBloodSugar(bloodSugarDTO);

        return targetBS;
    }

    // 혈당 정보를 삭제하기
    public Boolean deleteBloodSugar(Long bloodSugarId) {
        log.info("[ [BloodSugarService] / [deleteBloodSugar] ]");
        if (!bloodSugarRepository.existsById(bloodSugarId)) {
            return false;
        }
        bloodSugarRepository.deleteById(bloodSugarId);
        return true;
    }

    // 모든 사람의 혈당 정보를 조회하기
    public List<BloodSugar> getAllBloodSugar(){
        log.info("[ [BloodSugarService] / [getAllBloodSugar] ]");
        return bloodSugarRepository.findAll();
    }

    // 특정 사람의 특정 날짜의 혈당 정보 조회하기
    public List<BloodSugar> getBloodSugarBySpecificDate(Long userId, Date specificDate){
        /*
            [예시]
            specific Date : 2025-03-01
            startDate : 2025-03-01 00:00:00
            endDate : 2025-03-02 00:00:00
         */
        Date startDate = specificDate;
        Date endDate = getOneDayPlusDate(specificDate);
        log.info("[ [BloodSugarService] / [getBloodSugarBySpecificDate] ] specificDate : {} ~ {}",
                dateFormat.format(startDate), dateFormat.format(endDate) );
        return bloodSugarRepository.findByUser_UserIdAndCheckDateBetween(userId, startDate, endDate);
    }
    // 특정 사람의 혈당 데이터 다 조회하기
    public List<BloodSugar> getBloodSugarByUserId(Long userId){
        log.info("[ [BloodSugarService] / [getBloodSugarByUserId] ]");
        return bloodSugarRepository.findByUser_UserId(userId);
    }

    // 특정 사람의 날짜 범위의 혈당 정보 조회하기
    public List<BloodSugar> getBloodSugarByDateRange(Long userId, Date startDate, Date endDate){
        log.info("[ [BloodSugarService] / [getBloodSugarByDateRange] ] {} ~ {}", startDate, endDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        endDate = calendar.getTime(); // 하루 전날 23:59:59

        return bloodSugarRepository.findByUser_UserIdAndCheckDateBetween(userId, startDate, endDate);
    }
    private Date getOneDayPlusDate(Date date){
        // 하루 더해주고 +1 00:00:00
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // 1초 빼기 23:59:59
        Instant instant = calendar.getTime().toInstant().minusSeconds(1);
        return Date.from(instant);
    }
}
