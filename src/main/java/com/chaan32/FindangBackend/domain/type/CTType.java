package com.chaan32.FindangBackend.domain.type;

public enum CTType {
    /*
    혈당을 체크한 시점의 종류
     */
    BEFORE_BREAKFAST("아침 식사 전", 1),
    AFTER_BREAKFAST("아침 식사 후", 2),
    BEFORE_LUNCH("점심 식사 전", 3),
    AFTER_LUNCH("점심 식사 후", 4),
    BEFORE_DINNER("저녁 식사 전", 5),
    AFTER_DINNER("저녁 식사 후", 6),
    BEFORE_SLEEP("취침 전", 7),
    RANDOM("임의", 8),
    BEDTIME("취침 전", 9);

    private final String description;
    private final Integer number;

    CTType(String description, Integer number) {
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
        for (CTType type : CTType.values()) {
            if (type.getNumber().equals(number)) {
                return type.getDescription();
            }
        }
        return null; // 해당하는 값이 없으면 null 반환
    }

    // description을 받아서 number를 찾는 메서드
    public static Integer getNumberByDescription(String description) {
        for (CTType type : CTType.values()) {
            if (type.getDescription().equals(description)) {
                return type.getNumber();
            }
        }
        return null; // 해당하는 값이 없으면 null 반환
    }
}
