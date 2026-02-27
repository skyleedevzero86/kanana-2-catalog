package com.sleekydz86.kanana.domain.model;

public enum ModelVariant {
    BASE("사전 학습 단계. 파인튜닝·연구용"),
    MID("Mid-training. 추론 데이터 미포함 베이스"),
    INSTRUCT("지시 이행·도구 호출 극대화"),
    THINKING("추론 특화. 복잡한 논리적 사고");

    private final String description;

    ModelVariant(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
