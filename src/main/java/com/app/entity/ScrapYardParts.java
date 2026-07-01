package com.app.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrapYardParts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long scrapYardPartId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="part_id")
	private Part partId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="scrap_yard_id")
	private ScrapYard scrapYard;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "car_id", nullable = false)
	@JsonManagedReference
	private Car car;
	
    @OneToOne(mappedBy = "scrapYardPart", cascade = CascadeType.REMOVE)
	private Reservation reservation;
	
	private int wearLevel;
	private float price;
	private boolean reserved;
	
	public ScrapYardParts(Part partId, ScrapYard scrapYardId, int wearLevel, float price, boolean reserved) {
		super();
		this.partId = partId;
		this.scrapYard = scrapYardId;
		this.wearLevel = wearLevel;
		this.price = price;
		this.reserved = reserved;
	}



	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	
	public String getcategory() {
		return partId.getCategory().getCategoryName();
	}


	public void setPartId(Part partId) {
		this.partId = partId;
	}

	public String getScrapYardName() {
		return scrapYard.getName();
	}
	
	public String getScrapYardLocation() {
		return scrapYard.getLocation();
	}

	public void setScrapYardId(ScrapYard scrapYardId) {
		this.scrapYard = scrapYardId;
	}

	public int getWearLevel() {
		return wearLevel;
	}

	public void setWearLevel(int wearLevel) {
		this.wearLevel = wearLevel;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public String getPartName() {
	    return partId.getName();
	}
	
	public Long getScrapYardId() {
	    return scrapYard.getScrapYardId();
	}
	
}
