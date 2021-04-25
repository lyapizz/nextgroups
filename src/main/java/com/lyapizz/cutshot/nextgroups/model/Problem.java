package com.lyapizz.cutshot.nextgroups.model;

public enum Problem {
    QUOTA_IS_NOT_REACHED("Квота не набралась!");

    String message;

    Problem(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
