package com.chaan32.FindangBackend.dto;

import com.chaan32.FindangBackend.domain.BloodSugar;
import com.chaan32.FindangBackend.domain.type.BSType;
import com.chaan32.FindangBackend.domain.type.CTType;
import lombok.Data;


@Data
public class BloodSugarResponseDTO {
    private Long blood_sugar_id;
    private Long user_id;
    private Integer bloods_sugar_value;
    private String check_date;
    private String check_type;
    private String status;
    private String createAt;

    public BloodSugarResponseDTO(BloodSugar bloodSugar) {
        this.blood_sugar_id = bloodSugar.getBloodSugarId();
        this.bloods_sugar_value = bloodSugar.getBloodSugarValue();
        this.check_date = bloodSugar.getCheckDate().toString();
        this.check_type = CTType.getDescriptionByNumber(bloodSugar.getCheckType());
        this.status = BSType.getDescriptionByNumber(bloodSugar.getStatus());
        this.createAt = bloodSugar.getCreateAt().toString();
        this.user_id = bloodSugar.getUser().getUserId();
    }
}
