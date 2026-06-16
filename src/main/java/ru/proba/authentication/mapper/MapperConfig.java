package ru.proba.authentication.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public AccessTokenInfoMapper accessTokenInfoMapper(){
        return Mappers.getMapper(AccessTokenInfoMapper.class);
    }
}
