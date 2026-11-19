CREATE DATABASE IF NOT EXISTS ExposureApp;

CREATE TABLE ExposureApp.exposures (
    `exposure_id` INT AUTO_INCREMENT,
    `portfolio_id` INT,
    `exposure_type` VARCHAR(255),
    `exposure_value` DOUBLE,
    `calculated_at` DATETIME,
PRIMARY KEY (exposure_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE ExposureApp.instrument_exposures (
    `instrument_exposure_id` INT AUTO_INCREMENT,
    `exposure_id` INT,
    `instrument_id` INT,
    `market_value` DOUBLE,
    `exposure_percent` DOUBLE,
PRIMARY KEY (instrument_exposure_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE ExposureApp.sector_exposures (
    `sector_exposure_id` INT AUTO_INCREMENT,
    `exposure_id` INT,
    `sector` VARCHAR(255),
    `market_value` DOUBLE,
    `exposure_percent` DOUBLE,
PRIMARY KEY (sector_exposure_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE ExposureApp.currency_exposures (
    `currency_exposure_id` INT AUTO_INCREMENT,
    `exposure_id` INT,
    `currency` VARCHAR(255),
    `exposure_value` DOUBLE,
    `exposure_percent` DOUBLE,
PRIMARY KEY (currency_exposure_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
