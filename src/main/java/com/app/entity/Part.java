package com.app.entity;

import java.util.LinkedHashSet;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "parts")
public class Part {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partId;

	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = true, length = 100)
    private String imageName;

	
    @PrePersist
    public void prePersist() {
        if (this.imageName == null || this.imageName.isEmpty()) {
            this.imageName = this.name.toLowerCase().replace(" ", "-") + ".png";
        }
    }
    
    @ManyToMany( cascade = {CascadeType.PERSIST, CascadeType.MERGE},
	mappedBy = "parts")
	@JsonManagedReference
    private Set<Car> cars;

	@OneToMany(
			mappedBy = "partId",
		cascade = CascadeType.ALL,
		orphanRemoval = true
		)
	@JsonManagedReference
    private Set<ScrapYardParts> scrapYardParts = new LinkedHashSet<>();
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	@JsonManagedReference
    private PartCategory category;
	
}
