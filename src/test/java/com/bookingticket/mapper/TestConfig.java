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

    @Bean
    public BusCompanyMapper busCompanyMapper() {
        return Mappers.getMapper(BusCompanyMapper.class);
    }

    @Bean
    public BusScheduleMapper busScheduleMapper() {
        return Mappers.getMapper(BusScheduleMapper.class);
    }

//    @Bean
//    public Bus roleMapper() {
//        return Mappers.getMapper(RoleMapper.class);
//    }

//    @Bean
//    public RoleMapper roleMapper() {
//        return Mappers.getMapper(RoleMapper.class);
//    }
}