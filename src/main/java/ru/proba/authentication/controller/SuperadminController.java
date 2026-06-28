package ru.proba.authentication.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.SuperadminApi;
import ru.proba.authentication.generated.model.UserAndRoleDto;
import ru.proba.authentication.service.SuperadminService;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SuperadminController implements SuperadminApi {

    SuperadminService service;

    @Override
    public ResponseEntity<Void> addRoleToUser(UserAndRoleDto body) {
        boolean changed = service.roleStrategy(body, true);
        if (changed) {
            return ResponseEntity.ok().build();
        } else  {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Void> removeRole(UserAndRoleDto body) {
        boolean changed = service.roleStrategy(body, false);
        if (changed) {
            return ResponseEntity.ok().build();
        } else  {
            return ResponseEntity.badRequest().build();
        }
    }
}
