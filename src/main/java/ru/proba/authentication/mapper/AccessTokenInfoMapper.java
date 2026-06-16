package ru.proba.authentication.mapper;

import org.mapstruct.Mapper;
import ru.proba.authentication.entity.User;
import ru.proba.authentication.records.AccessTokenInfo;

@Mapper
public interface AccessTokenInfoMapper {
    AccessTokenInfo fromUser(User u);
}
