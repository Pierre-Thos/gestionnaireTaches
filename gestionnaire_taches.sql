-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:4022
-- Generation Time: Dec 17, 2025 at 08:34 PM
-- Server version: 5.7.24
-- PHP Version: 8.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestionnaire_taches`
--

-- --------------------------------------------------------

--
-- Table structure for table `projet`
--

CREATE TABLE `projet` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `projet`
--

INSERT INTO `projet` (`id`, `nom`, `description`) VALUES
(2, 'Projet NoSQL', 'Projet de backend avec fonctionnalités suivant les routes'),
(3, 'Projet Java', 'Projet Full stack avec interface et commandes CRUD');

-- --------------------------------------------------------

--
-- Table structure for table `projet_collaborateurs`
--

CREATE TABLE `projet_collaborateurs` (
  `projet_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `nom`) VALUES
(1, 'ADMIN'),
(2, 'CHEF_PROJET'),
(3, 'COLLABORATEUR');

-- --------------------------------------------------------

--
-- Table structure for table `tache`
--

CREATE TABLE `tache` (
  `id` int(11) NOT NULL,
  `texte` varchar(500) NOT NULL,
  `colonne` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `projet_id` int(11) DEFAULT NULL,
  `date_echeance` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tache`
--

INSERT INTO `tache` (`id`, `texte`, `colonne`, `created_at`, `user_id`, `projet_id`, `date_echeance`) VALUES
(3, 'Faire des routes POST', 1, '2025-12-17 11:45:12', 9, 2, '2025-12-20'),
(4, 'Ajout de deuxieme route d\'aggregation', 1, '2025-12-17 13:47:26', 7, 2, '2025-12-20'),
(5, 'Faire des test unitaires', 0, '2025-12-17 14:14:39', 7, 2, '2025-12-19'),
(6, 'Faire des requetes', 2, '2025-12-17 14:30:37', 9, 2, '2025-12-18'),
(7, 'Optimiser les routes', 0, '2025-12-17 20:13:15', 9, 2, '2025-12-18');

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `role_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `email`, `mot_de_passe`, `role_id`) VALUES
(7, 'pierre2@ece.fr', '$2a$10$eViGORoaELrwdgu5SRLZ8O1fNXd8LD0jPNdX6wmkKY8SOQg/huzfG', 3),
(9, 'jean@ece.fr', '$2a$10$zamakRSHoaLtQW7MwfNrJe3l.UzxkbCCqwyGe7.KCfGG8KJ2tf7UO', 3),
(10, 'admin@ece.fr', '$2a$10$Y8H.sxHXPMQMs9ggcb2fuup0g7pI7OQ/68R1CGvLGwz62d6Vrqhvy', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `projet`
--
ALTER TABLE `projet`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `projet_collaborateurs`
--
ALTER TABLE `projet_collaborateurs`
  ADD PRIMARY KEY (`projet_id`,`user_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQUE` (`nom`);

--
-- Indexes for table `tache`
--
ALTER TABLE `tache`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_tache_utilisateur` (`user_id`),
  ADD KEY `fk_projet` (`projet_id`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `projet`
--
ALTER TABLE `projet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tache`
--
ALTER TABLE `tache`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `projet_collaborateurs`
--
ALTER TABLE `projet_collaborateurs`
  ADD CONSTRAINT `projet_collaborateurs_ibfk_1` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `projet_collaborateurs_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `tache`
--
ALTER TABLE `tache`
  ADD CONSTRAINT `fk_projet` FOREIGN KEY (`projet_id`) REFERENCES `projet` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `fk_tache_utilisateur` FOREIGN KEY (`user_id`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
