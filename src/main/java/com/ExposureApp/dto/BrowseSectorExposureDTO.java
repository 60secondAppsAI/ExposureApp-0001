package com.ExposureApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseSectorExposureDTO {

	private Integer ownerId;

	private Integer sectorExposureId;

	private Integer sectorExposureStatus;
	
	private Integer nextOrPrevious;
}

