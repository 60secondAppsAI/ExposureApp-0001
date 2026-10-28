package com.ExposureApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class SectorExposureDTO {

	private Integer sectorExposureId;

	private Integer exposureId;

	private String sector;

	private Double marketValue;

	private Double exposurePercent;






}
