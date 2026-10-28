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

import com.ExposureApp.domain.InstrumentExposure;
import com.ExposureApp.dto.InstrumentExposureDTO;
import com.ExposureApp.dto.InstrumentExposureSearchDTO;
import com.ExposureApp.dto.InstrumentExposurePageDTO;
import com.ExposureApp.service.InstrumentExposureService;
import com.ExposureApp.dto.common.RequestDTO;
import com.ExposureApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/instrumentExposure")
@RestController
public class InstrumentExposureController {

	private final static Logger logger = LoggerFactory.getLogger(InstrumentExposureController.class);

	@Autowired
	InstrumentExposureService instrumentExposureService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<InstrumentExposure> getAll() {

		List<InstrumentExposure> instrumentExposures = instrumentExposureService.findAll();
		
		return instrumentExposures;	
	}

	@GetMapping(value = "/{instrumentExposureId}")
	@ResponseBody
	public InstrumentExposureDTO getInstrumentExposure(@PathVariable Integer instrumentExposureId) {
		
		return (instrumentExposureService.getInstrumentExposureDTOById(instrumentExposureId));
	}

 	@RequestMapping(value = "/addInstrumentExposure", method = RequestMethod.POST)
	public ResponseEntity<?> addInstrumentExposure(@RequestBody InstrumentExposureDTO instrumentExposureDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = instrumentExposureService.addInstrumentExposure(instrumentExposureDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/instrumentExposures")
	public ResponseEntity<InstrumentExposurePageDTO> getInstrumentExposures(InstrumentExposureSearchDTO instrumentExposureSearchDTO) {
 
		return instrumentExposureService.getInstrumentExposures(instrumentExposureSearchDTO);
	}	

	@RequestMapping(value = "/updateInstrumentExposure", method = RequestMethod.POST)
	public ResponseEntity<?> updateInstrumentExposure(@RequestBody InstrumentExposureDTO instrumentExposureDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = instrumentExposureService.updateInstrumentExposure(instrumentExposureDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
