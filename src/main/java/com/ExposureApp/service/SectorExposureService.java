package com.ExposureApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.ExposureApp.domain.SectorExposure;
import com.ExposureApp.dto.SectorExposureDTO;
import com.ExposureApp.dto.SectorExposureSearchDTO;
import com.ExposureApp.dto.SectorExposurePageDTO;
import com.ExposureApp.dto.SectorExposureConvertCriteriaDTO;
import com.ExposureApp.service.GenericService;
import com.ExposureApp.dto.common.RequestDTO;
import com.ExposureApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface SectorExposureService extends GenericService<SectorExposure, Integer> {

	List<SectorExposure> findAll();

	ResultDTO addSectorExposure(SectorExposureDTO sectorExposureDTO, RequestDTO requestDTO);

	ResultDTO updateSectorExposure(SectorExposureDTO sectorExposureDTO, RequestDTO requestDTO);

    Page<SectorExposure> getAllSectorExposures(Pageable pageable);

    Page<SectorExposure> getAllSectorExposures(Specification<SectorExposure> spec, Pageable pageable);

	ResponseEntity<SectorExposurePageDTO> getSectorExposures(SectorExposureSearchDTO sectorExposureSearchDTO);
	
	List<SectorExposureDTO> convertSectorExposuresToSectorExposureDTOs(List<SectorExposure> sectorExposures, SectorExposureConvertCriteriaDTO convertCriteria);

	SectorExposureDTO getSectorExposureDTOById(Integer sectorExposureId);



	
}
