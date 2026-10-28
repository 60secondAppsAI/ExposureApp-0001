package com.ExposureApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class CurrencyExposureDTO {

	private Integer currencyExposureId;

	private Integer exposureId;

	private String currency;

	private Double exposureValue;

	private Double exposurePercent;






}
