package com.bookingticket.repository;

import com.bookingticket.entity.BusCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusCompanyRepository extends JpaRepository<BusCompany, Long> {
    Optional<BusCompany> findByName(String name);
}
