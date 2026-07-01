package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dtos.partdto.PartResponseDto;
import com.app.entity.Part;
import com.app.mappers.part.PartMapper;
import com.app.repository.PartRepository;

/**
 * Service implementation for managing part-related operations.
 * Provides business logic to retrieve parts by car and category or fetch all parts in the system.
 */
@Service
public class PartServiceImpl implements PartService {
	
	@Autowired PartRepository partRepository;
	@Autowired PartMapper partMapper;

    /**
     * Retrieves all parts that belong to a specific car and part category.
     *
     * @param carId      the ID of the car
     * @param categoryId the ID of the part category
     * @return List of PartResponseDto representing the matching parts
     */
	@Override
	public List<PartResponseDto> getAllPartsByCar(Long carId, Long categoryId) {
		List<Part> parts =  partRepository.findPartsByCarIdAndCategoryId(carId, categoryId);
		return parts.stream()
								.map(partMapper::toResponse)
								.collect(Collectors.toList());
	}
	
    /**
     * Retrieves all parts available in the system.
     *
     * @return List of PartResponseDto representing all parts
     */
	@Override
	public List<PartResponseDto> getAllParts() {
		List<Part> parts =  partRepository.findAll();
		return parts.stream()
								.map(partMapper::toResponse)
								.collect(Collectors.toList());
	}
}
