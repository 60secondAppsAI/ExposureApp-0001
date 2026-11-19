package com.ExposureApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SectorExposureSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer sectorExposureId;
	
	private Integer exposureId;
	
	private String sector;
	
	private Double marketValue;
	
	private Double exposurePercent;
	
}
