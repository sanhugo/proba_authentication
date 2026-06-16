package ru.proba.authentication.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    SUPERADMIN, //владелец системы
    ADMIN, //администратор системы
    USER, //пользователь (новый)
    STUDENT, //студент
    MODERATOR, //модератор пробника
    REFEREE, //эксперт
    OWNER; //владелец помещения

    @Override
    public String getAuthority() {
        return "ROLE_"+this.name();
    }
}
