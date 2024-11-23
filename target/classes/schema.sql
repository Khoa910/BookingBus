-- Tạo bảng `role` lưu trữ các giá trị role
CREATE TABLE `role` (
                        `id` BIGINT PRIMARY KEY,
                        `name` VARCHAR(50) NOT NULL
);

-- Thêm các giá trị vào bảng `role`
INSERT INTO `role` (`id`, `name`) VALUES
                                      (1, 'USER'),
                                      (2, 'ADMIN'),
                                      (3, 'MANAGER');
CREATE TABLE `seattype` (
                            `id` BIGINT AUTO_INCREMENT PRIMARY KEY ,
                            `seat_count` BIGINT NOT NULL ,
                            `description` VARCHAR(255) NOT NULL
);

-- Thêm dữ liệu vào bảng `seattype` với phân chia nửa đầu là A và nửa sau là B
INSERT INTO `seattype` (`seat_count`, `description`) VALUES
                                                         ('34', '34-seat bus'),
                                                         ('40', '40-seat bus'),
                                                         ('46', '46-seat bus');
-- Tạo bảng `busstation`
CREATE TABLE `busstation` (
                              `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                              `name` VARCHAR(255) NOT NULL,
                              `address` VARCHAR(255) NOT NULL
);

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
);
-- Thêm dữ liệu vào bảng `buscompany`
INSERT INTO `buscompany` (`name`, `phone_number`) VALUES
                                                      ('Phương Trang', '0901234567'),
                                                      ('Mai Linh', '0912345678'),
                                                      ('Tuyến xe Nam', '0987654321');

-- Tạo bảng `bus`
CREATE TABLE `bus` (
                       `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                       `license_plate` VARCHAR(20) NOT NULL,
                       `seat_type_id` BIGINT NOT NULL,
                       `bus_type` ENUM('SLEEPER', 'SEATED') NOT NULL,
                       `bus_company_id` BIGINT,  -- Cột liên kết với `BusCompany`
                       `departure_station_id` BIGINT,
                       `arrival_station_id` BIGINT,
                       FOREIGN KEY (`bus_company_id`) REFERENCES `buscompany`(`id`),
                       FOREIGN KEY (`departure_station_id`) REFERENCES `busstation`(`id`),
                       FOREIGN KEY (`arrival_station_id`) REFERENCES `busstation`(`id`),
                       FOREIGN KEY (`seat_type_id`) REFERENCES `seattype`(`id`)
);

-- Thêm dữ liệu vào bảng `bus`
INSERT INTO `bus` (`license_plate`, `seat_type_id`, `bus_type`, `bus_company_id`, `departure_station_id`, `arrival_station_id`) VALUES
                                                                                                                                    ('29A-12345', 1, 'SLEEPER', 1, 1, 2),
                                                                                                                                    ('29B-67890', 2, 'SEATED', 2, 2, 3),
                                                                                                                                    ('30C-11222', 2, 'SEATED', 3, 1, 3),
                                                                                                                                    ('31D-33445', 1, 'SLEEPER', 1, 2, 1),
                                                                                                                                    ('32E-44556', 1, 'SEATED', 2, 3, 1);

-- Tạo bảng `user` với trường `role` là INTEGER
CREATE TABLE `user` (
                        `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                        `username` VARCHAR(50) NOT NULL UNIQUE,
                        `password` VARCHAR(255) NOT NULL,
                        `full_name` VARCHAR(100) NOT NULL,
                        `phone_number` VARCHAR(15) NOT NULL,
                        `email` VARCHAR(100),
                        `address` VARCHAR(255),
                        `role` BIGINT NOT NULL DEFAULT 1,  -- Trường role là INTEGER
                        FOREIGN KEY (`role`) REFERENCES `role`(`id`)  -- Khóa ngoại tới bảng `role`
);

-- Thêm dữ liệu vào bảng `user`
INSERT INTO `user` (`username`, `password`, `full_name`, `phone_number`, `email`, `address`, `role`) VALUES
                                                                                                         ('khach1', 'password1', 'Nguyễn Văn A', '0901112233', 'a@gmail.com', 'TP. Hồ Chí Minh', 1),
                                                                                                         ('admin', 'password2', 'Lê Thị B', '0911223344', 'b@gmail.com', 'Hà Nội', 2),
                                                                                                         ('quanly', 'password3', 'Trần Công C', '0922334455', 'c@gmail.com', 'Đà Nẵng', 3);

-- Tạo bảng `ticket`
CREATE TABLE `ticket` (
                          `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                          `bus_id` BIGINT,
                          `user_id` BIGINT,
                          `seat_number` VARCHAR(10),
                          `departure_time` DATETIME NOT NULL,
                          `price` DECIMAL(10, 2) NOT NULL,
                          `status` ENUM('SOLD', 'CANCELLED', 'PENDING') DEFAULT 'PENDING',
                          FOREIGN KEY (`bus_id`) REFERENCES `bus`(`id`),
                          FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

-- Thêm dữ liệu vào bảng `ticket`
INSERT INTO `ticket` (`bus_id`, `user_id`, `seat_number`, `departure_time`, `price`, `status`) VALUES
                                                                                                   (1, 1, 'S01', '2024-11-06 07:30:00', 200000, 'SOLD'),
                                                                                                   (2, 2, 'S05', '2024-11-06 08:00:00', 150000, 'SOLD'),
                                                                                                   (3, 3, 'S10', '2024-11-06 09:00:00', 120000, 'PENDING'),
                                                                                                   (4, 1, 'S12', '2024-11-06 10:30:00', 180000, 'CANCELLED'),
                                                                                                   (5, 2, 'S15', '2024-11-06 11:00:00', 160000, 'PENDING');

-- Tạo bảng `bus_schedule`
CREATE TABLE `busschedule` (
                                `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                `bus_id` BIGINT,
                                `departure_station_id` BIGINT,
                                `arrival_station_id` BIGINT,
                                `departure_time` DATETIME NOT NULL,
                                `arrival_time` DATETIME NOT NULL,
                                `price` DECIMAL(10, 2) NOT NULL,
                                FOREIGN KEY (`bus_id`) REFERENCES `bus`(`id`),
                                FOREIGN KEY (`departure_station_id`) REFERENCES `busstation`(`id`),
                                FOREIGN KEY (`arrival_station_id`) REFERENCES `busstation`(`id`)
);

-- Thêm dữ liệu vào bảng `bus_schedule`
INSERT INTO busschedule (`bus_id`, `departure_station_id`, `arrival_station_id`, `departure_time`, `arrival_time`, `price`) VALUES
                                                                                                                                   (1, 1, 2, '2024-11-06 07:30:00', '2024-11-06 11:00:00', 200000),
                                                                                                                                   (2, 2, 3, '2024-11-06 08:00:00', '2024-11-06 12:30:00', 150000),
                                                                                                                                   (3, 1, 3, '2024-11-06 09:00:00', '2024-11-06 13:00:00', 120000),
                                                                                                                                   (4, 2, 1, '2024-11-06 10:30:00', '2024-11-06 14:00:00', 180000),
                                                                                                                                   (5, 3, 1, '2024-11-06 11:00:00', '2024-11-06 15:00:00', 160000);

-- Tạo bảng `seat`
CREATE TABLE `seat` (
                        `id_seat` VARCHAR(10) PRIMARY KEY,
                        `bus_id` BIGINT,
                        `status` ENUM('AVAILABLE', 'BOOKED', 'RESERVED') NOT NULL,
                        `seat_name` VARCHAR(10),
                        `seat_type_id` BIGINT,
                        FOREIGN KEY (`bus_id`) REFERENCES `bus`(`id`),
                        FOREIGN KEY (`seat_type_id`) REFERENCES `seattype`(`id`)
);

-- Thêm dữ liệu vào bảng `seat`
INSERT INTO `seat` (`id_seat`, `bus_id`, `status`, `seat_name`, `seat_type_id`) VALUES
                                                                                    -- Ghế của xe có 34 chỗ ngồi
                                                                                    ('1-1', 1, 'AVAILABLE', 'A1', 1),
                                                                                    ('1-2', 1, 'BOOKED', 'A2', 1),
                                                                                    ('1-3', 1, 'AVAILABLE', 'A3', 1),
                                                                                    ('1-4', 1, 'AVAILABLE', 'A4', 1),
                                                                                    ('1-5', 1, 'AVAILABLE', 'A5', 1),
                                                                                    ('1-6', 1, 'AVAILABLE', 'A6', 1),
                                                                                    ('1-7', 1, 'AVAILABLE', 'A7', 1),
                                                                                    ('1-8', 1, 'AVAILABLE', 'A8', 1),
                                                                                    ('1-9', 1, 'BOOKED', 'A9', 1),
                                                                                    ('1-10', 1, 'AVAILABLE', 'A10', 1),
                                                                                    ('1-11', 1, 'AVAILABLE', 'A11', 1),
                                                                                    ('1-12', 1, 'AVAILABLE', 'A12', 1),
                                                                                    ('1-13', 1, 'AVAILABLE', 'A13', 1),
                                                                                    ('1-14', 1, 'AVAILABLE', 'A14', 1),
                                                                                    ('1-15', 1, 'AVAILABLE', 'A15', 1),
                                                                                    ('1-16', 1, 'AVAILABLE', 'A16', 1),
                                                                                    ('1-17', 1, 'AVAILABLE', 'B1', 1),
                                                                                    ('1-18', 1, 'AVAILABLE', 'B2', 1),
                                                                                    ('1-19', 1, 'AVAILABLE', 'B3', 1),
                                                                                    ('1-20', 1, 'AVAILABLE', 'B4', 1),
                                                                                    ('1-21', 1, 'AVAILABLE', 'B5', 1),
                                                                                    ('1-22', 1, 'AVAILABLE', 'B6', 1),
                                                                                    ('1-23', 1, 'AVAILABLE', 'B7', 1),
                                                                                    ('1-24', 1, 'AVAILABLE', 'B8', 1),
                                                                                    ('1-25', 1, 'AVAILABLE', 'B9', 1),
                                                                                    ('1-26', 1, 'AVAILABLE', 'B10', 1),
                                                                                    ('1-27', 1, 'AVAILABLE', 'B11', 1),
                                                                                    ('1-28', 1, 'AVAILABLE', 'B12', 1),
                                                                                    ('1-29', 1, 'AVAILABLE', 'B13', 1),
                                                                                    ('1-30', 1, 'AVAILABLE', 'B14', 1),
                                                                                    ('1-31', 1, 'AVAILABLE', 'B15', 1),
                                                                                    ('1-32', 1, 'AVAILABLE', 'B16', 1),
                                                                                    ('1-33', 1, 'AVAILABLE', 'B17', 1),
                                                                                    -- Ghế của xe có 40 chỗ ngồi
                                                                                    ('2-1', 2, 'AVAILABLE', 'A18', 2),
                                                                                    ('2-2', 2, 'BOOKED', 'A19', 2),
                                                                                    ('2-3', 2, 'AVAILABLE', 'A20', 2),
                                                                                    ('2-4', 2, 'AVAILABLE', 'A21', 2),
                                                                                    ('2-5', 2, 'AVAILABLE', 'A22', 2),
                                                                                    ('2-6', 2, 'AVAILABLE', 'A23', 2),
                                                                                    ('2-7', 2, 'AVAILABLE', 'A24', 2),
                                                                                    ('2-8', 2, 'AVAILABLE', 'A25', 2),
                                                                                    ('2-9', 2, 'AVAILABLE', 'A26', 2),
                                                                                    ('2-10', 2, 'AVAILABLE', 'A27', 2),
                                                                                    ('2-11', 2, 'AVAILABLE', 'A28', 2),
                                                                                    ('2-12', 2, 'AVAILABLE', 'A29', 2),
                                                                                    ('2-13', 2, 'AVAILABLE', 'A30', 2),
                                                                                    ('2-14', 2, 'AVAILABLE', 'A31', 2),
                                                                                    ('2-15', 2, 'AVAILABLE', 'A32', 2),
                                                                                    ('2-16', 2, 'AVAILABLE', 'B18', 2),
                                                                                    ('2-17', 2, 'AVAILABLE', 'B19', 2),
                                                                                    ('2-18', 2, 'AVAILABLE', 'B20', 2),
                                                                                    ('2-19', 2, 'AVAILABLE', 'B21', 2),
                                                                                    ('2-20', 2, 'AVAILABLE', 'B22', 2),
                                                                                    ('2-21', 2, 'AVAILABLE', 'B23', 2),
                                                                                    ('2-22', 2, 'AVAILABLE', 'B24', 2),
                                                                                    ('2-23', 2, 'AVAILABLE', 'B25', 2),
                                                                                    ('2-24', 2, 'AVAILABLE', 'B26', 2),
                                                                                    ('2-25', 2, 'AVAILABLE', 'B27', 2),
                                                                                    ('2-26', 2, 'AVAILABLE', 'B28', 2),
                                                                                    ('2-27', 2, 'AVAILABLE', 'B29', 2),
                                                                                    ('2-28', 2, 'AVAILABLE', 'B30', 2),
                                                                                    ('2-29', 2, 'AVAILABLE', 'B31', 2),
                                                                                    ('2-30', 2, 'AVAILABLE', 'B32', 2),
                                                                                    ('2-31', 2, 'AVAILABLE', 'B33', 2),
                                                                                    ('2-32', 2, 'AVAILABLE', 'B34', 2),
                                                                                    -- Ghế của xe có 46 chỗ ngồi
                                                                                    ('3-1', 3, 'AVAILABLE', 'A33', 3),
                                                                                    ('3-2', 3, 'BOOKED', 'A34', 3),
                                                                                    ('3-3', 3, 'AVAILABLE', 'A35', 3),
                                                                                    ('3-4', 3, 'AVAILABLE', 'A36', 3),
                                                                                    ('3-5', 3, 'AVAILABLE', 'A37', 3),
                                                                                    ('3-6', 3, 'AVAILABLE', 'A38', 3),
                                                                                    ('3-7', 3, 'AVAILABLE', 'A39', 3),
                                                                                    ('3-8', 3, 'AVAILABLE', 'A40', 3),
                                                                                    ('3-9', 3, 'AVAILABLE', 'A41', 3),
                                                                                    ('3-10', 3, 'AVAILABLE', 'A42', 3),
                                                                                    ('3-11', 3, 'AVAILABLE', 'A43', 3),
                                                                                    ('3-12', 3, 'AVAILABLE', 'A44', 3),
                                                                                    ('3-13', 3, 'AVAILABLE', 'A45', 3),
                                                                                    ('3-14', 3, 'AVAILABLE', 'A46', 3),
                                                                                    ('3-15', 3, 'AVAILABLE', 'B35', 3),
                                                                                    ('3-16', 3, 'AVAILABLE', 'B36', 3),
                                                                                    ('3-17', 3, 'AVAILABLE', 'B37', 3),
                                                                                    ('3-18', 3, 'AVAILABLE', 'B38', 3),
                                                                                    ('3-19', 3, 'AVAILABLE', 'B39', 3),
                                                                                    ('3-20', 3, 'AVAILABLE', 'B40', 3),
                                                                                    ('3-21', 3, 'AVAILABLE', 'B41', 3),
                                                                                    ('3-22', 3, 'AVAILABLE', 'B42', 3),
                                                                                    ('3-23', 3, 'AVAILABLE', 'B43', 3),
                                                                                    ('3-24', 3, 'AVAILABLE', 'B44', 3),
                                                                                    ('3-25', 3, 'AVAILABLE', 'B45', 3),
                                                                                    ('3-26', 3, 'AVAILABLE', 'B46', 3);




-- Tạo bảng `payment`
CREATE TABLE `payment` (
                           `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                           `ticket_id` BIGINT,
                           `payment_method` ENUM('CASH', 'BANKING', 'CREDIT') DEFAULT 'CASH',
                           `amount` DECIMAL(10, 2) NOT NULL,
                           `payment_time` DATETIME NOT NULL,
                           `status` ENUM('SUCCESS', 'FAILURE', 'PENDING') DEFAULT 'PENDING',
                           FOREIGN KEY (`ticket_id`) REFERENCES `ticket`(`id`)
);

-- Thêm dữ liệu vào bảng `payment`
INSERT INTO `payment` (`ticket_id`, `payment_method`, `amount`, `payment_time`, `status`) VALUES
                                                                                              (1, 'CASH', 200000, '2024-11-06 07:00:00', 'SUCCESS'),
                                                                                              (2, 'BANKING', 150000, '2024-11-06 08:30:00', 'SUCCESS'),
                                                                                              (3, 'CREDIT', 120000, '2024-11-06 09:30:00', 'PENDING'),
                                                                                              (4, 'CASH', 180000, '2024-11-06 10:00:00', 'FAILURE'),
                                                                                              (5, 'BANKING', 160000, '2024-11-06 11:30:00', 'PENDING');

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


