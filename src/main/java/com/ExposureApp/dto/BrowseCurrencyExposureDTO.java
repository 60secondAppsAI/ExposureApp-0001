package com.ExposureApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseCurrencyExposureDTO {

	private Integer ownerId;

	private Integer currencyExposureId;

	private Integer currencyExposureStatus;
	
	private Integer nextOrPrevious;
}

