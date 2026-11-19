package com.ExposureApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseInstrumentExposureDTO {

	private Integer ownerId;

	private Integer instrumentExposureId;

	private Integer instrumentExposureStatus;
	
	private Integer nextOrPrevious;
}

