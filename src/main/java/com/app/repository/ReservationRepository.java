package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entity.Reservation;

import jakarta.transaction.Transactional;

@Repository
public interface ReservationRepository  extends JpaRepository<Reservation, Long>{
	List<Reservation> findByUserUserId(Long userId);
	
    @Modifying
    @Transactional
    @Query("DELETE FROM Reservation r WHERE r.scrapYardPart.scrapYardPartId = :scrapYardPartId")
	void deleteByScrapYardPart(Long scrapYardPartId);
    
	List<Reservation> findByScrapYardPart_ScrapYard_ScrapYardId(Long scrapYardId);

	Reservation findByScrapYardPart_ScrapYardPartId(Long scrapYardPartId);
}
