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

    public String getDescription() {
        return this.description;
    }

    public Integer getNumber() {
        return this.number;
    }

    public static String getDescriptionByNumber(Integer number) {
        for (BSType type : BSType.values()) {
            if (type.getNumber().equals(number)) {
                return type.getDescription();
            }
        }
        return null; // 해당하는 값이 없으면 null 반환
    }

    // description을 받아서 number를 찾는 메서드
    public static Integer getNumberByDescription(String description) {
        for (BSType type : BSType.values()) {
            if (type.getDescription().equals(description)) {
                return type.getNumber();
            }
        }
        return null; // 해당하는 값이 없으면 null 반환
    }
}
