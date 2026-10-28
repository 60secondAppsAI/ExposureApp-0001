package com.ExposureApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="instrument_exposures")
@Getter @Setter @NoArgsConstructor
public class InstrumentExposure {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="instrument_exposure_id")
	private Integer instrumentExposureId;
    
  	@Column(name="exposure_id")
	private Integer exposureId;
    
  	@Column(name="instrument_id")
	private Integer instrumentId;
    
  	@Column(name="market_value")
	private Double marketValue;
    
  	@Column(name="exposure_percent")
	private Double exposurePercent;
    

}
