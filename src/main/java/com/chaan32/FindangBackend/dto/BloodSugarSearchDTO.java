package com.chaan32.FindangBackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import java.util.Date;

@Data
public class BloodSugarSearchDTO {
    // 혈당 정보 검색을 위한 DTO
    @JsonProperty("user_id")
    private Long userId;

    // 특정 기간
    @Temporal(TemporalType.DATE)
    @JsonProperty("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date endDate;

    // 특정 날짜
    @JsonProperty("specific_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date specificDate;
}
