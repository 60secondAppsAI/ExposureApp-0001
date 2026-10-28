package com.ExposureApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.ExposureApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.ExposureApp.domain.CurrencyExposure;
import com.ExposureApp.dto.CurrencyExposureDTO;
import com.ExposureApp.dto.CurrencyExposureSearchDTO;
import com.ExposureApp.dto.CurrencyExposurePageDTO;
import com.ExposureApp.service.CurrencyExposureService;
import com.ExposureApp.dto.common.RequestDTO;
import com.ExposureApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/currencyExposure")
@RestController
public class CurrencyExposureController {

	private final static Logger logger = LoggerFactory.getLogger(CurrencyExposureController.class);

	@Autowired
	CurrencyExposureService currencyExposureService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<CurrencyExposure> getAll() {

		List<CurrencyExposure> currencyExposures = currencyExposureService.findAll();
		
		return currencyExposures;	
	}

	@GetMapping(value = "/{currencyExposureId}")
	@ResponseBody
	public CurrencyExposureDTO getCurrencyExposure(@PathVariable Integer currencyExposureId) {
		
		return (currencyExposureService.getCurrencyExposureDTOById(currencyExposureId));
	}

 	@RequestMapping(value = "/addCurrencyExposure", method = RequestMethod.POST)
	public ResponseEntity<?> addCurrencyExposure(@RequestBody CurrencyExposureDTO currencyExposureDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = currencyExposureService.addCurrencyExposure(currencyExposureDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/currencyExposures")
	public ResponseEntity<CurrencyExposurePageDTO> getCurrencyExposures(CurrencyExposureSearchDTO currencyExposureSearchDTO) {
 
		return currencyExposureService.getCurrencyExposures(currencyExposureSearchDTO);
	}	

	@RequestMapping(value = "/updateCurrencyExposure", method = RequestMethod.POST)
	public ResponseEntity<?> updateCurrencyExposure(@RequestBody CurrencyExposureDTO currencyExposureDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = currencyExposureService.updateCurrencyExposure(currencyExposureDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
