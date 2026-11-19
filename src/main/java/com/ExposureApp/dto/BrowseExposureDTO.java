package com.ExposureApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseExposureDTO {

	private Integer ownerId;

	private Integer exposureId;

	private Integer exposureStatus;
	
	private Integer nextOrPrevious;
}

