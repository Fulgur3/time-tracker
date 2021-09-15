package com.model.entity;

public enum ActivityPriority {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
    EXTREMELY_HIGH("Extremely high");

    private final String simpleName;

    ActivityPriority(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return simpleName;
    }
}
