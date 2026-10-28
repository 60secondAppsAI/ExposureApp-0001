package com.ExposureApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.ExposureApp.dao.GenericDAO;
import com.ExposureApp.service.GenericService;
import com.ExposureApp.service.impl.GenericServiceImpl;
import com.ExposureApp.dao.InstrumentExposureDAO;
import com.ExposureApp.domain.InstrumentExposure;
import com.ExposureApp.dto.InstrumentExposureDTO;
import com.ExposureApp.dto.InstrumentExposureSearchDTO;
import com.ExposureApp.dto.InstrumentExposurePageDTO;
import com.ExposureApp.dto.InstrumentExposureConvertCriteriaDTO;
import com.ExposureApp.dto.common.RequestDTO;
import com.ExposureApp.dto.common.ResultDTO;
import com.ExposureApp.service.InstrumentExposureService;
import com.ExposureApp.util.ControllerUtils;


@Service
public class InstrumentExposureServiceImpl extends GenericServiceImpl<InstrumentExposure, Integer> implements InstrumentExposureService {

    private final static Logger logger = LoggerFactory.getLogger(InstrumentExposureServiceImpl.class);

	@Autowired
	InstrumentExposureDAO instrumentExposureDao;

	

	@Override
	public GenericDAO<InstrumentExposure, Integer> getDAO() {
		return (GenericDAO<InstrumentExposure, Integer>) instrumentExposureDao;
	}
	
	public List<InstrumentExposure> findAll () {
		List<InstrumentExposure> instrumentExposures = instrumentExposureDao.findAll();
		
		return instrumentExposures;	
		
	}

	public ResultDTO addInstrumentExposure(InstrumentExposureDTO instrumentExposureDTO, RequestDTO requestDTO) {

		InstrumentExposure instrumentExposure = new InstrumentExposure();

		instrumentExposure.setInstrumentExposureId(instrumentExposureDTO.getInstrumentExposureId());

		instrumentExposure.setExposureId(instrumentExposureDTO.getExposureId());

		instrumentExposure.setInstrumentId(instrumentExposureDTO.getInstrumentId());

		instrumentExposure.setMarketValue(instrumentExposureDTO.getMarketValue());

		instrumentExposure.setExposurePercent(instrumentExposureDTO.getExposurePercent());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		instrumentExposure = instrumentExposureDao.save(instrumentExposure);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<InstrumentExposure> getAllInstrumentExposures(Pageable pageable) {
		return instrumentExposureDao.findAll(pageable);
	}

	public Page<InstrumentExposure> getAllInstrumentExposures(Specification<InstrumentExposure> spec, Pageable pageable) {
		return instrumentExposureDao.findAll(spec, pageable);
	}

	public ResponseEntity<InstrumentExposurePageDTO> getInstrumentExposures(InstrumentExposureSearchDTO instrumentExposureSearchDTO) {
	
			Integer instrumentExposureId = instrumentExposureSearchDTO.getInstrumentExposureId(); 
 			Integer exposureId = instrumentExposureSearchDTO.getExposureId(); 
 			Integer instrumentId = instrumentExposureSearchDTO.getInstrumentId(); 
   			String sortBy = instrumentExposureSearchDTO.getSortBy();
			String sortOrder = instrumentExposureSearchDTO.getSortOrder();
			String searchQuery = instrumentExposureSearchDTO.getSearchQuery();
			Integer page = instrumentExposureSearchDTO.getPage();
			Integer size = instrumentExposureSearchDTO.getSize();

	        Specification<InstrumentExposure> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, instrumentExposureId, "instrumentExposureId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, exposureId, "exposureId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, instrumentId, "instrumentId"); 
			
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<InstrumentExposure> instrumentExposures = this.getAllInstrumentExposures(spec, pageable);
		
		//System.out.println(String.valueOf(instrumentExposures.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(instrumentExposures.getTotalPages()));
		
		List<InstrumentExposure> instrumentExposuresList = instrumentExposures.getContent();
		
		InstrumentExposureConvertCriteriaDTO convertCriteria = new InstrumentExposureConvertCriteriaDTO();
		List<InstrumentExposureDTO> instrumentExposureDTOs = this.convertInstrumentExposuresToInstrumentExposureDTOs(instrumentExposuresList,convertCriteria);
		
		InstrumentExposurePageDTO instrumentExposurePageDTO = new InstrumentExposurePageDTO();
		instrumentExposurePageDTO.setInstrumentExposures(instrumentExposureDTOs);
		instrumentExposurePageDTO.setTotalElements(instrumentExposures.getTotalElements());
		return ResponseEntity.ok(instrumentExposurePageDTO);
	}

	public List<InstrumentExposureDTO> convertInstrumentExposuresToInstrumentExposureDTOs(List<InstrumentExposure> instrumentExposures, InstrumentExposureConvertCriteriaDTO convertCriteria) {
		
		List<InstrumentExposureDTO> instrumentExposureDTOs = new ArrayList<InstrumentExposureDTO>();
		
		for (InstrumentExposure instrumentExposure : instrumentExposures) {
			instrumentExposureDTOs.add(convertInstrumentExposureToInstrumentExposureDTO(instrumentExposure,convertCriteria));
		}
		
		return instrumentExposureDTOs;

	}
	
	public InstrumentExposureDTO convertInstrumentExposureToInstrumentExposureDTO(InstrumentExposure instrumentExposure, InstrumentExposureConvertCriteriaDTO convertCriteria) {
		
		InstrumentExposureDTO instrumentExposureDTO = new InstrumentExposureDTO();

		instrumentExposureDTO.setInstrumentExposureId(instrumentExposure.getInstrumentExposureId());

		instrumentExposureDTO.setExposureId(instrumentExposure.getExposureId());

		instrumentExposureDTO.setInstrumentId(instrumentExposure.getInstrumentId());

		instrumentExposureDTO.setMarketValue(instrumentExposure.getMarketValue());

		instrumentExposureDTO.setExposurePercent(instrumentExposure.getExposurePercent());
		
		return instrumentExposureDTO;
	}

	public ResultDTO updateInstrumentExposure(InstrumentExposureDTO instrumentExposureDTO, RequestDTO requestDTO) {
		
		InstrumentExposure instrumentExposure = instrumentExposureDao.getById(instrumentExposureDTO.getInstrumentExposureId());
		
		instrumentExposure.setInstrumentExposureId(ControllerUtils.setValue(instrumentExposure.getInstrumentExposureId(), instrumentExposureDTO.getInstrumentExposureId()));
		
		instrumentExposure.setExposureId(ControllerUtils.setValue(instrumentExposure.getExposureId(), instrumentExposureDTO.getExposureId()));
		
		instrumentExposure.setInstrumentId(ControllerUtils.setValue(instrumentExposure.getInstrumentId(), instrumentExposureDTO.getInstrumentId()));
		
		instrumentExposure.setMarketValue(ControllerUtils.setValue(instrumentExposure.getMarketValue(), instrumentExposureDTO.getMarketValue()));
		
		instrumentExposure.setExposurePercent(ControllerUtils.setValue(instrumentExposure.getExposurePercent(), instrumentExposureDTO.getExposurePercent()));

        instrumentExposure = instrumentExposureDao.save(instrumentExposure);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InstrumentExposureDTO getInstrumentExposureDTOById(Integer instrumentExposureId) {
	
		InstrumentExposure instrumentExposure = instrumentExposureDao.getById(instrumentExposureId);
		
		InstrumentExposureConvertCriteriaDTO convertCriteria = new InstrumentExposureConvertCriteriaDTO();
		return(this.convertInstrumentExposureToInstrumentExposureDTO(instrumentExposure,convertCriteria));
	}

}
