package com.bookingticket.mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public BusMapper busMapper() {
        return Mappers.getMapper(BusMapper.class);
    }
}