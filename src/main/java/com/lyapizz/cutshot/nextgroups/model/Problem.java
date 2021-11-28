package com.lyapizz.cutshot.nextgroups.model;

public enum Problem {
    QUOTA_IS_NOT_REACHED("Квота не набралась!"),
    RANDOM_SEED_IS_NOT_DEFINED("К сожалению, катшот не указал номер жеребьевки. Расчет невозможен!");

    String message;

    Problem(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
