package com.app.entity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
//@ToString(exclude = "writers")
//@EqualsAndHashCode(exclude = "writers")
@Entity
@Table(name = "cars")
public class Car {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

	@ManyToMany(fetch = FetchType.LAZY,
			cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    		)
    @JoinTable(name = "car_parts",
		   joinColumns = @JoinColumn(name = "fk_car_id"),
		   inverseJoinColumns = @JoinColumn(name = "fk_part_id"))
    @JsonBackReference
	private List<Part> parts = new ArrayList<>();
	
	@OneToMany(
			mappedBy = "car",
		cascade = CascadeType.ALL,
		orphanRemoval = true
		)
	@JsonBackReference
	private List<ScrapYardParts> scrapYardParts = new ArrayList<>();
	
	


	@Column(nullable = false, length = 50)
	private String brand;
	
	@Column(nullable = false, length = 50)
	private String model;
	
	@Column(nullable = false, length = 50)
	private String engine;
}
