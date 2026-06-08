package ru.proba.authentication.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.proba.authentication.generated.api.SuperadminApi;
import ru.proba.authentication.generated.model.UserAndRoleForRemovalDto;
import ru.proba.authentication.generated.model.UserNewRoleDto;

@RestController
public class SuperadminController implements SuperadminApi {
    @Override
    public String addRoleToUser(UserNewRoleDto body) {
        return null;
    }

    @Override
    public String removeRole(UserAndRoleForRemovalDto body) {
        return null;
    }
}
