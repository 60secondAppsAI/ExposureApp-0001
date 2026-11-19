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
import com.ExposureApp.dao.CurrencyExposureDAO;
import com.ExposureApp.domain.CurrencyExposure;
import com.ExposureApp.dto.CurrencyExposureDTO;
import com.ExposureApp.dto.CurrencyExposureSearchDTO;
import com.ExposureApp.dto.CurrencyExposurePageDTO;
import com.ExposureApp.dto.CurrencyExposureConvertCriteriaDTO;
import com.ExposureApp.dto.common.RequestDTO;
import com.ExposureApp.dto.common.ResultDTO;
import com.ExposureApp.service.CurrencyExposureService;
import com.ExposureApp.util.ControllerUtils;


@Service
public class CurrencyExposureServiceImpl extends GenericServiceImpl<CurrencyExposure, Integer> implements CurrencyExposureService {

    private final static Logger logger = LoggerFactory.getLogger(CurrencyExposureServiceImpl.class);

	@Autowired
	CurrencyExposureDAO currencyExposureDao;

	

	@Override
	public GenericDAO<CurrencyExposure, Integer> getDAO() {
		return (GenericDAO<CurrencyExposure, Integer>) currencyExposureDao;
	}
	
	public List<CurrencyExposure> findAll () {
		List<CurrencyExposure> currencyExposures = currencyExposureDao.findAll();
		
		return currencyExposures;	
		
	}

	public ResultDTO addCurrencyExposure(CurrencyExposureDTO currencyExposureDTO, RequestDTO requestDTO) {

		CurrencyExposure currencyExposure = new CurrencyExposure();

		currencyExposure.setCurrencyExposureId(currencyExposureDTO.getCurrencyExposureId());

		currencyExposure.setExposureId(currencyExposureDTO.getExposureId());

		currencyExposure.setCurrency(currencyExposureDTO.getCurrency());

		currencyExposure.setExposureValue(currencyExposureDTO.getExposureValue());

		currencyExposure.setExposurePercent(currencyExposureDTO.getExposurePercent());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		currencyExposure = currencyExposureDao.save(currencyExposure);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<CurrencyExposure> getAllCurrencyExposures(Pageable pageable) {
		return currencyExposureDao.findAll(pageable);
	}

	public Page<CurrencyExposure> getAllCurrencyExposures(Specification<CurrencyExposure> spec, Pageable pageable) {
		return currencyExposureDao.findAll(spec, pageable);
	}

	public ResponseEntity<CurrencyExposurePageDTO> getCurrencyExposures(CurrencyExposureSearchDTO currencyExposureSearchDTO) {
	
			Integer currencyExposureId = currencyExposureSearchDTO.getCurrencyExposureId(); 
 			Integer exposureId = currencyExposureSearchDTO.getExposureId(); 
 			String currency = currencyExposureSearchDTO.getCurrency(); 
   			String sortBy = currencyExposureSearchDTO.getSortBy();
			String sortOrder = currencyExposureSearchDTO.getSortOrder();
			String searchQuery = currencyExposureSearchDTO.getSearchQuery();
			Integer page = currencyExposureSearchDTO.getPage();
			Integer size = currencyExposureSearchDTO.getSize();

	        Specification<CurrencyExposure> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, currencyExposureId, "currencyExposureId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, exposureId, "exposureId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, currency, "currency"); 
			
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("currency")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<CurrencyExposure> currencyExposures = this.getAllCurrencyExposures(spec, pageable);
		
		//System.out.println(String.valueOf(currencyExposures.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(currencyExposures.getTotalPages()));
		
		List<CurrencyExposure> currencyExposuresList = currencyExposures.getContent();
		
		CurrencyExposureConvertCriteriaDTO convertCriteria = new CurrencyExposureConvertCriteriaDTO();
		List<CurrencyExposureDTO> currencyExposureDTOs = this.convertCurrencyExposuresToCurrencyExposureDTOs(currencyExposuresList,convertCriteria);
		
		CurrencyExposurePageDTO currencyExposurePageDTO = new CurrencyExposurePageDTO();
		currencyExposurePageDTO.setCurrencyExposures(currencyExposureDTOs);
		currencyExposurePageDTO.setTotalElements(currencyExposures.getTotalElements());
		return ResponseEntity.ok(currencyExposurePageDTO);
	}

	public List<CurrencyExposureDTO> convertCurrencyExposuresToCurrencyExposureDTOs(List<CurrencyExposure> currencyExposures, CurrencyExposureConvertCriteriaDTO convertCriteria) {
		
		List<CurrencyExposureDTO> currencyExposureDTOs = new ArrayList<CurrencyExposureDTO>();
		
		for (CurrencyExposure currencyExposure : currencyExposures) {
			currencyExposureDTOs.add(convertCurrencyExposureToCurrencyExposureDTO(currencyExposure,convertCriteria));
		}
		
		return currencyExposureDTOs;

	}
	
	public CurrencyExposureDTO convertCurrencyExposureToCurrencyExposureDTO(CurrencyExposure currencyExposure, CurrencyExposureConvertCriteriaDTO convertCriteria) {
		
		CurrencyExposureDTO currencyExposureDTO = new CurrencyExposureDTO();

		currencyExposureDTO.setCurrencyExposureId(currencyExposure.getCurrencyExposureId());

		currencyExposureDTO.setExposureId(currencyExposure.getExposureId());

		currencyExposureDTO.setCurrency(currencyExposure.getCurrency());

		currencyExposureDTO.setExposureValue(currencyExposure.getExposureValue());

		currencyExposureDTO.setExposurePercent(currencyExposure.getExposurePercent());
		
		return currencyExposureDTO;
	}

	public ResultDTO updateCurrencyExposure(CurrencyExposureDTO currencyExposureDTO, RequestDTO requestDTO) {
		
		CurrencyExposure currencyExposure = currencyExposureDao.getById(currencyExposureDTO.getCurrencyExposureId());
		
		currencyExposure.setCurrencyExposureId(ControllerUtils.setValue(currencyExposure.getCurrencyExposureId(), currencyExposureDTO.getCurrencyExposureId()));
		
		currencyExposure.setExposureId(ControllerUtils.setValue(currencyExposure.getExposureId(), currencyExposureDTO.getExposureId()));
		
		currencyExposure.setCurrency(ControllerUtils.setValue(currencyExposure.getCurrency(), currencyExposureDTO.getCurrency()));
		
		currencyExposure.setExposureValue(ControllerUtils.setValue(currencyExposure.getExposureValue(), currencyExposureDTO.getExposureValue()));
		
		currencyExposure.setExposurePercent(ControllerUtils.setValue(currencyExposure.getExposurePercent(), currencyExposureDTO.getExposurePercent()));

        currencyExposure = currencyExposureDao.save(currencyExposure);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public CurrencyExposureDTO getCurrencyExposureDTOById(Integer currencyExposureId) {
	
		CurrencyExposure currencyExposure = currencyExposureDao.getById(currencyExposureId);
		
		CurrencyExposureConvertCriteriaDTO convertCriteria = new CurrencyExposureConvertCriteriaDTO();
		return(this.convertCurrencyExposureToCurrencyExposureDTO(currencyExposure,convertCriteria));
	}

}
