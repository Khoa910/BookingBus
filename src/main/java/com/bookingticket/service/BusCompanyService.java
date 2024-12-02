package com.bookingticket.service;

import com.bookingticket.dto.request.BusCompanyRequest;
import com.bookingticket.dto.respond.BusCompanyRespond;
import com.bookingticket.entity.BusCompany;
import com.bookingticket.entity.User;
import com.bookingticket.mapper.BusCompanyMapper;
import com.bookingticket.repository.BusCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusCompanyService {

    private final BusCompanyRepository busCompanyRepository;
    private final BusCompanyMapper busCompanyMapper;

    @Autowired
    public BusCompanyService(BusCompanyRepository busCompanyRepository, BusCompanyMapper busCompanyMapper) {
        this.busCompanyRepository = busCompanyRepository;
        this.busCompanyMapper = busCompanyMapper;
    }

    public List<BusCompanyRespond> getAllBusCompanies() {
        List<BusCompany> busCompanies = busCompanyRepository.findAll();
        return busCompanies.stream().map(busCompanyMapper::toRespond).toList();
    }

    public List<BusCompany> getAllBusCompanies2() {
        return busCompanyRepository.findAll();
    }

    public Optional<BusCompanyRespond> getBusCompanyById(Long id) {
        Optional<BusCompany> busCompany = busCompanyRepository.findById(id);
        return busCompany.map(busCompanyMapper::toRespond);
    }

    public BusCompanyRespond addBusCompany(BusCompanyRequest busCompanyRequest) {

        Optional<BusCompany> existingBusCompany = busCompanyRepository.findByName(busCompanyRequest.getName());
        if (existingBusCompany.isPresent()) {
            throw new IllegalArgumentException("Bus Company with this name already exists!");
        }

        BusCompany busCompany = busCompanyMapper.toEntity(busCompanyRequest);
        BusCompany savedBusCompany = busCompanyRepository.save(busCompany);
        return busCompanyMapper.toRespond(savedBusCompany);
    }

    public BusCompany addBusCompany2(BusCompany busCompany){
        return busCompanyRepository.save(busCompany);
    }

    public boolean addBusCompany3(BusCompany company) {
        try {
            busCompanyRepository.save(company); // Lưu tài khoản mới
            return true; // Trả về true nếu thêm thành công
        } catch (Exception e) {
            // Ghi log lỗi nếu cần
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public Optional<BusCompanyRespond> updateBusCompany(Long id, BusCompanyRequest busCompanyRequest) {
        Optional<BusCompany> existingBusCompany = busCompanyRepository.findById(id);
        if (existingBusCompany.isPresent()) {
            BusCompany busCompany = existingBusCompany.get();
            busCompany.setName(busCompanyRequest.getName());
            busCompany.setPhone_number(busCompanyRequest.getPhone_number());

            BusCompany updatedBusCompany = busCompanyRepository.save(busCompany);
            return Optional.of(busCompanyMapper.toRespond(updatedBusCompany));
        }
        return Optional.empty();
    }

    public boolean deleteBusCompany(Long id) {
        if (busCompanyRepository.existsById(id)) {
            busCompanyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
