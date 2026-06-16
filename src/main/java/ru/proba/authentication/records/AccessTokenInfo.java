package ru.proba.authentication.records;

import ru.proba.authentication.enums.Role;

import java.util.Set;
import java.util.UUID;

public record AccessTokenInfo(UUID id, Set<Role> roles) {
}
