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
import com.ExposureApp.dao.SectorExposureDAO;
import com.ExposureApp.domain.SectorExposure;
import com.ExposureApp.dto.SectorExposureDTO;
import com.ExposureApp.dto.SectorExposureSearchDTO;
import com.ExposureApp.dto.SectorExposurePageDTO;
import com.ExposureApp.dto.SectorExposureConvertCriteriaDTO;
import com.ExposureApp.dto.common.RequestDTO;
import com.ExposureApp.dto.common.ResultDTO;
import com.ExposureApp.service.SectorExposureService;
import com.ExposureApp.util.ControllerUtils;


@Service
public class SectorExposureServiceImpl extends GenericServiceImpl<SectorExposure, Integer> implements SectorExposureService {

    private final static Logger logger = LoggerFactory.getLogger(SectorExposureServiceImpl.class);

	@Autowired
	SectorExposureDAO sectorExposureDao;

	

	@Override
	public GenericDAO<SectorExposure, Integer> getDAO() {
		return (GenericDAO<SectorExposure, Integer>) sectorExposureDao;
	}
	
	public List<SectorExposure> findAll () {
		List<SectorExposure> sectorExposures = sectorExposureDao.findAll();
		
		return sectorExposures;	
		
	}

	public ResultDTO addSectorExposure(SectorExposureDTO sectorExposureDTO, RequestDTO requestDTO) {

		SectorExposure sectorExposure = new SectorExposure();

		sectorExposure.setSectorExposureId(sectorExposureDTO.getSectorExposureId());

		sectorExposure.setExposureId(sectorExposureDTO.getExposureId());

		sectorExposure.setSector(sectorExposureDTO.getSector());

		sectorExposure.setMarketValue(sectorExposureDTO.getMarketValue());

		sectorExposure.setExposurePercent(sectorExposureDTO.getExposurePercent());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		sectorExposure = sectorExposureDao.save(sectorExposure);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<SectorExposure> getAllSectorExposures(Pageable pageable) {
		return sectorExposureDao.findAll(pageable);
	}

	public Page<SectorExposure> getAllSectorExposures(Specification<SectorExposure> spec, Pageable pageable) {
		return sectorExposureDao.findAll(spec, pageable);
	}

	public ResponseEntity<SectorExposurePageDTO> getSectorExposures(SectorExposureSearchDTO sectorExposureSearchDTO) {
	
			Integer sectorExposureId = sectorExposureSearchDTO.getSectorExposureId(); 
 			Integer exposureId = sectorExposureSearchDTO.getExposureId(); 
 			String sector = sectorExposureSearchDTO.getSector(); 
   			String sortBy = sectorExposureSearchDTO.getSortBy();
			String sortOrder = sectorExposureSearchDTO.getSortOrder();
			String searchQuery = sectorExposureSearchDTO.getSearchQuery();
			Integer page = sectorExposureSearchDTO.getPage();
			Integer size = sectorExposureSearchDTO.getSize();

	        Specification<SectorExposure> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, sectorExposureId, "sectorExposureId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, exposureId, "exposureId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, sector, "sector"); 
			
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("sector")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<SectorExposure> sectorExposures = this.getAllSectorExposures(spec, pageable);
		
		//System.out.println(String.valueOf(sectorExposures.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(sectorExposures.getTotalPages()));
		
		List<SectorExposure> sectorExposuresList = sectorExposures.getContent();
		
		SectorExposureConvertCriteriaDTO convertCriteria = new SectorExposureConvertCriteriaDTO();
		List<SectorExposureDTO> sectorExposureDTOs = this.convertSectorExposuresToSectorExposureDTOs(sectorExposuresList,convertCriteria);
		
		SectorExposurePageDTO sectorExposurePageDTO = new SectorExposurePageDTO();
		sectorExposurePageDTO.setSectorExposures(sectorExposureDTOs);
		sectorExposurePageDTO.setTotalElements(sectorExposures.getTotalElements());
		return ResponseEntity.ok(sectorExposurePageDTO);
	}

	public List<SectorExposureDTO> convertSectorExposuresToSectorExposureDTOs(List<SectorExposure> sectorExposures, SectorExposureConvertCriteriaDTO convertCriteria) {
		
		List<SectorExposureDTO> sectorExposureDTOs = new ArrayList<SectorExposureDTO>();
		
		for (SectorExposure sectorExposure : sectorExposures) {
			sectorExposureDTOs.add(convertSectorExposureToSectorExposureDTO(sectorExposure,convertCriteria));
		}
		
		return sectorExposureDTOs;

	}
	
	public SectorExposureDTO convertSectorExposureToSectorExposureDTO(SectorExposure sectorExposure, SectorExposureConvertCriteriaDTO convertCriteria) {
		
		SectorExposureDTO sectorExposureDTO = new SectorExposureDTO();

		sectorExposureDTO.setSectorExposureId(sectorExposure.getSectorExposureId());

		sectorExposureDTO.setExposureId(sectorExposure.getExposureId());

		sectorExposureDTO.setSector(sectorExposure.getSector());

		sectorExposureDTO.setMarketValue(sectorExposure.getMarketValue());

		sectorExposureDTO.setExposurePercent(sectorExposure.getExposurePercent());
		
		return sectorExposureDTO;
	}

	public ResultDTO updateSectorExposure(SectorExposureDTO sectorExposureDTO, RequestDTO requestDTO) {
		
		SectorExposure sectorExposure = sectorExposureDao.getById(sectorExposureDTO.getSectorExposureId());
		
		sectorExposure.setSectorExposureId(ControllerUtils.setValue(sectorExposure.getSectorExposureId(), sectorExposureDTO.getSectorExposureId()));
		
		sectorExposure.setExposureId(ControllerUtils.setValue(sectorExposure.getExposureId(), sectorExposureDTO.getExposureId()));
		
		sectorExposure.setSector(ControllerUtils.setValue(sectorExposure.getSector(), sectorExposureDTO.getSector()));
		
		sectorExposure.setMarketValue(ControllerUtils.setValue(sectorExposure.getMarketValue(), sectorExposureDTO.getMarketValue()));
		
		sectorExposure.setExposurePercent(ControllerUtils.setValue(sectorExposure.getExposurePercent(), sectorExposureDTO.getExposurePercent()));

        sectorExposure = sectorExposureDao.save(sectorExposure);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public SectorExposureDTO getSectorExposureDTOById(Integer sectorExposureId) {
	
		SectorExposure sectorExposure = sectorExposureDao.getById(sectorExposureId);
		
		SectorExposureConvertCriteriaDTO convertCriteria = new SectorExposureConvertCriteriaDTO();
		return(this.convertSectorExposureToSectorExposureDTO(sectorExposure,convertCriteria));
	}

}
