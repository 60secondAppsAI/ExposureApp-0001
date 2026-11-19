package com.ExposureApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.ExposureApp.domain.InstrumentExposure;
import com.ExposureApp.dto.InstrumentExposureDTO;
import com.ExposureApp.dto.InstrumentExposureSearchDTO;
import com.ExposureApp.dto.InstrumentExposurePageDTO;
import com.ExposureApp.dto.InstrumentExposureConvertCriteriaDTO;
import com.ExposureApp.service.GenericService;
import com.ExposureApp.dto.common.RequestDTO;
import com.ExposureApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface InstrumentExposureService extends GenericService<InstrumentExposure, Integer> {

	List<InstrumentExposure> findAll();

	ResultDTO addInstrumentExposure(InstrumentExposureDTO instrumentExposureDTO, RequestDTO requestDTO);

	ResultDTO updateInstrumentExposure(InstrumentExposureDTO instrumentExposureDTO, RequestDTO requestDTO);

    Page<InstrumentExposure> getAllInstrumentExposures(Pageable pageable);

    Page<InstrumentExposure> getAllInstrumentExposures(Specification<InstrumentExposure> spec, Pageable pageable);

	ResponseEntity<InstrumentExposurePageDTO> getInstrumentExposures(InstrumentExposureSearchDTO instrumentExposureSearchDTO);
	
	List<InstrumentExposureDTO> convertInstrumentExposuresToInstrumentExposureDTOs(List<InstrumentExposure> instrumentExposures, InstrumentExposureConvertCriteriaDTO convertCriteria);

	InstrumentExposureDTO getInstrumentExposureDTOById(Integer instrumentExposureId);



	
}
