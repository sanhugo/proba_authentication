package ru.proba.authentication.records;

import java.util.UUID;

public record ApproveCodeDTO(UUID userID, String activation_code) {
}
