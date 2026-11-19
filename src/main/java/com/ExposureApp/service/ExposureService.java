package com.ExposureApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.ExposureApp.domain.Exposure;
import com.ExposureApp.dto.ExposureDTO;
import com.ExposureApp.dto.ExposureSearchDTO;
import com.ExposureApp.dto.ExposurePageDTO;
import com.ExposureApp.dto.ExposureConvertCriteriaDTO;
import com.ExposureApp.service.GenericService;
import com.ExposureApp.dto.common.RequestDTO;
import com.ExposureApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface ExposureService extends GenericService<Exposure, Integer> {

	List<Exposure> findAll();

	ResultDTO addExposure(ExposureDTO exposureDTO, RequestDTO requestDTO);

	ResultDTO updateExposure(ExposureDTO exposureDTO, RequestDTO requestDTO);

    Page<Exposure> getAllExposures(Pageable pageable);

    Page<Exposure> getAllExposures(Specification<Exposure> spec, Pageable pageable);

	ResponseEntity<ExposurePageDTO> getExposures(ExposureSearchDTO exposureSearchDTO);
	
	List<ExposureDTO> convertExposuresToExposureDTOs(List<Exposure> exposures, ExposureConvertCriteriaDTO convertCriteria);

	ExposureDTO getExposureDTOById(Integer exposureId);



	
}
