SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `pislibrary` DEFAULT CHARACTER SET utf8 COLLATE utf8_czech_ci ;
USE `pislibrary` ;

-- -----------------------------------------------------
-- Table `pislibrary`.`user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pislibrary`.`user` (
  `iduser` INT NOT NULL AUTO_INCREMENT ,
  `permitNumber` VARCHAR(20) NOT NULL ,
  `address` VARCHAR(255) NOT NULL ,
  `phone` VARCHAR(20) NULL ,
  `email` VARCHAR(100) NOT NULL ,
  `registered` DATE NOT NULL ,
  `expire` DATE NOT NULL ,
  `password` VARCHAR(32) NOT NULL ,
  `level` ENUM('reader', 'librarian', 'admin') NOT NULL DEFAULT 'reader' ,
  PRIMARY KEY (`iduser`) ,
  UNIQUE INDEX `permitNumber_UNIQUE` (`permitNumber` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pislibrary`.`genre`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pislibrary`.`genre` (
  `idgenre` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`idgenre`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pislibrary`.`publisher`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pislibrary`.`publisher` (
  `idpublisher` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `address` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`idpublisher`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pislibrary`.`books`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pislibrary`.`books` (
  `idbooks` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `year` DATE NOT NULL ,
  `pages` INT NULL ,
  `edition` DATE NULL ,
  `place` VARCHAR(255) NULL ,
  `idgenre` INT NOT NULL ,
  `idpublisher` INT NOT NULL ,
  PRIMARY KEY (`idbooks`) ,
  INDEX `fk_books_genre1` (`idgenre` ASC) ,
  INDEX `fk_books_publisher1` (`idpublisher` ASC) ,
  CONSTRAINT `fk_books_genre1`
    FOREIGN KEY (`idgenre` )
    REFERENCES `pislibrary`.`genre` (`idgenre` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_books_publisher1`
    FOREIGN KEY (`idpublisher` )
    REFERENCES `pislibrary`.`publisher` (`idpublisher` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pislibrary`.`exemplar`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pislibrary`.`exemplar` (
  `idexemplar` INT NOT NULL AUTO_INCREMENT ,
  `aquired` DATE NOT NULL ,
  `state` INT NOT NULL ,
  `idbooks` INT NOT NULL ,
  PRIMARY KEY (`idexemplar`) ,
  INDEX `fk_exemplar_books1` (`idbooks` ASC) ,
  CONSTRAINT `fk_exemplar_books1`
    FOREIGN KEY (`idbooks` )
    REFERENCES `pislibrary`.`books` (`idbooks` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pislibrary`.`borrow`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pislibrary`.`borrow` (
  `idborrow` INT NOT NULL AUTO_INCREMENT ,
  `borrowed` DATE NOT NULL ,
  `returned` DATE NULL ,
  `prolongations` INT NOT NULL DEFAULT 0 ,
  `iduser` INT NOT NULL ,
  `idexemplar` INT NOT NULL ,
  PRIMARY KEY (`idborrow`) ,
  INDEX `fk_borrow_user` (`iduser` ASC) ,
  INDEX `fk_borrow_exemplar1` (`idexemplar` ASC) ,
  CONSTRAINT `fk_borrow_user`
    FOREIGN KEY (`iduser` )
    REFERENCES `pislibrary`.`user` (`iduser` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_borrow_exemplar1`
    FOREIGN KEY (`idexemplar` )
    REFERENCES `pislibrary`.`exemplar` (`idexemplar` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pislibrary`.`booking`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pislibrary`.`booking` (
  `idbooking` INT NOT NULL AUTO_INCREMENT ,
  `date` DATE NOT NULL ,
  `state` INT NOT NULL ,
  `iduser` INT NOT NULL ,
  `idbooks` INT NOT NULL ,
  PRIMARY KEY (`idbooking`) ,
  INDEX `fk_booking_user1` (`iduser` ASC) ,
  INDEX `fk_booking_books1` (`idbooks` ASC) ,
  CONSTRAINT `fk_booking_user1`
    FOREIGN KEY (`iduser` )
    REFERENCES `pislibrary`.`user` (`iduser` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_booking_books1`
    FOREIGN KEY (`idbooks` )
    REFERENCES `pislibrary`.`books` (`idbooks` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pislibrary`.`book`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pislibrary`.`book` (
  `idbook` INT NOT NULL AUTO_INCREMENT ,
  `isbn` VARCHAR(255) NOT NULL ,
  `idbooks` INT NOT NULL ,
  PRIMARY KEY (`idbook`) ,
  INDEX `fk_book_books1` (`idbooks` ASC) ,
  CONSTRAINT `fk_book_books1`
    FOREIGN KEY (`idbooks` )
    REFERENCES `pislibrary`.`books` (`idbooks` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pislibrary`.`magazine`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pislibrary`.`magazine` (
  `idmagazine` INT NOT NULL AUTO_INCREMENT ,
  `issn` VARCHAR(255) NOT NULL ,
  `idbooks` INT NOT NULL ,
  PRIMARY KEY (`idmagazine`) ,
  INDEX `fk_magazine_books1` (`idbooks` ASC) ,
  CONSTRAINT `fk_magazine_books1`
    FOREIGN KEY (`idbooks` )
    REFERENCES `pislibrary`.`books` (`idbooks` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pislibrary`.`author`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pislibrary`.`author` (
  `idauthor` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`idauthor`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pislibrary`.`books_has_author`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `pislibrary`.`books_has_author` (
  `books_idbooks` INT NOT NULL ,
  `author_idauthor` INT NOT NULL ,
  PRIMARY KEY (`books_idbooks`, `author_idauthor`) ,
  INDEX `fk_books_has_author_author1` (`author_idauthor` ASC) ,
  CONSTRAINT `fk_books_has_author_books1`
    FOREIGN KEY (`books_idbooks` )
    REFERENCES `pislibrary`.`books` (`idbooks` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_books_has_author_author1`
    FOREIGN KEY (`author_idauthor` )
    REFERENCES `pislibrary`.`author` (`idauthor` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
