-- vložení unique indexu na rezervace titulu

ALTER TABLE `booking`
	ADD UNIQUE INDEX `iduser_idbook` (`iduser`, `idbook`)