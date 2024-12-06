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

    public Optional<BusCompany> getBusCompanyById2(long stationId) {
        return busCompanyRepository.findById(stationId);
    }

    public BusCompany getBusCompanyById3(long Id) {
        return busCompanyRepository.findById(Id).orElse(null);
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

    public boolean updateBusCompany2(BusCompany company) {
        BusCompany BCompany = busCompanyRepository.findById(company.getId()).orElse(null);
        if (BCompany != null) {
            BCompany.setName(company.getName());
            BCompany.setPhone_number(company.getPhone_number());
            busCompanyRepository.save(BCompany);
            return true;
        }
        return false;
    }


    public void deleteBusCompany(Long id) {
        Optional<BusCompany> busCompanyOptional = busCompanyRepository.findById(id);
        if (busCompanyOptional.isPresent()) {
            busCompanyRepository.delete(busCompanyOptional.get());
        } else {
            throw new RuntimeException("BusStation not found with id: " + id);
        }
    }
}
