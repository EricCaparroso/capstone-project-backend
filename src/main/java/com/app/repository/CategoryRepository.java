package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.PartCategory;

@Repository
public interface CategoryRepository extends JpaRepository<PartCategory, Long> {
	
	@Query("SELECT DISTINCT pc FROM PartCategory pc\r\n"
			+ "  JOIN pc.parts p\r\n"
			+ "  JOIN ScrapYardParts sp ON sp.partId = p\r\n"
			+ "  WHERE sp.car.carId = :carId\r\n"
			+ "    AND sp.reserved = false")
	List<PartCategory> findByCar(@Param("carId") Long carId);

}
