package com.ExposureApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ExposureDTO {

	private Integer exposureId;

	private Integer portfolioId;

	private String exposureType;

	private Double exposureValue;

	private Date calculatedAt;






}
