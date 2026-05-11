-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Erstellungszeit: 11. Mai 2026 um 19:44
-- Server-Version: 10.4.32-MariaDB
-- PHP-Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `ghostnet_db`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `geisternetz`
--

CREATE TABLE `geisternetz` (
  `id` bigint(20) NOT NULL,
  `gemeldetAm` datetime(6) DEFAULT NULL,
  `geschaetzteGroesse` int(11) DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `lon` double DEFAULT NULL,
  `status` enum('GEMELDET','BERGUNG_BEVORSTEHEND','GEBORGEN','VERSCHOLLEN') DEFAULT NULL,
  `bergende_person_id` bigint(20) DEFAULT NULL,
  `meldende_person_id` bigint(20) DEFAULT NULL,
  `verschollen_gemeldet_von_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `geisternetz`
--

INSERT INTO `geisternetz` (`id`, `gemeldetAm`, `geschaetzteGroesse`, `lat`, `lon`, `status`, `bergende_person_id`, `meldende_person_id`, `verschollen_gemeldet_von_id`) VALUES
(252, '2026-05-09 16:45:51.000000', 120, 54.321845, 6.742391, 'GEBORGEN', 302, 252, NULL),
(253, '2026-05-09 16:46:48.000000', 85, 55.018274, 6.742391, 'VERSCHOLLEN', 302, 252, 302),
(304, '2026-05-09 17:03:10.000000', 95, 57.482311, 1.924875, 'VERSCHOLLEN', 302, 353, 453),
(452, '2026-05-10 19:28:10.000000', 120, 54.321845, 6.742391, 'GEBORGEN', 353, NULL, NULL),
(502, '2026-05-10 20:13:31.000000', 360, 38.492731, -28.214875, 'VERSCHOLLEN', 406, 352, 454),
(503, '2026-05-10 20:14:01.000000', 240, 37.812639, -25.742183, 'BERGUNG_BEVORSTEHEND', 406, 402, NULL),
(504, '2026-05-10 20:14:29.000000', 195, 46.372918, -4.913762, 'BERGUNG_BEVORSTEHEND', 302, 403, NULL),
(505, '2026-05-10 20:14:48.000000', 185, 27.681294, -14.382905, 'VERSCHOLLEN', 407, NULL, 453),
(506, '2026-05-10 20:15:08.000000', 300, 28.143672, -15.927481, 'VERSCHOLLEN', 407, NULL, 353),
(507, '2026-05-10 20:15:31.000000', 145, 41.839274, 17.43862, 'BERGUNG_BEVORSTEHEND', 353, 404, NULL),
(508, '2026-05-10 20:15:53.000000', 205, 43.216894, 15.927381, 'BERGUNG_BEVORSTEHEND', 407, 404, NULL),
(509, '2026-05-10 20:16:27.000000', 170, 38.421957, 19.642813, 'BERGUNG_BEVORSTEHEND', 405, 403, NULL),
(510, '2026-05-10 20:18:50.000000', 95, 36.913742, 26.182649, 'BERGUNG_BEVORSTEHEND', 452, 408, NULL),
(511, '2026-05-10 20:19:22.000000', 130, 37.492816, 24.781395, 'BERGUNG_BEVORSTEHEND', 405, 252, NULL),
(512, '2026-05-10 20:19:45.000000', 75, 35.814927, 14.382751, 'GEMELDET', NULL, 252, NULL),
(513, '2026-05-10 20:20:10.000000', 230, 40.315829, 8.742103, 'VERSCHOLLEN', 405, 409, 353),
(514, '2026-05-10 20:21:36.000000', 115, 36.72148, -2.384951, 'BERGUNG_BEVORSTEHEND', 353, 352, NULL),
(516, '2026-05-10 20:27:13.000000', 160, 38.174629, 5.927314, 'BERGUNG_BEVORSTEHEND', 452, 410, NULL),
(517, '2026-05-10 20:27:57.000000', 90, 39.482716, 3.214895, 'GEMELDET', NULL, 410, NULL),
(552, '2026-05-11 16:43:33.000000', 275, 36.214759, -7.49261, 'GEMELDET', NULL, NULL, NULL),
(553, '2026-05-11 17:19:55.000000', 520, 38.762943, -12.384751, 'GEBORGEN', 353, 402, NULL),
(554, '2026-05-11 17:53:15.000000', 150, 43.874201, -9.613742, 'GEMELDET', NULL, 352, NULL),
(555, '2026-05-11 17:54:25.000000', 310, 45.219837, -6.482915, 'GEMELDET', NULL, 404, NULL),
(556, '2026-05-11 17:55:03.000000', 400, 48.732194, -8.391527, 'BERGUNG_BEVORSTEHEND', 353, 403, NULL),
(557, '2026-05-11 17:55:30.000000', 260, 57.018263, 20.134827, 'VERSCHOLLEN', NULL, 252, 353);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `geisternetz_seq`
--

CREATE TABLE `geisternetz_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `geisternetz_seq`
--

INSERT INTO `geisternetz_seq` (`next_val`) VALUES
(651);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `person`
--

CREATE TABLE `person` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `telefonnummer` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `person`
--

INSERT INTO `person` (`id`, `name`, `telefonnummer`) VALUES
(252, 'Benjamin Blümchen', '+493012340001'),
(302, 'Vaiana', '+41811230006'),
(352, 'Dori', '+4939112340004'),
(353, 'Käpt’n Blaubär', '+390612340010'),
(402, 'Patrick Star', '+4418651230020'),
(403, 'Nils Holgersson', '+46461230019'),
(404, 'Captain Hook', '+442012340014'),
(405, 'Wickie', '+47221230009'),
(406, 'Robinson Crusoe', '+464921230008'),
(407, 'Hein Blöd', '+911112340012'),
(408, 'Maya die Biene', '+4971112340017'),
(409, 'Jim Knopf', '+498912340015'),
(410, 'Bibi Blocksberg', '+493012340003'),
(452, 'Peter Pan', '+442012340013'),
(453, 'Pippi Langstrumpf', '+4681230007'),
(454, 'Bernd das Brot', '+911112340011');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `person_seq`
--

CREATE TABLE `person_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `person_seq`
--

INSERT INTO `person_seq` (`next_val`) VALUES
(551);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `geisternetz`
--
ALTER TABLE `geisternetz`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK30qb3xpsgi99vetpnwndr7d0c` (`bergende_person_id`),
  ADD KEY `FKhdmimbw2q4103ran1tlqenqfb` (`meldende_person_id`),
  ADD KEY `FKq4db6vjeyki8bl87o3rhs5af` (`verschollen_gemeldet_von_id`);

--
-- Indizes für die Tabelle `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_person_telefonnummer` (`telefonnummer`);

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `geisternetz`
--
ALTER TABLE `geisternetz`
  ADD CONSTRAINT `FK30qb3xpsgi99vetpnwndr7d0c` FOREIGN KEY (`bergende_person_id`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FKhdmimbw2q4103ran1tlqenqfb` FOREIGN KEY (`meldende_person_id`) REFERENCES `person` (`id`),
  ADD CONSTRAINT `FKq4db6vjeyki8bl87o3rhs5af` FOREIGN KEY (`verschollen_gemeldet_von_id`) REFERENCES `person` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
