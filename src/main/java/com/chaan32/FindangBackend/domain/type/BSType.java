package com.chaan32.FindangBackend.domain.type;

public enum BSType {
    /*
    혈당 상태의 종류
     */
    NORMAL("정상", 1),
    EXCESSIVE("과도", 2),
    LOW_BLOOD_SUGAR("저혈당", 3),
    HIGH_BLOOD_SUGAR("고혈당", 4),
    VERY_HIGH_BLOOD_SUGAR("매우 고혈당", 5);

    private final String description;
    private final Integer number;

    BSType(String description, Integer number) {
        this.description = description;
        this.number = number;
    }

    public String getDescription(BSType type) {
        return type.description;
    }

    public Integer getNumber(BSType type) {
        return type.number;
    }
}
