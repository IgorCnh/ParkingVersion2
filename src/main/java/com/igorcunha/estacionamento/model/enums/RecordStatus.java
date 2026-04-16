package com.igorcunha.estacionamento.model.enums;

public enum RecordStatus {
    ACTIVE("Active"),
    FINISHED("Finished");

    private String description;

    RecordStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
