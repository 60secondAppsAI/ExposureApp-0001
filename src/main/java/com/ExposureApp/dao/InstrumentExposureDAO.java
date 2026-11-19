package com.ExposureApp.dao;

import java.util.List;
import java.util.Date;

import com.ExposureApp.dao.GenericDAO;
import com.ExposureApp.domain.InstrumentExposure;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface InstrumentExposureDAO extends GenericDAO<InstrumentExposure, Integer> {
  
	List<InstrumentExposure> findAll();
	


}

