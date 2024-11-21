package com.bookingticket.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BusTest {

   @Test
   void getId() {
       // Tạo đối tượng Bus và gán ID là 1
       Bus bus = new Bus();
       Long id = 1L;
       bus.setId(id);  // Gán ID cho bus

       // Kiểm tra xem phương thức getId() có trả về đúng ID không
       assertEquals(1, bus.getId(), "The Bus ID should be 1");
       System.out.println("Test passed: The Bus ID is correctly set to 1");
   }


}
