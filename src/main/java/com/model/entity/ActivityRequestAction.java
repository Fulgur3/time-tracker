package com.model.entity;

public enum ActivityRequestAction {
    ADD("Add"),
    COMPLETE("Complete");

    private final String simpleName;

    ActivityRequestAction(String simpleName) {
        this.simpleName = simpleName;
    }

    @Override
    public String toString() {
        return simpleName;
    }
}
