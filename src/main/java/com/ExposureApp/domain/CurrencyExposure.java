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
@Table(name="currency_exposures")
@Getter @Setter @NoArgsConstructor
public class CurrencyExposure {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="currency_exposure_id")
	private Integer currencyExposureId;
    
  	@Column(name="exposure_id")
	private Integer exposureId;
    
  	@Column(name="currency")
	private String currency;
    
  	@Column(name="exposure_value")
	private Double exposureValue;
    
  	@Column(name="exposure_percent")
	private Double exposurePercent;
    

}
