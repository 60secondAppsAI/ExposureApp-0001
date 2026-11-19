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

import com.ExposureApp.domain.Exposure;
import com.ExposureApp.dto.ExposureDTO;
import com.ExposureApp.dto.ExposureSearchDTO;
import com.ExposureApp.dto.ExposurePageDTO;
import com.ExposureApp.service.ExposureService;
import com.ExposureApp.dto.common.RequestDTO;
import com.ExposureApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/exposure")
@RestController
public class ExposureController {

	private final static Logger logger = LoggerFactory.getLogger(ExposureController.class);

	@Autowired
	ExposureService exposureService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Exposure> getAll() {

		List<Exposure> exposures = exposureService.findAll();
		
		return exposures;	
	}

	@GetMapping(value = "/{exposureId}")
	@ResponseBody
	public ExposureDTO getExposure(@PathVariable Integer exposureId) {
		
		return (exposureService.getExposureDTOById(exposureId));
	}

 	@RequestMapping(value = "/addExposure", method = RequestMethod.POST)
	public ResponseEntity<?> addExposure(@RequestBody ExposureDTO exposureDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = exposureService.addExposure(exposureDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/exposures")
	public ResponseEntity<ExposurePageDTO> getExposures(ExposureSearchDTO exposureSearchDTO) {
 
		return exposureService.getExposures(exposureSearchDTO);
	}	

	@RequestMapping(value = "/updateExposure", method = RequestMethod.POST)
	public ResponseEntity<?> updateExposure(@RequestBody ExposureDTO exposureDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = exposureService.updateExposure(exposureDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
