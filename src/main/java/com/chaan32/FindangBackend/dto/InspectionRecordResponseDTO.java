package com.chaan32.FindangBackend.dto;

import com.chaan32.FindangBackend.domain.InspectionRecord;
import lombok.Data;

import java.util.Date;

@Data
public class InspectionRecordResponseDTO  {
    private Long inspectionRecordId;
    private Long userId;
    private Long reservationId;
    private Date inspectionDate;
    private Double hba1c;
    private Integer systolicBP;
    private Integer diastolicBP;

    public InspectionRecordResponseDTO(InspectionRecord inspectionRecord){
        this.inspectionDate = inspectionRecord.getInspectionDate();
        this.inspectionRecordId = inspectionRecord.getInspectionRecordId();
        this.userId = inspectionRecord.getUser().getUserId();
        this.reservationId = inspectionRecord.getReservation().getReservationId();
        this.hba1c = inspectionRecord.getHba1c();
        this.systolicBP = inspectionRecord.getSystolicBP();
        this.diastolicBP = inspectionRecord.getDiastolicBP();
    }
}
