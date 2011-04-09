# --------------------------------------------------------
# Host:                         127.0.0.1
# Server version:               5.1.41
# Server OS:                    pc-linux-gnu
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2011-04-09 11:36:32
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
# Dumping data for table pislibrary.author: ~17 rows (approximately)
DELETE FROM `author`;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` (`idauthor`, `name`) VALUES
	(1, 'Sharon Zakhour'),
	(2, 'Andi Gutmans'),
	(3, 'Stig Saether Bakken'),
	(4, 'Derick Rethans'),
	(5, 'Jiří Kosek'),
	(6, 'Stephen Prata'),
	(7, 'Jiří Žára'),
	(8, 'Bedřich Beneš'),
	(9, 'Jiří Sochor'),
	(10, 'Petr Felkel'),
	(11, 'Robert J. Shea'),
	(12, 'Robert Anton Wilson'),
	(13, 'David A. Vise'),
	(14, 'Mark Malseed'),
	(15, 'Lukáš Jelínek'),
	(16, 'Steve McConnel'),
	(17, 'Brian Marick');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;

# Dumping data for table pislibrary.book: ~11 rows (approximately)
DELETE FROM `book`;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` (`idbook`, `name`, `year`, `pages`, `edition`, `place`, `idgenre`, `idpublisher`, `type`, `code`) VALUES
	(1, 'Moderní počítačová grafika', '2004-01-01', 611, 1, 'Brno', 93, 1, 'isbn', '80-251-0454'),
	(3, 'Mistrovství v C++ - 2. aktualizované vydání', '2004-01-01', 1006, 2, 'Brno', 93, 1, 'isbn', '80-251-0098-7'),
	(4, 'PHP - tvorba interaktivních internetových aplikací, podrobný průvodce', '1999-01-01', 492, 1, 'Praha', 93, 2, 'isbn', '80-7169-373-1'),
	(5, 'Mistrovství v PHP 5', '2007-01-01', 655, 1, 'Brno', 93, 1, 'isbn', '978-80-251-1519-0'),
	(6, 'Java 6 - Výukový kurz', '2007-01-01', 536, 1, 'Brno', 93, 1, 'isbn', '978-80-251-1575-6'),
	(8, 'Illuminatus 2 - zlaté jablko', '2000-01-01', 246, 1, 'Praha', 133, 3, 'isbn', '80-7287-002-5'),
	(9, 'Illuminatus 3 - Leviatan', '2003-01-01', 280, 1, 'Praha', 133, 3, 'isbn', '80-7287-044-0'),
	(12, 'Google Strory', '2007-01-01', 368, 1, 'Praha', 93, 4, 'isbn', '978-80-7349-34-8'),
	(14, 'Jádro systému Linux', '2008-01-01', 686, 1, 'Brno', 93, 1, 'isbn', '978-80-251-2084-2'),
	(16, 'Dokonalý kód', '2006-01-01', 896, 1, 'Brno', 93, 1, 'isbn', '80-251-0849-X'),
	(17, 'The craft of software testing', '1995-01-01', 554, 1, 'New Jersey', 3, 5, 'isbn', '0-13-177411-5');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;

# Dumping data for table pislibrary.booking: ~0 rows (approximately)
DELETE FROM `booking`;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;

# Dumping data for table pislibrary.book_has_author: ~19 rows (approximately)
DELETE FROM `book_has_author`;
/*!40000 ALTER TABLE `book_has_author` DISABLE KEYS */;
INSERT INTO `book_has_author` (`books_idbook`, `author_idauthor`) VALUES
	(6, 1),
	(5, 2),
	(5, 3),
	(5, 4),
	(4, 5),
	(3, 6),
	(1, 7),
	(1, 8),
	(1, 9),
	(1, 10),
	(8, 11),
	(9, 11),
	(8, 12),
	(9, 12),
	(12, 13),
	(12, 14),
	(14, 15),
	(16, 16),
	(17, 17);
/*!40000 ALTER TABLE `book_has_author` ENABLE KEYS */;

# Dumping data for table pislibrary.borrow: ~0 rows (approximately)
DELETE FROM `borrow`;
/*!40000 ALTER TABLE `borrow` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrow` ENABLE KEYS */;

# Dumping data for table pislibrary.exemplar: ~24 rows (approximately)
DELETE FROM `exemplar`;
/*!40000 ALTER TABLE `exemplar` DISABLE KEYS */;
INSERT INTO `exemplar` (`idexemplar`, `aquired`, `state`, `idbook`) VALUES
	(1, '2004-04-13', 0, 1),
	(2, '2004-04-13', 0, 1),
	(3, '2006-07-23', 1, 1),
	(11, '2004-10-11', 0, 3),
	(12, '2004-10-11', 0, 3),
	(13, '2004-10-11', 0, 3),
	(14, '2004-10-11', 0, 3),
	(15, '2007-05-30', 0, 3),
	(16, '2007-05-30', 0, 3),
	(17, '1999-12-12', 0, 4),
	(18, '2008-02-19', 0, 5),
	(19, '2008-02-19', 0, 5),
	(20, '2007-01-03', 0, 6),
	(22, '2000-09-11', 0, 8),
	(23, '2000-09-11', 0, 8),
	(24, '2004-12-11', 0, 9),
	(25, '2004-12-11', 0, 9),
	(26, '2004-12-11', 0, 9),
	(27, '2004-12-11', 0, 9),
	(28, '2007-08-07', 0, 12),
	(29, '2008-12-20', 0, 14),
	(30, '2010-05-12', 0, 14),
	(31, '2006-07-11', 0, 16),
	(32, '1998-04-23', 0, 17);
/*!40000 ALTER TABLE `exemplar` ENABLE KEYS */;

# Dumping data for table pislibrary.genre: ~155 rows (approximately)
DELETE FROM `genre`;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` (`idgenre`, `name`) VALUES
	(2, 'Aforismy'),
	(3, 'Alternativní medicína'),
	(4, 'Antologie'),
	(5, 'Astrologie'),
	(6, 'Astronomie'),
	(7, 'Audiokniha'),
	(8, 'Autentické svědectví'),
	(9, 'Autobiografický román'),
	(10, 'Autobiografie'),
	(11, 'Báje a pověsti'),
	(12, 'Bajky'),
	(13, 'Balada'),
	(14, 'Báseň'),
	(15, 'Básně'),
	(16, 'Básnická sbírka'),
	(17, 'Beletrie'),
	(18, 'Biografický román'),
	(19, 'Biografie'),
	(20, 'Brožura'),
	(21, 'Červená knihovna'),
	(22, 'Česká klasika'),
	(23, 'Česká próza'),
	(24, 'Český román'),
	(25, 'Cestopis'),
	(26, 'Citáty'),
	(27, 'Dějiny'),
	(28, 'Deníkové záznamy'),
	(29, 'Detektivka'),
	(30, 'Detektivní komedie'),
	(31, 'Detektivní povídky'),
	(32, 'Detektivní román'),
	(33, 'Dětská literatura'),
	(34, 'Dievčenský román'),
	(35, 'Divadelní hra'),
	(36, 'Divadelní hra - komedie'),
	(37, 'Divadelní hra - tragédie'),
	(38, 'Divadlo'),
	(39, 'Dívčí román'),
	(40, 'Dobrodružný román'),
	(41, 'Dokumentární román'),
	(42, 'Drama'),
	(43, 'Dvojjazyčná kniha'),
	(44, 'Encyklopedie'),
	(45, 'Encyklopedie pro děti'),
	(46, 'Epika'),
	(47, 'Esej'),
	(48, 'Eseje, úvahy'),
	(49, 'Esoterika'),
	(50, 'Etno'),
	(51, 'Fantasy'),
	(52, 'Fantasy - gamebook'),
	(53, 'Fantasy povídky'),
	(54, 'Fantasy, horor'),
	(55, 'Fauna'),
	(56, 'Fejetony'),
	(57, 'Fejetony, citáty'),
	(58, 'Film'),
	(59, 'Filosofické spisy'),
	(60, 'Flora'),
	(61, 'Fotografická publikace'),
	(62, 'Historické příběhy a pověsti'),
	(63, 'Historický román'),
	(64, 'Hobby'),
	(65, 'Hobby - muži'),
	(66, 'Hobby - ženy'),
	(67, 'Horor'),
	(68, 'Hudba'),
	(69, 'Humor'),
	(70, 'Humoristický román'),
	(71, 'Komiks'),
	(72, 'Korespondence'),
	(73, 'Kriminální příběhy'),
	(74, 'Kronika'),
	(75, 'Kuchařky'),
	(76, 'Legenda'),
	(77, 'Literatura faktu'),
	(78, 'Lyrika'),
	(79, 'Malířství'),
	(80, 'Medicína - zdraví'),
	(81, 'Monografie'),
	(82, 'Náboženství'),
	(83, 'Náboženství, mystika'),
	(84, 'Naučná literatura'),
	(85, 'Naučno - populární'),
	(86, 'Novela'),
	(87, 'Odborná publikace'),
	(88, 'Opereta'),
	(89, 'Osobnosti'),
	(90, 'Paměti'),
	(91, 'Parodie na špionážní thrillery'),
	(92, 'Písně'),
	(93, 'Počítačová literatura'),
	(94, 'Poéma'),
	(95, 'Poesie pro děti'),
	(96, 'Poezie'),
	(97, 'Pohádka'),
	(98, 'Pohádková detektivka'),
	(99, 'Pohádková próza'),
	(100, 'Pohádkové příběhy'),
	(101, 'Pohádky'),
	(102, 'Pojednání'),
	(103, 'Politika'),
	(104, 'Pověsti'),
	(105, 'Povídka'),
	(106, 'Povídková kniha'),
	(107, 'Povídková sbírka'),
	(108, 'Povídky a apokryfy'),
	(109, 'Přednášky'),
	(110, 'Příběh'),
	(111, 'Příroda, zvířata'),
	(112, 'Příručka'),
	(113, 'Proslov'),
	(114, 'Próza'),
	(115, 'Průvodce'),
	(116, 'Psychologie'),
	(117, 'Publicistika'),
	(118, 'Publikace'),
	(119, 'Reportáž'),
	(120, 'Rodina'),
	(121, 'Rodová sága'),
	(122, 'Román'),
	(123, 'Román pro ženy'),
	(124, 'Romaneto'),
	(125, 'Románový cestopis'),
	(126, 'Rozhovory'),
	(127, 'Satira'),
	(128, 'Sborník'),
	(129, 'Scénář k filmu'),
	(130, 'Sci-fi'),
	(131, 'Sci-fi povídky'),
	(132, 'Sci-fi, fantasy'),
	(133, 'Scifi'),
	(134, 'Skladba'),
	(135, 'Slovník'),
	(136, 'Sociologie'),
	(137, 'Souborné vydání'),
	(138, 'Soudničky'),
	(139, 'Špionážní román'),
	(140, 'Sport a hry'),
	(141, 'Studie'),
	(142, 'Světová klasika'),
	(143, 'Světová próza'),
	(144, 'Thriller-napětí-dobrodružství'),
	(145, 'Učebnice'),
	(146, 'Utopie'),
	(147, 'Úvahy'),
	(148, 'Válečný román'),
	(149, 'Vědecká práce'),
	(150, 'Vojenství'),
	(151, 'Výbor z díla'),
	(152, 'Výbor z poezie'),
	(153, 'Výtvarné umění'),
	(154, 'Vzpomínky'),
	(155, 'Záhady, fakta a hypotézy'),
	(156, 'Zpěvy');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;

# Dumping data for table pislibrary.publisher: ~5 rows (approximately)
DELETE FROM `publisher`;
/*!40000 ALTER TABLE `publisher` DISABLE KEYS */;
INSERT INTO `publisher` (`idpublisher`, `name`, `address`) VALUES
	(1, 'Computer Press', 'nám. 28. dubna 48, 635 00 Brno'),
	(2, 'Grada Publishing', ''),
	(3, 'Dell Publishing', 'New York, U.S.A'),
	(4, 'Pragma', 'V Hodkovičkách 2/20, 147 00 Praha 4'),
	(5, 'Pearson Education Company', 'Upper Saddle River, NJ 07458, U.S.A.');
/*!40000 ALTER TABLE `publisher` ENABLE KEYS */;

# Dumping data for table pislibrary.user: ~1 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`iduser`, `forename`, `surname`, `permitNumber`, `address`, `phone`, `email`, `registered`, `expire`, `password`, `level`) VALUES
	(1, 'Admin', '', '', '', NULL, 'admin@server.net', '2011-04-08', '2035-04-08', 'admin', 'admin');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
