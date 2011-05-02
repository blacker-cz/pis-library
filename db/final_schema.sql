-- phpMyAdmin SQL Dump
-- version 3.2.5
-- http://www.phpmyadmin.net
--
-- Počítač: localhost
-- Vygenerováno: Pondělí 02. května 2011, 11:43
-- Verze MySQL: 5.1.48
-- Verze PHP: 5.3.2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databáze: `pislibrary`
--

-- --------------------------------------------------------

--
-- Struktura tabulky `author`
--

DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
  `idauthor` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`idauthor`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- --------------------------------------------------------

--
-- Struktura tabulky `book`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `idbook` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `year` date NOT NULL,
  `pages` int(11) DEFAULT NULL,
  `edition` int(11) DEFAULT NULL,
  `place` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `idgenre` int(11) NOT NULL,
  `idpublisher` int(11) NOT NULL,
  `type` enum('isbn','issn') COLLATE utf8_czech_ci NOT NULL DEFAULT 'isbn',
  `code` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  PRIMARY KEY (`idbook`),
  KEY `fk_book_genre1` (`idgenre`),
  KEY `fk_book_publisher1` (`idpublisher`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- --------------------------------------------------------

--
-- Struktura tabulky `booking`
--

DROP TABLE IF EXISTS `booking`;
CREATE TABLE `booking` (
  `idbooking` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `state` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  `idbook` int(11) NOT NULL,
  PRIMARY KEY (`idbooking`),
  UNIQUE KEY `iduser` (`iduser`,`idbook`),
  KEY `fk_booking_user1` (`iduser`),
  KEY `fk_booking_book1` (`idbook`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- --------------------------------------------------------

--
-- Struktura tabulky `book_has_author`
--

DROP TABLE IF EXISTS `book_has_author`;
CREATE TABLE `book_has_author` (
  `book_idbook` int(11) NOT NULL,
  `author_idauthor` int(11) NOT NULL,
  PRIMARY KEY (`book_idbook`,`author_idauthor`),
  KEY `fk_book_has_author_author1` (`author_idauthor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- --------------------------------------------------------

--
-- Struktura tabulky `borrow`
--

DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `idborrow` int(11) NOT NULL AUTO_INCREMENT,
  `borrowed` date NOT NULL,
  `returned` date DEFAULT NULL,
  `prolongations` int(11) NOT NULL DEFAULT '0',
  `iduser` int(11) NOT NULL,
  `idexemplar` int(11) NOT NULL,
  PRIMARY KEY (`idborrow`),
  KEY `fk_borrow_user` (`iduser`),
  KEY `fk_borrow_exemplar1` (`idexemplar`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- --------------------------------------------------------

--
-- Struktura tabulky `exemplar`
--

DROP TABLE IF EXISTS `exemplar`;
CREATE TABLE `exemplar` (
  `idexemplar` int(11) NOT NULL AUTO_INCREMENT,
  `aquired` date NOT NULL,
  `state` int(11) NOT NULL,
  `idbook` int(11) NOT NULL,
  PRIMARY KEY (`idexemplar`),
  KEY `fk_exemplar_book1` (`idbook`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- --------------------------------------------------------

--
-- Struktura tabulky `genre`
--

DROP TABLE IF EXISTS `genre`;
CREATE TABLE `genre` (
  `idgenre` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`idgenre`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- --------------------------------------------------------

--
-- Struktura tabulky `publisher`
--

DROP TABLE IF EXISTS `publisher`;
CREATE TABLE `publisher` (
  `idpublisher` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `address` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  PRIMARY KEY (`idpublisher`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

-- --------------------------------------------------------

--
-- Struktura tabulky `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `forename` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  `surname` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  `permitNumber` varchar(20) COLLATE utf8_czech_ci NOT NULL,
  `address` varchar(255) COLLATE utf8_czech_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8_czech_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_czech_ci NOT NULL,
  `registered` date NOT NULL,
  `expire` date NOT NULL,
  `password` varchar(32) COLLATE utf8_czech_ci NOT NULL,
  `level` enum('reader','librarian','admin') COLLATE utf8_czech_ci NOT NULL DEFAULT 'reader',
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `permitNumber_UNIQUE` (`permitNumber`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

--
-- Omezení pro exportované tabulky
--

--
-- Omezení pro tabulku `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `fk_book_genre1` FOREIGN KEY (`idgenre`) REFERENCES `genre` (`idgenre`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_book_publisher1` FOREIGN KEY (`idpublisher`) REFERENCES `publisher` (`idpublisher`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Omezení pro tabulku `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `fk_booking_book1` FOREIGN KEY (`idbook`) REFERENCES `book` (`idbook`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_booking_user1` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Omezení pro tabulku `book_has_author`
--
ALTER TABLE `book_has_author`
  ADD CONSTRAINT `fk_book_has_author_author1` FOREIGN KEY (`author_idauthor`) REFERENCES `author` (`idauthor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_book_has_author_book1` FOREIGN KEY (`book_idbook`) REFERENCES `book` (`idbook`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Omezení pro tabulku `borrow`
--
ALTER TABLE `borrow`
  ADD CONSTRAINT `fk_borrow_exemplar1` FOREIGN KEY (`idexemplar`) REFERENCES `exemplar` (`idexemplar`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_borrow_user` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Omezení pro tabulku `exemplar`
--
ALTER TABLE `exemplar`
  ADD CONSTRAINT `fk_exemplar_book1` FOREIGN KEY (`idbook`) REFERENCES `book` (`idbook`) ON DELETE NO ACTION ON UPDATE NO ACTION;

DELETE FROM `user`;
INSERT INTO `user` (`iduser`, `forename`, `surname`, `permitNumber`, `address`, `phone`, `email`, `registered`, `expire`, `password`, `level`) VALUES
	(1, 'Admin', 'Admin', '1', '', NULL, 'admin@example.com', '2011-04-08', '2035-04-08', 'admin', 'admin');