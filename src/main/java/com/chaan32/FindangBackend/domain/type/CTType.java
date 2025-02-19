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

    public String getDescription(CTType type) {
        return type.description;
    }

    public Integer getNumber(CTType type) {
        return type.number;
    }
}
