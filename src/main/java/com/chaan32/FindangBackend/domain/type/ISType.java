package com.chaan32.FindangBackend.domain.type;

public enum ISType {
    /*
    검사 항목의 종류
     */

    //당화혈색소
    HB1AC("HB1AC", 1),
    //금식
    FASTING("금식", 2),
    //소변검사
    URINE("소변검사", 3),
    //혈액검사
    BLOOD("혈액검사", 4),
    //초음파검사
    ULTRASONIC("초음파검사", 5),
    //혈당
    BLOOD_SUGAR("혈당", 6);

    private final String description;
    private final Integer number;

    ISType(String description, Integer number) {
        this.description = description;
        this.number = number;
    }

    public String getDescription(ISType type) {
        return type.description;
    }

    public Integer getNumber(ISType type) {
        return type.number;
    }
}
