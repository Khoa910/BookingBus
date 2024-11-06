-- Tạo bảng `role` lưu trữ các giá trị role
CREATE TABLE `role` (
                        `id` INT PRIMARY KEY,
                        `name` VARCHAR(50) NOT NULL
);

-- Thêm các giá trị vào bảng `role`
INSERT INTO `role` (`id`, `name`) VALUES
                                      (1, 'Khách hàng'),
                                      (2, 'Quản trị viên'),
                                      (3, 'Quản lý');

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
                       `seat_count` INT NOT NULL,
                       `bus_type` ENUM('Giường nằm', 'Ghế ngồi') NOT NULL,
                       `bus_company_id` BIGINT,  -- Cột liên kết với `BusCompany`
                       `departure_station_id` BIGINT,
                       `arrival_station_id` BIGINT,
                       FOREIGN KEY (`bus_company_id`) REFERENCES `buscompany`(`id`),
                       FOREIGN KEY (`departure_station_id`) REFERENCES `busstation`(`id`),
                       FOREIGN KEY (`arrival_station_id`) REFERENCES `busstation`(`id`)
);
-- Thêm dữ liệu vào bảng `bus`
INSERT INTO `bus` (`license_plate`, `seat_count`, `bus_type`, `bus_company_id`, `departure_station_id`, `arrival_station_id`) VALUES
                                                                                                                                  ('29A-12345', 40, 'Giường nằm', 1, 1, 2),
                                                                                                                                  ('29B-67890', 30, 'Ghế ngồi', 2, 2, 3),
                                                                                                                                  ('30C-11222', 45, 'Ghế ngồi', 3, 1, 3),
                                                                                                                                  ('31D-33445', 35, 'Giường nằm', 1, 2, 1),
                                                                                                                                  ('32E-44556', 50, 'Ghế ngồi', 2, 3, 1);

-- Tạo bảng `user` với trường `role` là INTEGER
CREATE TABLE `user` (
                        `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                        `username` VARCHAR(50) NOT NULL UNIQUE,
                        `password` VARCHAR(255) NOT NULL,
                        `full_name` VARCHAR(100) NOT NULL,
                        `phone_number` VARCHAR(15) NOT NULL,
                        `email` VARCHAR(100),
                        `address` VARCHAR(255),
                        `role` INT NOT NULL DEFAULT 1,  -- Trường role là INTEGER
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
                          `status` ENUM('Đã bán', 'Đã hủy', 'Đang chờ') DEFAULT 'Đang chờ',
                          FOREIGN KEY (`bus_id`) REFERENCES `bus`(`id`),
                          FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

-- Thêm dữ liệu vào bảng `ticket`
INSERT INTO `ticket` (`bus_id`, `user_id`, `seat_number`, `departure_time`, `price`, `status`) VALUES
                                                                                                   (1, 1, 'S01', '2024-11-06 07:30:00', 200000, 'Đã bán'),
                                                                                                   (2, 2, 'S05', '2024-11-06 08:00:00', 150000, 'Đã bán'),
                                                                                                   (3, 3, 'S10', '2024-11-06 09:00:00', 120000, 'Đang chờ'),
                                                                                                   (4, 1, 'S12', '2024-11-06 10:30:00', 180000, 'Đã hủy'),
                                                                                                   (5, 2, 'S15', '2024-11-06 11:00:00', 160000, 'Đang chờ');

-- Tạo bảng `bus_schedule`
CREATE TABLE `bus_schedule` (
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
INSERT INTO `bus_schedule` (`bus_id`, `departure_station_id`, `arrival_station_id`, `departure_time`, `arrival_time`, `price`) VALUES
                                                                                                                                   (1, 1, 2, '2024-11-06 07:30:00', '2024-11-06 11:00:00', 200000),
                                                                                                                                   (2, 2, 3, '2024-11-06 08:00:00', '2024-11-06 12:30:00', 150000),
                                                                                                                                   (3, 1, 3, '2024-11-06 09:00:00', '2024-11-06 13:00:00', 120000),
                                                                                                                                   (4, 2, 1, '2024-11-06 10:30:00', '2024-11-06 14:00:00', 180000),
                                                                                                                                   (5, 3, 1, '2024-11-06 11:00:00', '2024-11-06 15:00:00', 160000);

-- Tạo bảng `seat`
CREATE TABLE `seat` (
                        `id_seat` VARCHAR(10) PRIMARY KEY,
                        `bus_id` BIGINT,
                        `status` VARCHAR(50),
                        `seat_name` VARCHAR(10),
                        FOREIGN KEY (`bus_id`) REFERENCES `bus`(`id`)
);
INSERT INTO `seat` (`id_seat`, `bus_id`, `status`, `seat_name`) VALUES
                                                                    ('1_1', 1, '', '1_1'),
                                                                    ('1_2', 1, 'Đặt', '1_2'),
                                                                    ('1_4', 1, '', 'A3'),
                                                                    ('1_5', 1, '', 'A4'),
                                                                    ('2_1', 1, '', 'A5'),
                                                                    ('2_2', 1, '', 'A6'),
                                                                    ('2_4', 1, '', 'A7'),
                                                                    ('2_5', 1, '', 'A8'),
                                                                    ('3_1', 1, 'Đặt', 'A9'),
                                                                    ('3_2', 1, '', 'A10'),
                                                                    ('3_4', 1, '', 'A11'),
                                                                    ('3_5', 1, '', 'A12'),
                                                                    ('4_1', 1, '', 'A13'),
                                                                    ('4_2', 1, '', 'A14'),
                                                                    ('4_4', 1, '', 'A15'),
                                                                    ('4_5', 1, '', 'A16'),
                                                                    ('5_1', 1, 'Đặt', 'A17'),
                                                                    ('5_2', 1, '', 'A18'),
                                                                    ('5_4', 1, '', 'A19'),
                                                                    ('5_5', 1, '', 'A20'),
                                                                    ('6_1', 1, '', 'A21'),
                                                                    ('6_2', 1, '', 'A22'),
                                                                    ('6_4', 1, '', 'A23'),
                                                                    ('6_5', 1, '', 'A24'),
                                                                    ('7_1', 1, '', 'A25'),
                                                                    ('7_2', 1, '', 'A26'),
                                                                    ('7_4', 1, '', 'A27'),
                                                                    ('7_5', 1, '', 'A28'),
                                                                    ('8_1', 1, '', 'A29'),
                                                                    ('8_2', 1, '', 'A30'),
                                                                    ('8_4', 1, '', 'A31'),
                                                                    ('8_5', 1, '', 'A32'),
                                                                    ('9_1', 1, '', 'A33'),
                                                                    ('9_2', 1, '', 'A34'),
                                                                    ('9_3', 1, '', 'A35'),
                                                                    ('9_4', 1, '', 'A36'),
                                                                    ('9_5', 1, '', 'A37'),
                                                                    ('10_1', 1, NULL, ''),
                                                                    ('10_2', 1, NULL, ''),
                                                                    ('10_4', 1, NULL, '');

-- Tạo bảng `payment`
CREATE TABLE `payment` (
                           `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                           `ticket_id` BIGINT,
                           `payment_method` ENUM('Tiền mặt', 'Chuyển khoản', 'Thẻ tín dụng') NOT NULL,
                           `amount` DECIMAL(10, 2) NOT NULL,
                           `payment_time` DATETIME NOT NULL,
                           `status` ENUM('Thành công', 'Thất bại', 'Đang chờ') DEFAULT 'Đang chờ',
                           FOREIGN KEY (`ticket_id`) REFERENCES `ticket`(`id`)
);

-- Thêm dữ liệu vào bảng `payment`
INSERT INTO `payment` (`ticket_id`, `payment_method`, `amount`, `payment_time`, `status`) VALUES
                                                                                              (1, 'Tiền mặt', 200000, '2024-11-06 07:00:00', 'Thành công'),
                                                                                              (2, 'Chuyển khoản', 150000, '2024-11-06 08:30:00', 'Thành công'),
                                                                                              (3, 'Thẻ tín dụng', 120000, '2024-11-06 09:30:00', 'Đang chờ'),
                                                                                              (4, 'Tiền mặt', 180000, '2024-11-06 10:00:00', 'Thất bại'),
                                                                                              (5, 'Chuyển khoản', 160000, '2024-11-06 11:30:00', 'Đang chờ');