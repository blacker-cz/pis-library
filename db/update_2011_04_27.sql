-- Nullable collumns
ALTER TABLE `publisher` CHANGE `address` `address` VARCHAR( 255 ) CHARACTER SET utf8 COLLATE utf8_czech_ci NULL ;

ALTER TABLE `user` CHANGE `address` `address` VARCHAR( 255 ) CHARACTER SET utf8 COLLATE utf8_czech_ci NULL ;
