package com.ExposureApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CurrencyExposureSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer currencyExposureId;
	
	private Integer exposureId;
	
	private String currency;
	
	private Double exposureValue;
	
	private Double exposurePercent;
	
}
