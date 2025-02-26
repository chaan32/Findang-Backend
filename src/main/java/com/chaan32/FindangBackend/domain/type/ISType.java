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

    public String getDescription() {
        return this.description;
    }

    public Integer getNumber() {
        return this.number;
    }

    public static String getDescriptionByNumber(Integer number) {
        for (ISType type : ISType.values()) {
            if (type.getNumber().equals(number)) {
                return type.getDescription();
            }
        }
        return null; // 해당하는 값이 없으면 null 반환
    }
    public static Integer getNumberByDescription(String description) {
        for (ISType type : ISType.values()) {
            if (type.getDescription().equals(description)) {
                return type.getNumber();
            }
        }
        return null; // 해당하는 값이 없으면 null 반환
    }
}
