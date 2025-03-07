package com.chaan32.FindangBackend.dto;

import com.chaan32.FindangBackend.domain.Reservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class InspectionRecordDTO {
    @JsonProperty("inspection_record_id")
    private Long inspectionRecordId;

    @JsonProperty("reservation_id")
    private Long reservationId;

    @JsonProperty("inspection_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Date inspectionDate;

    @JsonProperty("hba1c")
    private Double hba1c;

    @JsonProperty("systolic_blood_pressure")
    private Integer systolicBP;

    @JsonProperty("diastolic_blood_pressure")
    private Integer diastolicBP;

}
