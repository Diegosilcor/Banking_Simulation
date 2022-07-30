-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-07-2022 a las 05:57:20
-- Versión del servidor: 10.4.18-MariaDB
-- Versión de PHP: 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `devsus_challenge`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `cliente_id` bigint(20) NOT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `age` bigint(20) DEFAULT NULL,
  `dni` bigint(20) DEFAULT NULL,
  `gender` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `soft_delete` bit(1) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`cliente_id`, `address`, `age`, `dni`, `gender`, `name`, `phone`, `password`, `soft_delete`, `status`) VALUES
(1, 'Otavalo sn y principal', NULL, NULL, NULL, 'José Lema', '098254785', '1234', b'0', b'1'),
(2, 'Amazonas y NNUU', NULL, NULL, NULL, 'Marianela Montalvo', '097548965', '5678', b'0', b'1'),
(3, '13 junio y Equinoccial', NULL, NULL, NULL, 'Juan Osorio', '098874587', '1245', b'0', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuentas`
--

CREATE TABLE `cuentas` (
  `cuenta_id` bigint(20) NOT NULL,
  `balance` bigint(20) DEFAULT NULL,
  `soft_delete` bit(1) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `cliente_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `cuentas`
--

INSERT INTO `cuentas` (`cuenta_id`, `balance`, `soft_delete`, `status`, `type`, `cliente_id`) VALUES
(1, 1425, b'0', b'1', 0, 1),
(2, 700, b'0', b'1', 1, 2),
(3, 150, b'0', b'1', 0, 3),
(4, 0, b'0', b'1', 0, 2),
(5, 1000, b'0', b'1', 0, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientos`
--

CREATE TABLE `movimientos` (
  `movimiento_id` bigint(20) NOT NULL,
  `amount` bigint(20) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `final_balance` bigint(20) DEFAULT NULL,
  `soft_delete` bit(1) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `cuenta_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `movimientos`
--

INSERT INTO `movimientos` (`movimiento_id`, `amount`, `date`, `final_balance`, `soft_delete`, `type`, `cuenta_id`) VALUES
(1, 575, '2022-07-16 00:51:25', 1425, b'0', 0, 1),
(2, 600, '2022-07-16 00:53:42', 700, b'0', 1, 2),
(3, 150, '2022-07-16 00:54:17', 150, b'0', 1, 3),
(4, 540, '2022-07-16 00:54:38', 0, b'0', 0, 4);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`cliente_id`);

--
-- Indices de la tabla `cuentas`
--
ALTER TABLE `cuentas`
  ADD PRIMARY KEY (`cuenta_id`),
  ADD KEY `FK65yk2321jpusl3fk96lqehrli` (`cliente_id`);

--
-- Indices de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD PRIMARY KEY (`movimiento_id`),
  ADD KEY `FK4moe88hxuohcysas5h70mdc09` (`cuenta_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `cliente_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `cuentas`
--
ALTER TABLE `cuentas`
  MODIFY `cuenta_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  MODIFY `movimiento_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cuentas`
--
ALTER TABLE `cuentas`
  ADD CONSTRAINT `FK65yk2321jpusl3fk96lqehrli` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`cliente_id`);

--
-- Filtros para la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD CONSTRAINT `FK4moe88hxuohcysas5h70mdc09` FOREIGN KEY (`cuenta_id`) REFERENCES `cuentas` (`cuenta_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
