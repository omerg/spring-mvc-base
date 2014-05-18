--
-- Database: `demoSchema`
--
CREATE DATABASE `demoSchema`;
USE `demoSchema`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `surname` varchar(50) DEFAULT NULL,
  `phoneNumber` varchar(13) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
);

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `username`, `name`, `surname`, `phoneNumber`) VALUES
(1238, 'gurarslan', 'Omer', 'Gurarslan', '0216444044'),
(1239, 'deliRemzi', 'Remzi', 'Delirium', '0216444044'),
(1240, 'safsataNuri', 'Nuri', 'Saphsatha', '0216444044'),
(1241, 'imamMustafa', 'Imam', 'Mustafa', '0216444044'),
(1242, 'kelNiyazi', 'Kel', 'Niyazi', '0216444044'),
(1243, 'babyFace', 'Justin', 'Biber', '0216444044'),
(1244, 'starsailor90', 'Hamza', 'Yerlikaya', '0216444044'),
(1245, 'yusuf1905', 'Yusuf', 'Cucumber', '0216444044'),
(1246, 'kloroflorokarbon', 'Kular', 'Fular', '0216444044'),
(1247, 'terliksi', 'Omer', 'Polaris', '0216444044'),
(1248, 'mitokondri', 'Mithat', 'Kondak', '0216444044');
