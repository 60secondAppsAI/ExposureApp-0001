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
import com.ExposureApp.dao.ExposureDAO;
import com.ExposureApp.domain.Exposure;
import com.ExposureApp.dto.ExposureDTO;
import com.ExposureApp.dto.ExposureSearchDTO;
import com.ExposureApp.dto.ExposurePageDTO;
import com.ExposureApp.dto.ExposureConvertCriteriaDTO;
import com.ExposureApp.dto.common.RequestDTO;
import com.ExposureApp.dto.common.ResultDTO;
import com.ExposureApp.service.ExposureService;
import com.ExposureApp.util.ControllerUtils;


@Service
public class ExposureServiceImpl extends GenericServiceImpl<Exposure, Integer> implements ExposureService {

    private final static Logger logger = LoggerFactory.getLogger(ExposureServiceImpl.class);

	@Autowired
	ExposureDAO exposureDao;

	

	@Override
	public GenericDAO<Exposure, Integer> getDAO() {
		return (GenericDAO<Exposure, Integer>) exposureDao;
	}
	
	public List<Exposure> findAll () {
		List<Exposure> exposures = exposureDao.findAll();
		
		return exposures;	
		
	}

	public ResultDTO addExposure(ExposureDTO exposureDTO, RequestDTO requestDTO) {

		Exposure exposure = new Exposure();

		exposure.setExposureId(exposureDTO.getExposureId());

		exposure.setPortfolioId(exposureDTO.getPortfolioId());

		exposure.setExposureType(exposureDTO.getExposureType());

		exposure.setExposureValue(exposureDTO.getExposureValue());

		exposure.setCalculatedAt(exposureDTO.getCalculatedAt());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		exposure = exposureDao.save(exposure);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Exposure> getAllExposures(Pageable pageable) {
		return exposureDao.findAll(pageable);
	}

	public Page<Exposure> getAllExposures(Specification<Exposure> spec, Pageable pageable) {
		return exposureDao.findAll(spec, pageable);
	}

	public ResponseEntity<ExposurePageDTO> getExposures(ExposureSearchDTO exposureSearchDTO) {
	
			Integer exposureId = exposureSearchDTO.getExposureId(); 
 			Integer portfolioId = exposureSearchDTO.getPortfolioId(); 
 			String exposureType = exposureSearchDTO.getExposureType(); 
  			Date calculatedAt = exposureSearchDTO.getCalculatedAt();
 			String sortBy = exposureSearchDTO.getSortBy();
			String sortOrder = exposureSearchDTO.getSortOrder();
			String searchQuery = exposureSearchDTO.getSearchQuery();
			Integer page = exposureSearchDTO.getPage();
			Integer size = exposureSearchDTO.getSize();

	        Specification<Exposure> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, exposureId, "exposureId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, portfolioId, "portfolioId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, exposureType, "exposureType"); 
			
			
			spec = ControllerUtils.andIfNecessary(spec, calculatedAt, "calculatedAt");
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("exposureType")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Exposure> exposures = this.getAllExposures(spec, pageable);
		
		//System.out.println(String.valueOf(exposures.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(exposures.getTotalPages()));
		
		List<Exposure> exposuresList = exposures.getContent();
		
		ExposureConvertCriteriaDTO convertCriteria = new ExposureConvertCriteriaDTO();
		List<ExposureDTO> exposureDTOs = this.convertExposuresToExposureDTOs(exposuresList,convertCriteria);
		
		ExposurePageDTO exposurePageDTO = new ExposurePageDTO();
		exposurePageDTO.setExposures(exposureDTOs);
		exposurePageDTO.setTotalElements(exposures.getTotalElements());
		return ResponseEntity.ok(exposurePageDTO);
	}

	public List<ExposureDTO> convertExposuresToExposureDTOs(List<Exposure> exposures, ExposureConvertCriteriaDTO convertCriteria) {
		
		List<ExposureDTO> exposureDTOs = new ArrayList<ExposureDTO>();
		
		for (Exposure exposure : exposures) {
			exposureDTOs.add(convertExposureToExposureDTO(exposure,convertCriteria));
		}
		
		return exposureDTOs;

	}
	
	public ExposureDTO convertExposureToExposureDTO(Exposure exposure, ExposureConvertCriteriaDTO convertCriteria) {
		
		ExposureDTO exposureDTO = new ExposureDTO();

		exposureDTO.setExposureId(exposure.getExposureId());

		exposureDTO.setPortfolioId(exposure.getPortfolioId());

		exposureDTO.setExposureType(exposure.getExposureType());

		exposureDTO.setExposureValue(exposure.getExposureValue());

		exposureDTO.setCalculatedAt(exposure.getCalculatedAt());
		
		return exposureDTO;
	}

	public ResultDTO updateExposure(ExposureDTO exposureDTO, RequestDTO requestDTO) {
		
		Exposure exposure = exposureDao.getById(exposureDTO.getExposureId());
		
		exposure.setExposureId(ControllerUtils.setValue(exposure.getExposureId(), exposureDTO.getExposureId()));
		
		exposure.setPortfolioId(ControllerUtils.setValue(exposure.getPortfolioId(), exposureDTO.getPortfolioId()));
		
		exposure.setExposureType(ControllerUtils.setValue(exposure.getExposureType(), exposureDTO.getExposureType()));
		
		exposure.setExposureValue(ControllerUtils.setValue(exposure.getExposureValue(), exposureDTO.getExposureValue()));
		
		exposure.setCalculatedAt(ControllerUtils.setValue(exposure.getCalculatedAt(), exposureDTO.getCalculatedAt()));

        exposure = exposureDao.save(exposure);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ExposureDTO getExposureDTOById(Integer exposureId) {
	
		Exposure exposure = exposureDao.getById(exposureId);
		
		ExposureConvertCriteriaDTO convertCriteria = new ExposureConvertCriteriaDTO();
		return(this.convertExposureToExposureDTO(exposure,convertCriteria));
	}

}
