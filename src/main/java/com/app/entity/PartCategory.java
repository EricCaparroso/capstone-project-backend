package com.app.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
//@ToString(exclude = "writers")
//@EqualsAndHashCode(exclude = "writers")
@Entity
@Table(name="parts_category")
public class PartCategory {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

	@OneToMany(
				mappedBy = "category",
			cascade = CascadeType.ALL,
			orphanRemoval = true
			)
	@JsonBackReference
	private List<Part> parts = new ArrayList<>();

	@Column(nullable = false, length = 50)
	private String categoryName;

}
