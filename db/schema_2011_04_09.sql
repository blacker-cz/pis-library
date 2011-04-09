# --------------------------------------------------------
# Host:                         127.0.0.1
# Server version:               5.1.41
# Server OS:                    pc-linux-gnu
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2011-04-09 11:31:22
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

# Dumping structure for table pislibrary.author
CREATE TABLE IF NOT EXISTS `author` (
  `idauthor` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`idauthor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

# Data exporting was unselected.


# Dumping structure for table pislibrary.book
CREATE TABLE IF NOT EXISTS `book` (
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
  KEY `fk_books_genre1` (`idgenre`),
  KEY `fk_books_publisher1` (`idpublisher`),
  CONSTRAINT `fk_books_genre1` FOREIGN KEY (`idgenre`) REFERENCES `genre` (`idgenre`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_books_publisher1` FOREIGN KEY (`idpublisher`) REFERENCES `publisher` (`idpublisher`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

# Data exporting was unselected.


# Dumping structure for table pislibrary.booking
CREATE TABLE IF NOT EXISTS `booking` (
  `idbooking` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `state` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  `idbook` int(11) NOT NULL,
  PRIMARY KEY (`idbooking`),
  KEY `fk_booking_user1` (`iduser`),
  KEY `fk_booking_books1` (`idbook`),
  CONSTRAINT `fk_booking_books1` FOREIGN KEY (`idbook`) REFERENCES `book` (`idbook`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_booking_user1` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

# Data exporting was unselected.


# Dumping structure for table pislibrary.book_has_author
CREATE TABLE IF NOT EXISTS `book_has_author` (
  `books_idbook` int(11) NOT NULL,
  `author_idauthor` int(11) NOT NULL,
  PRIMARY KEY (`books_idbook`,`author_idauthor`),
  KEY `fk_books_has_author_author1` (`author_idauthor`),
  CONSTRAINT `fk_books_has_author_author1` FOREIGN KEY (`author_idauthor`) REFERENCES `author` (`idauthor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_books_has_author_books1` FOREIGN KEY (`books_idbook`) REFERENCES `book` (`idbook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

# Data exporting was unselected.


# Dumping structure for table pislibrary.borrow
CREATE TABLE IF NOT EXISTS `borrow` (
  `idborrow` int(11) NOT NULL AUTO_INCREMENT,
  `borrowed` date NOT NULL,
  `returned` date DEFAULT NULL,
  `prolongations` int(11) NOT NULL DEFAULT '0',
  `iduser` int(11) NOT NULL,
  `idexemplar` int(11) NOT NULL,
  PRIMARY KEY (`idborrow`),
  KEY `fk_borrow_user` (`iduser`),
  KEY `fk_borrow_exemplar1` (`idexemplar`),
  CONSTRAINT `fk_borrow_exemplar1` FOREIGN KEY (`idexemplar`) REFERENCES `exemplar` (`idexemplar`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_borrow_user` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

# Data exporting was unselected.


# Dumping structure for table pislibrary.exemplar
CREATE TABLE IF NOT EXISTS `exemplar` (
  `idexemplar` int(11) NOT NULL AUTO_INCREMENT,
  `aquired` date NOT NULL,
  `state` int(11) NOT NULL,
  `idbook` int(11) NOT NULL,
  PRIMARY KEY (`idexemplar`),
  KEY `fk_exemplar_books1` (`idbook`),
  CONSTRAINT `fk_exemplar_books1` FOREIGN KEY (`idbook`) REFERENCES `book` (`idbook`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

# Data exporting was unselected.


# Dumping structure for table pislibrary.genre
CREATE TABLE IF NOT EXISTS `genre` (
  `idgenre` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`idgenre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

# Data exporting was unselected.


# Dumping structure for table pislibrary.publisher
CREATE TABLE IF NOT EXISTS `publisher` (
  `idpublisher` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `address` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`idpublisher`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

# Data exporting was unselected.


# Dumping structure for table pislibrary.user
CREATE TABLE IF NOT EXISTS `user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `forename` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  `surname` varchar(128) COLLATE utf8_czech_ci NOT NULL,
  `permitNumber` varchar(20) COLLATE utf8_czech_ci NOT NULL,
  `address` varchar(255) COLLATE utf8_czech_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8_czech_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8_czech_ci NOT NULL,
  `registered` date NOT NULL,
  `expire` date NOT NULL,
  `password` varchar(32) COLLATE utf8_czech_ci NOT NULL,
  `level` enum('reader','librarian','admin') COLLATE utf8_czech_ci NOT NULL DEFAULT 'reader',
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `permitNumber_UNIQUE` (`permitNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

# Data exporting was unselected.

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
