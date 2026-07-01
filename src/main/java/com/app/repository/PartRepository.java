package com.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.Car;
import com.app.entity.Part;
import com.app.entity.ScrapYardParts;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
	@Query("""
		    SELECT DISTINCT sp.partId FROM ScrapYardParts sp
		    WHERE sp.car.carId = :carId
		      AND sp.partId.category.categoryId = :categoryId
		      AND sp.reserved = false
		""")
    List<Part> findPartsByCarIdAndCategoryId(@Param("carId") Long carId, @Param("categoryId") Long categoryId);

	Optional<Part> findByName(String partName);

}
