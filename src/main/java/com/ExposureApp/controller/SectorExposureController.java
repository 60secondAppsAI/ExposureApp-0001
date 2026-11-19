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

import com.ExposureApp.domain.SectorExposure;
import com.ExposureApp.dto.SectorExposureDTO;
import com.ExposureApp.dto.SectorExposureSearchDTO;
import com.ExposureApp.dto.SectorExposurePageDTO;
import com.ExposureApp.service.SectorExposureService;
import com.ExposureApp.dto.common.RequestDTO;
import com.ExposureApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/sectorExposure")
@RestController
public class SectorExposureController {

	private final static Logger logger = LoggerFactory.getLogger(SectorExposureController.class);

	@Autowired
	SectorExposureService sectorExposureService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<SectorExposure> getAll() {

		List<SectorExposure> sectorExposures = sectorExposureService.findAll();
		
		return sectorExposures;	
	}

	@GetMapping(value = "/{sectorExposureId}")
	@ResponseBody
	public SectorExposureDTO getSectorExposure(@PathVariable Integer sectorExposureId) {
		
		return (sectorExposureService.getSectorExposureDTOById(sectorExposureId));
	}

 	@RequestMapping(value = "/addSectorExposure", method = RequestMethod.POST)
	public ResponseEntity<?> addSectorExposure(@RequestBody SectorExposureDTO sectorExposureDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = sectorExposureService.addSectorExposure(sectorExposureDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/sectorExposures")
	public ResponseEntity<SectorExposurePageDTO> getSectorExposures(SectorExposureSearchDTO sectorExposureSearchDTO) {
 
		return sectorExposureService.getSectorExposures(sectorExposureSearchDTO);
	}	

	@RequestMapping(value = "/updateSectorExposure", method = RequestMethod.POST)
	public ResponseEntity<?> updateSectorExposure(@RequestBody SectorExposureDTO sectorExposureDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = sectorExposureService.updateSectorExposure(sectorExposureDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
