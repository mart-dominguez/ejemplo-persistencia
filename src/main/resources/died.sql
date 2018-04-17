-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 17-04-2018 a las 13:44:53
-- Versión del servidor: 5.7.21-0ubuntu0.17.10.1
-- Versión de PHP: 7.1.15-0ubuntu0.17.10.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `died`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `AGENDA`
--

CREATE TABLE `AGENDA` (
  `ID` int(11) NOT NULL,
  `NOMBRE` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TAREA`
--

CREATE TABLE `TAREA` (
  `ID` int(11) NOT NULL,
  `DESCRIPCION` varchar(250) NOT NULL,
  `FINALIZADA` int(11) DEFAULT NULL,
  `ID_AGENDA` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `AGENDA`
--
ALTER TABLE `AGENDA`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `TAREA`
--
ALTER TABLE `TAREA`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `AGENDA`
--
ALTER TABLE `AGENDA`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=108;
--
-- AUTO_INCREMENT de la tabla `TAREA`
--
ALTER TABLE `TAREA`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=106;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
