create database tweet;

CREATE TABLE IF NOT EXISTS `tweet` (
  `tid` varchar(32) NOT NULL,
  `uid` varchar(32) NOT NULL,
  `score` int(5) DEFAULT NULL,
  `timestamp` varchar(32) DEFAULT NULL,
  `epoch` int(11) DEFAULT NULL,
  `text` text DEFAULT NULL,
  PRIMARY KEY (`tid`)
);

CREATE INDEX uid_index ON tweet (uid);
CREATE INDEX time_index ON tweet (timestamp);


LOAD DATA LOCAL INFILE "/path-to-file/part-x" REPLACE INTO TABLE `tweet`
FIELDS TERMINATED BY '<15619_delimiter>'
LINES TERMINATED BY '<15619_newline>';