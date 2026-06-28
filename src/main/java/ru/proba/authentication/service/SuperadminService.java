package ru.proba.authentication.service;

import cn.hutool.core.lang.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;
import ru.proba.authentication.enums.EnteredField;
import ru.proba.authentication.generated.model.UserAndRoleDto;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SuperadminService {

    RoleFixService rfs;

    public EnteredField getEnteredField(UserAndRoleDto body) {
        EmailValidator validator = EmailValidator.getInstance();
        boolean isEmail = validator.isValid(body.userData());
        if (isEmail) {
            return EnteredField.EMAIL;
        }
        else
        {
            boolean isUUID = Validator.isUUID(body.userData());
            if (isUUID) {
                return EnteredField.UUID;
            }
            else {
                return EnteredField.LOGIN;
            }
        }
    }

    public boolean roleStrategy(UserAndRoleDto body, boolean isAdd) {
        EnteredField ef = getEnteredField(body);
        boolean t=false;
        if (isAdd){
            t=rfs.addRole(body, ef);
        } else {
            t=rfs.removeRole(body, ef);
        }
        return t;
    }
}
