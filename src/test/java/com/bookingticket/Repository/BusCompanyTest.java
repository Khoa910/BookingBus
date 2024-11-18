package com.bookingticket.Repository;

import com.bookingticket.entity.BusCompany;
import com.bookingticket.repository.BusCompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BusCompanyTest {

    @Autowired
    private BusCompanyRepository busCompanyRepository;

    @Test
    public void testAddBusCompany() {

        // Tạo đối tượng BusCompany mới
        BusCompany newCompany = new BusCompany();
        newCompany.setName("KC");
        newCompany.setPhone_number("0336773026");

        // Lưu đối tượng vào cơ sở dữ liệu
        BusCompany savedCompany = busCompanyRepository.save(newCompany);

        // Kiểm tra xem đối tượng đã được lưu thành công hay chưa
        assertNotNull(savedCompany.getId(), "The saved BusCompany should have an ID");
        assertEquals(newCompany.getName(), savedCompany.getName(), "The name should be the same as the input");
        assertEquals(newCompany.getPhone_number(), savedCompany.getPhone_number(), "The phone number should be the same as the input");

        // In ra thông tin của công ty đã lưu
        System.out.println("Saved BusCompany ID: " + savedCompany.getId());
        System.out.println("Saved BusCompany Name: " + savedCompany.getName());
        System.out.println("Saved BusCompany Phone: " + savedCompany.getPhone_number());
    }
    @Test
    public void testUpdateBusCompany() {

        // Lấy đối tượng đã lưu từ cơ sở dữ liệu
        BusCompany companyToUpdate = busCompanyRepository.findById(6L).orElseThrow();

        // Cập nhật thông tin công ty
        companyToUpdate.setName("Phương Trang");
        companyToUpdate.setPhone_number("0336773027");

        // Lưu đối tượng đã cập nhật
        BusCompany updatedCompany = busCompanyRepository.save(companyToUpdate);

        // Kiểm tra xem đối tượng đã được cập nhật thành công
        assertNotNull(updatedCompany.getId(), "The updated BusCompany should have an ID");
        assertEquals("Phương Trang", updatedCompany.getName(), "The name should be updated correctly");
        assertEquals("0336773027", updatedCompany.getPhone_number(), "The phone number should be updated correctly");

        // In ra thông tin công ty đã cập nhật
        System.out.println("Updated BusCompany ID: " + updatedCompany.getId());
        System.out.println("Updated BusCompany Name: " + updatedCompany.getName());
        System.out.println("Updated BusCompany Phone: " + updatedCompany.getPhone_number());
    }
    @Test
    public void testDeleteBusCompanyById() {
        // Giả sử bạn có một id đã tồn tại trong cơ sở dữ liệu
        Long existingId = 6L; // Thay bằng ID thực tế có trong cơ sở dữ liệu của bạn

        // Kiểm tra xem đối tượng với ID đó có tồn tại không
        assertTrue(busCompanyRepository.findById(existingId).isPresent(), "BusCompany with ID " + existingId + " should exist");

        // Xóa đối tượng BusCompany bằng ID
        busCompanyRepository.deleteById(existingId);

        // Kiểm tra lại xem đối tượng đã bị xóa chưa
        assertFalse(busCompanyRepository.findById(existingId).isPresent(), "BusCompany with ID " + existingId + " should be deleted");

        System.out.println("BusCompany with ID " + existingId + " has been deleted.");
    }
}
