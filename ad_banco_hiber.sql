-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-01-2024 a las 12:29:27
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ad_banco_hiber`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `DNI` varchar(15) NOT NULL,
  `Nombre` varchar(255) NOT NULL,
  `Apellido1` varchar(255) NOT NULL,
  `Apellido2` varchar(255) DEFAULT NULL,
  `IDSucursal` int(11) DEFAULT NULL,
  `FechaAlta` date DEFAULT curdate(),
  `Contraseña` varchar(255) NOT NULL,
  `Telefono` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `IDSucursal` int(11) DEFAULT NULL,
  `codPostal` int(11) DEFAULT NULL,
  `tlf` int(11) DEFAULT NULL,
  `fecha_alta` varchar(11) NOT NULL,
  `Apellido1` varchar(25) NOT NULL,
  `Apellido2` varchar(25) NOT NULL,
  `Nombre` varchar(25) NOT NULL,
  `nacionalidad` varchar(25) DEFAULT NULL,
  `pais` varchar(25) DEFAULT NULL,
  `password` varchar(25) NOT NULL,
  `dni` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`IDSucursal`, `codPostal`, `tlf`, `fecha_alta`, `Apellido1`, `Apellido2`, `Nombre`, `nacionalidad`, `pais`, `password`, `dni`) VALUES
(1, 28232, -1, '2024-01-27', 'Barrondo', 'MArtin', 'Edurne', NULL, NULL, '1234', '54022903G');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuenta`
--

CREATE TABLE `cuenta` (
  `NUM` int(11) NOT NULL,
  `Saldo` decimal(10,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuentas`
--

CREATE TABLE `cuentas` (
  `numCuenta` int(11) NOT NULL,
  `saldo` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `info_cliente`
--

CREATE TABLE `info_cliente` (
  `DNI` varchar(15) NOT NULL,
  `Nacionalidad` varchar(255) DEFAULT NULL,
  `Pais` varchar(255) DEFAULT NULL,
  `CodigoPostal` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `info_cuenta`
--

CREATE TABLE `info_cuenta` (
  `NUM` int(11) NOT NULL,
  `DNI_Cliente` varchar(15) DEFAULT NULL,
  `Titular` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursal`
--

CREATE TABLE `sucursal` (
  `ID` int(11) NOT NULL,
  `Pais` varchar(255) NOT NULL,
  `Ciudad` varchar(255) NOT NULL,
  `Direccion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursales`
--

CREATE TABLE `sucursales` (
  `IDSucursal` int(11) NOT NULL,
  `pais` varchar(25) NOT NULL,
  `ciudad` varchar(30) NOT NULL,
  `direccion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `sucursales`
--

INSERT INTO `sucursales` (`IDSucursal`, `pais`, `ciudad`, `direccion`) VALUES
(1, 'España', 'Madrid', 'calle');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`DNI`),
  ADD KEY `IDSucursal` (`IDSucursal`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`dni`),
  ADD UNIQUE KEY `UK_1fo5nv5bypf6k9csqqlc10kd2` (`tlf`),
  ADD KEY `FKkwltx6lb2njcr7snw319q5qyx` (`IDSucursal`);

--
-- Indices de la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD PRIMARY KEY (`NUM`);

--
-- Indices de la tabla `cuentas`
--
ALTER TABLE `cuentas`
  ADD PRIMARY KEY (`numCuenta`);

--
-- Indices de la tabla `info_cliente`
--
ALTER TABLE `info_cliente`
  ADD PRIMARY KEY (`DNI`);

--
-- Indices de la tabla `info_cuenta`
--
ALTER TABLE `info_cuenta`
  ADD PRIMARY KEY (`NUM`),
  ADD KEY `DNI_Cliente` (`DNI_Cliente`);

--
-- Indices de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `sucursales`
--
ALTER TABLE `sucursales`
  ADD PRIMARY KEY (`IDSucursal`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cuentas`
--
ALTER TABLE `cuentas`
  MODIFY `numCuenta` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sucursales`
--
ALTER TABLE `sucursales`
  MODIFY `IDSucursal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`IDSucursal`) REFERENCES `sucursal` (`ID`),
  ADD CONSTRAINT `cliente_ibfk_2` FOREIGN KEY (`DNI`) REFERENCES `info_cliente` (`DNI`);

--
-- Filtros para la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD CONSTRAINT `FKkwltx6lb2njcr7snw319q5qyx` FOREIGN KEY (`IDSucursal`) REFERENCES `sucursales` (`IDSucursal`);

--
-- Filtros para la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD CONSTRAINT `cuenta_ibfk_1` FOREIGN KEY (`NUM`) REFERENCES `info_cuenta` (`NUM`);

--
-- Filtros para la tabla `info_cuenta`
--
ALTER TABLE `info_cuenta`
  ADD CONSTRAINT `info_cuenta_ibfk_1` FOREIGN KEY (`DNI_Cliente`) REFERENCES `cliente` (`DNI`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
