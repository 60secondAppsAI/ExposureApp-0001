package com.ExposureApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.ExposureApp.domain.CurrencyExposure;
import com.ExposureApp.dto.CurrencyExposureDTO;
import com.ExposureApp.dto.CurrencyExposureSearchDTO;
import com.ExposureApp.dto.CurrencyExposurePageDTO;
import com.ExposureApp.dto.CurrencyExposureConvertCriteriaDTO;
import com.ExposureApp.service.GenericService;
import com.ExposureApp.dto.common.RequestDTO;
import com.ExposureApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface CurrencyExposureService extends GenericService<CurrencyExposure, Integer> {

	List<CurrencyExposure> findAll();

	ResultDTO addCurrencyExposure(CurrencyExposureDTO currencyExposureDTO, RequestDTO requestDTO);

	ResultDTO updateCurrencyExposure(CurrencyExposureDTO currencyExposureDTO, RequestDTO requestDTO);

    Page<CurrencyExposure> getAllCurrencyExposures(Pageable pageable);

    Page<CurrencyExposure> getAllCurrencyExposures(Specification<CurrencyExposure> spec, Pageable pageable);

	ResponseEntity<CurrencyExposurePageDTO> getCurrencyExposures(CurrencyExposureSearchDTO currencyExposureSearchDTO);
	
	List<CurrencyExposureDTO> convertCurrencyExposuresToCurrencyExposureDTOs(List<CurrencyExposure> currencyExposures, CurrencyExposureConvertCriteriaDTO convertCriteria);

	CurrencyExposureDTO getCurrencyExposureDTOById(Integer currencyExposureId);



	
}
