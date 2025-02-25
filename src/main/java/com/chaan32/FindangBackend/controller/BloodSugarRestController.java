package com.chaan32.FindangBackend.controller;

import com.chaan32.FindangBackend.domain.BloodSugar;
import com.chaan32.FindangBackend.domain.User;
import com.chaan32.FindangBackend.domain.type.BSType;
import com.chaan32.FindangBackend.dto.BloodSugarDTO;
import com.chaan32.FindangBackend.dto.BloodSugarResponseDTO;
import com.chaan32.FindangBackend.dto.BloodSugarSearchDTO;
import com.chaan32.FindangBackend.exception.NotFoundBloodSugarException;
import com.chaan32.FindangBackend.exception.NotFoundUserException;
import com.chaan32.FindangBackend.service.BloodSugarService;
import com.chaan32.FindangBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("findang/blood-sugar")
@Slf4j
@RequiredArgsConstructor
public class BloodSugarRestController {
    private final BloodSugarService bloodSugarService;
    private final UserService userService;

    // 혈당 정보를 입력하기
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addBloodSugarInfo(@RequestBody BloodSugarDTO bloodSugarDTO) throws NotFoundUserException {
        printUserLog("AddBloodSugarInfo", bloodSugarDTO.getUserId());
        Optional<User> user = Optional.ofNullable(userService.getUser(bloodSugarDTO.getUserId())
                .orElseThrow(() -> new NotFoundUserException(bloodSugarDTO.getUserId() + "에 해당하는 유저가 없습니다.")));

        BloodSugar bs = BloodSugarDTO.toEntity(bloodSugarDTO, user.get());

        log.info("[ [BloodSugarRestController] / [AddBloodSugarInfo] ] will save BloodSugar : {}", bs);

        Map<String, Object> response = getResponseMap(bloodSugarService.saveBloodSugar(bs));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // 혈당 정보를 수정하기
    @PatchMapping("/update")
    public ResponseEntity<Map<String, Object>> updateBloodSugarInfo(@RequestBody BloodSugarDTO bloodSugarDTO) throws NotFoundBloodSugarException {
        log.info("[ [BloodSugarRestController] / [UpdateBloodSugarInfo] ] bloodSugarDTO : {}", bloodSugarDTO);
        BloodSugar bloodSugar = bloodSugarService.editBloodSugar(bloodSugarDTO);

        Map<String, Object> response = getResponseMap(bloodSugar);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // 혈당 정보를 삭제하기
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteBloodSugarInfo(@RequestBody BloodSugarDTO bloodSugarDTO) {
        log.info("[ [BloodSugarRestController] / [DeleteBloodSugarInfo] ] bloodSugarId : {}", bloodSugarDTO.getBloodSugarId());
        Map<String, Object> response = new HashMap<>();
        response.put("blood_sugar_id", bloodSugarDTO.getBloodSugarId());

        if (bloodSugarService.deleteBloodSugar(bloodSugarDTO.getBloodSugarId())){
            response.put("message", "삭제 함");
        } else {
            response.put("message", "삭제 실패");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // 모든 사람의 혈당 정보를 가져오기
    @GetMapping("/all")
    public ResponseEntity<List<BloodSugarResponseDTO>> getAllBloodSugarInfo() {
        log.info("[ [BloodSugarRestController] / [GetAllBloodSugarInfo] ]");
        List<BloodSugar> allBloodSugar = bloodSugarService.getAllBloodSugar();

        List<BloodSugarResponseDTO> DTOList = getBSResponseDTOList(allBloodSugar);
        printSizeLog("GetAllBloodSugarInfo", DTOList.size());

        return ResponseEntity.ok(DTOList);
    }
    // 특정 사람의 모든 혈당 정보
    @GetMapping("/user/all")
    public ResponseEntity<List<BloodSugarResponseDTO>> getBloodSugarInfoByUser(@RequestBody BloodSugarSearchDTO bsSearchDTO) {
        printUserLog("GetBloodSugarInfoByUser", bsSearchDTO.getUserId());
        List<BloodSugar> bloodSugarByUser = bloodSugarService.getBloodSugarByUserId(bsSearchDTO.getUserId());
        List<BloodSugarResponseDTO> DTOList = getBSResponseDTOList(bloodSugarByUser);
        printSizeLog("GetBloodSugarInfoByUser", DTOList.size());

        return ResponseEntity.ok(DTOList);
    }
    // 특정 사람의 혈당 정보를 가져오기 (특정 날짜)
    @GetMapping("/user/date")
    public ResponseEntity<List<BloodSugarResponseDTO>>  getBloodSugarInfoByDate(@RequestBody BloodSugarSearchDTO bsSearchDTO) {
        printUserLog("GetBloodSugarInfoByDate", bsSearchDTO.getUserId());
        List<BloodSugar> bloodSugarBySpecificDate =
                bloodSugarService.getBloodSugarBySpecificDate(bsSearchDTO.getUserId(), bsSearchDTO.getSpecificDate());
        List<BloodSugarResponseDTO> DTOList = getBSResponseDTOList(bloodSugarBySpecificDate);
        printSizeLog("GetBloodSugarInfoByDate", DTOList.size());
        return ResponseEntity.ok(DTOList);
    }
    // 특정 사람의 혈당 정보를 가져오기 (특정 기간)
    @GetMapping("/user/range")
    public ResponseEntity<List<BloodSugarResponseDTO>>  getBloodSugarInfoByRange(@RequestBody BloodSugarSearchDTO bsSearchDTO) {
        printUserLog("GetBloodSugarInfoByRange", bsSearchDTO.getUserId());
        List<BloodSugar> bloodSugarByRange = bloodSugarService
                .getBloodSugarByDateRange(bsSearchDTO.getUserId(), bsSearchDTO.getStartDate(), bsSearchDTO.getEndDate());
        List<BloodSugarResponseDTO> DTOList = getBSResponseDTOList(bloodSugarByRange);
        printSizeLog("GetBloodSugarInfoByRange", DTOList.size());
        return ResponseEntity.ok(DTOList);
    }

    private Map<String, Object> getResponseMap(BloodSugar bloodSugar) {
        Map<String, Object> response = new HashMap<>();
        response.put("user_id", bloodSugar.getUser().getUserId());
        response.put("blood_sugar_id", bloodSugar.getBloodSugarId());
        response.put("blood_sugar_value", bloodSugar.getBloodSugarValue());
        response.put("status description", BSType.getDescriptionByNumber(bloodSugar.getStatus()));
        response.put("status ", bloodSugar.getStatus());
        return response;
    }
    private List<BloodSugarResponseDTO> getBSResponseDTOList(List<BloodSugar> bloodSugarList) {
        List<BloodSugarResponseDTO> DTOList = new ArrayList<>();
        for (BloodSugar bloodSugar : bloodSugarList) {
            DTOList.add(new BloodSugarResponseDTO(bloodSugar));
        }
        return DTOList;
    }
    private void printUserLog(String methodName, Long userId){
        log.info("[ [BloodSugarRestController] / [{}] ] user_id : {}", methodName, userId);
    }
    private void printBSLog(String methodName, Long bloodSugarId){
        log.info("[ [BloodSugarRestController] / [{}] ] blood_sugar_id : {}", methodName, bloodSugarId);
    }
    private void printSizeLog(String methodName, int size){
        log.info("[ [BloodSugarRestController] / [{}] ] size : {}", methodName, size);
    }
}
