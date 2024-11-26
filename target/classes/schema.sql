-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost
-- Thời gian đã tạo: Th10 26, 2024 lúc 12:02 PM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `bookingticket`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `bus`
--

CREATE TABLE `bus` (
  `id` bigint(20) NOT NULL,
  `license_plate` varchar(20) NOT NULL,
  `seat_type_id` bigint(20) NOT NULL,
  `bus_type` enum('SLEEPER','SEATED') NOT NULL,
  `bus_company_id` bigint(20) DEFAULT NULL,
  `departure_station_id` bigint(20) DEFAULT NULL,
  `arrival_station_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `bus`
--

INSERT INTO `bus` (`id`, `license_plate`, `seat_type_id`, `bus_type`, `bus_company_id`, `departure_station_id`, `arrival_station_id`) VALUES
(1, '29A-12345', 1, 'SLEEPER', 1, 1, 2),
(2, '29B-67890', 2, 'SEATED', 2, 2, 3),
(3, '30C-11222', 2, 'SEATED', 3, 1, 3),
(4, '31D-33445', 1, 'SLEEPER', 1, 2, 1),
(5, '32E-44556', 1, 'SEATED', 2, 3, 1);

--
-- Bẫy `bus`
--
DELIMITER $$
CREATE TRIGGER `auto_create_seats` AFTER INSERT ON `bus` FOR EACH ROW BEGIN
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
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `buscompany`
--

CREATE TABLE `buscompany` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `buscompany`
--

INSERT INTO `buscompany` (`id`, `name`, `phone_number`) VALUES
(1, 'Phương Trang', '0901234567'),
(2, 'Mai Linh', '0912345678'),
(3, 'Tuyến xe Nam', '0987654321');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `busschedule`
--

CREATE TABLE `busschedule` (
  `id` bigint(20) NOT NULL,
  `bus_id` bigint(20) DEFAULT NULL,
  `departure_station_id` bigint(20) DEFAULT NULL,
  `arrival_station_id` bigint(20) DEFAULT NULL,
  `departure_time` datetime NOT NULL,
  `arrival_time` datetime NOT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `busschedule`
--

INSERT INTO `busschedule` (`id`, `bus_id`, `departure_station_id`, `arrival_station_id`, `departure_time`, `arrival_time`, `price`) VALUES
(1, 1, 1, 2, '2024-11-06 07:30:00', '2024-11-06 11:00:00', 200000.00),
(2, 2, 2, 3, '2024-11-06 08:00:00', '2024-11-06 12:30:00', 150000.00),
(3, 3, 1, 3, '2024-11-06 09:00:00', '2024-11-06 13:00:00', 120000.00),
(4, 4, 2, 1, '2024-11-06 10:30:00', '2024-11-06 14:00:00', 180000.00),
(5, 5, 3, 1, '2024-11-06 11:00:00', '2024-11-06 15:00:00', 160000.00);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `busstation`
--

CREATE TABLE `busstation` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `busstation`
--

INSERT INTO `busstation` (`id`, `name`, `address`) VALUES
(1, 'Bến xe Miền Đông', 'TP. Hồ Chí Minh'),
(2, 'Bến xe Giáp Bát', 'Hà Nội'),
(3, 'Bến xe Đà Nẵng', 'Đà Nẵng');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `payment`
--

CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL,
  `ticket_id` bigint(20) DEFAULT NULL,
  `payment_method` enum('CASH','BANKING','CREDIT') DEFAULT 'CASH',
  `amount` decimal(10,2) NOT NULL,
  `payment_time` datetime NOT NULL,
  `status` enum('SUCCESS','FAILURE','PENDING') DEFAULT 'PENDING'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `payment`
--

INSERT INTO `payment` (`id`, `ticket_id`, `payment_method`, `amount`, `payment_time`, `status`) VALUES
(1, 1, 'CASH', 200000.00, '2024-11-06 07:00:00', 'SUCCESS'),
(2, 2, 'BANKING', 150000.00, '2024-11-06 08:30:00', 'SUCCESS'),
(3, 3, 'CREDIT', 120000.00, '2024-11-06 09:30:00', 'PENDING'),
(4, 4, 'CASH', 180000.00, '2024-11-06 10:00:00', 'FAILURE'),
(5, 5, 'BANKING', 160000.00, '2024-11-06 11:30:00', 'PENDING');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'USER'),
(2, 'ADMIN'),
(3, 'MANAGER');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `seat`
--

CREATE TABLE `seat` (
  `id_seat` varchar(10) NOT NULL,
  `bus_id` bigint(20) DEFAULT NULL,
  `status` enum('AVAILABLE','BOOKED','RESERVED') NOT NULL,
  `seat_name` varchar(10) DEFAULT NULL,
  `seat_type_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `seat`
--

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
('1-9', 1, 'BOOKED', 'A9', 1),
('2-1', 2, 'AVAILABLE', 'A18', 2),
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
('2-2', 2, 'BOOKED', 'A19', 2),
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
('2-3', 2, 'AVAILABLE', 'A20', 2),
('2-30', 2, 'AVAILABLE', 'B32', 2),
('2-31', 2, 'AVAILABLE', 'B33', 2),
('2-32', 2, 'AVAILABLE', 'B34', 2),
('2-4', 2, 'AVAILABLE', 'A21', 2),
('2-5', 2, 'AVAILABLE', 'A22', 2),
('2-6', 2, 'AVAILABLE', 'A23', 2),
('2-7', 2, 'AVAILABLE', 'A24', 2),
('2-8', 2, 'AVAILABLE', 'A25', 2),
('2-9', 2, 'AVAILABLE', 'A26', 2),
('3-1', 3, 'AVAILABLE', 'A33', 3),
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
('3-2', 3, 'BOOKED', 'A34', 3),
('3-20', 3, 'AVAILABLE', 'B40', 3),
('3-21', 3, 'AVAILABLE', 'B41', 3),
('3-22', 3, 'AVAILABLE', 'B42', 3),
('3-23', 3, 'AVAILABLE', 'B43', 3),
('3-24', 3, 'AVAILABLE', 'B44', 3),
('3-25', 3, 'AVAILABLE', 'B45', 3),
('3-26', 3, 'AVAILABLE', 'B46', 3),
('3-3', 3, 'AVAILABLE', 'A35', 3),
('3-4', 3, 'AVAILABLE', 'A36', 3),
('3-5', 3, 'AVAILABLE', 'A37', 3),
('3-6', 3, 'AVAILABLE', 'A38', 3),
('3-7', 3, 'AVAILABLE', 'A39', 3),
('3-8', 3, 'AVAILABLE', 'A40', 3),
('3-9', 3, 'AVAILABLE', 'A41', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `seattype`
--

CREATE TABLE `seattype` (
  `id` bigint(20) NOT NULL,
  `seat_count` bigint(20) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `seattype`
--

INSERT INTO `seattype` (`id`, `seat_count`, `description`) VALUES
(1, 34, '34-seat bus'),
(2, 40, '40-seat bus'),
(3, 46, '46-seat bus');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ticket`
--

CREATE TABLE `ticket` (
  `id` bigint(20) NOT NULL,
  `bus_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `seat_number` varchar(10) DEFAULT NULL,
  `departure_time` datetime NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `status` enum('SOLD','CANCELLED','PENDING') DEFAULT 'PENDING'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ticket`
--

INSERT INTO `ticket` (`id`, `bus_id`, `user_id`, `seat_number`, `departure_time`, `price`, `status`) VALUES
(1, 1, 1, 'S01', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(2, 2, 2, 'S05', '2024-11-06 08:00:00', 150000.00, 'SOLD'),
(3, 3, 3, 'S10', '2024-11-06 09:00:00', 120000.00, 'PENDING'),
(4, 4, 1, 'S12', '2024-11-06 10:30:00', 180000.00, 'CANCELLED'),
(5, 5, 2, 'S15', '2024-11-06 11:00:00', 160000.00, 'PENDING'),
(6, 1, 4, '1-1', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(7, 1, 4, '1-10', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(8, 1, 4, '1-11', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(9, 1, 4, '1-12', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(10, 1, 4, '1-13', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(11, 1, 4, '1-14', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(12, 1, 4, '1-15', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(13, 1, 4, '1-16', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(14, 1, 4, '1-17', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(15, 1, 4, '1-18', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(16, 1, 4, '1-19', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(17, 1, 4, '1-20', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(18, 1, 4, '1-21', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(19, 1, 4, '1-22', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(20, 1, 4, '1-23', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(21, 1, 4, '1-24', '2024-11-06 07:30:00', 200000.00, 'SOLD'),
(22, 1, 4, '1-25', '2024-11-06 07:30:00', 200000.00, 'SOLD');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `role` bigint(20) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `full_name`, `phone_number`, `email`, `address`, `role`) VALUES
(1, 'khach1', 'password1', 'Nguyễn Văn A', '0901112233', 'a@gmail.com', 'TP. Hồ Chí Minh', 1),
(2, 'admin', 'password2', 'Lê Thị B', '0911223344', 'b@gmail.com', 'Hà Nội', 2),
(3, 'quanly', 'password3', 'Trần Công C', '0922334455', 'c@gmail.com', 'Đà Nẵng', 3),
(4, 'Unknow', '$2a$10$K1b4IzW5UcpczcIElw8ht.1NJztZlscgD8WkI2Rf.iAVawTXwA6z2', 'Unknow', '0123456789', 'abcds@gmail.com', 'Unknow', 1),
(5, 'Lê Duy Khang', 'OAuth', 'Lê Duy Khang', '1234567890', 'lengankhanh2006@gmail.com', 'TPHCM', 1),
(6, 'Ticket Booking', 'OAuth', 'Ticket Booking', '1234567890', 'ticketbooking571@gmail.com', 'TPHCM', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `bus`
--
ALTER TABLE `bus`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bus_company_id` (`bus_company_id`),
  ADD KEY `departure_station_id` (`departure_station_id`),
  ADD KEY `arrival_station_id` (`arrival_station_id`),
  ADD KEY `seat_type_id` (`seat_type_id`);

--
-- Chỉ mục cho bảng `buscompany`
--
ALTER TABLE `buscompany`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `busschedule`
--
ALTER TABLE `busschedule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bus_id` (`bus_id`),
  ADD KEY `departure_station_id` (`departure_station_id`),
  ADD KEY `arrival_station_id` (`arrival_station_id`);

--
-- Chỉ mục cho bảng `busstation`
--
ALTER TABLE `busstation`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ticket_id` (`ticket_id`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `seat`
--
ALTER TABLE `seat`
  ADD PRIMARY KEY (`id_seat`),
  ADD KEY `bus_id` (`bus_id`),
  ADD KEY `seat_type_id` (`seat_type_id`);

--
-- Chỉ mục cho bảng `seattype`
--
ALTER TABLE `seattype`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `ticket`
--
ALTER TABLE `ticket`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bus_id` (`bus_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `role` (`role`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `bus`
--
ALTER TABLE `bus`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `buscompany`
--
ALTER TABLE `buscompany`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `busschedule`
--
ALTER TABLE `busschedule`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `busstation`
--
ALTER TABLE `busstation`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `payment`
--
ALTER TABLE `payment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `seattype`
--
ALTER TABLE `seattype`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `ticket`
--
ALTER TABLE `ticket`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `bus`
--
ALTER TABLE `bus`
  ADD CONSTRAINT `bus_ibfk_1` FOREIGN KEY (`bus_company_id`) REFERENCES `buscompany` (`id`),
  ADD CONSTRAINT `bus_ibfk_2` FOREIGN KEY (`departure_station_id`) REFERENCES `busstation` (`id`),
  ADD CONSTRAINT `bus_ibfk_3` FOREIGN KEY (`arrival_station_id`) REFERENCES `busstation` (`id`),
  ADD CONSTRAINT `bus_ibfk_4` FOREIGN KEY (`seat_type_id`) REFERENCES `seattype` (`id`);

--
-- Các ràng buộc cho bảng `busschedule`
--
ALTER TABLE `busschedule`
  ADD CONSTRAINT `busschedule_ibfk_1` FOREIGN KEY (`bus_id`) REFERENCES `bus` (`id`),
  ADD CONSTRAINT `busschedule_ibfk_2` FOREIGN KEY (`departure_station_id`) REFERENCES `busstation` (`id`),
  ADD CONSTRAINT `busschedule_ibfk_3` FOREIGN KEY (`arrival_station_id`) REFERENCES `busstation` (`id`);

--
-- Các ràng buộc cho bảng `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`ticket_id`) REFERENCES `ticket` (`id`);

--
-- Các ràng buộc cho bảng `seat`
--
ALTER TABLE `seat`
  ADD CONSTRAINT `seat_ibfk_1` FOREIGN KEY (`bus_id`) REFERENCES `bus` (`id`),
  ADD CONSTRAINT `seat_ibfk_2` FOREIGN KEY (`seat_type_id`) REFERENCES `seattype` (`id`);

--
-- Các ràng buộc cho bảng `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`bus_id`) REFERENCES `bus` (`id`),
  ADD CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Các ràng buộc cho bảng `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
