-- Tạo bảng `role` lưu trữ các giá trị role
CREATE TABLE `role` (
                        `id` BIGINT PRIMARY KEY,
                        `name` VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

-- Thêm các giá trị vào bảng `role`
INSERT INTO `role` (`id`, `name`) VALUES
                                      (1, 'USER'),
                                      (2, 'ADMIN');


-- Tạo bảng `seattype`
CREATE TABLE `seattype` (
                            `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                            `seat_count` BIGINT NOT NULL,
                            `description` VARCHAR(255)  NULL
) ENGINE=InnoDB;
-- Thêm dữ liệu vào bảng `seattype` với phân chia nửa đầu là A và nửa sau là B
INSERT INTO `seattype` (`seat_count`, `description`) VALUES
    ('34', '34-seat bus');

-- Tạo bảng `busstation`
CREATE TABLE `busstation` (
                              `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                              `name` VARCHAR(255) NOT NULL,
                              `address` VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

-- Thêm dữ liệu vào bảng `busstation`
INSERT INTO `busstation` (`name`, `address`) VALUES
                                                 ('Bến xe Miền Đông', 'TP. Hồ Chí Minh'),
                                                 ('Bến xe Giáp Bát', 'Hà Nội'),
                                                 ('Bến xe Đà Nẵng', 'Đà Nẵng');

-- Tạo bảng `buscompany`
CREATE TABLE `buscompany` (
                              `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                              `name` VARCHAR(255) NOT NULL,
                              `phone_number` VARCHAR(15)
) ENGINE=InnoDB;

INSERT INTO `buscompany` (`name`, `phone_number`) VALUES
                                                      ('Công ty Xe khách Phương Trang', '1900 6060'),
                                                      ('Công ty Xe khách Mai Linh', '1900 5959'),
                                                      ('Công ty Xe khách Hoàng Long', '1900 9090');

-- Tạo bảng `bus`
CREATE TABLE `bus` (
                       `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                       `license_plate` VARCHAR(20) NOT NULL,
                       `seat_type_id` BIGINT NULL,
                       `bus_type` ENUM('SLEEPER', 'SEATED') NOT NULL,
                       `bus_company_id` BIGINT NULL,
                       `departure_station_id` BIGINT NULL,
                       `arrival_station_id` BIGINT NULL,
                       FOREIGN KEY (`bus_company_id`) REFERENCES `buscompany`(`id`) ON DELETE SET NULL,
                       FOREIGN KEY (`departure_station_id`) REFERENCES `busstation`(`id`) ON DELETE SET NULL,
                       FOREIGN KEY (`arrival_station_id`) REFERENCES `busstation`(`id`) ON DELETE SET NULL,
                       FOREIGN KEY (`seat_type_id`) REFERENCES `seattype`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Thêm dữ liệu vào bảng `bus`
INSERT INTO `bus` (`license_plate`, `seat_type_id`, `bus_type`, `bus_company_id`, `departure_station_id`, `arrival_station_id`) VALUES
                                                                                                                                    ('29A-12345', 1, 'SLEEPER', 1, 1, 2),
                                                                                                                                    ('31D-33445', 1, 'SLEEPER', 1, 2, 1),
                                                                                                                                    ('32E-44556', 1, 'SEATED', 2, 3, 1);

-- Tạo bảng `user`
CREATE TABLE `user` (
                        `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                        `username` VARCHAR(50) NOT NULL UNIQUE,
                        `password` VARCHAR(255) NOT NULL,
                        `full_name` VARCHAR(100) NOT NULL,
                        `phone_number` VARCHAR(15) NOT NULL,
                        `email` VARCHAR(100) NOT NULL ,
                        `address` VARCHAR(255) NULL,
                        `role` BIGINT  NULL DEFAULT 1,
                        FOREIGN KEY (`role`) REFERENCES `role`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Thêm dữ liệu vào bảng `user`
INSERT INTO `user` (`username`, `password`, `full_name`, `phone_number`, `email`, `address`, `role`) VALUES
                                                                                                         ('admin', 'password1', 'Nguyễn Văn A', '0901112233', 'a@gmail.com', 'TP. Hồ Chí Minh', 1),
                                                                                                         ('khach1', 'password2', 'Lê Thị B', '0911223344', 'b@gmail.com', 'Hà Nội', 2);


-- Tạo bảng `ticket`
CREATE TABLE `ticket` (
                          `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                          `bus_id` BIGINT NULL,
                          `user_id` BIGINT NULL,
                          `seat_number` VARCHAR(10),
                          `departure_time` DATETIME NOT NULL,
                          `price` DECIMAL(10, 2) NOT NULL,
                          `status` ENUM('SOLD', 'CANCELLED', 'PENDING') DEFAULT 'PENDING',
                          FOREIGN KEY (`bus_id`) REFERENCES `bus`(`id`) ON DELETE SET NULL,
                          FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Thêm dữ liệu vào bảng `ticket`
INSERT INTO `ticket` (`bus_id`, `user_id`, `seat_number`, `departure_time`, `price`, `status`) VALUES
    (1, 1, 'S01', '2024-11-06 07:30:00', 200000, 'SOLD');


-- Tạo bảng `busschedule`
CREATE TABLE `busschedule` (
                               `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                               `bus_id` BIGINT,
                               `departure_station_id` BIGINT NULL,
                               `arrival_station_id` BIGINT NULL,
                               `departure_time` DATETIME NOT NULL,
                               `arrival_time` DATETIME NOT NULL,
                               `price` DECIMAL(10, 2) NOT NULL,
                               FOREIGN KEY (`bus_id`) REFERENCES `bus`(`id`) ON DELETE CASCADE,
                               FOREIGN KEY (`departure_station_id`) REFERENCES `busstation`(`id`) ON DELETE SET NULL,
                               FOREIGN KEY (`arrival_station_id`) REFERENCES `busstation`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

INSERT INTO `busschedule` (`bus_id`, `departure_station_id`, `arrival_station_id`, `departure_time`, `arrival_time`, `price`) VALUES
                                                                                                                                  (1, 1, 2, '2024-11-07 07:30:00', '2024-11-07 11:00:00', 200000),
                                                                                                                                  (2, 2, 3, '2024-11-07 08:00:00', '2024-11-07 12:30:00', 150000),
                                                                                                                                  (3, 1, 3, '2024-11-07 09:00:00', '2024-11-07 13:00:00', 120000),
                                                                                                                                  (1, 1, 2, '2024-11-07 10:30:00', '2024-11-07 14:00:00', 200000),
                                                                                                                                  (2, 2, 1, '2024-11-07 11:00:00', '2024-11-07 15:30:00', 150000),
                                                                                                                                  (3, 1, 3, '2024-11-07 12:00:00', '2024-11-07 16:00:00', 120000),
                                                                                                                                  (2, 2, 1, '2024-11-07 13:00:00', '2024-11-07 17:30:00', 150000),
                                                                                                                                  (1, 2, 3, '2024-11-07 14:00:00', '2024-11-07 18:30:00', 200000);


-- Tạo bảng `seat`
CREATE TABLE `seat` (
                        `id_seat` VARCHAR(10) PRIMARY KEY,
                        `bus_id` BIGINT NULL,
                        `status` ENUM('AVAILABLE', 'BOOKED', 'RESERVED') NOT NULL,
                        `seat_name` VARCHAR(10),
                        `seat_type_id` BIGINT NULL,
                        FOREIGN KEY (`bus_id`) REFERENCES `bus`(`id`) ON DELETE SET NULL,
                        FOREIGN KEY (`seat_type_id`) REFERENCES `seattype`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

INSERT INTO `seat` (`id_seat`, `bus_id`, `status`, `seat_name`, `seat_type_id`) VALUES
                                                                                    ('1-1', 1, 'BOOKED', 'A1', 1),
                                                                                    ('1-10', 1, 'BOOKED', 'A10', 1),
                                                                                    ('1-11', 1, 'BOOKED', 'A11', 1),
                                                                                    ('1-12', 1, 'BOOKED', 'A12', 1),
                                                                                    ('1-13', 1, 'BOOKED', 'A13', 1),
                                                                                    ('1-14', 1, 'BOOKED', 'A14', 1),
                                                                                    ('1-15', 1, 'BOOKED', 'A15', 1),
                                                                                    ('1-16', 1, 'BOOKED', 'A16', 1),
                                                                                    ('1-17', 1, 'BOOKED', 'A17', 1),
                                                                                    ('1-18', 1, 'BOOKED', 'B1', 1),
                                                                                    ('1-19', 1, 'BOOKED', 'B2', 1),
                                                                                    ('1-2', 1, 'BOOKED', 'A2', 1),
                                                                                    ('1-20', 1, 'BOOKED', 'B3', 1),
                                                                                    ('1-21', 1, 'BOOKED', 'B4', 1),
                                                                                    ('1-22', 1, 'BOOKED', 'B5', 1),
                                                                                    ('1-23', 1, 'BOOKED', 'B6', 1),
                                                                                    ('1-24', 1, 'BOOKED', 'B7', 1),
                                                                                    ('1-25', 1, 'BOOKED', 'B8', 1),
                                                                                    ('1-26', 1, 'AVAILABLE', 'B9', 1),
                                                                                    ('1-27', 1, 'AVAILABLE', 'B10', 1),
                                                                                    ('1-28', 1, 'AVAILABLE', 'B11', 1),
                                                                                    ('1-29', 1, 'AVAILABLE', 'B12', 1),
                                                                                    ('1-3', 1, 'AVAILABLE', 'A3', 1),
                                                                                    ('1-30', 1, 'AVAILABLE', 'B13', 1),
                                                                                    ('1-31', 1, 'AVAILABLE', 'B14', 1),
                                                                                    ('1-32', 1, 'AVAILABLE', 'B15', 1),
                                                                                    ('1-33', 1, 'AVAILABLE', 'B16', 1),
                                                                                    ('1-34', 1, 'AVAILABLE', 'B17', 1),
                                                                                    ('1-4', 1, 'AVAILABLE', 'A4', 1),
                                                                                    ('1-5', 1, 'AVAILABLE', 'A5', 1),
                                                                                    ('1-6', 1, 'AVAILABLE', 'A6', 1),
                                                                                    ('1-7', 1, 'AVAILABLE', 'A7', 1),
                                                                                    ('1-8', 1, 'AVAILABLE', 'A8', 1),
                                                                                    ('1-9', 1, 'BOOKED', 'A9', 1);





-- Tạo bảng `payment`
CREATE TABLE `payment` (
                           `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                           `ticket_id` BIGINT NULL,
                           `payment_method` ENUM('CASH', 'BANKING', 'CREDIT') DEFAULT 'CASH',
                           `amount` DECIMAL(10, 2) NOT NULL,
                           `payment_time` DATETIME NOT NULL,
                           `status` ENUM('SUCCESS', 'FAILURE', 'PENDING') DEFAULT 'PENDING',
                           FOREIGN KEY (`ticket_id`) REFERENCES `ticket`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Thêm dữ liệu vào bảng `payment`
INSERT INTO `payment` (`ticket_id`, `payment_method`, `amount`, `payment_time`, `status`) VALUES
                                                                                              (1, 'CASH', 200000, '2024-11-06 07:00:00', 'SUCCESS');


DELIMITER $$

CREATE TRIGGER `auto_create_seats`
    AFTER INSERT ON `bus`
    FOR EACH ROW
BEGIN
    DECLARE seat_count BIGINT;
    DECLARE i INT DEFAULT 1;
    DECLARE seat_name VARCHAR(10);
    DECLARE group_size INT;

    -- Lấy số lượng ghế từ `seattype` tương ứng với `seat_type_id` của chuyến xe
    SELECT seattype.seat_count INTO seat_count
    FROM seattype
    WHERE seattype.id = NEW.seat_type_id;

    IF seat_count IS NOT NULL AND seat_count > 0 THEN
        -- Xác định kích thước nhóm ghế A và B
        SET group_size = seat_count / 2;

        -- Tạo các ghế tương ứng
        WHILE i <= seat_count DO
                -- Tạo tên ghế (A1-A17, B1-B17 hoặc tương tự cho xe khác)
                IF i <= group_size THEN
                    -- Ghế thuộc nhóm A
                    SET seat_name = CONCAT('A', i);
                ELSE
                    -- Ghế thuộc nhóm B
                    SET seat_name = CONCAT('B', i - group_size);  -- Lấy lại chỉ số cho nhóm B
                END IF;

                -- Thêm ghế vào bảng `seat`
                INSERT INTO `seat` (`id_seat`, `bus_id`, `status`, `seat_name`, `seat_type_id`)
                VALUES (CONCAT(NEW.id, '-', i), NEW.id, 'AVAILABLE', seat_name, NEW.seat_type_id);

                SET i = i + 1;
            END WHILE;
    ELSE
        -- Nếu không có seat_count hợp lệ, có thể ghi log hoặc thực hiện hành động khác
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid seat type or seat count not found';
    END IF;
END$$

DELIMITER ;


