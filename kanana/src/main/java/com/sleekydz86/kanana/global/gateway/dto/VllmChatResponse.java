package com.sleekydz86.kanana.global.gateway.dto;

import java.util.List;

public class VllmChatResponse {

    private List<VllmChoice> choices;

    public List<VllmChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<VllmChoice> choices) {
        this.choices = choices;
    }
}
